package br.com.pr.sida.denuncia.dto.request;

import br.com.pr.sida.denuncia.como.afetou.ComoTeAfetou;
import br.com.pr.sida.denuncia.enums.*;
import br.com.pr.sida.denuncia.pratica.acao.QuemPratica;
import br.com.pr.sida.security.tirar.xss.TirarXss;
import br.com.pr.sida.denuncia.situacao.denuncia.SituacaoDenunciada;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DenunciaRequestDTO(
        @NotNull
        Long idEscola,
        @NotNull
        Afetados afetados,
        @NotBlank
        @TirarXss
        String oqueAconteceu,
        @NotNull
        boolean estaEmPerigo,
        @NotNull
        FrequenciaOcorre frequenciaOcorre,
        QuandoOcorreu quandoOcorreu,
        Boolean continuaAcontecendo,
        @TirarXss
        String detalhesAgressores,
        @NotNull
        Testemunha possuiTestemunha,
        @TirarXss
        String detalhesTestemunha,
        @NotNull
        RelatadoParaOResponsavel relatadoParaOResponsavel,
        ResultadoRelato resultadoRelato,
        @NotNull
        boolean senteSeguroNaEscola,
        @TirarXss
        String pedidoOuInformacaoExtra,
        List<OndeOcorreu> ondeOcorreuList,
        List<ComoTeAfetou> comoTeAfetouList,
        List<QuemPratica> quemPratica,
        List<SituacaoDenunciada> situacaoDenunciadas
) {
}
