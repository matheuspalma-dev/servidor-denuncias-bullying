package br.com.pr.sida.responsavel.denuncia;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.denuncia.Denuncia;
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
    @JoinColumn(name = "denuncia_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Denuncia denuncia;
    @JoinColumn(name = "orgao_competente_id", referencedColumnName = "id", nullable = true)
    @ManyToOne
    private OrgaoCompetente orgaoCompetenteResponsavel;
    @ManyToOne
    @JoinColumn(name = "escola_responsavel_id", referencedColumnName = "id", nullable = false)
    private Escola escolaResponsavelId;
}
