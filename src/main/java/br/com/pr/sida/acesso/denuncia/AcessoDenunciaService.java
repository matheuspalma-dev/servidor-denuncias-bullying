package br.com.pr.sida.acesso.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.request.AcessoDenunciaRequestDTO;
import br.com.pr.sida.acesso.denuncia.dto.response.AcessoDenunciaResponseDTO;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.security.service.SecurityService;
import br.com.pr.sida.util.mappers.DenunciaMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AcessoDenunciaService {

    private final AcessoDenunciaRepository acessoDenunciaRepository;
    private final TextEncryptor textEncryptor;
    private final PasswordEncoder passwordEncoder;
    private final DenunciaMapper denunciaMapper;
    private final SecurityService securityService;
    @Value("${sida.seguranca.crypto-hmac}") private String hmacSecretKey;

    public AcessoDenunciaResponseDTO salvarAcessoDenuncia(Denuncia denuncia) {
        Acesso acesso = criarAcessoDenuncia(denuncia);
        AcessoDenunciaResponseDTO resposta = new AcessoDenunciaResponseDTO(acesso.getCodigoAcesso(), acesso.getSenhaAcesso());
        acesso.setCodigoAcesso(criptografarCodigoAcesso(acesso.getCodigoAcesso()));
        acesso.setSenhaAcesso(critografarSenhaAcesso(acesso.getSenhaAcesso()));
        acessoDenunciaRepository.save(acesso);
        return resposta;
    }

    private Acesso criarAcessoDenuncia(Denuncia denuncia){
        Acesso acesso = new Acesso();
        acesso.setDenuncia(denuncia);
        acesso.setCodigoAcesso(gerarCodigoAcesso());
        acesso.setCodigoAcessoHash(gerarHashCodigoAcesso(acesso.getCodigoAcesso()));
        acesso.setSenhaAcesso(gerarSenhaAcesso());
        return acesso;
    }

    private String gerarCodigoAcesso() {
        String aleatorio = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String codigoAcesso = aleatorio + "/" + Year.now().toString();
        return codigoAcesso;
    }

    private String gerarHashCodigoAcesso(String codigoAcesso){
        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKey = new SecretKeySpec(
                    hmacSecretKey.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256");
            mac.init(secretKey);

            byte[] hashBytes = mac.doFinal(codigoAcesso.trim().getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hashBytes);
        } catch (Exception e){
            throw new RuntimeException("Erro ao gerar hash do código de acesso", e);
        }
    }

    private String gerarSenhaAcesso() {
        String senhaAceso = UUID.randomUUID().toString().replace("-", "").substring(0, 7);
        return senhaAceso;
    }

    private String critografarSenhaAcesso(String senhaAcesso) {
        return passwordEncoder.encode(senhaAcesso);

    }

    private String criptografarCodigoAcesso(String codigoAcesso){
        String codigoAcessoCriptografado;
        do {
            codigoAcessoCriptografado = textEncryptor.encrypt(codigoAcesso);
        } while(acessoDenunciaRepository.existsByCodigoAcesso(codigoAcessoCriptografado));
        return codigoAcessoCriptografado;
    }

    public DenunciaResponseDTO acessarDenuncia(AcessoDenunciaRequestDTO acessoDenunciaRequestDTO){
        String codigoAcesso = acessoDenunciaRequestDTO.codigoAcesso();
        String codigoAcessoCriptografado = gerarHashCodigoAcesso(codigoAcesso);
        Acesso acesso = acessoDenunciaRepository.findByCodigoAcessoHash(codigoAcessoCriptografado)
                .orElseThrow(() -> new RuntimeException("Acesso não encontrado"));

        if (!passwordEncoder.matches(acessoDenunciaRequestDTO.senhaAcesso(), acesso.getSenhaAcesso())){
            throw new RuntimeException("Senha de acesso incorreta");
        }

        System.out.println("True");
        return denunciaMapper.converterDenunciaEmDTO(acesso.getDenuncia());
    }

    public DenunciaResponseDTO acessoDenuncia(String email, String codigoAcesso){
        Acesso acesso = acessoDenunciaRepository.findByCodigoAcessoHash(gerarHashCodigoAcesso(codigoAcesso))
                .orElseThrow(() -> new RuntimeException("Acesso não encontrado"));

        Denuncia denuncia = acesso.getDenuncia();

        boolean temPermissao = securityService.temPermissaoDeAcessoDenuncia(email, denuncia);

        if (!temPermissao){
            throw new BadCredentialsException("Usuário não tem permissão para acessar essa denúncia");
        }
        DenunciaResponseDTO denunciaResponseDTO = denunciaMapper.converterDenunciaEmDTO(denuncia);
        return denunciaResponseDTO;
    }
}
