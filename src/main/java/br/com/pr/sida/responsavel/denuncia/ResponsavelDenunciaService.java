package br.com.pr.sida.responsavel.denuncia;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.responsavel.denuncia.dto.response.ResponsavelDenunciaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsavelDenunciaService {
    private final ResponsavelDenunciaRepository responsavelDenunciaRepository;
    private final ResponsavelDenunciaMapper responsavelDenunciaMapper;

    public void adicionarResponsavelDenuncia(Denuncia denuncia, Escola escola, List<OrgaoCompetente> orgaoCompetenteList, boolean escolaVaiTerAcesso){
        if (orgaoCompetenteList == null || orgaoCompetenteList.isEmpty()) {
            ResponsavelDenuncia responsavelDenuncia = new ResponsavelDenuncia();
            responsavelDenuncia.setDenuncia(denuncia);
            responsavelDenuncia.setEscolaResponsavel(escola);
            responsavelDenuncia.setOrgaoCompetenteResponsavel(null);
            responsavelDenuncia.setEscolaVaiTerAcesso(escolaVaiTerAcesso);
            responsavelDenunciaRepository.save(responsavelDenuncia);
        } else {
            for (OrgaoCompetente orgaoCompetente : orgaoCompetenteList) {
                ResponsavelDenuncia responsavelDenuncia = new ResponsavelDenuncia();
                responsavelDenuncia.setDenuncia(denuncia);
                responsavelDenuncia.setEscolaResponsavel(escola);
                responsavelDenuncia.setOrgaoCompetenteResponsavel(orgaoCompetente);
                responsavelDenuncia.setEscolaVaiTerAcesso(escolaVaiTerAcesso);
                responsavelDenunciaRepository.save(responsavelDenuncia);
            }
        }
    }

    public List<ResponsavelDenunciaResponseDTO> retornarResponsavelDenuncia(List<ResponsavelDenuncia> responsavelDenunciaList){
        List<ResponsavelDenunciaResponseDTO> responsavelDenunciaResponseDTOList = new ArrayList<>();

        for (ResponsavelDenuncia responsavelDenuncia : responsavelDenunciaList){
            if (responsavelDenuncia.getOrgaoCompetenteResponsavel() == null){
                responsavelDenunciaResponseDTOList.add(responsavelDenunciaMapper.converterResponsavelDenunciaEscolaResponsavelEmDTO(responsavelDenuncia));
            } else {
                responsavelDenunciaResponseDTOList.add(responsavelDenunciaMapper.converterResponsavelDenunciaOrgaoResponsavelEmDTO(responsavelDenuncia));
            }
        }
        return responsavelDenunciaResponseDTOList;
    }
}
