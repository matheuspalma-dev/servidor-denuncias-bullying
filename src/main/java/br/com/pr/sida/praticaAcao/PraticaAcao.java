package br.com.pr.sida.praticaAcao;

import br.com.pr.sida.denuncia.Denuncia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pratica_acao")
@Getter
@Setter
public class PraticaAcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quem_pratica")
    @Enumerated(EnumType.STRING)
    private QuemPratica quemPratica;
    @ManyToOne
    @JoinColumn(name = "denuncia_id", referencedColumnName = "id")
    private Denuncia denuncia;
}
