package minha.contaCerta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ValidarCodigoRequest(
    
    @NotBlank (message = "O email é obrigatorio")
    @Email (message = "Formato do email inválido")
    String email,
    @NotBlank (message = "O codigo é obrigatorio")
    String codigo
) {}