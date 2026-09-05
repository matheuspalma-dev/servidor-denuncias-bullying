package br.com.pr.sida.denuncia.situacao.denuncia.dto.request;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.situacao.denuncia.SituacaoDenunciada;

public record SituacaoDenunciaRequestDTO(
        SituacaoDenunciada situacaoDenunciada,
        Denuncia denuncia
) {
}
