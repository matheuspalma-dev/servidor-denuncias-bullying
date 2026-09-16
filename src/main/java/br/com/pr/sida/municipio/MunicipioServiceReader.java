package br.com.pr.sida.municipio;

import br.com.pr.sida.municipio.exception.MunicipioNaoFoiCadastradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MunicipioServiceReader {
    private final MunicipioRepository municipioRepository;

    public Municipio buscarPorCodigoIbge(Long codigoIbge) {
        return municipioRepository.findById(codigoIbge)
                .orElseThrow(() -> new MunicipioNaoFoiCadastradoException("Município não foi cadastrado ou não existe."));
    }
}
