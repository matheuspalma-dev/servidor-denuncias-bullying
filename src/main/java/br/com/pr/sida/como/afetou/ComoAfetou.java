package br.com.pr.sida.como.afetou;

import br.com.pr.sida.denuncia.Denuncia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "como_te_afetou")
@Getter
@Setter
public class ComoAfetou {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "como_te_afetou")
    private ComoTeAfetou comoTeAfetou;
    @ManyToOne
    @JoinColumn(name = "denuncia_id", referencedColumnName = "id")
    private Denuncia denuncia;
}
