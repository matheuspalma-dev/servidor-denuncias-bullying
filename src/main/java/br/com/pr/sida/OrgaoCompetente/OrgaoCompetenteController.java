package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.OrgaoCompetente.dto.request.OrgaoCompetenteRegisterDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orgao-competente")
@RequiredArgsConstructor
public class OrgaoCompetenteController {

    private final OrgaoCompetenteService orgaoCompetenteService;

    @PostMapping("/registrar")
    public void registrarOrgaoCompetente(@RequestBody @Valid OrgaoCompetenteRegisterDTO orgaoCompetenteRegisterDTO) {
        orgaoCompetenteService.registrarOrgaoCompetente(orgaoCompetenteRegisterDTO);
    }

}
