package minha.contaCerta.dto;

import jakarta.validation.constraints.NotBlank;

public record LogouRequest(
    @NotBlank (message = "O refreshToken é obrigatório")
    String refreshToken
) {}
