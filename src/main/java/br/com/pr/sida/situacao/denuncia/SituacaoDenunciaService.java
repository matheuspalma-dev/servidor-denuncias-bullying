package br.com.pr.sida.situacao.denuncia;

import br.com.pr.sida.situacao.denuncia.dto.request.SituacaoDenunciaRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SituacaoDenunciaService {

    private final SituacaoDenunciaRepository situacaoDenunciaRepository;
    private final SituacaoDenunciaMapper situacaoDenunciaMapper;

    public void adicionarSituacaoDenuncia(SituacaoDenunciaRequestDTO situacaoDenunciaRequestDTO) {
        SituacaoDenuncia situacaoDenuncia = situacaoDenunciaMapper.converterDTOEmEntity(situacaoDenunciaRequestDTO);
        situacaoDenunciaRepository.save(situacaoDenuncia);
    }

    public List<SituacaoDenunciada> retornarSituacoesDenunciadas(List<SituacaoDenuncia> situacaoDenunciaList){
        List<SituacaoDenunciada> situacaoDenunciadaList = new ArrayList<>();
        for (SituacaoDenuncia situacaoDenuncia : situacaoDenunciaList){
            situacaoDenunciadaList.add(situacaoDenuncia.getSituacaoDenunciada());
        }
        return  situacaoDenunciadaList;
    }

}
