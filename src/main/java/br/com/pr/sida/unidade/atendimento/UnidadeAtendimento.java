package br.com.pr.sida.unidade.atendimento;

import br.com.pr.sida.util.TipoUnidade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
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
