package br.com.pr.sida.municipio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record MunicipioRequestDTO(
        @Positive
        @Length(min = 7, max = 7)
        Long codigoIbge,
        @NotBlank
        String nome,
        @NotBlank
        @Length(min = 2, max = 2)
        String uf
) {
}
