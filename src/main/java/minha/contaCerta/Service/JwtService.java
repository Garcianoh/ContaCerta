package minha.contaCerta.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import minha.contaCerta.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service 
public class JwtService {
    
    @Value ("${jwt.secret}")
    private String secretKey;

    private static long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 15;
    private static  long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7;

    public String gerarAccessToken(UserDetails user) {
        return criarToken(user, ACCESS_TOKEN_EXPIRATION);
    }

    public String gerarRefreshToken(UserDetails user) {
        return criarToken(user, REFRESH_TOKEN_EXPIRATION);
    }

    public String criarToken(UserDetails user, long tempoExpiracao) {
        return Jwts.builder()
            .subject(user.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + tempoExpiracao))
            .signWith(getSignInKey())
            .compact();
    }

    public String extrairUsername(String token) {
        return extrairTodosClaims(token).getSubject();
    }

    public boolean eValidoToken(String token, UserDetails user) {
        String username = extrairUsername(token);
        return username.equals(user.getUsername()) && !eExpiradoToken(token);
    }

    private boolean eExpiradoToken(String token) {
        return extrairTodosClaims(token).getExpiration().before(new Date());
    }

    public  Claims extrairTodosClaims(String token) {
        return  Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
