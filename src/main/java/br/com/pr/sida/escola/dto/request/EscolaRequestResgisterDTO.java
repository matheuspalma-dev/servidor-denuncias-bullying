package br.com.pr.sida.escola.dto.request;

import br.com.pr.sida.escola.RedeEnsino;
import jakarta.validation.constraints.*;

public record EscolaRequestResgisterDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,
        @NotNull(message = "A rede de ensino é obrigatória")
        RedeEnsino redeEnsino,
        @NotNull(message = "O órgão competente é obrigatório")
        @Positive(message = "O ID do órgão competente deve ser um número positivo")
        Long orgaoCompetenteId,
        @Positive(message = "O código IBGE do município deve ser um número positivo")
        @NotNull(message = "O código IBGE do município é obrigatório")
        @Digits(integer = 7, fraction = 0, message = "O código IBGE do município deve ter no máximo 7 dígitos")
        Long codigoIbgeMunicipio
) {
}
