package br.com.abim.bestmeal.service;

import br.com.abim.bestmeal.domain.CartaoCredito;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CartaoCredito}.
 */
public interface CartaoCreditoService {

    /**
     * Save a cartaoCredito.
     *
     * @param cartaoCredito the entity to save.
     * @return the persisted entity.
     */
    CartaoCredito save(CartaoCredito cartaoCredito);

    /**
     * Get all the cartaoCreditos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CartaoCredito> findAll(Pageable pageable);


    /**
     * Get the "id" cartaoCredito.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CartaoCredito> findOne(Long id);

    /**
     * Delete the "id" cartaoCredito.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
