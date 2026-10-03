package minha.contaCerta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import minha.contaCerta.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository <RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
