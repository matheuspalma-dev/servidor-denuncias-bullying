package br.com.pr.sida.denuncia;

import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {
    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public void criarDenuncia(
            @RequestBody DenunciaRequestDTO denunciaRequestDTO
    )
    {
        denunciaService.salvarDenuncia(denunciaRequestDTO);
    }

    //rota só para teste, não será exposta na versão final
    @PostMapping("/deletar/{id}")
    public void deletarDenuncia(@PathVariable Long id) {
        denunciaService.deletarDenuncia(id);
    }
}
