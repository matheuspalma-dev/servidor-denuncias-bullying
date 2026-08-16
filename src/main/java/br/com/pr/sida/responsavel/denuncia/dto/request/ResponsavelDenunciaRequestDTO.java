package br.com.pr.sida.responsavel.denuncia.dto.request;

import br.com.pr.sida.util.TipoUnidade;

public record ResponsavelDenunciaRequestDTO(
        Long denunciaId,
        TipoUnidade tipoUnidade
) {
}
