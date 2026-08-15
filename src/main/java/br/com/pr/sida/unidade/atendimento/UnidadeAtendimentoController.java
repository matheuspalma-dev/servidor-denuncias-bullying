package br.com.pr.sida.unidade.atendimento;

import br.com.pr.sida.unidade.atendimento.dto.request.UnidadeAtendimentoRegisterDTO;
import br.com.pr.sida.unidade.atendimento.dto.request.UnidadeAtendimentoRequestDTO;
import br.com.pr.sida.unidade.atendimento.dto.response.UnidadeAtendimentoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unidade-atendimento")
public class UnidadeAtendimentoController {

    private final UnidadeAtendimentoService unidadeAtendimentoService;

    public UnidadeAtendimentoController(UnidadeAtendimentoService unidadeAtendimentoService) {
        this.unidadeAtendimentoService = unidadeAtendimentoService;
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastro(UnidadeAtendimentoRegisterDTO unidadeAtendimentoRegisterDTO){
        unidadeAtendimentoService.cadastro(unidadeAtendimentoRegisterDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UnidadeAtendimentoResponseDTO> login(@RequestBody UnidadeAtendimentoRequestDTO unidadeAtendimentoRequestDTO){
        UnidadeAtendimentoResponseDTO unidadeAtendimentoResponseDTO = unidadeAtendimentoService.login(unidadeAtendimentoRequestDTO);
        if (unidadeAtendimentoResponseDTO != null){
            return ResponseEntity.accepted().body(unidadeAtendimentoResponseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
