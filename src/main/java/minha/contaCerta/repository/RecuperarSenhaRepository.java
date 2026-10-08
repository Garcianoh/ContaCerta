package minha.contaCerta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import minha.contaCerta.enun.RecuperacaoSenhaStatusEnun;
import minha.contaCerta.model.RecuperacaoSenha;
import minha.contaCerta.model.User;

public interface RecuperarSenhaRepository extends JpaRepository<RecuperacaoSenha, Long> {
    List<RecuperacaoSenha> findByUserAndStatus(User user, RecuperacaoSenhaStatusEnun status);
    Optional<RecuperacaoSenha> findFirstByUserOrderByDataCriacaoDesc(User user);
}

