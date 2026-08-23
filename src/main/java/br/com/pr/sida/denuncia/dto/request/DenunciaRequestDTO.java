package br.com.pr.sida.denuncia.dto.request;

import br.com.pr.sida.util.Genero;
import br.com.pr.sida.util.OndeOcorreu;
import br.com.pr.sida.util.PreferenciaEnvio;
import br.com.pr.sida.util.TipoViolencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DenunciaRequestDTO(
        @NotNull
        Long idEscola,
        @NotNull
        OndeOcorreu ondeOcorreu,
        @NotNull
        TipoViolencia tipoViolencia,
        @NotNull
        boolean riscoAgressao,
        @NotNull
        boolean situacaoGrave,
        @NotNull
        boolean violacaoDireitos,
        String salaVitimas,
        String salaAgressores,
        String mensagem
) {
}
