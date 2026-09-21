package br.com.pr.sida.denuncia.triagem.dto.response;

import br.com.pr.sida.denuncia.enums.Prioridade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TriagemResponseDTO {
    private Prioridade prioridade;
    private boolean escolaVaiTerAcesso;
}
