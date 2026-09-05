package br.com.pr.sida.escola.dto.request;

import br.com.pr.sida.escola.RedeEnsino;
import br.com.pr.sida.security.tirar.xss.TirarXss;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EscolaRequestResgisterDTO(
        @TirarXss
        @NotBlank
        String nome,
        @TirarXss
        @Email
        String email,
        @NotNull
        RedeEnsino redeEnsino,
        @NotNull
        @Positive
        Long orgaoCompetenteId,
        @NotBlank
        String senhaAcesso
) {
}
