package br.com.pr.sida.security.service;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteRepository;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaRepository;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final OrgaoCompetenteRepository orgaoCompetenteRepository;
    private final EscolaRepository escolaRepository;

    public boolean temPermissaoDeAcessoDenuncia(String email, Denuncia denuncia) {
        Escola escola = escolaRepository.findByEmail(email)
                .orElse(null);

        OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findByEmail(email)
                .orElse(null);

        if (escola != null || orgaoCompetente != null) {
            for (ResponsavelDenuncia responsavelDenuncia : denuncia.getResponsavelDenuncias()) {
                if (escola != null) {
                    if (responsavelDenuncia.getEscolaResponsavelId().getId() == escola.getId()) {
                        return true;
                    }
                }

                if (orgaoCompetente != null) {
                    if (responsavelDenuncia.getOrgaoCompetenteResponsavel().getId() == orgaoCompetente.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean temPermissaoDeAcessoEscola(String email, Long escolaId) {
        Escola escolaAlvo = escolaRepository.findById(escolaId)
                .orElse(null);

        if (escolaAlvo == null) {
            return false;
        }

        Escola escola = escolaRepository.findByEmail(email)
                .orElse(null);

        OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findByEmail(email)
                .orElse(null);

        if (escola != null) {
            if (escola.getId() == escolaId) {
                return true;
            }
        } else if (orgaoCompetente != null) {
            if (orgaoCompetente.getEscolas().contains(escolaAlvo)) {
                return true;
            }

        }
        return false;
    }

    public boolean temPermissaoDeAcessoOrgaoCompetente(String email, Long orgaoCompetenteId) {
        OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findByEmail(email)
                .orElse(null);

        if (orgaoCompetente == null || orgaoCompetente.getId() != orgaoCompetenteId) {
            return false;
        }

        return true;
    }

}
