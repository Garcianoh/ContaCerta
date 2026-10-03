package minha.contaCerta.model;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minha.contaCerta.enun.RecuperacaoSenhaStatusEnun;


@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
@Table (name = "recuperacao_senha")
public class RecuperacaoSenha {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;
    
    @Column (name = "codigo", nullable = false)
    private String codigo;

    @Column (name = "data_expiracao", nullable = false)
    private Instant dataExpiracao;

    @Column (name = "data_criacao", nullable = false)
    @CreationTimestamp 
    private Instant dataCriacao;

    @Enumerated (EnumType.STRING)
    @Column (name = "status", nullable = false)
    private  RecuperacaoSenhaStatusEnun status;

    @ManyToOne 
    @JoinColumn (name = "user_id", nullable = false)
    private User user;
}
