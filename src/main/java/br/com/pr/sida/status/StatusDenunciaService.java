package br.com.pr.sida.status;

import br.com.pr.sida.auditoria.AuditoriaService;
import br.com.pr.sida.auditoria.dto.AuditoriaDTO;
import br.com.pr.sida.auditoria.enums.AcaoEnum;
import br.com.pr.sida.auditoria.enums.EntidadeAfetadaEnum;
import br.com.pr.sida.auditoria.enums.QuemRealizouEnum;
import br.com.pr.sida.denuncia.DenunciaServiceReader;
import br.com.pr.sida.security.jwt.UsuarioAutenticado;
import br.com.pr.sida.status.dto.response.StatusDenunciaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatusDenunciaService {

    private final StatusDenunciaRepository statusDenunciaRepository;
    private final DenunciaServiceReader denunciaServiceReader;
    private final StatusMapper statusMapper;
    private final AuditoriaService auditoria;

    public void atualizarStatusDenuncia(Long denunciaId, StatusDenunciaEnum status, boolean criacaoDenuncia) {
        StatusDenuncia statusDenuncia = new StatusDenuncia();
        statusDenuncia.setDataCriacao(LocalDate.now());
        statusDenuncia.setDenuncia(denunciaServiceReader.buscarDenunciaPorId(denunciaId));
        statusDenuncia.setStatusDenunciaEnum(status);

        statusDenunciaRepository.save(statusDenuncia);

        if (!criacaoDenuncia){
            UsuarioAutenticado usuarioAutenticado = (UsuarioAutenticado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Map<String, String> detalhes = new HashMap<>();
            detalhes.put("denunciaId", denunciaId.toString());
            detalhes.put("novoStatus", status.toString());
            auditoria.registrarAuditoria(
                    new AuditoriaDTO(
                            AcaoEnum.ATUALIZAR_STATUS_DENUNCIA,
                            QuemRealizouEnum.USUARIO,
                            usuarioAutenticado.usuarioId(),
                            usuarioAutenticado.lotacao(),
                            usuarioAutenticado.entidadeId(),
                            EntidadeAfetadaEnum.STATUS_DENUNCIA,
                            null,
                            null,
                            detalhes,
                            usuarioAutenticado.enderecoIp(),
                            usuarioAutenticado.userAgent()
                    )
            );
        }
    }

    public List<StatusDenunciaResponseDTO> retornarStatusDenuncia(List<StatusDenuncia> statusDenunciaList){
        List<StatusDenunciaResponseDTO> statusDenunciaResponseDTOList = new ArrayList<>();
        for (StatusDenuncia statusDenuncia : statusDenunciaList){
            statusMapper.converterStatusDenunciaEmDTO(statusDenuncia);
        }
        return statusDenunciaResponseDTOList;
    }

}
