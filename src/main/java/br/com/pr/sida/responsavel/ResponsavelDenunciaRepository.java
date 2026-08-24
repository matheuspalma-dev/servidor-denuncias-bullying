package br.com.pr.sida.responsavel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsavelDenunciaRepository extends JpaRepository<ResponsavelDenuncia, Long> {
}
