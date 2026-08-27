package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.OrgaoCompetente.dto.request.OrgaoCompetenteRegisterDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.security.service.SecurityService;
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
    private final SecurityService securityService;

    public List<DenunciaResponseDTO> acessarDenuncias(String email, Long orgaoCompetenteId) {
        boolean temPermissao = securityService.temPermissaoDeAcessoOrgaoCompetente(email, orgaoCompetenteId);

        if (temPermissao) {
            OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findById(orgaoCompetenteId)
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
