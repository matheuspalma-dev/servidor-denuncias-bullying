package br.com.pr.sida.orgao.competente.dto.request;

import br.com.pr.sida.orgao.competente.TipoOrgaoCompetente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record OrgaoCompetenteRegisterDTO(
        @NotBlank
        String nome,
        @NotNull
        TipoOrgaoCompetente tipoOrgaoCompetente,
        @NotBlank
        String numero,
        @NotBlank
        String email,
        @Positive
        @Length(min = 7, max = 7)
        Long codigoIbgeMunicipio
) {
}
