package br.com.pr.sida.denuncia.situacao.denuncia;

import br.com.pr.sida.denuncia.situacao.denuncia.dto.request.SituacaoDenunciaRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class SituacaoDenunciaMapper {

    public SituacaoDenuncia converterDTOEmEntity(SituacaoDenunciaRequestDTO situacaoDenunciaRequestDTO){
        SituacaoDenuncia situacaoDenuncia = new SituacaoDenuncia();
        situacaoDenuncia.setSituacaoDenunciada(situacaoDenunciaRequestDTO.situacaoDenunciada());
        situacaoDenuncia.setDenuncia(situacaoDenunciaRequestDTO.denuncia());
        return situacaoDenuncia;
    }
}
