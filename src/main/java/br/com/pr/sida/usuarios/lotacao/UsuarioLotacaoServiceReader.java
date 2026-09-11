package br.com.pr.sida.usuarios.lotacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioLotacaoServiceReader {

    private final UsuarioLotacaoRepository usuarioLotacaoRepository;

    public List<UsuarioLotacao> buscarLotacoesPorUsuario(Long idUsuario){
        return usuarioLotacaoRepository.findByUsuarioId(idUsuario);
    }
}
