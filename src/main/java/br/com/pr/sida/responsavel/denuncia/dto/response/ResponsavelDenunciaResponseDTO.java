package br.com.pr.sida.responsavel.denuncia.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsavelDenunciaResponseDTO {
    private Long idOrgaoCompetenteResponsavel;
    private String nomeOrgaoCompetenteResponsavel;
    private String numeroOrgaoCompetenteResponsavel;
    private String emailOrgaoCompetenteResponsavel;
}
