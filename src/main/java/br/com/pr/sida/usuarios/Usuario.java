package br.com.pr.sida.usuarios;

import br.com.pr.sida.usuarios.lotacao.UsuarioLotacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {
    @Id
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "senha", nullable = false)
    private String senha;
    @OneToMany(mappedBy = "usuario")
    private List<UsuarioLotacao> lotacoes;
}
