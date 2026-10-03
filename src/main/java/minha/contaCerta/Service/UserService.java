package minha.contaCerta.Service;


import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import minha.contaCerta.dto.UserPerfilResponse;
import minha.contaCerta.dto.UpdateProfileRequest;
import minha.contaCerta.dto.UserCreateRequest;
import minha.contaCerta.dto.UserCreateResponse;
import minha.contaCerta.exception.BusinessException;
import minha.contaCerta.repository.UserRepository;
import minha.contaCerta.model.User;

@Service 
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService (UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserCreateResponse cadastrar(UserCreateRequest request) {
        if(this.userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email já cadastrado!", HttpStatus.CONFLICT);
        }

        String senhaCriptografada = passwordEncoder.encode(request.senha());
        User user = new User();
                user.setNome(request.nome());
                user.setEmail(request.email());
                user.setSenha(senhaCriptografada);

        User salvo = userRepository.save(user);

        String accessToken = jwtService.gerarAccessToken(salvo);
        String refreshToken = jwtService.gerarRefreshToken(salvo);

        return new UserCreateResponse(
            salvo.getId(), 
            salvo.getNome(), 
            salvo.getEmail(), 
            accessToken, 
            refreshToken
        );
    }

    public UserPerfilResponse visualizarMeuPerfil (String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new BusinessException("Usuário não encontrado", HttpStatus.NOT_FOUND));

        return new UserPerfilResponse(
            user.getId(),
            user.getNome(),
            user.getEmail(),
            user.getDataCriacao()
        );
    }

    public UserPerfilResponse editarPerfil(String emailAtuall, UpdateProfileRequest request){
        User user = userRepository.findByEmail(emailAtuall)
            .orElseThrow(() -> new BusinessException("Usuário não encontrado", HttpStatus.NOT_FOUND));

        if (request.nome() != null && !request.email().isBlank()) {
            user.setNome(request.nome());
        }

        if (request.email() != null && !request.email().isBlank() && !request.email().equals(emailAtuall)) {
            if (userRepository.existsByEmail(request.email())) {
                throw new BusinessException("Email já está em uso por outra conta", HttpStatus.CONFLICT);
            }
            user.setEmail(request.email());
        }

        User atualizado = userRepository.save(user);

        return new UserPerfilResponse(
            atualizado.getId(),
            atualizado.getNome(),
            atualizado.getEmail(),
            atualizado.getDataCriacao()
        );
    }
}

