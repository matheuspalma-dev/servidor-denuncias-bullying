package br.com.pr.sida.usuarios.lotacao.dto.request;

import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record UsuarioLotacaoRequestDTO(
        @Positive
        Long usuarioId,
        @NotNull
        UsuarioLotacaoEnum usuarioLotacaoEnum,
        @Positive
        Long entidadeId,
        @NotBlank
        String cargo,
        @NotNull
        LocalDate dataInicio,
        @NotNull
        LocalDate dataFim
) {
}
