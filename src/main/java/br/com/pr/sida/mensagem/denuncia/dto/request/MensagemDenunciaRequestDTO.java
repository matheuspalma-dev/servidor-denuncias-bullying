package br.com.pr.sida.mensagem.denuncia.dto.request;

import br.com.pr.sida.util.enums.AutorMensagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MensagemDenunciaRequestDTO(
        @NotNull
        Long idDenuncia,
        @NotNull
        AutorMensagem autor,
        @NotBlank
        String mensagem
) {
}
