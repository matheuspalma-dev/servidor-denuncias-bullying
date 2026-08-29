package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.OrgaoCompetente.dto.request.OrgaoCompetenteRegisterDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrgaoCompetenteService {

    private final OrgaoCompetenteRepository orgaoCompetenteRepository;
    private final PasswordEncoder passwordEncoder;

    public void registrarOrgaoCompetente(OrgaoCompetenteRegisterDTO orgaoCompetenteRegisterDTO){
        OrgaoCompetente orgaoCompetente = criarOrgaoCompetente(orgaoCompetenteRegisterDTO);
        orgaoCompetenteRepository.save(orgaoCompetente);
    }

    public OrgaoCompetente criarOrgaoCompetente(OrgaoCompetenteRegisterDTO orgaoCompetenteRegisterDTO){
        OrgaoCompetente orgaoCompetente = new OrgaoCompetente();
        orgaoCompetente.setNome(orgaoCompetenteRegisterDTO.nome());
        orgaoCompetente.setTipoOrgaoCompetente(orgaoCompetenteRegisterDTO.tipoOrgaoCompetente());
        orgaoCompetente.setNumero(orgaoCompetenteRegisterDTO.numero());
        orgaoCompetente.setEmail(orgaoCompetenteRegisterDTO.email());
        orgaoCompetente.setSenhaAcesso(criptografarSenha(orgaoCompetenteRegisterDTO.senhaAcesso()));

        return orgaoCompetente;
    }

    private String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }

    public OrgaoCompetente buscarOrgaoCompetentePorId(Long id) {
        return orgaoCompetenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Órgão competente não encontrado"));
    }

    public OrgaoCompetente buscarOrgaoCompetentePorEmail(String email) {
        return orgaoCompetenteRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Órgão competente não encontrado"));
    }

    public OrgaoCompetente buscarOrgaoCompetentePorEmailSemExcecao(String email) {
        return orgaoCompetenteRepository.findByEmail(email)
                .orElse(null);
    }

    public OrgaoCompetente buscarOrgaoCompetentePorTipoDeUnidade(TipoOrgaoCompetente tipoOrgaoCompetente) {
        return orgaoCompetenteRepository.findByTipoOrgaoCompetente(tipoOrgaoCompetente)
                .orElseThrow(() -> new EntityNotFoundException("Órgão competente não encontrado"));
    }

}
