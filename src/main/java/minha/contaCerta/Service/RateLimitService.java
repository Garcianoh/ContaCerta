package minha.contaCerta.Service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import minha.contaCerta.exception.BusinessException;

@Service 
public class RateLimitService {
    
    private final StringRedisTemplate redisTemplate;

    public RateLimitService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void registrarTentativa(String chave, int maxTentativas, Duration tempoBloqueio, String mensagemErro) {
        String chaveCompleta = "rate_limit:"+ chave;
        Long tentativas = redisTemplate.opsForValue().increment(chaveCompleta);

        if (tentativas != null && tentativas == 1) {
            redisTemplate.expire(chaveCompleta, tempoBloqueio);
        }

        if (tentativas != null && tentativas > maxTentativas) {
            throw new BusinessException(mensagemErro, HttpStatus.TOO_MANY_REQUESTS);
        }
    }

    public void limpar(String chave) {
        redisTemplate.delete("rate_limit:" + chave);
    }

    public boolean estaBloqueado(String chave, int maxTentativas) {
        String chaveCompleta = "rate_limit:"+ chave;
        String valor = redisTemplate.opsForValue().get(chaveCompleta);
        
        return valor != null && Long.parseLong(valor) > maxTentativas;
    }
}
