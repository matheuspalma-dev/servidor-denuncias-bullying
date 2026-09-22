package br.com.pr.sida.municipio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MunicipioRequestDTO(
        @Positive(message = "O código do município deve ser um número positivo.")
        @Size(min = 7, max = 7, message = "O código do município deve ter exatamente 7 dígitos.")
        Long codigoIbge,
        @NotBlank(message = "O nome do município é obrigatório.")
        String nome,
        @NotBlank(message = "O uf é obrigatório.")
        @Size(min = 2, max = 2, message = "O uf deve ter exatamente 2 caracteres.")
        String uf
) {
}
