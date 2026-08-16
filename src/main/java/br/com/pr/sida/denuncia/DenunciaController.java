package br.com.pr.sida.denuncia;

import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.mensagem.denuncia.MensagemDenunciaService;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {
    private final DenunciaService denunciaService;
    private final MensagemDenunciaService mensagemDenunciaService;

    public DenunciaController(DenunciaService denunciaService, MensagemDenunciaService mensagemDenunciaService) {
        this.denunciaService = denunciaService;
        this.mensagemDenunciaService = mensagemDenunciaService;
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public void criarDenuncia(
            @RequestBody DenunciaRequestDTO denunciaRequestDTO
    )
    {
        denunciaService.salvarDenuncia(denunciaRequestDTO);
    }

    @PostMapping("/mensagem/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionarMensagemDenuncia(@RequestBody MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO){
        mensagemDenunciaService.salvarMensagem(mensagemDenunciaRequestDTO);
    }
}
