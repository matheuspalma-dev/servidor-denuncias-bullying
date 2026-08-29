package br.com.pr.sida.status;

import br.com.pr.sida.status.dto.response.StatusDenunciaResponseDTO;
import org.springframework.stereotype.Component;

@Component
class StatusMapper {

    public StatusDenunciaResponseDTO converterStatusDenunciaEmDTO(StatusDenuncia statusDenuncia){
        StatusDenunciaResponseDTO statusDenunciaResponseDTO = new StatusDenunciaResponseDTO();
        statusDenunciaResponseDTO.setDataCriacao(statusDenuncia.getDataCriacao());
        statusDenunciaResponseDTO.setStatusDenunciaEnum(statusDenuncia.getStatusDenunciaEnum());
        return statusDenunciaResponseDTO;
    }
}
