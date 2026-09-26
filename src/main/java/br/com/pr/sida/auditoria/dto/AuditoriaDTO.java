package br.com.pr.sida.auditoria.dto;

import br.com.pr.sida.auditoria.enums.AcaoEnum;
import br.com.pr.sida.auditoria.enums.EntidadeAfetadaEnum;
import br.com.pr.sida.auditoria.enums.QuemRealizouEnum;
import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;

import java.util.Map;

public record AuditoriaDTO(
        AcaoEnum acao,
        QuemRealizouEnum quemRealizou,
        Long idUsuario,
        UsuarioLotacaoEnum lotacaoNoMomentoDaAcao,
        Long entidadeId,
        EntidadeAfetadaEnum entidadeAfetada,
        Map<String, Object> dadosAnteriores,
        Map<String, Object> dadosNovos,
        String ipOrigem,
        String userAgent

) {
}
