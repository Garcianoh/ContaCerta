package minha.contaCerta.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SolicitarRedefinicaoSenhaRequest(
    @NotBlank (message = "O email é obrigatório")
    @Email (message = "Formuato do email inválido!")
    String email
) {}
