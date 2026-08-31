package br.com.pr.sida.security.service;

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

    @Before("@annotation(RequerAcesso)")
    public void verificarPermissao(JoinPoint joinPoint){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] nomesParametros = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        Long denunciaId = null;

        if (nomesParametros != null) {
            for (int i = 0; i < nomesParametros.length; i++) {
                if ("denunciaId".equals(nomesParametros[i]) && args[i] instanceof Long) {
                    denunciaId = (Long) args[i];
                    break;
                }
            }
        }

        if (denunciaId == null) {
            throw new IllegalArgumentException("Parâmetro 'denunciaId' não encontrado no método.");
        }

        boolean temPermissao = securityService.temPermissaoDeAcessoDenuncia1(email, denunciaId);

        if (!temPermissao) {
            throw new RuntimeException("Acesso Negado: Você não tem permissão para esta denúncia.");
        }
    }
}
