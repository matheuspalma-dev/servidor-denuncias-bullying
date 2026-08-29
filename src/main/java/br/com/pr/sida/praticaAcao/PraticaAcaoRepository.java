package br.com.pr.sida.praticaAcao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PraticaAcaoRepository extends JpaRepository<PraticaAcao, Long> {
}
