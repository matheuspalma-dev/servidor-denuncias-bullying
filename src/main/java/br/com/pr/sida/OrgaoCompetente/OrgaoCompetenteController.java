package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.OrgaoCompetente.dto.request.OrgaoCompetenteRegisterDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.security.jwt.TokenService;
import br.com.pr.sida.util.enums.ROLE;
import br.com.pr.sida.util.loginDTOS.LoginRequestDTO;
import br.com.pr.sida.util.loginDTOS.LoginResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orgao-competente")
@RequiredArgsConstructor
public class OrgaoCompetenteController {

    private final OrgaoCompetenteService orgaoCompetenteService;

    @PostMapping("/registrar")
    public void registrarOrgaoCompetente(@RequestBody OrgaoCompetenteRegisterDTO orgaoCompetenteRegisterDTO) {
        orgaoCompetenteService.registrarOrgaoCompetente(orgaoCompetenteRegisterDTO);
    }

    @GetMapping("/{orgaoCompetenteId}/denuncias")
    @PreAuthorize("hasRole('ORGAO_COMPETENTE')")
    public ResponseEntity<List<DenunciaResponseDTO>> acessarDenuncias(@PathVariable Long orgaoCompetenteId) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<DenunciaResponseDTO> denuncias = orgaoCompetenteService.acessarDenuncias(email, orgaoCompetenteId);
        return ResponseEntity.ok(denuncias);
    }

}
