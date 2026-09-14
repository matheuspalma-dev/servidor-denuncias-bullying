package br.com.pr.sida.municipio.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipioResponseDTO {
    private Long codigoIbge;
    private String nome;
    private String uf;
}
