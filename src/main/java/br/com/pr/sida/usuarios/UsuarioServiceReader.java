package br.com.pr.sida.usuarios;

import br.com.pr.sida.usuarios.exception.UsuarioNaoExisteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceReader {

    private final UsuarioRepository usuarioRepository;

    public Usuario buscarUsuarioPorId(Long idUsuario){
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNaoExisteException("Usuário não encontrado"));
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioNaoExisteException("Usuário não encontrado"));
    }
}
