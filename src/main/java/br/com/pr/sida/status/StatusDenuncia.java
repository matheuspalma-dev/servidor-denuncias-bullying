package br.com.pr.sida.status;

import br.com.pr.sida.denuncia.Denuncia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "status")
@Getter
@Setter
public class StatusDenuncia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusDenunciaEnum statusDenunciaEnum;
    @ManyToOne
    @JoinColumn(name = "denuncia_id", nullable = false, referencedColumnName = "id")
    private Denuncia denuncia;
}
