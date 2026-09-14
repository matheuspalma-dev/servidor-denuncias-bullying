package br.com.pr.sida.municipio;

import br.com.pr.sida.municipio.dto.request.MunicipioRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/municipios")
@RequiredArgsConstructor
public class MunicipioController {

    private final MunicipioService municipioService;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarMunicipio(@RequestBody @Valid MunicipioRequestDTO municipioRequestDTO) {
        municipioService.cadastrarMunicipio(municipioRequestDTO);
    }
}
