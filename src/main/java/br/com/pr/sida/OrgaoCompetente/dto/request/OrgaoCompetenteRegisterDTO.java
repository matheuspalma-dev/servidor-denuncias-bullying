package br.com.pr.sida.OrgaoCompetente.dto.request;

import br.com.pr.sida.OrgaoCompetente.TipoOrgaoCompetente;

public record OrgaoCompetenteRegisterDTO(
        String nome,
        TipoOrgaoCompetente tipoOrgaoCompetente,
        String numero,
        String email,
        String senhaAcesso
) {
}
