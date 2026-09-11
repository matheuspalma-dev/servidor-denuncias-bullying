package br.com.pr.sida.usuarios.lotacao.dto.response;

import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioLotacaoResponseDTO {
    private UsuarioLotacaoEnum lotacao;
    private String nomeDaEntidade;
    private String emailDaEntidade;
    private String cargo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
