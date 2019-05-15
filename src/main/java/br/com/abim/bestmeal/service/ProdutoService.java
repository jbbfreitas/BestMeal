package br.com.abim.bestmeal.service;

import br.com.abim.bestmeal.domain.Produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Produto}.
 */
public interface ProdutoService {

    /**
     * Save a produto.
     *
     * @param produto the entity to save.
     * @return the persisted entity.
     */
    Produto save(Produto produto);

    /**
     * Get all the produtos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Produto> findAll(Pageable pageable);


    /**
     * Get the "id" produto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Produto> findOne(Long id);

    /**
     * Delete the "id" produto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
