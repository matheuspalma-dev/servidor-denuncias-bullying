package br.com.pr.sida.praticaAcao;

import br.com.pr.sida.praticaAcao.dto.request.PraticaAcaoRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PraticaAcaoService {

    private final PraticaAcaoRepository praticaAcaoRepository;
    private final PraticaAcaoMapper praticaAcaoMapper;

    public void salvarPraticaAcao(PraticaAcaoRequestDTO praticaAcaoRequestDTO){
        PraticaAcao praticaAcao = praticaAcaoMapper.converterDTOEmEntity(praticaAcaoRequestDTO);
        praticaAcaoRepository.save(praticaAcao);
    }

    public List<QuemPratica> retornarQuemPratica(List<PraticaAcao> praticaAcaoList){
        List<QuemPratica> quemPraticaList = new ArrayList<>();
        for (PraticaAcao praticaAcao : praticaAcaoList){
            quemPraticaList.add(praticaAcao.getQuemPratica());
        }
        return quemPraticaList;
    }
}
