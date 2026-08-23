package br.com.pr.sida.escola;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteRepository;
import br.com.pr.sida.escola.dto.request.EscolaRequestResgisterDTO;
import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
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
        escolaResponseDTO.setId(escolaResponseDTO.getId());
        escolaResponseDTO.setNome(escolaResponseDTO.getNome());
        return escolaResponseDTO;
    }

    public void adicionarEscola(EscolaRequestResgisterDTO escolaRequestResgisterDTO) {
        Escola escola = criarEscola(escolaRequestResgisterDTO);
        escolaRepository.save(escola);
    }

    private Escola criarEscola(EscolaRequestResgisterDTO escolaRequestResgisterDTO) {
        Escola escola = new Escola();
        escola.setNome(escolaRequestResgisterDTO.nome());
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
