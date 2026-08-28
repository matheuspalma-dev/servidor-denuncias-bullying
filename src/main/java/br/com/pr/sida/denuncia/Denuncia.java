package br.com.pr.sida.denuncia;

import br.com.pr.sida.denuncia.enums.TipoViolencia;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.mensagem.denuncia.MensagemDenuncia;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.status.StatusDenuncia;
import br.com.pr.sida.denuncia.enums.OndeOcorreu;
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
    @Column(name = "onde_ocorreu", nullable = false)
    @Enumerated(EnumType.STRING)
    private OndeOcorreu ondeOcorreu;
    @Column(name = "tipo_violencia", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoViolencia tipoViolencia;
    @Column(name = "risco_agressao", nullable = false)
    private boolean riscoAgressao;
    @Column(name = "situacao_grave", nullable = false)
    private boolean situacaoGrave;
    @Column(name = "violacao_direitos", nullable = false)
    private boolean violacaoDireitos;
    @Column(name = "sala_vitimas", nullable = true)
    private String salaVitimas;
    @Column(name = "sala_agressores", nullable = true)
    private String salaAgressores;
    @OneToMany(mappedBy = "denuncia")
    private List<StatusDenuncia> statusDenuncia;
    @OneToMany(mappedBy = "denuncia")
    private List<MensagemDenuncia> mensagens;
    @OneToMany(mappedBy = "denuncia")
    private List<ResponsavelDenuncia> responsavelDenuncias;
}
