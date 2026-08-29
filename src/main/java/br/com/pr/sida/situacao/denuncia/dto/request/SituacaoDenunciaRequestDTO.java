package br.com.pr.sida.situacao.denuncia.dto.request;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.situacao.denuncia.SituacaoDenunciada;

public record SituacaoDenunciaRequestDTO(
        SituacaoDenunciada situacaoDenunciada,
        Denuncia denuncia
) {
}
