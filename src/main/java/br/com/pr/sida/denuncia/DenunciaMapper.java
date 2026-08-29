package br.com.pr.sida.denuncia;

import br.com.pr.sida.como.afetou.ComoTeAfetou;
import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.denuncia.enums.OndeOcorreu;
import br.com.pr.sida.denuncia.enums.Prioridade;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import br.com.pr.sida.praticaAcao.QuemPratica;
import br.com.pr.sida.responsavel.denuncia.dto.response.ResponsavelDenunciaResponseDTO;
import br.com.pr.sida.situacao.denuncia.SituacaoDenunciada;
import br.com.pr.sida.status.dto.response.StatusDenunciaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DenunciaMapper {

    private final TextEncryptor textEncryptor;

    public DenunciaResponseDTO converterDenunciaEmDTO(
            Denuncia denuncia,
            List<ComoTeAfetou> comoTeAfetouList,
            List<QuemPratica> quemPraticaList,
            List<SituacaoDenunciada> situacaoDenunciadaList,
            List<StatusDenunciaResponseDTO> statusDenunciaResponseDTOList,
            List<MensagensDenunciaResponseDTO> mensagensDenunciaResponseDTOList,
            List<ResponsavelDenunciaResponseDTO> responsavelDenunciaResponseDTOList,
            List<OndeOcorreu> ondeOcorreuList
    ){
        DenunciaResponseDTO denunciaResponseDTO = new DenunciaResponseDTO();
        denunciaResponseDTO.setId(denuncia.getId());
        denunciaResponseDTO.setDataCriacao(denuncia.getDataCriacao());
        denunciaResponseDTO.setNomeEscola(denuncia.getEscola().getNome());
        denunciaResponseDTO.setAfetados(denuncia.getAfetados());
        denunciaResponseDTO.setOqueAconteceu(descriptografarDetalhes(denuncia.getOqueAconteceu()));
        denunciaResponseDTO.setEstaEmPerigo(denuncia.isEstaEmPerigo());
        denunciaResponseDTO.setFrequenciaOcorre(denuncia.getFrequenciaOcorre());
        denunciaResponseDTO.setQuandoOcorreu(denuncia.getQuandoOcorreu());
        denunciaResponseDTO.setContinuaAcontecendo(denuncia.isContinuaAcontecendo());
        denunciaResponseDTO.setDetalhesAgressores(descriptografarDetalhes(denuncia.getDetalhesAgressores()));
        denunciaResponseDTO.setPossuiTestemuna(denuncia.getPossuiTestemuna());
        denunciaResponseDTO.setDetalhesTestemunha(descriptografarDetalhes(denuncia.getDetalhesTestemunha()));
        denunciaResponseDTO.setRelatadoParaOResponsavel(denuncia.getRelatadoParaOResponsavel());
        denunciaResponseDTO.setResultadoRelato(denuncia.getResultadoRelato());
        denunciaResponseDTO.setSenteSeguroNaEscola(denuncia.isSenteSeguroNaEscola());
        denunciaResponseDTO.setPedidoOuInformacaoExtra(descriptografarDetalhes(denuncia.getPedidoOuInformacaoExtra()));
        denunciaResponseDTO.setPrioridade(denuncia.getPrioridade());

        denunciaResponseDTO.setCodigoAcesso(descriptografarDetalhes(denuncia.getAcesso().getCodigoAcesso()));
        denunciaResponseDTO.setOndeOcorreuList(ondeOcorreuList);
        denunciaResponseDTO.setComoTeAfetouList(comoTeAfetouList);
        denunciaResponseDTO.setQuemPraticaList(quemPraticaList);
        denunciaResponseDTO.setSituacaoDenunciadaList(situacaoDenunciadaList);
        denunciaResponseDTO.setStatusDenunciaResponseDTOList(statusDenunciaResponseDTOList);
        denunciaResponseDTO.setMensagensDenunciaResponseDTOList(mensagensDenunciaResponseDTOList);
        denunciaResponseDTO.setResponsavelDenunciaResponseDTOList(responsavelDenunciaResponseDTOList);
        return denunciaResponseDTO;
    }

    private String descriptografarDetalhes(String mensagemCriptografada){
        return textEncryptor.decrypt(mensagemCriptografada);
    }

    public Denuncia converterDTOEmDenuncia(DenunciaRequestDTO denunciaRequestDTO, Long idGerado, Escola escola, Prioridade prioridade){
        Denuncia denuncia = new Denuncia();
        denuncia.setId(idGerado);
        denuncia.setDataCriacao(LocalDate.now());
        denuncia.setEscola(escola);
        denuncia.setAfetados(denunciaRequestDTO.afetados());
        denuncia.setOqueAconteceu(criptografarDetalhes(denunciaRequestDTO.oqueAconteceu()));
        denuncia.setEstaEmPerigo(denunciaRequestDTO.estaEmPerigo());
        denuncia.setFrequenciaOcorre(denunciaRequestDTO.frequenciaOcorre());
        denuncia.setQuandoOcorreu(denunciaRequestDTO.quandoOcorreu());
        denuncia.setContinuaAcontecendo(denunciaRequestDTO.continuaAcontecendo());
        denuncia.setDetalhesAgressores(criptografarDetalhes(denunciaRequestDTO.detalhesAgressores()));
        denuncia.setPossuiTestemuna(denunciaRequestDTO.possuiTestemunha());
        denuncia.setDetalhesTestemunha(criptografarDetalhes(denunciaRequestDTO.detalhesTestemunha()));
        denuncia.setRelatadoParaOResponsavel((denunciaRequestDTO.relatadoParaOResponsavel()));
        denuncia.setResultadoRelato(denunciaRequestDTO.resultadoRelato());
        denuncia.setSenteSeguroNaEscola(denunciaRequestDTO.senteSeguroNaEscola());
        denuncia.setPedidoOuInformacaoExtra(criptografarDetalhes(denunciaRequestDTO.pedidoOuInformacaoExtra()));
        denuncia.setPrioridade(prioridade);
        return denuncia;
    }

    private String criptografarDetalhes(String detalhes){
        return textEncryptor.encrypt(detalhes);
    }


}
