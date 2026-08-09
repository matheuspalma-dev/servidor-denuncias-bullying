package br.com.pr.sida.denuncia.dto.request;

import br.com.pr.sida.util.Genero;
import br.com.pr.sida.util.TipoViolencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DenunciaRequestDTO(
        @NotBlank
        String nomeMunicipio,
        @NotBlank
        String nomeEscola,
        @Positive
        int idadeDenunciante,
        @NotNull
        Genero generoDenunciante,
        @NotNull
        boolean violenciaNaEscola,
        @NotNull
        TipoViolencia tipoViolencia,
        @NotBlank
        String mensagemDenuncia
) {
}
