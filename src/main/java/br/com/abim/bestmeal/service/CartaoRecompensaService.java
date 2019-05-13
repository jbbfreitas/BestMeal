package br.com.abim.bestmeal.service;

import br.com.abim.bestmeal.domain.CartaoRecompensa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CartaoRecompensa}.
 */
public interface CartaoRecompensaService {

    /**
     * Save a cartaoRecompensa.
     *
     * @param cartaoRecompensa the entity to save.
     * @return the persisted entity.
     */
    CartaoRecompensa save(CartaoRecompensa cartaoRecompensa);

    /**
     * Get all the cartaoRecompensas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CartaoRecompensa> findAll(Pageable pageable);


    /**
     * Get the "id" cartaoRecompensa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CartaoRecompensa> findOne(Long id);

    /**
     * Delete the "id" cartaoRecompensa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
