package br.com.abim.bestmeal.repository;

import br.com.abim.bestmeal.domain.Restaurante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Restaurante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}
