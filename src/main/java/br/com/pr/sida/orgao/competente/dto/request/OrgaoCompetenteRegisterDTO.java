package br.com.pr.sida.orgao.competente.dto.request;

import br.com.pr.sida.orgao.competente.TipoOrgaoCompetente;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

public record OrgaoCompetenteRegisterDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotNull(message = "O tipo do órgão competente é obrigatório")
        TipoOrgaoCompetente tipoOrgaoCompetente,
        @NotBlank(message = "O número do órgão competente é obrigatório")
        String numero,
        @NotBlank(message = "O e-mail do órgão competente é obrigatório")
        @Email(message = "O e-mail do órgão competente deve ser válido")
        String email,
        @Positive(message = "O código IBGE do município deve ser um número positivo")
        @Range(min = 1000000, max = 9999999, message = "O código do município deve ter exatamente 7 digitos.")
        Long codigoIbgeMunicipio
) {
}
