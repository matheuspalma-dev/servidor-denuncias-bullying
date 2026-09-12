package br.com.pr.sida.usuarios.lotacao;

import br.com.pr.sida.security.tirar.xss.TirarXssService;
import br.com.pr.sida.usuarios.Usuario;
import br.com.pr.sida.usuarios.lotacao.dto.request.UsuarioLotacaoRequestDTO;
import br.com.pr.sida.usuarios.lotacao.dto.response.UsuarioLotacaoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioLotacaoMapper {

    private final TirarXssService tirarXssService;

    public UsuarioLotacao converterDTOEmEntity(UsuarioLotacaoRequestDTO usuarioLotacaoRequestDTO, Usuario usuario){
        UsuarioLotacao usuarioLotacao = new UsuarioLotacao();
        usuarioLotacao.setUsuario(usuario);
        usuarioLotacao.setCargo(tirarXss(usuarioLotacaoRequestDTO.cargo()));
        usuarioLotacao.setLotacao(usuarioLotacaoRequestDTO.usuarioLotacaoEnum());
        usuarioLotacao.setEntidadeId(usuarioLotacaoRequestDTO.entidadeId());
        usuarioLotacao.setDataInicio(usuarioLotacaoRequestDTO.dataInicio());
        usuarioLotacao.setDataFim(usuarioLotacaoRequestDTO.dataFim());
        return usuarioLotacao;
    }

    public UsuarioLotacaoResponseDTO converterEntityEmDTO(UsuarioLotacao usuarioLotacao, String nomeEntidade, String emailEntidade){
        UsuarioLotacaoResponseDTO usuarioLotacaoResponseDTO = new UsuarioLotacaoResponseDTO();
        usuarioLotacaoResponseDTO.setNomeDaEntidade(nomeEntidade);
        usuarioLotacaoResponseDTO.setEmailDaEntidade(emailEntidade);
        usuarioLotacaoResponseDTO.setLotacao(usuarioLotacao.getLotacao());
        usuarioLotacaoResponseDTO.setCargo(usuarioLotacao.getCargo());
        usuarioLotacaoResponseDTO.setDataInicio(usuarioLotacao.getDataInicio());
        usuarioLotacaoResponseDTO.setDataFim(usuarioLotacao.getDataFim());
        return usuarioLotacaoResponseDTO;
    }

    private String tirarXss(String entrada){
        return tirarXssService.tirarXss(entrada);
    }
}
