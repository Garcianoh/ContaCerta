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
import minha.contaCerta.enun.GrupoMembroPapel;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity  
@Table (name = "grupo_membro")
public class GrupoMembro {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;

    @Column (name = "data_entrada", nullable = false)
    private Instant dataEntrada;

    @Enumerated (EnumType.STRING)
    @Column (name = "papel", nullable = false)
    private GrupoMembroPapel papel;

    @ManyToOne 
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "grupo_id", nullable = false)
    private Grupo grupo;
}
