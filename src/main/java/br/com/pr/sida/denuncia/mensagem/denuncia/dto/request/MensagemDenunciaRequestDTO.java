package br.com.pr.sida.denuncia.mensagem.denuncia.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MensagemDenunciaRequestDTO(
        @NotBlank
        String mensagem
) {
}
