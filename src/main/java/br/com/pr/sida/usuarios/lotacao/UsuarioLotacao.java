package br.com.pr.sida.usuarios.lotacao;

import br.com.pr.sida.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios_lotacao")
@Getter
@Setter
public class UsuarioLotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lotacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private  UsuarioLotacaoEnum lotacao;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuario;
    @Column(name = "entidade_id", nullable = false)
    private Long entidadeId;
    @Column(name = "cargo", nullable = false)
    private String cargo;
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;
    @Column(name = "data_fim")
    private LocalDate dataFim;
}
