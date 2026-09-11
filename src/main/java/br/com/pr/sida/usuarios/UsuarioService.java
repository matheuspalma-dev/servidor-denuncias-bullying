package br.com.pr.sida.usuarios;

import br.com.pr.sida.usuarios.exception.InformacoesIncorretasException;
import br.com.pr.sida.usuarios.dto.request.UsuarioLoginRequestDTO;
import br.com.pr.sida.usuarios.dto.request.UsuarioResgisterRequestDTO;
import br.com.pr.sida.usuarios.dto.response.UsuarioLoginResponseDTO;
import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoService;
import br.com.pr.sida.usuarios.lotacao.dto.request.UsuarioLotacaoRequestDTO;
import br.com.pr.sida.usuarios.lotacao.dto.response.UsuarioLotacaoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioLotacaoService usuarioLotacaoService;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioServiceReader usuarioServiceReader;
    private final PasswordEncoder encoder;

    public void cadastrarUsuario(UsuarioResgisterRequestDTO usuarioResgisterRequestDTO){
        Usuario usuario = usuarioMapper.converterDTOEmEntity(usuarioResgisterRequestDTO);
        usuarioRepository.save(usuario);
    }

    public void adicionarLotacao(UsuarioLotacaoRequestDTO usuarioLotacaoRequestDTO){
        usuarioLotacaoService.adicionarLotacao(usuarioLotacaoRequestDTO);
    }

    public UsuarioLoginResponseDTO login(UsuarioLoginRequestDTO usuarioLoginRequestDTO) {
        Usuario usuario = usuarioServiceReader.buscarUsuarioPorEmail(usuarioLoginRequestDTO.email());

        if (!encoder.matches(usuarioLoginRequestDTO.senha(), usuario.getSenha())) {
            throw new InformacoesIncorretasException("Informações incorretas");
        }

        List<UsuarioLotacaoResponseDTO> lotacoes = usuarioLotacaoService.buscarLotacoesPorUsuario(usuario.getId());

        return usuarioMapper.converterEntityEmDTO(usuario, lotacoes);
    }
}
