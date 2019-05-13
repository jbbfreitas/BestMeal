package br.com.abim.bestmeal.repository;

import br.com.abim.bestmeal.domain.CartaoRecompensa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CartaoRecompensa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartaoRecompensaRepository extends JpaRepository<CartaoRecompensa, Long> {

}
