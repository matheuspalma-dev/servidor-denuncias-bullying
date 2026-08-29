package br.com.pr.sida.denuncia;

import br.com.pr.sida.acesso.denuncia.Acesso;
import br.com.pr.sida.como.afetou.ComoAfetou;
import br.com.pr.sida.denuncia.enums.*;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.mensagem.denuncia.MensagemDenuncia;
import br.com.pr.sida.onde.ocorreu.OndeOcorreuDenuncia;
import br.com.pr.sida.praticaAcao.PraticaAcao;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.situacao.denuncia.SituacaoDenuncia;
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
    @Column(name = "afetados")
    @Enumerated(EnumType.STRING)
    private Afetados afetados;
    @Column(name = "oqueAconteceu")
    private String oqueAconteceu;
    @Column(name = "esta_em_perigo")
    private boolean estaEmPerigo;
    @Column(name = "frequencia_que_ocorre")
    @Enumerated(EnumType.STRING)
    private FrequenciaOcorre frequenciaOcorre;
    @Column(name = "quando_aconteceu")
    @Enumerated(EnumType.STRING)
    private QuandoOcorreu quandoOcorreu;
    @Column(name = "continua_acontecendo")
    private boolean continuaAcontecendo;
    @Column(name = "detalhes_agressores")
    private String detalhesAgressores;
    @Column(name = "possui_testemunha")
    @Enumerated(EnumType.STRING)
    private Testemunha possuiTestemuna;
    @Column(name = "detalhes_testemunha")
    private String detalhesTestemunha;
    @Column(name = "relatado_para_qual_responsavel")
    private RelatadoParaOResponsavel relatadoParaOResponsavel;
    @Column(name = "resultado_relato", nullable = true)
    private ResultadoRelato resultadoRelato;
    @Column(name = "se_sente_seguro_na_escola")
    private boolean senteSeguroNaEscola;
    @Column(name = "pedido_ou_informacao_extra")
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
