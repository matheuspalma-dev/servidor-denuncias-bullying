package br.com.pr.sida.unidade.atendimento;

import br.com.pr.sida.util.TipoUnidade;
import jakarta.persistence.*;

@Entity
public class UnidadeAtendimento {
    @Id
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "tipo_unidade", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUnidade tipoUnidade;
    @Column(name = "senha", nullable = false)
    private String senha;
}
