package br.com.pr.sida.security.jwt;

import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;

public record UsuarioAutenticado(
        String email,
        Long entidadeId,
        UsuarioLotacaoEnum lotacao,
        String enderecoIp,
        String userAgent
) {
}
