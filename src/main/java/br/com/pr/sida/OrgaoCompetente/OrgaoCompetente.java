package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.util.TipoOrgaoCompetente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orgao_competente")
@Getter
@Setter
public class OrgaoCompetente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "tipo_orgao_competente", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoOrgaoCompetente tipoOrgaoCompetente;
    @Column(name = "numero", nullable = false)
    private String numero;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "senha", nullable = false)
    private String senha;
}
