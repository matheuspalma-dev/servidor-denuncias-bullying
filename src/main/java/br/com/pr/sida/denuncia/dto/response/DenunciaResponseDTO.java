package br.com.pr.sida.denuncia.dto.response;

import br.com.pr.sida.mensagem.denuncia.MensagemDenuncia;
import br.com.pr.sida.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import br.com.pr.sida.util.Genero;
import br.com.pr.sida.util.PreferenciaEnvio;
import br.com.pr.sida.util.Status;
import br.com.pr.sida.util.TipoViolencia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DenunciaResponseDTO{
    private Long id;
    private LocalDate dataCriacao;
    private String nomeMunicipio;
    private String nomeEscola;
    private int idadeDenunciante;
    private Genero generoDenunciante;
    private boolean violenciaNaEscola;
    private TipoViolencia tipoViolencia;
    private Status statusDenuncia;
    private PreferenciaEnvio preferenciaEnvio;
    private List<MensagensDenunciaResponseDTO> mensagens;
}
