package br.com.pr.sida.responsavel.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.unidade.atendimento.UnidadeAtendimento;
import jakarta.persistence.*;

@Entity
public class ResponsavelDenuncia {
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
