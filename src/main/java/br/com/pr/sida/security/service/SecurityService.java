package br.com.pr.sida.security.service;

import br.com.pr.sida.orgao.competente.OrgaoCompetente;
import br.com.pr.sida.orgao.competente.OrgaoCompetenteServiceReader;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaServiceReader;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaServiceReader;
import br.com.pr.sida.denuncia.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.security.jwt.UsuarioAutenticado;
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

    public boolean temPermissaoDeAcessoDenuncia(UsuarioAutenticado usuarioAutenticado, Long denunciaId) {
        Escola escola = null;
        OrgaoCompetente orgaoCompetente = null;
        if (usuarioAutenticado.lotacao() == UsuarioLotacaoEnum.REDE_ENSINO){
            escola = escolaServiceReader.buscarEscolaPorId(usuarioAutenticado.entidadeId());
        } else { // Orgao Competente
            orgaoCompetente = orgaoCompetenteServiceReader.buscarPorId(usuarioAutenticado.entidadeId());
        }

        Denuncia denuncia = denunciaServiceReader.buscarDenunciaPorId(denunciaId);

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


    public boolean temPermissaoDeAcessoEscola(UsuarioAutenticado usuarioAutenticado, Long escolaId) {
        Escola escolaAlvo = escolaServiceReader.buscarEscolaPorIdSemExcecao(escolaId);

        if (escolaAlvo == null) {
            return false;
        }

        Escola escola = null;
        OrgaoCompetente orgaoCompetente = null;

        if (usuarioAutenticado.lotacao() == UsuarioLotacaoEnum.REDE_ENSINO){
            escola = escolaServiceReader.buscarEscolaPorId(usuarioAutenticado.entidadeId());
        } else { // Orgao Competente
            orgaoCompetente = orgaoCompetenteServiceReader.buscarPorId(usuarioAutenticado.entidadeId());
        }

        if (escola != null) {
            if (Objects.equals(escola.getId(), escolaId)) {
                return true;
            }
        } else if (orgaoCompetente != null) {
            if (orgaoCompetente.getEscolas().contains(escolaAlvo)) {
                return true;
            }

        }
        return false;
    }

    public boolean temPermissaoDeAcessoOrgaoCompetente(UsuarioAutenticado usuarioAutenticado, Long orgaoCompetenteId) {
        OrgaoCompetente orgaoCompetente = orgaoCompetenteServiceReader.buscarPorId(usuarioAutenticado.entidadeId());

        if (orgaoCompetente == null || !Objects.equals(orgaoCompetente.getId(), orgaoCompetenteId)) {
            return false;
        }

        return true;
    }

    public boolean podeAcessarEntidade(UsuarioAutenticado usuarioAutenticado, Long entidadeId){
        Usuario usuario = usuarioServiceReader.buscarUsuarioPorEmail(usuarioAutenticado.email());
        if (usuarioAutenticado.lotacao() == UsuarioLotacaoEnum.REDE_ENSINO) {
            Escola escola = escolaServiceReader.buscarEscolaPorId(entidadeId);


            for (UsuarioLotacao usuarioLotacao : usuario.getLotacoes()){
                if (Objects.equals(usuarioLotacao.getEntidadeId(), escola.getId()) && usuarioLotacao.getLotacao() == UsuarioLotacaoEnum.REDE_ENSINO){
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
