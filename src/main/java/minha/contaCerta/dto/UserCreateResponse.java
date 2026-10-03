package minha.contaCerta.dto;

public record UserCreateResponse(
    Long id,
    String nome,
    String email,
    String access_token,
    String refresh_token
    ) {
}
