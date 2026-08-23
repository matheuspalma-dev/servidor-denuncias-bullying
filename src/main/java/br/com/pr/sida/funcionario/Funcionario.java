package br.com.pr.sida.funcionario;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.util.PerfilAcesso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "funcionarios")
@Getter
@Setter
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "cargo", nullable = false)
    private String cargo;
    @Column(name = "senha", nullable = false)
    private String sennha;
    @Column(name = "perfil", nullable = false)
    @Enumerated(EnumType.STRING)
    private PerfilAcesso perfil;
    @JoinColumn(name = "escola_id", referencedColumnName = "id", nullable = true)
    @ManyToOne
    private Escola escola;
    @JoinColumn(name = "orgao_competente_id", referencedColumnName = "id", nullable = true)
    @ManyToOne
    private OrgaoCompetente orgaoCompetente;
}
