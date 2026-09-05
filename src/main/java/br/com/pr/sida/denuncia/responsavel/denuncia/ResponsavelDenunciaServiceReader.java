package br.com.pr.sida.denuncia.responsavel.denuncia;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsavelDenunciaServiceReader {

    private final ResponsavelDenunciaRepository responsavelDenunciaRepository;

    public List<ResponsavelDenuncia> buscarDenunciasPorEscolaId(Long id){
        return responsavelDenunciaRepository.findByEscolaResponsavelIdAndEscolaVaiTerAcessoIsTrue(id);
    }

    public List<ResponsavelDenuncia> buscarDenunciasPorOrgaoCompetenteId(Long id){
        return responsavelDenunciaRepository.findByOrgaoCompetenteResponsavelId(id);
    }
}
