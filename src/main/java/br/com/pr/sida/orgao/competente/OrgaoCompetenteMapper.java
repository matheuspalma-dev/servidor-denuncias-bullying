package br.com.pr.sida.orgao.competente;

import br.com.pr.sida.municipio.Municipio;
import br.com.pr.sida.orgao.competente.dto.request.OrgaoCompetenteRegisterDTO;
import br.com.pr.sida.security.tirar.xss.TirarXssService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrgaoCompetenteMapper {

    private final TirarXssService tirarXssService;

    public OrgaoCompetente converterDTOEmEntity(OrgaoCompetenteRegisterDTO orgaoCompetenteRegisterDTO, Municipio municipio){
        OrgaoCompetente orgaoCompetente = new OrgaoCompetente();
        orgaoCompetente.setNome(tirarXss(orgaoCompetenteRegisterDTO.nome()));
        orgaoCompetente.setTipoOrgaoCompetente(orgaoCompetenteRegisterDTO.tipoOrgaoCompetente());
        orgaoCompetente.setNumero(tirarXss(orgaoCompetenteRegisterDTO.numero()));
        orgaoCompetente.setEmail(orgaoCompetenteRegisterDTO.email());
        orgaoCompetente.setMunicipio(municipio);

        return orgaoCompetente;
    }

    private String tirarXss(String entrada) {
        return tirarXssService.tirarXss(entrada);
    }
}
