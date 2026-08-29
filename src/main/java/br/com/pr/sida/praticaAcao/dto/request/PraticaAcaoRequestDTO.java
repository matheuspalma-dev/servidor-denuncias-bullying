package br.com.pr.sida.praticaAcao.dto.request;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.praticaAcao.QuemPratica;

public record PraticaAcaoRequestDTO(
        QuemPratica quemPratica,
        Denuncia denuncia
) {
}
