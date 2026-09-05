package br.com.pr.sida.acesso.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.request.AcessoDenunciaRequestDTO;
import br.com.pr.sida.acesso.denuncia.dto.response.AcessoDenunciaResponseDTO;
import br.com.pr.sida.acesso.denuncia.exception.ErroInternoException;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaService;
import br.com.pr.sida.denuncia.DenunciaServiceReader;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResumoResponseDTO;
import br.com.pr.sida.login.exceptions.InformacoesIncorretasException;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenunciaServiceReader;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AcessoDenunciaService {

    private final AcessoDenunciaRepository acessoDenunciaRepository;
    private final TextEncryptor textEncryptor;
    private final PasswordEncoder passwordEncoder;
    private final DenunciaService denunciaService;
    private final DenunciaServiceReader denunciaServiceReader;
    private final ResponsavelDenunciaServiceReader responsavelDenunciaServiceReader;
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
            throw new ErroInternoException("Erro ao gerar código de acesso");
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
                .orElseThrow(() -> new InformacoesIncorretasException("Informações de acesso incorretas"));

        if (!passwordEncoder.matches(acessoDenunciaRequestDTO.senhaAcesso(), acesso.getSenhaAcesso())){
            throw new InformacoesIncorretasException("Informações de acesso incorretas");
        }

        System.out.println("True");
        return denunciaService.retornarDenunciaResponseDTO(acesso.getDenuncia());
    }

    public DenunciaResponseDTO acessoDenuncia(Long denunciaId){
        Denuncia denuncia = denunciaServiceReader.buscarDenunciaPorId(denunciaId);
        return denunciaService.retornarDenunciaResponseDTO(denuncia);
    }

    public List<DenunciaResumoResponseDTO> acessarDenunciasEscola(String email, Long escolaId){
        List<Denuncia> denunciaList = denunciaServiceReader.buscarDenunciasPorEscolaId(escolaId);

        return denunciaServiceReader.retornarDenunciasResumo(denunciaList);
    }

    public List<DenunciaResumoResponseDTO> acessarDenunciasOrgaoCompetente(String email, Long orgaoCompetenteId){
        List<ResponsavelDenuncia> responsavelList = responsavelDenunciaServiceReader.listarTodasAsDenunciasResponsavel(orgaoCompetenteId);
        List<Denuncia> denunciaList = converterResponsavelDenunciaParaDenuncia(responsavelList);

        return denunciaServiceReader.retornarDenunciasResumo(denunciaList);
    }

    private List<Denuncia> converterResponsavelDenunciaParaDenuncia(List<ResponsavelDenuncia> responsavelList){
        List<Denuncia> denunciaList = new ArrayList<>();
        for (ResponsavelDenuncia responsavelDenuncia : responsavelList){
            denunciaList.add(responsavelDenuncia.getDenuncia());
        }
        return denunciaList;
    }
}
