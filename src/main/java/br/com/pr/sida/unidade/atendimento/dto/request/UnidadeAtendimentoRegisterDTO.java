package br.com.pr.sida.unidade.atendimento.dto.request;

import br.com.pr.sida.util.TipoUnidade;

public record UnidadeAtendimentoRegisterDTO(
        String nome,
        TipoUnidade tipoUnidade,
        String senha
) {
}
