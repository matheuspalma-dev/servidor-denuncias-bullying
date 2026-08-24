package br.com.pr.sida.jwt;

import br.com.pr.sida.util.loginDTOS.LoginResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    @Value("${sida.seguranca.jwt.secret}")
    private String secretKey;
    @Value("${sida.seguranca.jwt.expiration}")
    private Long tempoexpiracao;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(LoginResponseDTO loginResponseDTO){
        return Jwts.builder()
                .subject(loginResponseDTO.getEmail())
                .claim("id", loginResponseDTO.getId())
                .claim("role", loginResponseDTO.getPermissao().name())
                .issuedAt(new java.util.Date())
                .expiration(new Date(System.currentTimeMillis() + tempoexpiracao))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims pegarClaims(String token) {
        try{
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e){
            return null;
        }
    }
}
