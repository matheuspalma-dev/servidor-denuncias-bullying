package br.com.pr.sida.responsavel;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.escola.Escola;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsavelDenunciaService {

    private final ResponsavelDenunciaRepository responsavelDenunciaRepository;

    public void adicionarResponsavelDenuncia(Escola escola, List<OrgaoCompetente> orgaoCompetenteList){
        for (OrgaoCompetente orgaoCompetente : orgaoCompetenteList ) {
            ResponsavelDenuncia responsavelDenuncia = new ResponsavelDenuncia();
            responsavelDenuncia.setEscolaResponsavel(escola);
            responsavelDenuncia.setOrgaoCompetenteResponsavel(orgaoCompetente);
            responsavelDenunciaRepository.save(responsavelDenuncia);
        }
    }
}
