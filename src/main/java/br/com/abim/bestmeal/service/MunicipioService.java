package br.com.abim.bestmeal.service;

import br.com.abim.bestmeal.domain.Municipio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Municipio}.
 */
public interface MunicipioService {

    /**
     * Save a municipio.
     *
     * @param municipio the entity to save.
     * @return the persisted entity.
     */
    Municipio save(Municipio municipio);

    /**
     * Get all the municipios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Municipio> findAll(Pageable pageable);


    /**
     * Get the "id" municipio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Municipio> findOne(Long id);

    /**
     * Delete the "id" municipio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
