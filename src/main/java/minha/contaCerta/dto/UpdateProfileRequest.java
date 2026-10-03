package minha.contaCerta.dto;

import jakarta.validation.constraints.Email;

public record UpdateProfileRequest(
    String nome,
    @Email (message = "Formato do email inválido")
    String email
) {}
