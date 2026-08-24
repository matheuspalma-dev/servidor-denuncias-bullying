package br.com.pr.sida.mensagem.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.util.enums.AutorMensagem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class MensagemDenuncia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "denuncia_id", nullable = false)
    private Denuncia denuncia;
    @Enumerated(EnumType.STRING)
    @Column(name = "autor_mensagem", nullable = false)
    private AutorMensagem autor;
    @Column(name = "mensagem", nullable = false)
    private String mensagem;
    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;
}
