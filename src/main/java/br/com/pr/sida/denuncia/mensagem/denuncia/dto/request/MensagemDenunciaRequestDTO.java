package br.com.pr.sida.denuncia.mensagem.denuncia.dto.request;

import br.com.pr.sida.security.tirar.xss.TirarXss;
import jakarta.validation.constraints.NotBlank;

public record MensagemDenunciaRequestDTO(
        @NotBlank
        @TirarXss
        String mensagem
) {
}
