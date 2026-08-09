package br.com.pr.sida.mensagem.denuncia.dto.request;

import br.com.pr.sida.util.AutorMensagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MensagemDenunciaRequestDTO(
        Long idDenuncia,
        @NotNull
        AutorMensagem autor,
        @NotBlank
        String mensagem
) {
}
