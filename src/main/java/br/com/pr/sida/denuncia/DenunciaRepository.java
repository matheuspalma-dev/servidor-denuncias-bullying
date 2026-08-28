package br.com.pr.sida.denuncia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
}
