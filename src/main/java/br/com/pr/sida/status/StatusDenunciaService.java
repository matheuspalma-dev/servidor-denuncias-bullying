package br.com.pr.sida.status;

import br.com.pr.sida.denuncia.DenunciaServiceReader;
import br.com.pr.sida.security.service.SecurityService;
import br.com.pr.sida.status.dto.response.StatusDenunciaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusDenunciaService {

    private final StatusDenunciaRepository statusDenunciaRepository;
    private final DenunciaServiceReader denunciaServiceReader;
    private final SecurityService securityService;
    private final StatusMapper statusMapper;

    public void atualizarStatusDenuncia(String email, Long denunciaId, StatusDenunciaEnum status) {
        boolean temPermissao = securityService.temPermissaoDeAcessoDenuncia(email, denunciaServiceReader.buscarDenunciaPorId(denunciaId));
        if (temPermissao) {
            br.com.pr.sida.status.StatusDenuncia statusDenuncia = new br.com.pr.sida.status.StatusDenuncia();
            statusDenuncia.setDataCriacao(LocalDate.now());
            statusDenuncia.setDenuncia(denunciaServiceReader.buscarDenunciaPorId(denunciaId));
            statusDenuncia.setStatusDenunciaEnum(status);

            statusDenunciaRepository.save(statusDenuncia);
        }
    }

    public void adicionarStatusDenuncia(Long denunciaId, StatusDenunciaEnum status) {
        br.com.pr.sida.status.StatusDenuncia statusDenuncia = new br.com.pr.sida.status.StatusDenuncia();
        statusDenuncia.setDataCriacao(LocalDate.now());
        statusDenuncia.setDenuncia(denunciaServiceReader.buscarDenunciaPorId(denunciaId));
        statusDenuncia.setStatusDenunciaEnum(status);
        statusDenunciaRepository.save(statusDenuncia);
    }

    public List<StatusDenunciaResponseDTO> retornarStatusDenuncia(List<StatusDenuncia> statusDenunciaList){
        List<StatusDenunciaResponseDTO> statusDenunciaResponseDTOList = new ArrayList<>();
        for (StatusDenuncia statusDenuncia : statusDenunciaList){
            statusMapper.converterStatusDenunciaEmDTO(statusDenuncia);
        }
        return statusDenunciaResponseDTOList;
    }

}
