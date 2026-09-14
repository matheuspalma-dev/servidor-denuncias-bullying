package br.com.pr.sida.acesso.denuncia.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record AcessoDenunciaRequestDTO(
        @NotBlank
        @Length(min = 13, max = 13)
        String codigoAcesso,
        @NotBlank
        @Length(min = 8, max = 8)
        String senhaAcesso
) {
}
