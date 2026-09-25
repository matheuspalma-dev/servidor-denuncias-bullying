package br.com.pr.sida.municipio.dto.request;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

public record MunicipioRequestDTO(
        @Positive(message = "O código do município deve ser um número positivo.")
        @Range(min = 1000000, max = 9999999, message = "O código do município deve ter exatamente 7 digitos.")
        Long codigoIbge,
        @NotBlank(message = "O nome do município é obrigatório.")
        String nome,
        @NotBlank(message = "O uf é obrigatório.")
        @Size(min = 2, max = 2, message = "O uf deve ter exatamente 2 caracteres.")
        String uf
) {
}
