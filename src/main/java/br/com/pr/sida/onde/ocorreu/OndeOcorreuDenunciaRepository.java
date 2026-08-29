package br.com.pr.sida.onde.ocorreu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OndeOcorreuDenunciaRepository extends JpaRepository<OndeOcorreuDenuncia, Long> {
}
