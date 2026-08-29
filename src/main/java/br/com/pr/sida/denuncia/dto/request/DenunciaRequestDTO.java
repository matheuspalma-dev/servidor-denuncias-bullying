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
        boolean continuaAcontecendo,
        @NotBlank
        String detalhesAgressores,
        @NotNull
        Testemunha possuiTestemunha,
        String detalhesTestemunha,
        @NotNull
        RelatadoParaOResponsavel relatadoParaOResponsavel,
        ResultadoRelato resultadoRelato,
        @NotNull
        boolean senteSeguroNaEscola,
        @NotBlank
        String pedidoOuInformacaoExtra,
        @NotNull
        List<OndeOcorreu> ondeOcorreuList,
        @NotNull
        List<ComoTeAfetou> comoTeAfetouList,
        @NotNull
        List<QuemPratica> quemPratica,
        @NotNull
        List<SituacaoDenunciada> situacaoDenunciadas
) {
}
