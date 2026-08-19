package br.com.pr.sida.unidade.atendimento;

import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.responsavel.denuncia.dto.response.ResponsavelDenunciaEncaminhamentoDTO;
import br.com.pr.sida.unidade.atendimento.dto.request.UnidadeAtendimentoRegisterDTO;
import br.com.pr.sida.unidade.atendimento.dto.request.UnidadeAtendimentoRequestDTO;
import br.com.pr.sida.unidade.atendimento.dto.response.UnidadeAtendimentoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/acessar/encaminhamentos/{unidadeId}")
    public ResponseEntity<List<ResponsavelDenunciaEncaminhamentoDTO>> acessarDenunciasEncaminhamento(@PathVariable Long unidadeId){
        List<ResponsavelDenunciaEncaminhamentoDTO> encaminhamentos = unidadeAtendimentoService.acessarDenunciasEncaminhamento(unidadeId);
        return ResponseEntity.ok().body(encaminhamentos);
    }

    @GetMapping("/acessar/encaminhadas/{unidadeId}")
    public ResponseEntity<List<DenunciaResponseDTO>> acessarDenunciasEncaminhadas(@PathVariable Long unidadeId){
        List<DenunciaResponseDTO> encaminhadas = unidadeAtendimentoService.acessarDenunciasEncaminhadas(unidadeId);
        return ResponseEntity.ok().body(encaminhadas);
    }
}
