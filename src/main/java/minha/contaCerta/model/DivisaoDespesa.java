package minha.contaCerta.model;

import java.math.BigDecimal;

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

@Setter 
@Getter 
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
@Table (name = "divisao_despesa")
public class DivisaoDespesa {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;

    @Column (name = "valor_devido", nullable = false, precision = 15, scale = 3)
    private BigDecimal valorDevido;
    
    @ManyToOne
    @JoinColumn (name = "despesa_id", nullable = false)
    private Despesa despesa;

    @ManyToOne 
    @JoinColumn (name = "user_id", nullable = false)
    private User user;
}
