package br.com.pr.sida.denuncia.mensagem.denuncia.dto.response;

import br.com.pr.sida.denuncia.mensagem.denuncia.AutorMensagem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MensagensDenunciaResponseDTO {
    private Long id;
    private AutorMensagem autorMensagem;
    private String mensagem;
    private LocalDate dataCriacao;
}
