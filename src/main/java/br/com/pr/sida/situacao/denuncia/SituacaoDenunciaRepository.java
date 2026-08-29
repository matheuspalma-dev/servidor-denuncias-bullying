package br.com.pr.sida.situacao.denuncia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituacaoDenunciaRepository extends JpaRepository<SituacaoDenuncia, Long> {
}
