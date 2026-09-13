package br.com.pr.sida.usuarios.lotacao;

import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaServiceReader;
import br.com.pr.sida.usuarios.exception.InformacoesIncorretasException;
import br.com.pr.sida.orgao.competente.OrgaoCompetente;
import br.com.pr.sida.orgao.competente.OrgaoCompetenteServiceReader;
import br.com.pr.sida.usuarios.Usuario;
import br.com.pr.sida.usuarios.UsuarioServiceReader;
import br.com.pr.sida.usuarios.lotacao.dto.request.UsuarioLotacaoRequestDTO;
import br.com.pr.sida.usuarios.lotacao.dto.response.UsuarioLotacaoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioLotacaoService {

    private final UsuarioLotacaoRepository usuarioLotacaoRepository;
    private final UsuarioLotacaoMapper usuarioLotacaoMapper;
    private final EscolaServiceReader escolaServiceReader;
    private final OrgaoCompetenteServiceReader orgaoCompetenteServiceReader;
    private final UsuarioServiceReader usuarioServiceReader;
    private final UsuarioLotacaoServiceReader usuarioLotacaoServiceReader;

    public void adicionarLotacao(UsuarioLotacaoRequestDTO usuarioLotacaoRequestDTO){
        boolean existeEntidade = usuarioLotacaoRequestDTO.usuarioLotacaoEnum() == UsuarioLotacaoEnum.REDE_ENSINO? verificarSeEscolaExistePorId(usuarioLotacaoRequestDTO.entidadeId()) : verificarSeOrgaoCompetenteExistePorId(usuarioLotacaoRequestDTO.entidadeId());

        if (!existeEntidade){
            throw new InformacoesIncorretasException("Entidade não encontrada");
        }

        Usuario usuario = usuarioServiceReader.buscarUsuarioPorId(usuarioLotacaoRequestDTO.usuarioId());

        UsuarioLotacao usuarioLotacao = usuarioLotacaoMapper.converterDTOEmEntity(usuarioLotacaoRequestDTO, usuario);

        usuarioLotacaoRepository.save(usuarioLotacao);

    }

    private boolean verificarSeEscolaExistePorId(Long idEscola){
        return escolaServiceReader.verificarSeEscolaExistePorId(idEscola);
    }

    private boolean verificarSeOrgaoCompetenteExistePorId(Long idOrgaoCompetente){
        return orgaoCompetenteServiceReader.verificarSeOrgaoCompetenteExistePorId(idOrgaoCompetente);
    }

    public List<UsuarioLotacaoResponseDTO> buscarLotacoesPorUsuario(Long idUsuario){
        List<UsuarioLotacao> lotacoes = usuarioLotacaoServiceReader.buscarLotacoesPorUsuario(idUsuario);
        if (lotacoes.isEmpty()){
            throw new InformacoesIncorretasException("Nenhuma lotação encontrada para o usuário");
        }

        List<UsuarioLotacaoResponseDTO> lotacoesResponseDTO = new ArrayList<>();

        for (UsuarioLotacao usuarioLotacao : lotacoes){
            if (usuarioLotacao.getLotacao() == UsuarioLotacaoEnum.REDE_ENSINO){
                Escola escola = escolaServiceReader.buscarEscolaPorId(usuarioLotacao.getEntidadeId());
                lotacoesResponseDTO.add(usuarioLotacaoMapper.converterEntityEmDTO(usuarioLotacao, escola.getNome(), escola.getEmail()));
            } else if (usuarioLotacao.getLotacao() == UsuarioLotacaoEnum.ORGAO_COMPETENTE){
                OrgaoCompetente orgaoCompetente = orgaoCompetenteServiceReader.buscarPorId(usuarioLotacao.getId());
                lotacoesResponseDTO.add(usuarioLotacaoMapper.converterEntityEmDTO(usuarioLotacao, orgaoCompetente.getNome(), orgaoCompetente.getEmail()));
            }
        }

        return lotacoesResponseDTO;
    }
}
