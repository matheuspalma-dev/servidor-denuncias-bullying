package br.com.pr.sida.security.jwt;

import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
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
public class FiltroAutentificacaoJWT extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);

        if (token != null) {
            Claims claims = tokenService.pegarClaims(token);
            if (claims != null) {
                if ("acesso_denuncia".equals(claims.get("type", String.class))) {
                    String idDenuncia = claims.getSubject();
                    String roleDenuncia = claims.get("role", String.class);

                    SimpleGrantedAuthority permissao = new SimpleGrantedAuthority("ROLE_" + roleDenuncia);

                    UsernamePasswordAuthenticationToken autentificacao = new UsernamePasswordAuthenticationToken(Long.valueOf(idDenuncia), null, List.of(permissao));

                    SecurityContextHolder.getContext().setAuthentication(autentificacao);
                } else if ("acesso_solicitar".equals(claims.get("type", String.class))){
                    String email = claims.getSubject();
                    String role = claims.get("role", String.class);

                    SimpleGrantedAuthority permissao = new SimpleGrantedAuthority("ROLE_" + role);

                    UsernamePasswordAuthenticationToken autentificacao = new UsernamePasswordAuthenticationToken(email, null, List.of(permissao));

                    SecurityContextHolder.getContext().setAuthentication(autentificacao);
                } else if ("acesso_usuario".equals(claims.get("type", String.class))){
                    String email = claims.getSubject();
                    String role = claims.get("role", String.class);
                    Long entidadeId = claims.get("entidadeId", Long.class);

                    String enderoIp = buscarEnderecoIp(request);

                    String userAgent = buscarUserAgent(request);

                    UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado(email, entidadeId, UsuarioLotacaoEnum.valueOf(role), enderoIp, userAgent);

                    SimpleGrantedAuthority permissao = new SimpleGrantedAuthority("ROLE_" + role);

                    UsernamePasswordAuthenticationToken autentificacao = new UsernamePasswordAuthenticationToken(usuarioAutenticado, null, List.of(permissao));

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

    private String buscarEnderecoIp(HttpServletRequest request){
        String enderoIp = request.getHeader("X-Forwarded-For");
        if (enderoIp == null || enderoIp.isEmpty()) {
            enderoIp = request.getRemoteAddr();
        }

        return enderoIp;
    }

    private String buscarUserAgent(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");

        String os = descobrirSistemaOperacional(userAgent.toLowerCase());
        String navegador = descobrirNavegador(userAgent.toLowerCase());
        return os + " - " + navegador;
    }

    private String descobrirSistemaOperacional(String userAgent) {
        if (userAgent.contains("windows"))       return "Windows";
        else if (userAgent.contains("mac os"))   return "macOS";
        else if (userAgent.contains("android"))  return "Android";
        else if (userAgent.contains("iphone") || userAgent.contains("ipad")) return "iOS";
        else if (userAgent.contains("linux"))    return "Linux";
        else return "Desconhecido";
    }

    private String descobrirNavegador(String userAgent) {
        if (userAgent.contains("edg/") || userAgent.contains("edge/"))  return "Edge";
        if (userAgent.contains("opr/") || userAgent.contains("opera"))  return "Opera";
        if (userAgent.contains("chrome"))  return "Chrome";
        if (userAgent.contains("firefox"))  return "Firefox";
        if (userAgent.contains("safari"))  return "Safari";
        return "Desconhecido";
    }
}
