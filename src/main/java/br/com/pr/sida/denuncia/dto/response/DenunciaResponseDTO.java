package br.com.pr.sida.denuncia.dto.response;

import br.com.pr.sida.como.afetou.ComoTeAfetou;
import br.com.pr.sida.denuncia.enums.*;
import br.com.pr.sida.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import br.com.pr.sida.praticaAcao.QuemPratica;
import br.com.pr.sida.responsavel.denuncia.dto.response.ResponsavelDenunciaResponseDTO;
import br.com.pr.sida.situacao.denuncia.SituacaoDenunciada;
import br.com.pr.sida.status.dto.response.StatusDenunciaResponseDTO;
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
    private Afetados afetados;
    private String oqueAconteceu;
    private boolean estaEmPerigo;
    private FrequenciaOcorre frequenciaOcorre;
    private QuandoOcorreu quandoOcorreu;
    private Boolean continuaAcontecendo;
    private String detalhesAgressores;
    private Testemunha possuiTestemuna;
    private String detalhesTestemunha;
    private RelatadoParaOResponsavel relatadoParaOResponsavel;
    private ResultadoRelato resultadoRelato;
    private boolean senteSeguroNaEscola;
    private String pedidoOuInformacaoExtra;
    private Prioridade prioridade;
    private String codigoAcesso;
    private List<OndeOcorreu> ondeOcorreuList;
    private List<ComoTeAfetou> comoTeAfetouList;
    private List<QuemPratica> quemPraticaList;
    private List<SituacaoDenunciada> situacaoDenunciadaList;
    private List<StatusDenunciaResponseDTO> statusDenunciaResponseDTOList;
    private List<MensagensDenunciaResponseDTO> mensagensDenunciaResponseDTOList;
    private List<ResponsavelDenunciaResponseDTO> responsavelDenunciaResponseDTOList;
}
