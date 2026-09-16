package br.com.pr.sida.escola.dto.response;

import br.com.pr.sida.escola.RedeEnsino;
import br.com.pr.sida.municipio.dto.response.MunicipioResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EscolaResponseDTO {
    private Long id;
    private String nome;
    private RedeEnsino redeEnsino;
    private MunicipioResponseDTO municipio;
}
