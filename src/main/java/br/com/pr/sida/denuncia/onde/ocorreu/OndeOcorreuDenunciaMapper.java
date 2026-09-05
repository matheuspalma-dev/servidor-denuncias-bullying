package br.com.pr.sida.denuncia.onde.ocorreu;

import br.com.pr.sida.denuncia.onde.ocorreu.dto.request.OndeOcorreuRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class OndeOcorreuDenunciaMapper {

    public OndeOcorreuDenuncia converterDTOEmEntity(OndeOcorreuRequestDTO ondeOcorreuRequestDTO){
        OndeOcorreuDenuncia ondeOcorreuDenuncia = new OndeOcorreuDenuncia();
        ondeOcorreuDenuncia.setOndeOcorreu(ondeOcorreuRequestDTO.ondeOcorreu());
        ondeOcorreuDenuncia.setDenuncia(ondeOcorreuRequestDTO.denuncia());
        return ondeOcorreuDenuncia;
    }
}
