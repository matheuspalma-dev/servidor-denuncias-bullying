package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.OrgaoCompetente.dto.request.OrgaoCompetenteLoginRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orgao-competente")
public class OrgaoCompetenteController {

    private final OrgaoCompetenteService orgaoCompetenteService;

    public OrgaoCompetenteController(OrgaoCompetenteService orgaoCompetenteService) {
        this.orgaoCompetenteService = orgaoCompetenteService;
    }

    public void login(@RequestBody OrgaoCompetenteLoginRequestDTO orgaoCompetenteLoginRequestDTO){
        orgaoCompetenteService.login(orgaoCompetenteLoginRequestDTO);
    }
}
