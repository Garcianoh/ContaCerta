package minha.contaCerta.model;

import java.math.BigDecimal;
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
import minha.contaCerta.enun.TransferenciaStatusEnun;

@Setter 
@Getter 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table (name = "transferencia")
public class Transferencia {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;

    @Column (name = "valor", nullable = false, precision = 15, scale = 3)
    private BigDecimal valor;

    @Enumerated (EnumType.STRING)
    @Column (name = "status", nullable = false)
    private TransferenciaStatusEnun status;

    @Column (name = "data_confirmacao", nullable = false)
    private Instant dataConfirmacao;

    @ManyToOne 
    @JoinColumn (name = "grupo_id", nullable = false)
    private Grupo grupo;

    @ManyToOne 
    @JoinColumn (name = "credor_id", nullable = false)
    private User credor;

    @ManyToOne 
    @JoinColumn (name = "devedor_id", nullable = false)
    private User desvedor;
}
