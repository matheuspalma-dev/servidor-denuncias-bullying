package br.com.pr.sida.usuarios;

import br.com.pr.sida.security.jwt.ROLE;
import br.com.pr.sida.security.jwt.TokenService;
import br.com.pr.sida.security.service.RequerPermissao;
import br.com.pr.sida.usuarios.dto.request.UsuarioLoginRequestDTO;
import br.com.pr.sida.usuarios.dto.request.UsuarioResgisterRequestDTO;
import br.com.pr.sida.usuarios.dto.response.UsuarioLoginResponseDTO;
import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
import br.com.pr.sida.usuarios.lotacao.dto.request.UsuarioLotacaoRequestDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarUsuario(@RequestBody @Valid UsuarioResgisterRequestDTO usuarioResgisterRequestDTO){
        usuarioService.cadastrarUsuario(usuarioResgisterRequestDTO);
    }

    @PostMapping("/lotacao/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionarLotacao(@RequestBody @Valid UsuarioLotacaoRequestDTO usuarioLotacaoRequestDTO){
        usuarioService.adicionarLotacao(usuarioLotacaoRequestDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponseDTO> login(
            @RequestBody @Valid UsuarioLoginRequestDTO usuarioLoginRequestDTO,
            HttpServletResponse response
    )
    {
        UsuarioLoginResponseDTO usuarioLoginResponseDTO = usuarioService.login(usuarioLoginRequestDTO);

        String token;

        token = tokenService.gerarTokenUsuario(usuarioLoginResponseDTO);

        ResponseCookie cookie = ResponseCookie.from("tokenAcesso", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(3600) // 1 hora
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(usuarioLoginResponseDTO);
    }

    @GetMapping("/solicitar-acesso/{entidadeId}/{entidadeTipo}")
    @PreAuthorize("hasRole('SOLICITAR_PERMISSAO_ACESSO')")
    public ResponseEntity solicitarAcessoAoSistema(
            @PathVariable Long entidadeId,
            @PathVariable UsuarioLotacaoEnum entidadeTipo,
            HttpServletResponse response
    )
    {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token;
        if (entidadeTipo == UsuarioLotacaoEnum.REDE_ENSINO) {
            token = tokenService.gerarTokenDeAcessoUsuario(email, ROLE.REDE_ENSINO, entidadeId);
        } else {
            token = tokenService.gerarTokenDeAcessoUsuario(email, ROLE.ORGAO_COMPETENTE, entidadeId);
        }

        ResponseCookie cookie = ResponseCookie.from("tokenAcesso", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(3600) // 1 hora
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }
}
