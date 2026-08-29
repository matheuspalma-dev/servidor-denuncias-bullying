package br.com.pr.sida.como.afetou;

import br.com.pr.sida.como.afetou.dto.request.ComoAfetouRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComoAfetouService {

    private final ComoAfetouRepository comoAfetouRepository;
    private final ComoAfetouMapper comoAfetouMapper;

    public void salvarComoAfetou(ComoAfetouRequestDTO comoAfetouRequestDTO){
        ComoAfetou comoAfetou = comoAfetouMapper.conveterDTOEmEntity(comoAfetouRequestDTO);
        comoAfetouRepository.save(comoAfetou);
    }

    public List<ComoTeAfetou> retornarComoAfetou(List<ComoAfetou> comoAfetouList){
         List<ComoTeAfetou> comoTeAfetouList = new ArrayList<>();
         for (ComoAfetou comoAfetou : comoAfetouList){
             comoTeAfetouList.add(comoAfetou.getComoTeAfetou());
         }

         return comoTeAfetouList;
    }

}
