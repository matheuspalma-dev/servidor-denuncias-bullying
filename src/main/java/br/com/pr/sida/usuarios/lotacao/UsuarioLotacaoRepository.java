package br.com.pr.sida.usuarios.lotacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface UsuarioLotacaoRepository extends JpaRepository<UsuarioLotacao, Long> {

    public List<UsuarioLotacao> findByUsuarioId(Long usuarioId);
}
