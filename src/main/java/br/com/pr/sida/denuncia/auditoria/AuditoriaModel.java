package br.com.pr.sida.denuncia.auditoria;

import br.com.pr.sida.usuarios.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AuditoriaModel {
    @ManyToOne
    @JoinColumn(name = "criado_por_id", referencedColumnName = "id", nullable = false)
    private Usuario criadoPor;
    @Column(name = "criado_em", nullable = false)
    private LocalDate criadoEm;
}
