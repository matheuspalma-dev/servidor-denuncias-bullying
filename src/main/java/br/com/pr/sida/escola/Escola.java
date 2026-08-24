package br.com.pr.sida.escola;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.util.enums.RedeEnsino;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "escolas")
@Getter
@Setter
public class Escola {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "rede_ensino", nullable = false)
    @Enumerated(EnumType.STRING)
    private RedeEnsino redeEnsino;
    @Column(name = "ativa", nullable = false)
    private boolean ativa;
    @ManyToOne
    @JoinColumn(name = "orgao_competente_id", nullable = false)
    private OrgaoCompetente orgaoCompetente;
    @Column(name = "senha_acesso", nullable = false)
    private String senhaAcesso;
    @OneToMany(mappedBy = "escola")
    private List<Denuncia> denuncias;
}
