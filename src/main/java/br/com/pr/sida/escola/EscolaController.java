package br.com.pr.sida.escola;

import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.escola.dto.request.EscolaRequestResgisterDTO;
import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/adicionar")
    public void adicionarEscola(@RequestBody EscolaRequestResgisterDTO escolaRequestResgisterDTO) {
        escolaService.adicionarEscola(escolaRequestResgisterDTO);
    }

    @GetMapping("/{escolaId}/denuncias")
    public ResponseEntity<List<DenunciaResponseDTO>> acessarDenuncias(@PathVariable long escolaId) {
        List<DenunciaResponseDTO> denuncias = escolaService.acessarDenuncias(escolaId);
        return ResponseEntity.ok(denuncias);
    }
}
