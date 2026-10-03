package minha.contaCerta.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{
    
    private final HttpStatus status;

    public BusinessException(String mensagem, HttpStatus status) {
        super(mensagem);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
