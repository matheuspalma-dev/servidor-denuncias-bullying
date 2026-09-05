package br.com.pr.sida.denuncia.responsavel.denuncia;

import br.com.pr.sida.denuncia.responsavel.denuncia.dto.response.ResponsavelDenunciaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ResponsavelDenunciaMapper {

    public ResponsavelDenunciaResponseDTO converterResponsavelDenunciaOrgaoResponsavelEmDTO(ResponsavelDenuncia responsavelDenuncia) {
        ResponsavelDenunciaResponseDTO responsavelDenunciaResponseDTO = new ResponsavelDenunciaResponseDTO();
        responsavelDenunciaResponseDTO.setNomeOrgaoCompetenteResponsavel(responsavelDenuncia.getOrgaoCompetenteResponsavel().getNome());
        responsavelDenunciaResponseDTO.setEmailOrgaoCompetenteResponsavel(responsavelDenuncia.getOrgaoCompetenteResponsavel().getEmail());
        responsavelDenunciaResponseDTO.setNumeroOrgaoCompetenteResponsavel(responsavelDenuncia.getOrgaoCompetenteResponsavel().getNumero());
        return responsavelDenunciaResponseDTO;
    }

    public ResponsavelDenunciaResponseDTO converterResponsavelDenunciaEscolaResponsavelEmDTO(ResponsavelDenuncia responsavelDenuncia) {
        ResponsavelDenunciaResponseDTO responsavelDenunciaResponseDTO = new ResponsavelDenunciaResponseDTO();
        responsavelDenunciaResponseDTO.setNomeOrgaoCompetenteResponsavel(responsavelDenuncia.getEscolaResponsavel().getNome());
        responsavelDenunciaResponseDTO.setEmailOrgaoCompetenteResponsavel(responsavelDenuncia.getEscolaResponsavel().getEmail());
        return responsavelDenunciaResponseDTO;
    }
}
