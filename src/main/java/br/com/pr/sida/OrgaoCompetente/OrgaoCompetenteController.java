package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.OrgaoCompetente.dto.request.OrgaoCompetenteRegisterDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.util.loginDTOS.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}/denuncias")
    public ResponseEntity<List<DenunciaResponseDTO>> acessarDenuncias(@PathVariable Long id) {
        List<DenunciaResponseDTO> denuncias = orgaoCompetenteService.acessarDenuncias(id);
        return ResponseEntity.ok(denuncias);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void login(@RequestBody LoginRequestDTO loginDTO) {
        orgaoCompetenteService.login(loginDTO);
    }
}
