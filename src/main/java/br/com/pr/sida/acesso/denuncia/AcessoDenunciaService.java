package br.com.pr.sida.acesso.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.AcessoDenunciaRequestDTO;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.mensagem.denuncia.MensagemDenuncia;
import br.com.pr.sida.mensagem.denuncia.MensagemDenunciaService;
import br.com.pr.sida.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AcessoDenunciaService(
            AcessoDenunciaRepository acessoDenunciaRepository,
            @Qualifier("criptografarTextos") TextEncryptor textEncryptor,
            PasswordEncoder passwordEncoder,
            MensagemDenunciaService mensagemDenunciaService
    ) {
        this.acessoDenunciaRepository = acessoDenunciaRepository;
        this.textEncryptor = textEncryptor;
        this.passwordEncoder = passwordEncoder;
        this.mensagemDenunciaService = mensagemDenunciaService;
    }

    public DenunciaResponseDTO acessarDenuncia(AcessoDenunciaRequestDTO acessoDenunciaRequestDTO){
        String codigoAcesso = acessoDenunciaRequestDTO.codigoAcesso();
        String codigoAcessoCriptografado = criptografarCodigoAcesso(codigoAcesso);
        Acesso acesso = acessoDenunciaRepository.findByCodigoAcesso(codigoAcessoCriptografado)
                .orElseThrow(() -> new RuntimeException("Acesso não encontrado"));

        if (!passwordEncoder.matches(acessoDenunciaRequestDTO.senhaAcesso(), acesso.getSenhaAcesso())){
            throw new RuntimeException("Senha de acesso incorreta");
        }

        return converterDenunciaEmDTO(acesso.getDenuncia());
    }

    public void salvarAcessoDenuncia(Denuncia denuncia) {
        Acesso acesso = criarAcessoDenuncia(denuncia);
        acessoDenunciaRepository.save(acesso);
    }

    public DenunciaResponseDTO converterDenunciaEmDTO(Denuncia denuncia){
        DenunciaResponseDTO denunciaResponseDTO = new DenunciaResponseDTO();
        denunciaResponseDTO.setId(denuncia.getId());
        denunciaResponseDTO.setDataCriacao(denuncia.getDataCriacao());
        denunciaResponseDTO.setNomeMunicipio(denuncia.getNomeMunicipio());
        denunciaResponseDTO.setNomeEscola(denuncia.getNomeEscola());
        denunciaResponseDTO.setIdadeDenunciante(denuncia.getIdadeDenunciante());
        denunciaResponseDTO.setGeneroDenunciante(denuncia.getGeneroDenunciante());
        denunciaResponseDTO.setViolenciaNaEscola(denuncia.isViolenciaNaEscola());
        denunciaResponseDTO.setTipoViolencia(denuncia.getTipoViolencia());
        denunciaResponseDTO.setStatusDenuncia(denuncia.getStatusDenuncia());
        denunciaResponseDTO.setPreferenciaEnvio(denuncia.getPreferenciaEnvio());
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
        return denunciaResponseDTO;
    }

    private Acesso criarAcessoDenuncia(Denuncia denuncia){
        Acesso acesso = new Acesso();
        acesso.setDenuncia(denuncia);
        acesso.setCodigoAcesso(gerarCodigoAcesso());
        acesso.setSenhaAcesso(gerarSenhaAcesso());
        return acesso;
    }

    private String gerarCodigoAcesso() {
        String aleatorio = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String codigoAcesso;
        do {
            codigoAcesso = criptografarCodigoAcesso(aleatorio + "/" + Year.now().toString());
        } while(acessoDenunciaRepository.existsByCodigoAcesso(codigoAcesso));
        return codigoAcesso;
    }

    private String gerarSenhaAcesso() {
        String senhaAcesoAleatoria = UUID.randomUUID().toString().replace("-", "").substring(0, 7);
        return critografarSenhaAcesso(senhaAcesoAleatoria);
    }

    private String critografarSenhaAcesso(String senhaAcesso) {
        return passwordEncoder.encode(senhaAcesso);

    }

    private String criptografarCodigoAcesso(String codigoAcesso){
        return textEncryptor.encrypt(codigoAcesso);
    }
}
