package br.com.pr.sida.acesso.denuncia.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AcessoDenunciaRequestDTO(
        @NotBlank(message = "O código de acesso é obrigatório.")
        @Size(min = 13, max = 13, message = "O código de acesso deve conter exatamente 13 caracteres.")
        String codigoAcesso,
        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, max = 8, message = "A senha deve conter exatamente 8 caracteres.")
        String senhaAcesso
) {
}
