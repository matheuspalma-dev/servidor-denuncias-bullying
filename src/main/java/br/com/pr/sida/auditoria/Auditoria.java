package br.com.pr.sida.auditoria;

import br.com.pr.sida.auditoria.enums.AcaoEnum;
import br.com.pr.sida.auditoria.enums.EntidadeAfetadaEnum;
import br.com.pr.sida.auditoria.enums.QuemRealizouEnum;
import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "auditoria")
@Getter
@Setter
public class Auditoria {
    @Id
    private Long id;
    @Column(name = "criado_em", nullable = false, updatable = false)
    private final Instant criadoEm = Instant.now();
    @Column(name = "acao", nullable = false, updatable = false)
    private AcaoEnum acao;
    @Column(name = "quem_realizou", nullable = false, updatable = false)
    private QuemRealizouEnum quemRealizou;
    @Column(name = "id_usuario", nullable = true, updatable = false)
    private Long idUsuario;
    @Column(name = "lotacao", nullable = true, updatable = false)
    private UsuarioLotacaoEnum lotacao;
    @Column(name = "entidade_id", nullable = false, updatable = false)
    private Long entidadeId;
    @Column(name = "entidade_afetada", nullable = false, updatable = false)
    private EntidadeAfetadaEnum entidadeAfetada;
    @Column(name = "dados_anteriores", nullable = true, updatable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> dadosAnteriores;
    @Column(name = "dados_novos", nullable = true, updatable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object>  dadosNovos;
    private String ipOrigem;
    private String userAgent;
}
