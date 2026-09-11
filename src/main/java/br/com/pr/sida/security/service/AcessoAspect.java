package br.com.pr.sida.security.service;

import br.com.pr.sida.security.exception.NaoTemPermissaoException;
import br.com.pr.sida.usuarios.exception.InformacoesIncorretasException;
import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AcessoAspect {

    private final SecurityService securityService;

    @Before("@annotation(br.com.pr.sida.security.service.RequerPermissao)")
    public void verificarPermissao(JoinPoint joinPoint) throws NaoTemPermissaoException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] nomesParametros = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        Long denunciaId = null;
        Long escolaId = null;
        Long orgaoCompetenteId = null;
        Long entidadeId = null;
        UsuarioLotacaoEnum lotacao = null;

        if (nomesParametros != null) {
            for (int i = 0; i < nomesParametros.length; i++) {
                if ("entidadeId".equals(nomesParametros[i]) && args[i] instanceof Long){
                    entidadeId = (Long) args[i];
                } else if ("denunciaId".equals(nomesParametros[i]) && args[i] instanceof Long) {
                    denunciaId = (Long) args[i];
                    break;
                } else if("escolaId".equals(nomesParametros[i]) && args[i] instanceof Long){
                    escolaId = (Long) args[i];
                } else if("orgaoCompetenteId".equals(nomesParametros[i]) && args[i] instanceof Long){
                    orgaoCompetenteId = (Long) args[i];
                } else if ("entidadeTipo".equals(nomesParametros[i]) && args[i] instanceof UsuarioLotacaoEnum){
                    lotacao = (UsuarioLotacaoEnum) args[i];
                }
            }
        }

        if (denunciaId == null && escolaId == null && orgaoCompetenteId == null && entidadeId == null) {
            throw new InformacoesIncorretasException("Informações incorretas");
        } else {
            boolean temPermissao;

            if (entidadeId != null){
                temPermissao = securityService.podeAcessarEntidade(email, entidadeId, lotacao);
            }
            else if (denunciaId != null){
                temPermissao = securityService.temPermissaoDeAcessoDenuncia(email, denunciaId);
            } else if (escolaId != null) {
                temPermissao = securityService.temPermissaoDeAcessoEscola(email, escolaId);
            } else {
                temPermissao = securityService.temPermissaoDeAcessoOrgaoCompetente(email, orgaoCompetenteId);
            }

            if (!temPermissao) {
                throw new NaoTemPermissaoException("Você não tem permissão para acessar esta denúncia ou ela não existe.");
            }
        }
    }
}
