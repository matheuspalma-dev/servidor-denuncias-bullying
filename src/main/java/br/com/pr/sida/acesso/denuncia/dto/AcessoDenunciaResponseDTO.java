package br.com.pr.sida.acesso.denuncia.dto;

import lombok.Getter;
import lombok.Setter;

public record AcessoDenunciaResponseDTO(
        String codigoAcesso,
        String senhaAcesso
) {
}
