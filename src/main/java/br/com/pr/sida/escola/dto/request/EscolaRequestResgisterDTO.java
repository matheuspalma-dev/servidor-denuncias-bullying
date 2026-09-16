package br.com.pr.sida.escola.dto.request;

import br.com.pr.sida.escola.RedeEnsino;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EscolaRequestResgisterDTO(
        @NotBlank
        String nome,
        @Email
        String email,
        @NotNull
        RedeEnsino redeEnsino,
        @NotNull
        @Positive
        Long orgaoCompetenteId,
        @Positive
        @NotNull
        Long codigoIbgeMunicipio
) {
}
