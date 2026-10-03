package minha.contaCerta.dto;

import java.time.Instant;

public record UserPerfilResponse(
    Long id,
    String nome,
    String email,
    Instant dataCriacao
) {}
