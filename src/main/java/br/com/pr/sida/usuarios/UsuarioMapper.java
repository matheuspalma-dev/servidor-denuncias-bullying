package br.com.pr.sida.usuarios;

import br.com.pr.sida.usuarios.dto.request.UsuarioResgisterRequestDTO;
import br.com.pr.sida.usuarios.dto.response.UsuarioLoginResponseDTO;
import br.com.pr.sida.usuarios.lotacao.dto.response.UsuarioLotacaoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
class UsuarioMapper {

    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    public Usuario converterDTOEmEntity(UsuarioResgisterRequestDTO usuarioResgisterRequestDTO){
        Usuario usuario = new Usuario();
        usuario.setId(gerarId());
        usuario.setNome(usuarioResgisterRequestDTO.nome());
        usuario.setEmail(usuarioResgisterRequestDTO.email());
        usuario.setCpf(usuarioResgisterRequestDTO.cpf());
        usuario.setSenha(criptografarSenha(usuarioResgisterRequestDTO.senha()));
        return usuario;
    }

    public UsuarioLoginResponseDTO converterEntityEmDTO(Usuario usuario, List<UsuarioLotacaoResponseDTO> lotacoes){
        UsuarioLoginResponseDTO usuarioLoginResponseDTO = new UsuarioLoginResponseDTO();
        usuarioLoginResponseDTO.setNome(usuario.getNome());
        usuarioLoginResponseDTO.setCpf(usuario.getCpf());
        usuarioLoginResponseDTO.setEmail(usuario.getEmail());
        usuarioLoginResponseDTO.setLotacoes(lotacoes);
        return usuarioLoginResponseDTO;
    }

    private String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }

    private Long gerarId() {
        return random.nextLong();
    }
}
