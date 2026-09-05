package br.com.pr.sida.denuncia.situacao.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "situacao_denuncia")
@Getter
@Setter
public class SituacaoDenuncia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "situacao_denunciada", nullable = false)
    private SituacaoDenunciada situacaoDenunciada;
    @ManyToOne
    @JoinColumn(name = "denuncia_id", referencedColumnName = "id", nullable = false)
    private Denuncia denuncia;
}
