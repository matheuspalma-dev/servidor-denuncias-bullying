package br.com.pr.sida.como.afetou;

import br.com.pr.sida.como.afetou.dto.request.ComoAfetouRequestDTO;
import org.springframework.stereotype.Component;

@Component
class ComoAfetouMapper {

    public ComoAfetou conveterDTOEmEntity(ComoAfetouRequestDTO comoAfetouRequestDTO){
        ComoAfetou comoAfetou = new ComoAfetou();
        comoAfetou.setComoTeAfetou(comoAfetouRequestDTO.comoTeAfetou());
        comoAfetou.setDenuncia(comoAfetouRequestDTO.denuncia());
        return comoAfetou;
    }

}
