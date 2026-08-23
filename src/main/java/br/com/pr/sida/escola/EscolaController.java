package br.com.pr.sida.escola;

import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/escolas")
public class EscolaController {
    private final EscolaService escolaService;

    public EscolaController(EscolaService escolaService) {
        this.escolaService = escolaService;
    }

    @GetMapping("/todas")
    public ResponseEntity<List<EscolaResponseDTO>> retornarTodasEscolas() {
        List<EscolaResponseDTO> escolas = escolaService.retornarTodasEscolas();
        return ResponseEntity.ok(escolas);
    }
}
