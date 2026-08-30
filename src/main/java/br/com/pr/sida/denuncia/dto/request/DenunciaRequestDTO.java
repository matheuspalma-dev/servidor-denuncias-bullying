package br.com.pr.sida.denuncia.dto.request;

import br.com.pr.sida.como.afetou.ComoTeAfetou;
import br.com.pr.sida.denuncia.enums.*;
import br.com.pr.sida.praticaAcao.QuemPratica;
import br.com.pr.sida.situacao.denuncia.SituacaoDenunciada;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DenunciaRequestDTO(
        @NotNull
        Long idEscola,
        @NotNull
        Afetados afetados,
        @NotBlank
        String oqueAconteceu,
        @NotNull
        boolean estaEmPerigo,
        @NotNull
        FrequenciaOcorre frequenciaOcorre,
        QuandoOcorreu quandoOcorreu,
        Boolean continuaAcontecendo,
        String detalhesAgressores,
        @NotNull
        Testemunha possuiTestemunha,
        String detalhesTestemunha,
        @NotNull
        RelatadoParaOResponsavel relatadoParaOResponsavel,
        ResultadoRelato resultadoRelato,
        @NotNull
        boolean senteSeguroNaEscola,
        String pedidoOuInformacaoExtra,
        List<OndeOcorreu> ondeOcorreuList,
        List<ComoTeAfetou> comoTeAfetouList,
        List<QuemPratica> quemPratica,
        List<SituacaoDenunciada> situacaoDenunciadas
) {
}
