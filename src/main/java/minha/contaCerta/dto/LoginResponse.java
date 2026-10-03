package minha.contaCerta.dto;

public record LoginResponse(
    Long id,
    String nome,
    String accessToken,
    String refreshToken
) {
}