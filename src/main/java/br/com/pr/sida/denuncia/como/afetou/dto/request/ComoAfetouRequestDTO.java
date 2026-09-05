package br.com.pr.sida.denuncia.como.afetou.dto.request;

import br.com.pr.sida.denuncia.como.afetou.ComoTeAfetou;
import br.com.pr.sida.denuncia.Denuncia;

public record ComoAfetouRequestDTO(
        ComoTeAfetou comoTeAfetou,
        Denuncia denuncia
) {
}
