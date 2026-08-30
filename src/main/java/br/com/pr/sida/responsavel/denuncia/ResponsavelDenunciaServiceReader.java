package br.com.pr.sida.responsavel.denuncia;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsavelDenunciaServiceReader {

    private final ResponsavelDenunciaRepository responsavelDenunciaRepository;

    public List<ResponsavelDenuncia> listarTodasAsDenunciasResponsavel(Long responsavelId) {
        return responsavelDenunciaRepository.findAllById(Collections.singleton(responsavelId));
    }
}
