package minha.contaCerta.Service;


import minha.contaCerta.repository.RefreshTokenRepository;
import minha.contaCerta.repository.UserRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import minha.contaCerta.dto.LoginRequest;
import minha.contaCerta.dto.LoginResponse;
import minha.contaCerta.dto.LogouRequest;
import minha.contaCerta.exception.BusinessException;
import minha.contaCerta.model.RefreshToken;
import minha.contaCerta.model.User;

@Service 
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RateLimitService rateLimitService;

    private static final int MAX_TENTATIVAS_LOGIN = 5;
    private static final Duration TEMPO_BLOQUEIO_LOGIN = Duration.ofMinutes(15);

    public AuthService(
        RefreshTokenRepository refreshTokenRepository, 
        AuthenticationManager authenticationManager, 
        UserRepository userRepository,
        JwtService jwtService,
        RateLimitService rateLimitService
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.rateLimitService = rateLimitService;
    }

    public void logout (LogouRequest request) {
        RefreshToken storedToken = refreshTokenRepository.findByToken(request.refreshToken())
            .orElseThrow(() -> new BusinessException("Refresh token não encontrado!", HttpStatus.NOT_FOUND));

        storedToken.setRevogado(true);
        refreshTokenRepository.save(storedToken);
    }

    public LoginResponse login (LoginRequest request) {
        String chave = "login:" + request.email();

        if (rateLimitService.estaBloqueado(chave, MAX_TENTATIVAS_LOGIN)) {
            throw new BusinessException("Conta temporariamente bloqueiada. Tente novamente mas tarde", HttpStatus.TOO_MANY_REQUESTS);
        }

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
            );
        } catch (BadCredentialsException e) {
            rateLimitService.registrarTentativa(chave, MAX_TENTATIVAS_LOGIN, TEMPO_BLOQUEIO_LOGIN, "Conta temporariamente bloqueiada. Tente novamente mais tarde");
            throw e;
        }

        rateLimitService.limpar(chave);

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String accessToken = jwtService.gerarAccessToken(user);
        String refreshToken = jwtService.gerarRefreshToken(user);

        salvarRefreshToken(refreshToken, user);
        return new LoginResponse(user.getId(), user.getNome(),accessToken, refreshToken);
    }

    public LoginResponse refresh(LogouRequest request) {
        RefreshToken storedToken = refreshTokenRepository.findByToken(request.refreshToken())
            .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        if (storedToken.isRevogado() || storedToken.getExpiryData().isBefore(Instant.now())) {
            throw new BusinessException("Refresh token expirado ou revogado", HttpStatus.UNAUTHORIZED);
        }

        User user = storedToken.getUser();

        String novoAccessToken = jwtService.gerarAccessToken(user);
        String novoRefreshToken = jwtService.gerarRefreshToken(user);

        //Renoga o antigo e cria um novo
        storedToken.setRevogado(true);
        refreshTokenRepository.save(storedToken);
        salvarRefreshToken(novoRefreshToken, user);

        return new LoginResponse(user.getId(), user.getNome(), novoAccessToken, novoRefreshToken);
    }


    private void salvarRefreshToken(String token, User user) {
        RefreshToken refreshToken = new RefreshToken(
            token,
            user,
            Instant.now().plus(7, ChronoUnit.DAYS)
        );
        refreshTokenRepository.save(refreshToken);
    }
}
