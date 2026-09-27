package br.com.pr.sida.auditoria;

import br.com.pr.sida.auditoria.enums.AcaoEnum;
import br.com.pr.sida.auditoria.enums.EntidadeAfetadaEnum;
import br.com.pr.sida.auditoria.enums.QuemRealizouEnum;
import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
import jakarta.persistence.*;
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
    private Instant criadoEm;
    @Column(name = "acao", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private AcaoEnum acao;
    @Column(name = "quem_realizou", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private QuemRealizouEnum quemRealizou;
    @Column(name = "id_usuario", nullable = true, updatable = false)
    private Long idUsuario;
    @Column(name = "lotacao", nullable = true, updatable = false)
    @Enumerated(EnumType.STRING)
    private UsuarioLotacaoEnum lotacao;
    @Column(name = "entidade_id", nullable = false, updatable = false)
    private Long entidadeId;
    @Column(name = "entidade_afetada", nullable = true, updatable = false)
    @Enumerated(EnumType.STRING)
    private EntidadeAfetadaEnum entidadeAfetada;
    @Column(name = "dados_anteriores", nullable = true, updatable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> dadosAnteriores;
    @Column(name = "dados_novos", nullable = true, updatable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object>  dadosNovos;
    @Column(name = "detalhes", nullable = false, updatable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> detalhes;
    @Column(name = "ip_origem", nullable = false, updatable = false)
    private String ipOrigem;
    @Column(name = "user_agent", nullable = false, updatable = false)
    private String userAgent;
}
