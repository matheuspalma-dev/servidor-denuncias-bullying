package br.com.pr.sida.denuncia.onde.ocorreu;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.enums.OndeOcorreu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "onde_ocorreu")
@Getter
@Setter
public class OndeOcorreuDenuncia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "onde_ocorreu", nullable = false)
    @Enumerated(EnumType.STRING)
    private OndeOcorreu ondeOcorreu;
    @JoinColumn(name = "denuncia_id", nullable = false, referencedColumnName = "id")
    @ManyToOne
    private Denuncia denuncia;
}
