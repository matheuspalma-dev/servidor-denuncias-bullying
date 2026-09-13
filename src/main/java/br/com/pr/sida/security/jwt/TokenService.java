package br.com.pr.sida.security.jwt;

import br.com.pr.sida.acesso.denuncia.RoleDenuncia;
import br.com.pr.sida.usuarios.dto.response.UsuarioLoginResponseDTO;
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

    public String gerarTokenUsuario(UsuarioLoginResponseDTO loginResponseDTO){
        return Jwts.builder()
                .subject(loginResponseDTO.getEmail())
                .claim("role", ROLE.SOLICITAR_PERMISSAO_ACESSO)
                .claim("type", "acesso_solicitar")
                .issuedAt(new java.util.Date())
                .expiration(new Date(System.currentTimeMillis() + tempoexpiracao))
                .signWith(getSecretKey())
                .compact();
    }

    public String gerarTokenDeAcessoUsuario(String email, ROLE role, Long entidadeId){
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .claim("type", "acesso_usuario")
                .claim("entidadeId", entidadeId)
                .issuedAt(new java.util.Date())
                .expiration(new Date(System.currentTimeMillis() + tempoexpiracao))
                .signWith(getSecretKey())
                .compact();
    }

    public String gerarTokenAcessoDenuncia(Long idDenuncia, RoleDenuncia roleDenuncia){
        return Jwts.builder()
                .subject(String.valueOf(idDenuncia))
                .claim("type", "acesso_denuncia")
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
