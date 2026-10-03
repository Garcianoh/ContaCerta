package minha.contaCerta.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity 
@Table (name = "refresh_token")
public class RefreshToken {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;

    @Column (name = "token", nullable = false, unique = true)
    private String token;

    @ManyToOne 
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column (name = "expiry_date", nullable = false)
    private Instant expiryData;

    @Column (nullable = false)
    private boolean revogado;

    public RefreshToken() {}

    public RefreshToken(String token, User user, Instant expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryData = expiryDate;
    }
}

