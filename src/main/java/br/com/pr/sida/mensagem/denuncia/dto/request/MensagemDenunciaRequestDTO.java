package br.com.pr.sida.mensagem.denuncia.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MensagemDenunciaRequestDTO(
        @NotBlank
        String mensagem
) {
}
