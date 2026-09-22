package br.com.pr.sida.escola.dto.request;

import br.com.pr.sida.escola.RedeEnsino;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
        Long codigoIbgeMunicipio
) {
}
