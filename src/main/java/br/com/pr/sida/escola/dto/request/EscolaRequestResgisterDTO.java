package br.com.pr.sida.escola.dto.request;

import br.com.pr.sida.escola.RedeEnsino;

public record EscolaRequestResgisterDTO(
        String nome,
        String email,
        RedeEnsino redeEnsino,
        Long orgaoCompetenteId,
        String senhaAcesso
) {
}
