package minha.contaCerta.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import minha.contaCerta.dto.RedefinirSenhaRequest;
import minha.contaCerta.dto.SolicitarRedefinicaoSenhaRequest;
import minha.contaCerta.dto.ValidarCodigoRequest;
import minha.contaCerta.dto.ValidarCodigoResponse;
import minha.contaCerta.enun.RecuperacaoSenhaStatusEnun;
import minha.contaCerta.exception.BusinessException;
import minha.contaCerta.model.RecuperacaoSenha;
import minha.contaCerta.model.RefreshToken;
import minha.contaCerta.model.User;
import minha.contaCerta.repository.RecuperarSenhaRepository;
import minha.contaCerta.repository.RefreshTokenRepository;
import minha.contaCerta.repository.UserRepository;

//Recuperação de senha: solicitação do código
// e confirmação com redefinição da password.
@Service 
public class RecuperarSenhaService {

    private final RecuperarSenhaRepository recuperarSenhaRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public RecuperarSenhaService (
        RecuperarSenhaRepository recuperarSenhaRepository,
        UserRepository userRepository,
        EmailService emailService,
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        RefreshTokenRepository refreshTokenRepository
    ) {
        this.recuperarSenhaRepository = recuperarSenhaRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    private  static final Long TEMPO_EXPIRACAO = 1000L * 60 * 10;
    
    private static final String MENSAGEM_GENERICA_SOLICITACAO =
                "Se o email estiver registrado, enviámos um código de recuperaçaõ.";
    
    private static final String MENSAGEM_SUCESSO_CONFIRMACAO = 
                "Password redefinida com sucesso.";


    @Transactional 
    public String solicitarRecuperacao(SolicitarRedefinicaoSenhaRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(
            user -> {
                String codigo = gerarCodigo(user);
                emailService.enviarCodigoRecuperacao(user.getEmail(), user.getNome(), codigo);
            }
        );

        return MENSAGEM_GENERICA_SOLICITACAO;
    }

    @Transactional
    public String confirmarRedefinicaoSenha(RedefinirSenhaRequest request) {
        if (!request.novaSenha().equals(request.confirmarSenha())) {
            throw new BusinessException("Senhas não Coinciden", HttpStatus.BAD_REQUEST);
        }

        String email = jwtService.extrairUsername(request.token());

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new BusinessException("Token inválido", HttpStatus.UNAUTHORIZED));

        if (!jwtService.eValidoToken(request.token(), user)) {
            throw new BusinessException("Token Invalido ou Expirado", HttpStatus.UNAUTHORIZED);
        }

        String novaSenhaCriptografada = passwordEncoder.encode(request.novaSenha());
        user.setSenha(novaSenhaCriptografada);
        userRepository.save(user);

        List<RefreshToken> tokensAtivos = refreshTokenRepository.findByUserAndRevogado(user, false);
        for (RefreshToken rf : tokensAtivos) {
            rf.setRevogado(true);
        }
        refreshTokenRepository.saveAll(tokensAtivos);

        return MENSAGEM_SUCESSO_CONFIRMACAO;
    }


    @Transactional 
    public ValidarCodigoResponse validarCodigo(ValidarCodigoRequest request) {
        User user = userRepository.findByEmail(request.email())
            .orElseThrow(()-> new BusinessException("Código inválido ou expirado", HttpStatus.NOT_FOUND));

            RecuperacaoSenha recuperacao = recuperarSenhaRepository.findFirstByUserOrderByDataCriacaoDesc(user)
                .orElseThrow(() -> new BusinessException("Código inválido ou expirado", HttpStatus.BAD_REQUEST));

            if (!recuperacao.getStatus().equals(RecuperacaoSenhaStatusEnun.PENDENTE)) {
                throw new BusinessException("Código inválido ou expirado",HttpStatus.BAD_REQUEST);
            }

            if (!recuperacao.getCodigo().equals(request.codigo())) {
                throw new BusinessException("Codigo Inválido", HttpStatus.BAD_REQUEST);
            }

            if (recuperacao.getDataExpiracao().isBefore(Instant.now())) {
                recuperacao.setStatus(RecuperacaoSenhaStatusEnun.EXPIRADO);
                recuperarSenhaRepository.save(recuperacao);
                throw new BusinessException("Código expirado", HttpStatus.GONE);
            }

        recuperacao.setStatus(RecuperacaoSenhaStatusEnun.USADO);
        recuperarSenhaRepository.save(recuperacao);

        String tokenTemporario = jwtService.criarToken(user, TEMPO_EXPIRACAO);
        return new ValidarCodigoResponse(tokenTemporario);
    }


    private String gerarCodigo (User user) {
        //invalidar qualquer codigo pendente anterior deste usuário
        List<RecuperacaoSenha> todosPendente = recuperarSenhaRepository.findByUserAndStatus(user, RecuperacaoSenhaStatusEnun.PENDENTE);
        for(RecuperacaoSenha r: todosPendente) {
            r.setStatus(RecuperacaoSenhaStatusEnun.EXPIRADO);
        }
        Random randon = new Random();

        String codigoGerado = String.valueOf(100000 + randon.nextInt(900000));

        RecuperacaoSenha recupe = new RecuperacaoSenha( 
            codigoGerado, 
            Instant.now().plusMillis(TEMPO_EXPIRACAO), 
            Instant.now(), 
            RecuperacaoSenhaStatusEnun.PENDENTE, 
            user
        );
        recuperarSenhaRepository.save(recupe);
        return codigoGerado;
    }
}
