package minha.contaCerta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record UserCreateRequest(

    @NotBlank 
    String nome,

    @Email (message = "Formato de email inválido!")
    @NotBlank (message = "O email é obrigatório!")
    String email,

    @NotBlank (message = "A senha é obrigatória")
    @Size (min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @Pattern (
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*()_\\-]).*$",
        message = "A senha deve conter letra maiúscula, minuscula, número e caracteres especial"
    )
    String senha
){}

