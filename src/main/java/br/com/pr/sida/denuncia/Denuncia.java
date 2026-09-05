package br.com.pr.sida.denuncia;

import br.com.pr.sida.acesso.denuncia.Acesso;
import br.com.pr.sida.denuncia.como.afetou.ComoAfetou;
import br.com.pr.sida.denuncia.enums.*;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.denuncia.mensagem.denuncia.MensagemDenuncia;
import br.com.pr.sida.denuncia.onde.ocorreu.OndeOcorreuDenuncia;
import br.com.pr.sida.denuncia.pratica.acao.PraticaAcao;
import br.com.pr.sida.denuncia.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.denuncia.situacao.denuncia.SituacaoDenuncia;
import br.com.pr.sida.status.StatusDenuncia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Denuncia {
    @Id
    private Long id;
    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;
    @JoinColumn(name = "escola_id", nullable = false)
    @ManyToOne
    private Escola escola;
    @Column(name = "afetados", nullable = false)
    @Enumerated(EnumType.STRING)
    private Afetados afetados;
    @Column(name = "oqueAconteceu", nullable = false)
    private String oqueAconteceu;
    @Column(name = "esta_em_perigo", nullable = false)
    private boolean estaEmPerigo;
    @Column(name = "frequencia_que_ocorre", nullable = false)
    @Enumerated(EnumType.STRING)
    private FrequenciaOcorre frequenciaOcorre;
    @Column(name = "quando_aconteceu", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuandoOcorreu quandoOcorreu;
    @Column(name = "continua_acontecendo", nullable = true)
    private Boolean continuaAcontecendo;
    @Column(name = "detalhes_agressores", nullable = false)
    private String detalhesAgressores;
    @Column(name = "possui_testemunha", nullable = false)
    @Enumerated(EnumType.STRING)
    private Testemunha possuiTestemuna;
    @Column(name = "detalhes_testemunha", nullable = false)
    private String detalhesTestemunha;
    @Column(name = "relatado_para_qual_responsavel", nullable = false)
    private RelatadoParaOResponsavel relatadoParaOResponsavel;
    @Column(name = "resultado_relato", nullable = true)
    private ResultadoRelato resultadoRelato;
    @Column(name = "se_sente_seguro_na_escola", nullable = false)
    private boolean senteSeguroNaEscola;
    @Column(name = "pedido_ou_informacao_extra", nullable = false)
    private String pedidoOuInformacaoExtra;
    @Column(name = "prioridade_denuncia", nullable = false)
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;
    @OneToOne(mappedBy = "denuncia")
    private Acesso acesso;
    @OneToMany(mappedBy = "denuncia")
    private List<OndeOcorreuDenuncia> ondeOcorreuDenunciaList;
    @OneToMany(mappedBy = "denuncia")
    private List<ComoAfetou> comoTeAfetou;
    @OneToMany(mappedBy = "denuncia")
    private List<PraticaAcao> praticantesAcao;
    @OneToMany(mappedBy = "denuncia")
    private List<SituacaoDenuncia> situacaoDenuncias;
    @OneToMany(mappedBy = "denuncia")
    private List<StatusDenuncia> statusDenuncia;
    @OneToMany(mappedBy = "denuncia")
    private List<MensagemDenuncia> mensagens;
    @OneToMany(mappedBy = "denuncia")
    private List<ResponsavelDenuncia> responsavelDenuncias;
}
