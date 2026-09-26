package br.com.pr.sida.auditoria;

import br.com.pr.sida.auditoria.dto.AuditoriaDTO;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaMapper {

    public Auditoria converterDTOParaEntity(AuditoriaDTO auditoriaDTO){
        Auditoria auditoria = new Auditoria();
        auditoria.setAcao(auditoriaDTO.acao());
        auditoria.setQuemRealizou(auditoriaDTO.quemRealizou());
        auditoria.setIdUsuario(auditoriaDTO.idUsuario());
        auditoria.setLotacao(auditoriaDTO.lotacaoNoMomentoDaAcao());
        auditoria.setEntidadeId(auditoriaDTO.entidadeId());
        auditoria.setEntidadeAfetada(auditoriaDTO.entidadeAfetada());
        auditoria.setDadosAnteriores(auditoriaDTO.dadosAnteriores());
        auditoria.setDadosNovos(auditoriaDTO.dadosNovos());
        auditoria.setIpOrigem(auditoriaDTO.ipOrigem());
        auditoria.setUserAgent(auditoriaDTO.userAgent());
        return auditoria;
    }
}
