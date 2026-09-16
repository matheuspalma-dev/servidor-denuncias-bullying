package br.com.pr.sida.escola;

import br.com.pr.sida.municipio.Municipio;
import br.com.pr.sida.orgao.competente.OrgaoCompetente;
import br.com.pr.sida.denuncia.Denuncia;
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
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "rede_ensino", nullable = false)
    @Enumerated(EnumType.STRING)
    private RedeEnsino redeEnsino;
    @Column(name = "ativa", nullable = false)
    private boolean ativa;
    @ManyToOne
    @JoinColumn(name = "orgao_competente_id", nullable = false)
    private OrgaoCompetente orgaoCompetente;
    @ManyToOne
    @JoinColumn(name = "municipio_id", nullable = false, referencedColumnName = "codigo_ibge")
    private Municipio municipio;
    @OneToMany(mappedBy = "escola")
    private List<Denuncia> denuncias;
}
