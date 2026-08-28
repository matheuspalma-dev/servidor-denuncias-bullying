package br.com.pr.sida.denuncia.dto.request;

import br.com.pr.sida.denuncia.enums.OndeOcorreu;
import br.com.pr.sida.denuncia.enums.TipoViolencia;
import jakarta.validation.constraints.NotNull;

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
