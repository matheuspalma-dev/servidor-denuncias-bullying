package br.com.pr.sida.acesso.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.request.AcessoDenunciaRequestDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResumoResponseDTO;
import br.com.pr.sida.security.jwt.TokenService;
import br.com.pr.sida.security.service.RequerPermissao;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acesso/denuncia")
@RequiredArgsConstructor
public class AcessoDenunciaController {

    private final AcessoDenunciaService acessoDenunciaService;
    private final TokenService tokenService;

    @PostMapping("/acessar")
    public ResponseEntity<DenunciaResponseDTO> acessarDenuncia(
            @RequestBody @Valid AcessoDenunciaRequestDTO acessoDenunciaRequestDTO,
            HttpServletResponse response
    )
    {
        DenunciaResponseDTO denuncia = acessoDenunciaService.acessarDenuncia(acessoDenunciaRequestDTO);
        String token = tokenService.gerarTokenAcessoDenuncia(denuncia.getId(), RoleDenuncia.DENUNCIANTE);

        ResponseCookie cookie = ResponseCookie.from("tokenAcesso", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(1800) // 30 minutos
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().body(denuncia);
    }

    @GetMapping("/acessar/{denunciaId}")
    @PreAuthorize("hasAnyRole('ORGAO_COMPETENTE', 'REDE_ENSINO')")
    @RequerPermissao
    public ResponseEntity<DenunciaResponseDTO> acessarDenunciaResponsavel(@PathVariable Long denunciaId){
        DenunciaResponseDTO denuncia = acessoDenunciaService.acessoDenuncia(denunciaId);
        return ResponseEntity.ok().body(denuncia);
    }

    @GetMapping("/escola/{escolaId}/denuncias")
    @PreAuthorize("hasAnyRole('ORGAO_COMPETENTE', 'REDE_ENSINO')")
    @RequerPermissao
    public ResponseEntity<List<DenunciaResumoResponseDTO>> acessarDenunciasEscola(@PathVariable Long escolaId){
        List<DenunciaResumoResponseDTO> denuncias = acessoDenunciaService.acessarDenunciasEscola(escolaId);
        return ResponseEntity.ok().body(denuncias);
    }

    @GetMapping("/orgaoCompetente/{orgaoCompetenteId}/denuncias")
    @PreAuthorize("hasAnyRole('ORGAO_COMPETENTE', 'REDE_ENSINO')")
    @RequerPermissao
    public ResponseEntity<List<DenunciaResumoResponseDTO>> acessarDenunciasOrgaoCompetente(@PathVariable Long orgaoCompetenteId){
        List<DenunciaResumoResponseDTO> denuncias = acessoDenunciaService.acessarDenunciasOrgaoCompetente(orgaoCompetenteId);
        return ResponseEntity.ok().body(denuncias);
    }

}
