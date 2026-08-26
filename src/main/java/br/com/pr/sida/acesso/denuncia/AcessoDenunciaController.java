package br.com.pr.sida.acesso.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.request.AcessoDenunciaRequestDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.jwt.TokenService;
import br.com.pr.sida.util.enums.RoleDenuncia;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acesso/denuncia")
@RequiredArgsConstructor
public class AcessoDenunciaController {

    private final AcessoDenunciaService acessoDenunciaService;
    private final TokenService tokenService;

    @PostMapping("/acessar")
    public ResponseEntity<DenunciaResponseDTO> acessarDenuncia(
            @RequestBody AcessoDenunciaRequestDTO acessoDenunciaRequestDTO,
            HttpServletResponse response
    )
    {
        DenunciaResponseDTO denuncia = acessoDenunciaService.acessarDenuncia(acessoDenunciaRequestDTO);
        String token = tokenService.gerarTokenAcessoDenuncia(denuncia.getId(), RoleDenuncia.DENUNCIANTE);

        ResponseCookie cookie = ResponseCookie.from("tokenAcesso", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(1800) // 30 minutos
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().body(denuncia);
    }
}
