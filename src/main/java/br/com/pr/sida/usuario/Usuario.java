package br.com.pr.sida.usuario;

import br.com.pr.sida.util.Atividade;
import br.com.pr.sida.util.Genero;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {
    @Id
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "email_hash", nullable = false, unique = true)
    private String emailHash;
    @Column(name = "senha", nullable = false)
    private String senha;
    @Column(name = "status", nullable = false)
    private Atividade status;
    @Column(name = "genero", nullable = false)
    private Genero genero;
    @Column(name = "ano_nascimento", nullable = false)
    private int anoNascimento;
    @Column(name = "codigo_verificacao", nullable = false)
    private String codigoVerificacao;
}
