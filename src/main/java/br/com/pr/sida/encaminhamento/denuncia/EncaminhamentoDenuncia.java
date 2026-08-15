package br.com.pr.sida.encaminhamento.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.unidade.atendimento.UnidadeAtendimento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EncaminhamentoDenuncia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "denuncia_id", referencedColumnName = "id")
    private Denuncia denuncia;
    @ManyToOne
    @JoinColumn(name = "unidade_atendimento_id", referencedColumnName = "id")
    private UnidadeAtendimento unidadeAtendimento;
}
