package br.com.abim.bestmeal.service;

import br.com.abim.bestmeal.domain.Menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Menu}.
 */
public interface MenuService {

    /**
     * Save a menu.
     *
     * @param menu the entity to save.
     * @return the persisted entity.
     */
    Menu save(Menu menu);

    /**
     * Get all the menus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Menu> findAll(Pageable pageable);


    /**
     * Get the "id" menu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Menu> findOne(Long id);

    /**
     * Delete the "id" menu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
