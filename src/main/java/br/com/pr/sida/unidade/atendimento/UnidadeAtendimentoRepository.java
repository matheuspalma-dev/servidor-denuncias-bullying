package br.com.pr.sida.unidade.atendimento;

import br.com.pr.sida.util.TipoUnidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnidadeAtendimentoRepository extends JpaRepository<UnidadeAtendimento, Long> {
    public Optional<UnidadeAtendimento> findByNome(String nome);
    public Optional<UnidadeAtendimento> findByTipoUnidade(TipoUnidade tipoUnidade);
}
