package br.com.pr.sida.denuncia.mensagem.denuncia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MensagemDenunciaRepository extends JpaRepository<MensagemDenuncia, Long> {
}
