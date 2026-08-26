package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.OrgaoCompetente.dto.request.OrgaoCompetenteRegisterDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.util.loginDTOS.LoginRequestDTO;
import br.com.pr.sida.util.loginDTOS.LoginResponseDTO;
import br.com.pr.sida.util.mappers.DenunciaMapper;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.util.mappers.LoginMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrgaoCompetenteService {

    private final OrgaoCompetenteRepository orgaoCompetenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final DenunciaMapper denunciaMapper;
    private final LoginMapper loginMapper;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Órgão competente não encontrado"));

        if (!passwordEncoder.matches(loginRequestDTO.getSenha(), orgaoCompetente.getSenhaAcesso())) {
            throw new RuntimeException("Senha incorreta");
        }

        return loginMapper.devolverLoginResponseDTO(orgaoCompetente.getId(), orgaoCompetente.getNome(), orgaoCompetente.getEmail());
    }

    public List<DenunciaResponseDTO> acessarDenuncias(String email, Long id) {
        boolean temPermissao = temPermissaoDeAcesso(email, id);

        if (temPermissao) {
            OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Órgão competente não encontrado"));

            List<DenunciaResponseDTO> denuncias = new ArrayList<>();

            for (ResponsavelDenuncia responsavelDenuncia : orgaoCompetente.getDenunciasResponsaveis()) {
                DenunciaResponseDTO denunciaResponseDTO = denunciaMapper.converterDenunciaEmDTO(responsavelDenuncia.getDenuncia());
                denuncias.add(denunciaResponseDTO);
            }
            return denuncias;
        }
        return null;
    }

    private boolean temPermissaoDeAcesso(String email, Long id){
        OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Órgão competente não encontrado"));

        if (orgaoCompetente.getId() != id) {
            throw new RuntimeException("Acesso negado: você não tem permissão para acessar as denúncias deste órgão competente.");
        }

        return true;
    }

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

}
