package br.com.pr.sida.acesso.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.request.AcessoDenunciaRequestDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acesso/denuncia")
public class AcessoDenunciaController {

    private final AcessoDenunciaService acessoDenunciaService;

    public AcessoDenunciaController(AcessoDenunciaService acessoDenunciaService) {
        this.acessoDenunciaService = acessoDenunciaService;
    }

    // sem token
    @PostMapping("/acessar")
    public ResponseEntity<DenunciaResponseDTO> acessarDenuncia(
            @RequestBody AcessoDenunciaRequestDTO acessoDenunciaRequestDTO
    )
    {
        DenunciaResponseDTO denuncia = acessoDenunciaService.acessarDenuncia(acessoDenunciaRequestDTO);
        return ResponseEntity.ok().body(denuncia);
    }
}
