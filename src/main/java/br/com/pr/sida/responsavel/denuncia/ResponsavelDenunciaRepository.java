package br.com.pr.sida.responsavel.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponsavelDenunciaRepository extends JpaRepository<ResponsavelDenuncia, Long> {
    public Optional<ResponsavelDenuncia> findByDenunciaId(Long id);
    public List<ResponsavelDenuncia> findByUnidadeAtendimentoId(Long id);
}
