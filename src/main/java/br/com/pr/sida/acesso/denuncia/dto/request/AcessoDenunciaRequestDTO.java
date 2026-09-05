package br.com.pr.sida.acesso.denuncia.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AcessoDenunciaRequestDTO(
        @NotBlank
        String codigoAcesso,
        @NotBlank
        String senhaAcesso
) {
}
