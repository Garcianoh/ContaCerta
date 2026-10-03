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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minha.contaCerta.enun.GrupoStatusEnum;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table (name = "grupo")
public class Grupo {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;

    @Column (name = "nome", nullable = false)
    private String nome;

    @Enumerated (EnumType.STRING)
    @Column (name = "status", nullable = false)
    private GrupoStatusEnum status;

    @Column (name = "data_criacao", nullable = false)
    @CreationTimestamp 
    private Instant dataCriacao;
}
