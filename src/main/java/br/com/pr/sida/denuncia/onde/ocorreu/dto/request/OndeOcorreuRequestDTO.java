package br.com.pr.sida.denuncia.onde.ocorreu.dto.request;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.enums.OndeOcorreu;

public record OndeOcorreuRequestDTO(
        OndeOcorreu ondeOcorreu,
        Denuncia denuncia
) {
}
