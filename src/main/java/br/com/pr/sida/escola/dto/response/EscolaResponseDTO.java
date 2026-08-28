package br.com.pr.sida.escola.dto.response;

import br.com.pr.sida.escola.RedeEnsino;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EscolaResponseDTO {
    private Long id;
    private String nome;
    private RedeEnsino redeEnsino;
}
