package minha.contaCerta.model;

import java.time.Instant;

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
import minha.contaCerta.enun.ConviteStatusEnun;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table (name = "convite")
public class Convite {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;

    @Column (name = "codigo", nullable = false)
    private String codigo;

    @Column (name = "data_criacao", nullable = false)
    private Instant dataCriacao;

    @Enumerated (EnumType.STRING)
    @Column (name = "status", nullable = false)
    private ConviteStatusEnun status;

    @ManyToOne 
    @JoinColumn (name = "grupo_id", nullable = false)
    private Grupo grupo_id; 
}
