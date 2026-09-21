package br.com.pr.sida.denuncia.triagem;

import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.denuncia.enums.FrequenciaOcorre;
import br.com.pr.sida.denuncia.enums.Prioridade;
import br.com.pr.sida.denuncia.enums.RelatadoParaOResponsavel;
import br.com.pr.sida.denuncia.enums.ResultadoRelato;
import br.com.pr.sida.denuncia.pratica.acao.QuemPratica;
import br.com.pr.sida.denuncia.situacao.denuncia.SituacaoDenunciada;
import br.com.pr.sida.denuncia.triagem.dto.response.TriagemResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class TriagemService {

    private static final int PONTOS_ESTA_EM_PERIGO       = 100;
    private static final int PONTOS_VIOLENCIA_FISICA      = 50;
    private static final int PONTOS_SITUACAO_PIOROU       = 40;
    private static final int PONTOS_ASSEDIO               = 30;
    private static final int PONTOS_AGRESSOR_ESCOLA       = 30;
    private static final int PONTOS_CONTINUA_ACONTECENDO  = 20;
    private static final int PONTOS_TODOS_OS_DIAS         = 20;
    private static final int PONTOS_FREQUENTEMENTE        = 15;
    private static final int PONTOS_NAO_SE_SENTE_SEGURO   = 10;
    private static final int PONTOS_NAO_MEDO              = 10;
    private static final int PONTOS_NEGLIGENCIA_ESCOLA    = 15;

    private static final int LIMIAR_URGENTE = 100;
    private static final int LIMIAR_ALTA    = 40;
    private static final int LIMIAR_MEDIA   = 20;

    public TriagemResponseDTO triar(DenunciaRequestDTO dto) {
        int pontuacao = calcularPontuacao(dto);
        Prioridade prioridade = classificarPrioridade(pontuacao);
        boolean escolaVaiTerAcesso = calcularAcessoEscola(dto);

        TriagemResponseDTO resultado = new TriagemResponseDTO();
        resultado.setPrioridade(prioridade);
        resultado.setEscolaVaiTerAcesso(escolaVaiTerAcesso);
        return resultado;
    }

    private int calcularPontuacao(DenunciaRequestDTO dto) {
        int pontuacao = 0;

        if (dto.estaEmPerigo()) {
            pontuacao += PONTOS_ESTA_EM_PERIGO;
        }

        if (contemSituacao(dto, SituacaoDenunciada.VIOLENCIA_FISICA)) {
            pontuacao += PONTOS_VIOLENCIA_FISICA;
        }

        if (dto.resultadoRelato() == ResultadoRelato.SITUACAO_PIOROU) {
            pontuacao += PONTOS_SITUACAO_PIOROU;
        }

        if (contemSituacao(dto, SituacaoDenunciada.ASSEDIO)) {
            pontuacao += PONTOS_ASSEDIO;
        }

        if (agressorEEquipeEscolar(dto)) {
            pontuacao += PONTOS_AGRESSOR_ESCOLA;
        }

        if (Boolean.TRUE.equals(dto.continuaAcontecendo())) {
            pontuacao += PONTOS_CONTINUA_ACONTECENDO;
        }

        if (dto.frequenciaOcorre() == FrequenciaOcorre.TODOS_OS_DIAS) {
            pontuacao += PONTOS_TODOS_OS_DIAS;
        } else if (dto.frequenciaOcorre() == FrequenciaOcorre.FREQUENTEMENTE) {
            pontuacao += PONTOS_FREQUENTEMENTE;
        }

        if (!dto.senteSeguroNaEscola()) {
            pontuacao += PONTOS_NAO_SE_SENTE_SEGURO;
        }

        if (dto.relatadoParaOResponsavel() == RelatadoParaOResponsavel.NAO_MEDO) {
            pontuacao += PONTOS_NAO_MEDO;
        }

        if (houveNegligenciaEscola(dto)) {
            pontuacao += PONTOS_NEGLIGENCIA_ESCOLA;
        }

        return pontuacao;
    }

    private Prioridade classificarPrioridade(int pontuacao) {
        if (pontuacao >= LIMIAR_URGENTE) return Prioridade.URGENTE;
        if (pontuacao >= LIMIAR_ALTA)    return Prioridade.ALTA;
        if (pontuacao >= LIMIAR_MEDIA)   return Prioridade.MEDIA;
        return Prioridade.NORMAL;
    }

    private boolean calcularAcessoEscola(DenunciaRequestDTO dto) {
        if (agressorEEquipeEscolar(dto)) {
            return false;
        }

        if (houveNegligenciaEscolaComNotificacao(dto)) {
            return false;
        }

        return true;
    }

    private boolean contemSituacao(DenunciaRequestDTO dto, SituacaoDenunciada situacao) {
        return dto.situacaoDenunciadas() != null
                && dto.situacaoDenunciadas().contains(situacao);
    }

    private boolean agressorEEquipeEscolar(DenunciaRequestDTO dto) {
        return dto.quemPratica() != null
                && (dto.quemPratica().contains(QuemPratica.PROFESSOR)
                    || dto.quemPratica().contains(QuemPratica.FUNCIONARIO_ESCOLA));
    }

    private boolean houveNegligenciaEscola(DenunciaRequestDTO dto) {
        if (dto.resultadoRelato() == null) return false;
        return dto.resultadoRelato() == ResultadoRelato.NINGUEM_FEZ_NADA
                || dto.resultadoRelato() == ResultadoRelato.DISSERAM_QUE_NAO_PODIAM_FAZER_NADA;
    }

    private boolean houveNegligenciaEscolaComNotificacao(DenunciaRequestDTO dto) {
        return dto.relatadoParaOResponsavel() == RelatadoParaOResponsavel.SIM_EQUIPE_ESCOLA
                && houveNegligenciaEscola(dto);
    }
}
