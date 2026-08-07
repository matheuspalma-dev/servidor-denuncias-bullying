package br.com.pr.sida.usuario;

import br.com.pr.sida.autentificacao.AutentificacaoService;
import br.com.pr.sida.usuario.dto.request.UsuarioLoginRequestDTO;
import br.com.pr.sida.usuario.dto.request.UsuarioRegisterRequestDTO;
import br.com.pr.sida.usuario.dto.response.CadastroResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService, AutentificacaoService autentificacaoService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<CadastroResponseDTO> cadastrarUsuario(@RequestBody UsuarioRegisterRequestDTO usuarioRegisterRequestDTO){
        CadastroResponseDTO cadastroResponseDTO = usuarioService.cadastrarUsuario(usuarioRegisterRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cadastroResponseDTO);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody UsuarioLoginRequestDTO usuarioLoginRequestDTO){
        usuarioService.login(usuarioLoginRequestDTO);
    }

    @PostMapping("/login/verificacao")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void verificarCodigo(@RequestParam String email, @RequestParam String codigo){
        usuarioService.verificarCodigo(email, codigo);
    }

    @DeleteMapping("/deletar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario(@RequestParam Long id){
        usuarioService.deletarUsuario(id);
    }
}
