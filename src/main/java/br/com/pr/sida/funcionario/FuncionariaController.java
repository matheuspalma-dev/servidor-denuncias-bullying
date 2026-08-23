package br.com.pr.sida.funcionario;

import br.com.pr.sida.funcionario.dto.FuncionarioLoginRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionarios")
public class FuncionariaController {

    private final FuncionarioService funcionarioService;
    public FuncionariaController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/login")
    public void login(@RequestBody FuncionarioLoginRequestDTO funcionarioLoginRequestDTO){
        funcionarioService.login(funcionarioLoginRequestDTO);
    }
}
