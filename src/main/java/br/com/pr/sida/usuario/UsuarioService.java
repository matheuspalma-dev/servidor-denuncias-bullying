package br.com.pr.sida.usuario;

import br.com.pr.sida.autentificacao.AutentificacaoService;
import br.com.pr.sida.usuario.dto.request.UsuarioLoginRequestDTO;
import br.com.pr.sida.usuario.dto.request.UsuarioRegisterRequestDTO;
import br.com.pr.sida.usuario.dto.response.CadastroResponseDTO;
import br.com.pr.sida.util.Atividade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Random;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TextEncryptor textEncryptor;
    private final AutentificacaoService autentificacaoService;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            @Qualifier("criptografarTextos") TextEncryptor textEncryptor,
            AutentificacaoService autentificacaoService
    )
    {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.textEncryptor = textEncryptor;
        this.autentificacaoService = autentificacaoService;
    }

    public void verificarCodigo(String email, String codigo){
        String emailHash = gerarHashEmail(email.trim().toLowerCase());

        Usuario usuario = usuarioRepository.findByEmailHash(emailHash)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!autentificacaoService.verificarCodigo(usuario.getCodigoVerificacao(), codigo)){
            throw new RuntimeException("Código de verificação incorreto");
        }
    }

    // funções relacionadas a login
    public void login(UsuarioLoginRequestDTO usuarioLoginRequestDTO){
        String emailHash = gerarHashEmail(usuarioLoginRequestDTO.email().trim().toLowerCase());

        Usuario usuario = usuarioRepository.findByEmailHash(emailHash)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(usuarioLoginRequestDTO.senha(), usuario.getSenha())){
            throw new RuntimeException("Senha incorreta");
        }
    }

    // funções relacionadas a cadastro
    public CadastroResponseDTO cadastrarUsuario(UsuarioRegisterRequestDTO usuarioRegisterRequestDTO) {
        Usuario usuario = converterDTOParaUsuario(usuarioRegisterRequestDTO);
        String codigoSecreto = autentificacaoService.gerarCodigoSecreto();
        usuario.setCodigoVerificacao(codigoSecreto);
        usuarioRepository.save(usuario);
        String qrCodeUrl = autentificacaoService.getQrCodeUrl(usuarioRegisterRequestDTO.nome(), codigoSecreto);
        return new CadastroResponseDTO(qrCodeUrl);
    }

    private Usuario converterDTOParaUsuario(UsuarioRegisterRequestDTO usuarioRegisterRequestDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(gerarId());
        usuario.setNome(criptografarTexto(usuarioRegisterRequestDTO.nome()));
        usuario.setEmail(criptografarTexto(usuarioRegisterRequestDTO.email()));
        usuario.setEmailHash(gerarHashEmail(usuarioRegisterRequestDTO.email().trim().toLowerCase()));
        usuario.setSenha(critografarSenha(usuarioRegisterRequestDTO.senha()));
        usuario.setAnoNascimento(usuarioRegisterRequestDTO.anoNascimento());
        usuario.setStatus(Atividade.ATIVA);
        usuario.setGenero(usuarioRegisterRequestDTO.genero());

        return usuario;
    }

    private Long gerarId() {
        Random random = new Random();
        Long id = random.nextLong();

        do {
            id = random.nextLong();
        } while (usuarioRepository.existsById(id) || id < 0);

        System.out.println("ID gerado: " + id);
        return id;
    }

    // funções relacionadas a criptografia

    private String critografarSenha(String senha){
        return passwordEncoder.encode(senha);
    }

    private String criptografarTexto(String texto){
        return textEncryptor.encrypt(texto);
    }

    private String gerarHashEmail(String email){
        try {
            MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");

            byte[] hash = algoritmo.digest(
                    email.trim()
                            .toLowerCase()
                            .getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException erro) {
            throw new RuntimeException(erro);
        }
    }

    public void deletarUsuario(Long id){
        if (!usuarioRepository.existsById(id)){
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
