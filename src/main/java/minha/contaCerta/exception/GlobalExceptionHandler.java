package minha.contaCerta.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    //Erros de regras de negocio (email ja cadastrado, saldo pendente, grupos arquivados, ...)
    @ExceptionHandler (BusinessException.class)
    public ResponseEntity<Object> tratarBusinessException(BusinessException ex) {
        return construirResposta(ex.getStatus(), ex.getMessage());
    }

    //Credencias invalidas no login
    @ExceptionHandler ({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<Object> tratarCredenciasInvalidas(Exception ex) {
        return construirResposta(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
    }

    //tratar erros de validação do Bean Validation (@NotBlank, @Email, @Size, ...)
    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<Object> tratarErrosValidacao (MethodArgumentNotValidException ex) {
        Map<String, String> erros = new LinkedHashMap<>();

        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            erros.put(erro.getField(), erro.getDefaultMessage());
        }

        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", Instant.now());
        corpo.put("status", HttpStatus.BAD_REQUEST.value());
        corpo.put("erros", erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    @ExceptionHandler (Exception.class)
    public ResponseEntity<Object> tratarErroGenerico(Exception ex) {
        return construirResposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
    }

    private ResponseEntity<Object> construirResposta (HttpStatus status, String mensagem) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", Instant.now());
        corpo.put("status", status.value());
        corpo.put("mensagem", mensagem);

        return ResponseEntity.status(status).body(corpo);
    }
}
