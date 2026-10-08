package minha.contaCerta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RedefinirSenhaRequest(
    String token,
    
    @NotBlank (message = "A senha é obrigatória")
    @Size (min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @Pattern (
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*()_\\-]).*$",
        message = "A senha deve conter letra maiúscula, minuscula, número e caracteres especial"
    )
    String novaSenha,

    @NotBlank (message = "A senha é obrigatória")
    String confirmarSenha
) {}
