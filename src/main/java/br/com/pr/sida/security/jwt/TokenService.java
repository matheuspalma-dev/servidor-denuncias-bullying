package br.com.pr.sida.security.jwt;

import br.com.pr.sida.acesso.denuncia.RoleDenuncia;
import br.com.pr.sida.login.dto.response.LoginResponseDTO;
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
    @Value("${sida.seguranca.jwt.denuncia.expiration}")
    private Long tempoExpiracaoAcessoDenuncia;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarTokenUsuario(LoginResponseDTO loginResponseDTO, ROLE role){
        return Jwts.builder()
                .subject(loginResponseDTO.getEmail())
                .claim("role", role.name())
                .issuedAt(new java.util.Date())
                .expiration(new Date(System.currentTimeMillis() + tempoexpiracao))
                .signWith(getSecretKey())
                .compact();
    }

    public String gerarTokenAcessoDenuncia(Long idDenuncia, RoleDenuncia roleDenuncia){
        return Jwts.builder()
                .subject("acesso_denuncia")
                .claim("idDenuncia", idDenuncia)
                .claim("role", roleDenuncia.name())
                .issuedAt(new java.util.Date())
                .expiration(new Date(System.currentTimeMillis() + tempoExpiracaoAcessoDenuncia))
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
