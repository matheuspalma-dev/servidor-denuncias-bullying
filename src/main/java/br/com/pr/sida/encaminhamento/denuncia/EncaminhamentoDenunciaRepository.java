package br.com.pr.sida.encaminhamento.denuncia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncaminhamentoDenunciaRepository extends JpaRepository<EncaminhamentoDenuncia, Long> {
}
