package br.com.pr.sida.usuarios.lotacao.dto.request;

import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record UsuarioLotacaoRequestDTO(
        @NotNull(message = "O id do usuário é obrigatório.")
        @Positive(message = "O id do usuário deve ser um número positivo valido.")
        Long usuarioId,
        @NotNull(message = "O tipo de lotação do usuário é obrigatório.")
        UsuarioLotacaoEnum usuarioLotacaoEnum,
        @NotNull(message = "O id da entidade é obrigatório.")
        @Positive(message = "O id da entidade deve ser um número positivo valido.")
        Long entidadeId,
        @NotBlank(message = "O cargo do usuário é obrigatório.")
        String cargo,
        @NotNull(message = "A data de início da lotação do usuário é obrigatória.")
        LocalDate dataInicio,
        @NotNull(message = "A data de fim da lotação do usuário é obrigatória.")
        LocalDate dataFim
) {
}
