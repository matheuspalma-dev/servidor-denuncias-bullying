package br.com.pr.sida.security.service;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteServiceReader;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaServiceReader;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final OrgaoCompetenteServiceReader orgaoCompetenteServiceReader;
    private final EscolaServiceReader escolaServiceReader;

    public boolean temPermissaoDeAcessoDenuncia(String email, Denuncia denuncia) {
        Escola escola = escolaServiceReader.buscarEscolaPorEmailSemExcecao(email);

        OrgaoCompetente orgaoCompetente = orgaoCompetenteServiceReader.buscarPorEmailSemExcessao(email);

        if (escola != null || orgaoCompetente != null) {
            for (ResponsavelDenuncia responsavelDenuncia : denuncia.getResponsavelDenuncias()) {
                if (escola != null) {
                    if (responsavelDenuncia.getEscolaResponsavel().getId() == escola.getId() && responsavelDenuncia.isEscolaVaiTerAcesso()) {
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
        Escola escolaAlvo = escolaServiceReader.buscarEscolaPorIdSemExcecao(escolaId);

        if (escolaAlvo == null) {
            return false;
        }

        Escola escola = escolaServiceReader.buscarEscolaPorEmailSemExcecao(email);

        OrgaoCompetente orgaoCompetente = orgaoCompetenteServiceReader
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
        OrgaoCompetente orgaoCompetente = orgaoCompetenteServiceReader
                .buscarPorEmailSemExcessao(email);

        if (orgaoCompetente == null || orgaoCompetente.getId() != orgaoCompetenteId) {
            return false;
        }

        return true;
    }

}
