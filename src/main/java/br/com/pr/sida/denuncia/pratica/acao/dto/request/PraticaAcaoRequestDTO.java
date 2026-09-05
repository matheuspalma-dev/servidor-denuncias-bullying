package br.com.pr.sida.denuncia.pratica.acao.dto.request;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.pratica.acao.QuemPratica;

public record PraticaAcaoRequestDTO(
        QuemPratica quemPratica,
        Denuncia denuncia
) {
}
