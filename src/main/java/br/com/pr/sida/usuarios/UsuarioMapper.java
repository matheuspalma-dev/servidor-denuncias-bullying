package br.com.pr.sida.usuarios;

import br.com.pr.sida.security.tirar.xss.TirarXssService;
import br.com.pr.sida.usuarios.dto.request.UsuarioResgisterRequestDTO;
import br.com.pr.sida.usuarios.dto.response.UsuarioLoginResponseDTO;
import br.com.pr.sida.usuarios.lotacao.dto.response.UsuarioLotacaoResponseDTO;
import br.com.pr.sida.shared.Criptografia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
class UsuarioMapper {

    private final Criptografia criptografia;
    private final Random random = new Random();
    private final TirarXssService tirarXssService;
    private final UsuarioRepository usuarioRepository;

    public Usuario converterDTOEmEntity(UsuarioResgisterRequestDTO usuarioResgisterRequestDTO){
        Usuario usuario = new Usuario();
        usuario.setId(gerarId());
        usuario.setNome(tirarXss(usuarioResgisterRequestDTO.nome()));
        usuario.setEmail(usuarioResgisterRequestDTO.email());
        usuario.setCpf(tirarXss(usuarioResgisterRequestDTO.cpf()));
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
        return criptografia.criptografarSenhas(senha);
    }

    private Long gerarId() {
        Long id;
        do{
            id = random.nextLong();
        }
        while (id <= 0 || usuarioRepository.existsById(id));
        return id;
    }

    private String tirarXss(String entrada){
        return tirarXssService.tirarXss(entrada);
    }
}
