package br.com.pr.sida.escola;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteRepository;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.escola.dto.request.EscolaRequestResgisterDTO;
import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
import br.com.pr.sida.util.loginDTOS.LoginRequestDTO;
import br.com.pr.sida.util.loginDTOS.LoginResponseDTO;
import br.com.pr.sida.util.mappers.DenunciaMapper;
import br.com.pr.sida.util.mappers.LoginMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EscolaService {
    private final EscolaRepository escolaRepository;
    private final OrgaoCompetenteRepository orgaoCompetenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final DenunciaMapper denunciaMapper;
    private final LoginMapper loginMapper;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        Escola escola = escolaRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Escola não encontrada"));

        if (!passwordEncoder.matches(loginRequestDTO.getSenha(), escola.getSenhaAcesso())) {
            throw new RuntimeException("Senha incorreta");
        }

        return loginMapper.devolverLoginResponseDTO(escola.getId(), escola.getNome(),escola.getEmail());
    }

    public List<DenunciaResponseDTO> acessarDenuncias(String email, long escolaId) {

        boolean temPermissao = verificarSeTemPermissao(email, escolaId);

        if (temPermissao) {

            Escola escola = escolaRepository.findById(escolaId)
                    .orElseThrow(() -> new RuntimeException("Escola não encontrada"));

            List<DenunciaResponseDTO> denunciaResponseDTOList = new ArrayList<>();

            for (Denuncia denuncia : escola.getDenuncias()) {
                DenunciaResponseDTO denunciaResponseDTO = denunciaMapper.converterDenunciaEmDTO(denuncia);
                denunciaResponseDTOList.add(denunciaResponseDTO);
            }

            return denunciaResponseDTOList;
        }
        return null;
    }

    private boolean verificarSeTemPermissao(String email, Long escolaId) {
        Escola escola = escolaRepository.findByEmail(email)
                .orElse(null);

        if (escola != null){
            if (escola.getId() == escolaId) {
                return true;
            }
        }

        OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findByEmail(email)
                .orElse(null);

        if (orgaoCompetente != null){
            Escola escolaAlvo = escolaRepository.findById(escolaId)
                    .orElseThrow(() -> new EntityNotFoundException("Escola não encontrada"));
            if (orgaoCompetente.getEscolas().contains(escolaAlvo)) {
                return true;
            }
        }

        return false;
    }

    public List<EscolaResponseDTO> retornarTodasEscolas() {
        List<Escola> escolas = escolaRepository.findAll();
        List<EscolaResponseDTO> escolaResponseDTO = new ArrayList<>();

        for (Escola escola : escolas) {
            escolaResponseDTO.add(converterEntityEmDTO(escola));
        }
        return escolaResponseDTO;
    }

    private EscolaResponseDTO converterEntityEmDTO(Escola escola){
        EscolaResponseDTO escolaResponseDTO = new EscolaResponseDTO();
        escolaResponseDTO.setId(escola.getId());
        escolaResponseDTO.setNome(escola.getNome());
        return escolaResponseDTO;
    }

    public void adicionarEscola(EscolaRequestResgisterDTO escolaRequestResgisterDTO) {
        Escola escola = criarEscola(escolaRequestResgisterDTO);
        escolaRepository.save(escola);
    }

    private Escola criarEscola(EscolaRequestResgisterDTO escolaRequestResgisterDTO) {
        Escola escola = new Escola();
        escola.setNome(escolaRequestResgisterDTO.nome());
        escola.setEmail(escolaRequestResgisterDTO.email());
        escola.setAtiva(true);
        escola.setRedeEnsino(escolaRequestResgisterDTO.redeEnsino());
        escola.setSenhaAcesso(criptografarSenha(escolaRequestResgisterDTO.senhaAcesso()));
        escola.setOrgaoCompetente(buscarOrgaoCompetentePorId(escolaRequestResgisterDTO.orgaoCompetenteId()));
        return escola;
    }

    private OrgaoCompetente buscarOrgaoCompetentePorId(Long orgaoCompetenteId) {
        return orgaoCompetenteRepository.findById(orgaoCompetenteId)
                .orElseThrow(() -> new RuntimeException("Órgão competente não encontrado"));
    }

    private String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }

}
