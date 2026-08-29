package br.com.pr.sida.security.service;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteReader;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteService;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaReader;
import br.com.pr.sida.escola.EscolaService;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final OrgaoCompetenteReader orgaoCompetenteReader;
    private final EscolaReader escolaReader;

    public boolean temPermissaoDeAcessoDenuncia(String email, Denuncia denuncia) {
        Escola escola = escolaReader.buscarEscolaPorEmail(email);

        OrgaoCompetente orgaoCompetente = orgaoCompetenteReader.buscarPorEmail(email);

        if (escola != null || orgaoCompetente != null) {
            for (ResponsavelDenuncia responsavelDenuncia : denuncia.getResponsavelDenuncias()) {
                if (escola != null) {
                    if (responsavelDenuncia.getEscolaResponsavel().getId() == escola.getId()) {
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
        Escola escolaAlvo = escolaReader.buscarEscolaPorIdSemExcecao(escolaId);

        if (escolaAlvo == null) {
            return false;
        }

        Escola escola = escolaReader.buscarEscolaPorEmailSemExcecao(email);

        OrgaoCompetente orgaoCompetente = orgaoCompetenteReader
                .buscarPorEmailSemExcessao(email);

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
        OrgaoCompetente orgaoCompetente = orgaoCompetenteReader
                .buscarPorEmailSemExcessao(email);

        if (orgaoCompetente == null || orgaoCompetente.getId() != orgaoCompetenteId) {
            return false;
        }

        return true;
    }

}
