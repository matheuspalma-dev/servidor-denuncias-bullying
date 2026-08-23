package br.com.pr.sida.denuncia;

import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.mensagem.denuncia.MensagemDenuncia;
import br.com.pr.sida.status.StatusDenuncia;
import br.com.pr.sida.util.*;
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
    // Alguém foi machucado fisicamente ou corre o risco de ser agredido a qualquer momento?
    @Column(name = "risco_agressao", nullable = false)
    private boolean riscoAgressao;
    // A situação envolve o uso de armas, facas, drogas ou ameaça de morte?
    @Column(name = "situacao_grave", nullable = false)
    private boolean situacaoGrave;
    // A situação acontece de forma grave e frequente sem ajuda em casa, ou envolve abuso/exploração de um menor de idade?
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
}
