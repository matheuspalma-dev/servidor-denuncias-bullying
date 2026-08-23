package br.com.pr.sida.escola;

import br.com.pr.sida.util.RedeEnsino;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "escolas")
@Getter
@Setter
public class Escola {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "rede_ensino", nullable = false)
    @Enumerated(EnumType.STRING)
    private RedeEnsino redeEnsino;
    @Column(name = "ativa", nullable = false)
    private boolean ativa;
}
