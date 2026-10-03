package minha.contaCerta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record LoginRequest(
    @Email 
    @NotBlank (message = "O email é obrigatório")
    String email,

    @NotBlank (message = "A senha é obrigatória")
    String senha
) {}
