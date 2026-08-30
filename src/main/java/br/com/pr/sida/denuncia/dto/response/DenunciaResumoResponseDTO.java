package br.com.pr.sida.denuncia.dto.response;

import br.com.pr.sida.denuncia.enums.Prioridade;
import br.com.pr.sida.status.StatusDenunciaEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DenunciaResumoResponseDTO {
    private Long denunciaId;
    private Prioridade prioridadeDenuncia;
    private String nomeEscola;
}
