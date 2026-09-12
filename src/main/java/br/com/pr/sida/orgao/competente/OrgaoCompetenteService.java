package br.com.pr.sida.orgao.competente;

import br.com.pr.sida.orgao.competente.dto.request.OrgaoCompetenteRegisterDTO;
import br.com.pr.sida.security.tirar.xss.TirarXssService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrgaoCompetenteService {

    private final OrgaoCompetenteRepository orgaoCompetenteRepository;
    private final TirarXssService tirarXssService;

    public void registrarOrgaoCompetente(OrgaoCompetenteRegisterDTO orgaoCompetenteRegisterDTO){
        OrgaoCompetente orgaoCompetente = criarOrgaoCompetente(orgaoCompetenteRegisterDTO);
        orgaoCompetenteRepository.save(orgaoCompetente);
    }

    public OrgaoCompetente criarOrgaoCompetente(OrgaoCompetenteRegisterDTO orgaoCompetenteRegisterDTO){
        OrgaoCompetente orgaoCompetente = new OrgaoCompetente();
        orgaoCompetente.setNome(tirarXss(orgaoCompetenteRegisterDTO.nome()));
        orgaoCompetente.setTipoOrgaoCompetente(orgaoCompetenteRegisterDTO.tipoOrgaoCompetente());
        orgaoCompetente.setNumero(tirarXss(orgaoCompetenteRegisterDTO.numero()));
        orgaoCompetente.setEmail(orgaoCompetenteRegisterDTO.email());

        return orgaoCompetente;
    }

    private String tirarXss(String entrada) {
        return tirarXssService.tirarXss(entrada);
    }
}
