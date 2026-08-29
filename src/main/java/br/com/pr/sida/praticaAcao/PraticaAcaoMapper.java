package br.com.pr.sida.praticaAcao;

import br.com.pr.sida.praticaAcao.dto.request.PraticaAcaoRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class PraticaAcaoMapper {

    public PraticaAcao converterDTOEmEntity(PraticaAcaoRequestDTO praticaAcaoRequestDTO){
        PraticaAcao praticaAcao = new PraticaAcao();
        praticaAcao.setQuemPratica(praticaAcaoRequestDTO.quemPratica());
        praticaAcao.setDenuncia(praticaAcaoRequestDTO.denuncia());
        return praticaAcao;
    }
}
