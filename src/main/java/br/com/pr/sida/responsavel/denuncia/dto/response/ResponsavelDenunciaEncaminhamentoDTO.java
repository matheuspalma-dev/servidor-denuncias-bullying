package br.com.pr.sida.responsavel.denuncia.dto.response;

import br.com.pr.sida.util.PreferenciaEnvio;
import br.com.pr.sida.util.Status;
import br.com.pr.sida.util.TipoViolencia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResponsavelDenunciaEncaminhamentoDTO {
    private Long denunciaId;
    private LocalDate dataCriacao;
    private String nomeMunicipio;
    private String nomeEscola;
    private boolean violenciaNaEscola;
    private TipoViolencia tipoViolencia;
    private Status status;
    private PreferenciaEnvio preferenciaEnvio;
}
