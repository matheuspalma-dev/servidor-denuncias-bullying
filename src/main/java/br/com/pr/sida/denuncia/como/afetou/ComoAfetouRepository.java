package br.com.pr.sida.denuncia.como.afetou;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ComoAfetouRepository extends JpaRepository<ComoAfetou, Long> {
}
