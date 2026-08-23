package br.com.pr.sida.acesso.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.request.AcessoDenunciaRequestDTO;
import br.com.pr.sida.acesso.denuncia.dto.response.AcessoDenunciaResponseDTO;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.mensagem.denuncia.MensagemDenuncia;
import br.com.pr.sida.mensagem.denuncia.MensagemDenunciaService;
import br.com.pr.sida.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import br.com.pr.sida.status.StatusDenuncia;
import br.com.pr.sida.status.dto.response.StatusDenunciaResponseDTO;
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
public class AcessoDenunciaService {

    private final AcessoDenunciaRepository acessoDenunciaRepository;
    private final TextEncryptor textEncryptor;
    private final PasswordEncoder passwordEncoder;
    private final MensagemDenunciaService mensagemDenunciaService;
    @Value("${sida.seguranca.crypto-hmac}") private String hmacSecretKey;

    public AcessoDenunciaService(
            AcessoDenunciaRepository acessoDenunciaRepository,
            TextEncryptor textEncryptor,
            PasswordEncoder passwordEncoder,
            MensagemDenunciaService mensagemDenunciaService
    ) {
        this.acessoDenunciaRepository = acessoDenunciaRepository;
        this.textEncryptor = textEncryptor;
        this.passwordEncoder = passwordEncoder;
        this.mensagemDenunciaService = mensagemDenunciaService;
    }

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
        return converterDenunciaEmDTO(acesso.getDenuncia());
    }


    public DenunciaResponseDTO converterDenunciaEmDTO(Denuncia denuncia){
        DenunciaResponseDTO denunciaResponseDTO = new DenunciaResponseDTO();
        denunciaResponseDTO.setId(denuncia.getId());
        denunciaResponseDTO.setDataCriacao(denuncia.getDataCriacao());
        denunciaResponseDTO.setNomeEscola(denuncia.getEscola().getNome());
        denunciaResponseDTO.setRedeEnsino(denuncia.getEscola().getRedeEnsino());
        denunciaResponseDTO.setOrgaoCompetenteNome(denuncia.getEscola().getOrgaoCompetente().getNome());
        denunciaResponseDTO.setOndeOcorreu(denuncia.getOndeOcorreu());
        denunciaResponseDTO.setTipoViolencia(denuncia.getTipoViolencia());
        denunciaResponseDTO.setRiscoAgressao(denuncia.isRiscoAgressao());
        denunciaResponseDTO.setSituacaoGrave(denuncia.isSituacaoGrave());
        denunciaResponseDTO.setViolacaoDireitos(denuncia.isViolacaoDireitos());
        List<MensagensDenunciaResponseDTO> mensagensDescriptografadas = new ArrayList<>();

        for (MensagemDenuncia mensagemDenuncia : denuncia.getMensagens()) {
            MensagensDenunciaResponseDTO mensagemDTO = new MensagensDenunciaResponseDTO();
            mensagemDTO.setId(mensagemDenuncia.getId());
            mensagemDTO.setAutorMensagem(mensagemDenuncia.getAutor());
            String mensagemDescriptografada = mensagemDenunciaService.descriptografarMensagem(mensagemDenuncia.getMensagem());
            mensagemDTO.setMensagem(mensagemDescriptografada);
            mensagemDTO.setDataCriacao(mensagemDenuncia.getDataCriacao());
            mensagensDescriptografadas.add(mensagemDTO);
        }

        denunciaResponseDTO.setMensagens(mensagensDescriptografadas);

        List<StatusDenunciaResponseDTO> statusDenunciaResponseDTOList = new ArrayList<>();

        for (StatusDenuncia statusDenuncia : denuncia.getStatusDenuncia()){
            StatusDenunciaResponseDTO statusDenunciaResponseDTO = new StatusDenunciaResponseDTO();
            statusDenunciaResponseDTO.setDataCriacao(statusDenuncia.getDataCriacao());
            statusDenunciaResponseDTO.setStatus(statusDenuncia.getStatus());
            statusDenunciaResponseDTOList.add(statusDenunciaResponseDTO);
        }

        denunciaResponseDTO.setStatusDenuncias(statusDenunciaResponseDTOList);
        return denunciaResponseDTO;
    }


}
