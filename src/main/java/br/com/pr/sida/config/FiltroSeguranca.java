package br.com.pr.sida.config;

import br.com.pr.sida.jwt.TokenService;
import br.com.pr.sida.util.enums.ROLE;
import br.com.pr.sida.util.enums.RoleDenuncia;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FiltroSeguranca extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);

        if (token != null) {
            Claims claims = tokenService.pegarClaims(token);
            if (claims != null) {
                if ("acesso_denuncia".equals(claims.getSubject())) {
                    Long idDenuncia = claims.get("idDenuncia", Long.class);
                    String roleDenuncia = claims.get("role", String.class);

                    SimpleGrantedAuthority permissao = new SimpleGrantedAuthority("ROLE_" + roleDenuncia);

                    UsernamePasswordAuthenticationToken autentificacao = new UsernamePasswordAuthenticationToken(idDenuncia, null, List.of(permissao));

                    SecurityContextHolder.getContext().setAuthentication(autentificacao);
                } else {
                    String email = claims.getSubject();
                    String role = claims.get("role", String.class);

                    SimpleGrantedAuthority permissao = new SimpleGrantedAuthority("ROLE_" + role);

                    UsernamePasswordAuthenticationToken autentificacao = new UsernamePasswordAuthenticationToken(email, null, List.of(permissao));

                    SecurityContextHolder.getContext().setAuthentication(autentificacao);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request){
        if (request.getCookies() != null){
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("tokenAcesso")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
