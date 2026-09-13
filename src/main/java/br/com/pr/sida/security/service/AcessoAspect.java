package br.com.pr.sida.security.service;

import br.com.pr.sida.security.exception.NaoTemPermissaoException;
import br.com.pr.sida.security.jwt.UsuarioAutenticado;
import br.com.pr.sida.usuarios.exception.InformacoesIncorretasException;
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

    @Before("@annotation(requerPermissao)")
    public void verificarPermissao(JoinPoint joinPoint, RequerPermissao requerPermissao) throws NaoTemPermissaoException {
        UsuarioAutenticado usuarioAutenticado = (UsuarioAutenticado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TipoRecurso tipoRecurso = requerPermissao.tipoRecurso();
        String nomeDoIdGuardado = requerPermissao.id();
        Long id = -1L;

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] nomeParametros = methodSignature.getParameterNames();
        Object[] argumentos = joinPoint.getArgs();

        for (int i = 0; i < nomeParametros.length; i++){
            if (nomeParametros[i].equals(nomeDoIdGuardado)){
                id = (Long) argumentos[i];
            }
        }

        boolean temPermissao = switch (tipoRecurso){
            case ACESSO_INFORMACOES_DENUNCIA -> securityService.temPermissaoDeAcessoDenuncia(usuarioAutenticado, id);
            case ACESSO_INFORMACOES_ESCOLA -> securityService.temPermissaoDeAcessoEscola(usuarioAutenticado, id);
            case ACESSO_INFORMACOES_ORGAO_COMPETENTE -> securityService.temPermissaoDeAcessoOrgaoCompetente(usuarioAutenticado, id);
            case ACESSO_INFORMACOES_ENTIDADE -> securityService.podeAcessarEntidade(usuarioAutenticado, id);
        };


        if (!temPermissao) {
            throw new NaoTemPermissaoException("Você não tem permissão para acessar esta denúncia ou ela não existe.");
        }

    }
}
