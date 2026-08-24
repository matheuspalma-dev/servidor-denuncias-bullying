package br.com.pr.sida.denuncia.dto.response;

import br.com.pr.sida.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import br.com.pr.sida.responsavel.denuncia.dto.response.ResponsavelDenunciaResponseDTO;
import br.com.pr.sida.status.dto.response.StatusDenunciaResponseDTO;
import br.com.pr.sida.util.enums.OndeOcorreu;
import br.com.pr.sida.util.enums.RedeEnsino;
import br.com.pr.sida.util.enums.TipoViolencia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DenunciaResponseDTO{
    private Long id;
    private LocalDate dataCriacao;
    private String nomeEscola;
    private RedeEnsino redeEnsino;
    private String orgaoCompetenteNome;
    private OndeOcorreu ondeOcorreu;
    private TipoViolencia tipoViolencia;
    private boolean riscoAgressao;
    private boolean situacaoGrave;
    private boolean violacaoDireitos;
    private List<StatusDenunciaResponseDTO> statusDenuncias;
    private List<MensagensDenunciaResponseDTO> mensagens;
    private List<ResponsavelDenunciaResponseDTO> responsaveisDenuncia;
}
