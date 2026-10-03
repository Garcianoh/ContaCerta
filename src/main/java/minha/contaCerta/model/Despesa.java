package minha.contaCerta.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table (name = "despesa")
public class Despesa {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;

    @Column (name = "descricao", nullable = false)
    private String descricao;

    @Column (name = "valor_total", nullable = false, precision = 15, scale = 3)
    private BigDecimal valorTotal;

    @Column (name = "data_despesa", nullable = false)
    private LocalDate dataDespesa;

    @Column (name = "data_criacao", nullable = false)
    private Instant dataCriacao;

    @ManyToOne 
    @JoinColumn (name = "grupo_id", nullable = false)
    private Grupo grupo;

    @ManyToOne 
    @JoinColumn (name = "pagador_id", nullable = false)
    private User user;
}
