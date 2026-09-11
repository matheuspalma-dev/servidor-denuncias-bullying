package br.com.pr.sida.security.service;

import br.com.pr.sida.orgao.competente.OrgaoCompetente;
import br.com.pr.sida.orgao.competente.OrgaoCompetenteServiceReader;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaServiceReader;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaServiceReader;
import br.com.pr.sida.denuncia.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.usuarios.Usuario;
import br.com.pr.sida.usuarios.UsuarioServiceReader;
import br.com.pr.sida.usuarios.lotacao.UsuarioLotacao;
import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final OrgaoCompetenteServiceReader orgaoCompetenteServiceReader;
    private final EscolaServiceReader escolaServiceReader;
    private final DenunciaServiceReader denunciaServiceReader;
    private final UsuarioServiceReader usuarioServiceReader;

    public boolean temPermissaoDeAcessoDenuncia(String email, Long denunciaId) {
        Escola escola = escolaServiceReader.buscarEscolaPorEmailSemExcecao(email);

        Denuncia denuncia = denunciaServiceReader.buscarDenunciaPorId(denunciaId);

        OrgaoCompetente orgaoCompetente = buscarOrgaoCompetentePorEmailSemExcecao(email);

        if (escola != null || orgaoCompetente != null) {
            for (ResponsavelDenuncia responsavelDenuncia : denuncia.getResponsavelDenuncias()) {
                if (escola != null) {
                    if (Objects.equals(responsavelDenuncia.getEscolaResponsavel().getId(), escola.getId()) && denuncia.isEscolaVaiTerAcesso()) {
                        return true;
                    }
                }

                if (orgaoCompetente != null) {
                    if (Objects.equals(responsavelDenuncia.getOrgaoCompetenteResponsavel().getId(), orgaoCompetente.getId())) {
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

        OrgaoCompetente orgaoCompetente = buscarOrgaoCompetentePorEmailSemExcecao(email);

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
        OrgaoCompetente orgaoCompetente = buscarOrgaoCompetentePorEmailSemExcecao(email);

        if (orgaoCompetente == null || !Objects.equals(orgaoCompetente.getId(), orgaoCompetenteId)) {
            return false;
        }

        return true;
    }

    private OrgaoCompetente buscarOrgaoCompetentePorEmailSemExcecao(String email) {
        return orgaoCompetenteServiceReader.buscarPorEmailSemExcessao(email);
    }

    public boolean podeAcessarEntidade(String email, Long entidadeId, UsuarioLotacaoEnum lotacao){
        Usuario usuario = usuarioServiceReader.buscarUsuarioPorEmail(email);
        if (lotacao == UsuarioLotacaoEnum.ESCOLA) {
            Escola escola = escolaServiceReader.buscarEscolaPorId(entidadeId);


            for (UsuarioLotacao usuarioLotacao : usuario.getLotacoes()){
                if (Objects.equals(usuarioLotacao.getEntidadeId(), escola.getId()) && usuarioLotacao.getLotacao() == UsuarioLotacaoEnum.ESCOLA){
                    return true;
                }
            }

            return false;
        } else { // Orgão Competente
            OrgaoCompetente orgaoCompetente = orgaoCompetenteServiceReader.buscarPorId(entidadeId);


            for (UsuarioLotacao usuarioLotacao : usuario.getLotacoes()){
                if (Objects.equals(usuarioLotacao.getEntidadeId(), orgaoCompetente.getId()) && usuarioLotacao.getLotacao() == UsuarioLotacaoEnum.ORGAO_COMPETENTE){
                    return true;
                }
            }

            return false;
        }
    }

}
