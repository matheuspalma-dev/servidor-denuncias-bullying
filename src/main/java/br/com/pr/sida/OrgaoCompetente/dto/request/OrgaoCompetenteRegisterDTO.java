package br.com.pr.sida.OrgaoCompetente.dto.request;

import br.com.pr.sida.OrgaoCompetente.TipoOrgaoCompetente;
import br.com.pr.sida.security.tirar.xss.TirarXss;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrgaoCompetenteRegisterDTO(
        @TirarXss
        @NotBlank
        String nome,
        @NotNull
        TipoOrgaoCompetente tipoOrgaoCompetente,
        @TirarXss
        @NotBlank
        String numero,
        @TirarXss
        @NotBlank
        String email,
        @NotBlank
        String senhaAcesso
) {
}
