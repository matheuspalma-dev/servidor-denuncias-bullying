package br.com.pr.sida.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusDenunciaRepository extends JpaRepository<StatusDenuncia, Long> {
}
