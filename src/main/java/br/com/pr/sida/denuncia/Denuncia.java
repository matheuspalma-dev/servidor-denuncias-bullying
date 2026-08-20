package br.com.pr.sida.denuncia;

import br.com.pr.sida.mensagem.denuncia.MensagemDenuncia;
import br.com.pr.sida.util.Genero;
import br.com.pr.sida.util.PreferenciaEnvio;
import br.com.pr.sida.util.Status;
import br.com.pr.sida.util.TipoViolencia;
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
    @Column(name = "nome_municipio", nullable = false)
    private String nomeMunicipio;
    @Column(name = "nome_escola", nullable = false)
    private String nomeEscola;
    @Column(name = "idade_denunciante", nullable = false)
    private int idadeDenunciante;
    @Column(name = "genero_denunciante", nullable = false)
    @Enumerated(EnumType.STRING)
    private Genero generoDenunciante;
    @Column(name = "violencia_na_escola", nullable = false)
    private boolean violenciaNaEscola;
    @Column(name = "tipo_violencia", nullable = true)
    @Enumerated(EnumType.STRING)
    private TipoViolencia tipoViolencia;
    @Column(name = "status_denuncia", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status statusDenuncia;
    @Column(name = "preferencia_envio", nullable = false)
    @Enumerated(EnumType.STRING)
    private PreferenciaEnvio preferenciaEnvio;
    @OneToMany(mappedBy = "denuncia")
    private List<MensagemDenuncia> mensagens;
}
