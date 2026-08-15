package br.com.pr.sida.unidade.atendimento.dto.response;

import br.com.pr.sida.util.TipoUnidade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeAtendimentoResponseDTO {
    private Long id;
    private String nome;
    private TipoUnidade tipoUnidade;
}
