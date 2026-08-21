package br.com.pr.sida.acesso.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Acesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "denuncia_id", nullable = false)
    private Denuncia denuncia;
    @Column(name = "codigo_acesso", nullable = false, unique = true)
    private String codigoAcesso;
    @Column(name = "codigo_acesso_hash", nullable = false, unique = true)
    private String codigoAcessoHash;
    @Column(name = "senha_acesso", nullable = false)
    private String senhaAcesso;
}
