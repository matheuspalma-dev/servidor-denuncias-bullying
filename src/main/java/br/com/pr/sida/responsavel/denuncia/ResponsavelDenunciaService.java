package br.com.pr.sida.responsavel.denuncia;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.escola.Escola;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsavelDenunciaService {
    private final ResponsavelDenunciaRepository responsavelDenunciaRepository;

    public void adicionarResponsavelDenuncia(Denuncia denuncia, Escola escola, List<OrgaoCompetente> orgaoCompetenteList){
        if (orgaoCompetenteList == null || orgaoCompetenteList.isEmpty()) {
            ResponsavelDenuncia responsavelDenuncia = new ResponsavelDenuncia();
            responsavelDenuncia.setDenuncia(denuncia);
            responsavelDenuncia.setEscolaResponsavelId(escola);
            responsavelDenuncia.setOrgaoCompetenteResponsavel(null);
            responsavelDenunciaRepository.save(responsavelDenuncia);
        }
        for (OrgaoCompetente orgaoCompetente : orgaoCompetenteList ) {
            ResponsavelDenuncia responsavelDenuncia = new ResponsavelDenuncia();
            responsavelDenuncia.setDenuncia(denuncia);
            responsavelDenuncia.setEscolaResponsavelId(escola);
            responsavelDenuncia.setOrgaoCompetenteResponsavel(orgaoCompetente);
            responsavelDenunciaRepository.save(responsavelDenuncia);
        }
    }
}
