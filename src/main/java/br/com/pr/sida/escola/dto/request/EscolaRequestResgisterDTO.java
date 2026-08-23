package br.com.pr.sida.escola.dto.request;

import br.com.pr.sida.util.RedeEnsino;

public record EscolaRequestResgisterDTO(
        String nome,
        RedeEnsino redeEnsino,
        Long orgaoCompetenteId,
        String senhaAcesso
) {
}
