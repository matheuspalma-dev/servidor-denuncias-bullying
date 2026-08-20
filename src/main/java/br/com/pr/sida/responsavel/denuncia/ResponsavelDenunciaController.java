package br.com.pr.sida.responsavel.denuncia;

import br.com.pr.sida.responsavel.denuncia.dto.request.ResponsavelDenunciaRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responsavel-denuncia")
public class ResponsavelDenunciaController {
    private final ResponsavelDenunciaService responsavelDenunciaService;

    public ResponsavelDenunciaController(ResponsavelDenunciaService responsavelDenunciaService) {
        this.responsavelDenunciaService = responsavelDenunciaService;
    }

    @PostMapping("/mudar-responsavel")
    @ResponseStatus(HttpStatus.OK)
    public void mudarResponsavelDenuncia(@RequestBody ResponsavelDenunciaRequestDTO responsavelDenunciaRequestDTO) {
        responsavelDenunciaService.mudarResponsavelDenuncia(responsavelDenunciaRequestDTO);
    }
}
