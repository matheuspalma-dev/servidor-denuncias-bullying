package br.com.pr.sida.responsavel;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.escola.Escola;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "responsavel_denuncia")
@Getter
@Setter
public class ResponsavelDenuncia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "orgao_competente_id", referencedColumnName = "id")
    @ManyToOne
    private OrgaoCompetente orgaoCompetenteResponsavel;
    @ManyToOne
    @JoinColumn(name = "escola_responsavel", referencedColumnName = "id")
    private Escola escolaResponsavel;
}
