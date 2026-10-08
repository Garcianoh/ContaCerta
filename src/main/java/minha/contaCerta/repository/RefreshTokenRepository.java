package minha.contaCerta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import minha.contaCerta.model.RefreshToken;
import minha.contaCerta.model.User;

public interface RefreshTokenRepository extends JpaRepository <RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findByUserAndRevogado(User user, boolean revogado);
}
