package br.com.pr.sida.usuarios.dto.response;

import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
import br.com.pr.sida.usuarios.lotacao.dto.response.UsuarioLotacaoResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioLoginResponseDTO {
    private String nome;
    private String cpf;
    private String email;
    private List<UsuarioLotacaoResponseDTO> lotacoes;

    public boolean possuiLotacao(UsuarioLotacaoEnum lotacao){
        return  lotacoes.stream()
                .anyMatch(l -> l.getLotacao().equals(lotacao));
    }
}
