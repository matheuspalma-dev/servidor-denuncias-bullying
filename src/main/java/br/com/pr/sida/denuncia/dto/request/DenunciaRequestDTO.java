package br.com.pr.sida.denuncia.dto.request;

import br.com.pr.sida.denuncia.como.afetou.ComoTeAfetou;
import br.com.pr.sida.denuncia.enums.*;
import br.com.pr.sida.denuncia.pratica.acao.QuemPratica;
import br.com.pr.sida.denuncia.situacao.denuncia.SituacaoDenunciada;
import jakarta.validation.constraints.*;

import java.util.List;

public record DenunciaRequestDTO(
        @NotNull(message = "A escola é obrigatória.")
        @Positive(message = "O ID da escola deve ser um número positivo válido.")
        Long idEscola,
        @NotNull(message = "Selecione o público afetado.")
        Afetados afetados,
        @NotBlank(message = "Forneça os detalhes sobre o ocorrido.")
        @Size(min = 10, max = 2000, message = "A descrição deve conter entre 10 e 2000 caracteres.")
        String oqueAconteceu,
        @NotNull(message = "Informe se a vítima está em perigo iminente.")
        Boolean estaEmPerigo,
        @NotNull(message = "Selecione a frequência em que o fato ocorre.")
        FrequenciaOcorre frequenciaOcorre,
        QuandoOcorreu quandoOcorreu,
        Boolean continuaAcontecendo,
        String detalhesAgressores,
        @NotNull(message = "Informe se há testemunhas do ocorrido.")
        Testemunha possuiTestemunha,
        @Size(max = 1000, message = "Os detalhes dos agressores devem ter no máximo 1000 caracteres.")
        String detalhesTestemunha,
        @NotNull(message = "Informe se o fato foi relatado para um responsável.")
        RelatadoParaOResponsavel relatadoParaOResponsavel,
        ResultadoRelato resultadoRelato,
        @NotNull(message = "Informe se a vítima se sente segura na escola.")
        Boolean senteSeguroNaEscola,
        @Size(max = 1000, message = "As informações extras devem ter no máximo 1000 caracteres.")
        String pedidoOuInformacaoExtra,
        @NotEmpty(message = "Selecione ao menos um local onde o fato ocorreu.")
        List<OndeOcorreu> ondeOcorreuList,
        @NotEmpty(message = "Selecione ao menos uma opção de como o fato te afetou.")
        List<ComoTeAfetou> comoTeAfetouList,
        @NotEmpty(message = "Selecione ao menos uma categoria de quem pratica o ato.")
        List<QuemPratica> quemPratica,
        @NotEmpty(message = "Selecione ao menos uma situação denunciada.")
        List<SituacaoDenunciada> situacaoDenunciadas
) {
}
