package br.com.pr.sida.status.dto.response;

import br.com.pr.sida.util.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StatusDenunciaResponseDTO {
    private LocalDate dataCriacao;
    private Status status;
}
