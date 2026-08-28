package br.com.pr.sida.security.service;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteService;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaService;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final OrgaoCompetenteService orgaoCompetenteService;
    private final EscolaService escolaService;

    public boolean temPermissaoDeAcessoDenuncia(String email, Denuncia denuncia) {
        Escola escola = escolaService.buscarEscolaPorEmail(email);

        OrgaoCompetente orgaoCompetente = orgaoCompetenteService.buscarOrgaoCompetentePorEmailSemExcecao(email);

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
        Escola escolaAlvo = escolaService.buscarEscolaPorIdSemExcecao(escolaId);

        if (escolaAlvo == null) {
            return false;
        }

        Escola escola = escolaService.buscarEscolaPorEmailSemExcecao(email);

        OrgaoCompetente orgaoCompetente = orgaoCompetenteService
                .buscarOrgaoCompetentePorEmailSemExcecao(email);

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
        OrgaoCompetente orgaoCompetente = orgaoCompetenteService
                .buscarOrgaoCompetentePorEmailSemExcecao(email);

        if (orgaoCompetente == null || orgaoCompetente.getId() != orgaoCompetenteId) {
            return false;
        }

        return true;
    }

}
