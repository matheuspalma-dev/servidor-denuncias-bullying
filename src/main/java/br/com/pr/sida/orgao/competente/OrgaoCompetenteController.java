package br.com.pr.sida.orgao.competente;

import br.com.pr.sida.orgao.competente.dto.request.OrgaoCompetenteRegisterDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orgao-competente")
@RequiredArgsConstructor
public class OrgaoCompetenteController implements OrgaoCompetenteApi{

    private final OrgaoCompetenteService orgaoCompetenteService;

    @Override
    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarOrgaoCompetente(@RequestBody @Valid OrgaoCompetenteRegisterDTO orgaoCompetenteRegisterDTO) {
        orgaoCompetenteService.registrarOrgaoCompetente(orgaoCompetenteRegisterDTO);
    }

}
