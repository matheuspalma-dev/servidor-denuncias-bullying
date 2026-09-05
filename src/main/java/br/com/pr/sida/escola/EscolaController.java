package br.com.pr.sida.escola;

import br.com.pr.sida.escola.dto.request.EscolaRequestResgisterDTO;
import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/escolas")
@RequiredArgsConstructor
public class EscolaController {

    private final EscolaService escolaService;

    @GetMapping("/todas")
    public ResponseEntity<List<EscolaResponseDTO>> retornarTodasEscolas() {
        List<EscolaResponseDTO> escolas = escolaService.retornarTodasEscolas();
        return ResponseEntity.ok(escolas);
    }

    @PostMapping("/adicionar")
    public void adicionarEscola(@RequestBody @Valid EscolaRequestResgisterDTO escolaRequestResgisterDTO) {
        escolaService.adicionarEscola(escolaRequestResgisterDTO);
    }
}
