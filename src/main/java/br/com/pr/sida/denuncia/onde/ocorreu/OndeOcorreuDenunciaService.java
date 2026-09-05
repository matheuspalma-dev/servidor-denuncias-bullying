package br.com.pr.sida.denuncia.onde.ocorreu;

import br.com.pr.sida.denuncia.enums.OndeOcorreu;
import br.com.pr.sida.denuncia.onde.ocorreu.dto.request.OndeOcorreuRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OndeOcorreuDenunciaService {

    private final OndeOcorreuDenunciaRepository ondeOcorreuDenunciaRepository;
    private final OndeOcorreuDenunciaMapper ondeOcorreuDenunciaMapper;

    public void adicionarOndeOcorreuDenuncia(OndeOcorreuRequestDTO ondeOcorreuRequestDTO){
        OndeOcorreuDenuncia ondeOcorreuDenuncia = ondeOcorreuDenunciaMapper.converterDTOEmEntity(ondeOcorreuRequestDTO);
        ondeOcorreuDenunciaRepository.save(ondeOcorreuDenuncia);
    }

    public List<OndeOcorreu> listarOndeOcorreuDenuncia(List<OndeOcorreuDenuncia> ondeOcorreuDenunciaList){
        List<OndeOcorreu> ondeOcorreuList = new ArrayList<>();
        for (OndeOcorreuDenuncia ondeOcorreuDenuncia : ondeOcorreuDenunciaList){
            ondeOcorreuList.add(ondeOcorreuDenuncia.getOndeOcorreu());
        }
        return ondeOcorreuList;
    }
}
