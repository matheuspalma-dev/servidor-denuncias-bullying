package br.com.pr.sida.escola;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long> {
    public Optional<Escola> findByEmail(String email);
}
