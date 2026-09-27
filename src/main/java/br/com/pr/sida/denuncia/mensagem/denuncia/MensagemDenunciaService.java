package br.com.pr.sida.denuncia.mensagem.denuncia;

import br.com.pr.sida.auditoria.AuditoriaService;
import br.com.pr.sida.auditoria.dto.AuditoriaDTO;
import br.com.pr.sida.auditoria.enums.AcaoEnum;
import br.com.pr.sida.auditoria.enums.EntidadeAfetadaEnum;
import br.com.pr.sida.auditoria.enums.QuemRealizouEnum;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaServiceReader;
import br.com.pr.sida.denuncia.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.denuncia.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import br.com.pr.sida.security.jwt.UsuarioAutenticado;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MensagemDenunciaService {
    private final MensagemDenunciaRepository mensagemDenunciaRepository;
    private final DenunciaServiceReader denunciaServiceReader;
    private final MensagemDenunciaMapper mensagemDenunciaMapper;
    private final AuditoriaService auditoria;

    @Transactional
    public void salvarMensagem(
            Long idDenuncia,
            MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            AutorMensagem autorMensagem
    ){
        Denuncia denuncia = denunciaServiceReader.buscarDenunciaPorId(idDenuncia);
        MensagemDenuncia mensagemDenuncia = criarMensagemDenuncia(mensagemDenunciaRequestDTO, denuncia, autorMensagem);
        mensagemDenunciaRepository.save(mensagemDenuncia);
    }

    public void salvarMensagemResponsavel(Long idDenuncia, MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO){
        UsuarioAutenticado usuarioAutenticado = (UsuarioAutenticado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Denuncia denuncia = denunciaServiceReader.buscarDenunciaPorId(idDenuncia);
        MensagemDenuncia mensagemDenuncia = criarMensagemDenuncia(mensagemDenunciaRequestDTO, denuncia, AutorMensagem.RESPONSAVEL);
        mensagemDenunciaRepository.save(mensagemDenuncia);

        Map<String, String> detalhes = new HashMap<>();
        detalhes.put("Denuncia ID", String.valueOf(idDenuncia));
        auditoria.registrarAuditoria(
                new AuditoriaDTO(
                        AcaoEnum.ENVIAR_MENSAGEM_DENUNCIA_RESPONSAVEL,
                        QuemRealizouEnum.USUARIO,
                        usuarioAutenticado.usuarioId(),
                        usuarioAutenticado.lotacao(),
                        usuarioAutenticado.entidadeId(),
                        EntidadeAfetadaEnum.DENUNCIA,
                        null,
                        null,
                        detalhes,
                        usuarioAutenticado.enderecoIp(),
                        usuarioAutenticado.userAgent()
                )
        );

    }

    private MensagemDenuncia criarMensagemDenuncia(
            MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            Denuncia denuncia,
            AutorMensagem autorMensagem
    ) {
        return mensagemDenunciaMapper.converterDTOEmEntity(mensagemDenunciaRequestDTO, denuncia, autorMensagem);
    }

    public List<MensagensDenunciaResponseDTO> retornarMensagens(List<MensagemDenuncia> mensagemDenunciaList){
        List<MensagensDenunciaResponseDTO> mensagensDenunciaResponseDTOList = new ArrayList<>();
        for (MensagemDenuncia mensagemDenuncia : mensagemDenunciaList){
            mensagensDenunciaResponseDTOList.add(mensagemDenunciaMapper.converterEntityEmDTO(mensagemDenuncia));
        }
        return mensagensDenunciaResponseDTOList;
    }


}
