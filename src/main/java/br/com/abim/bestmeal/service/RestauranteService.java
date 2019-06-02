package br.com.abim.bestmeal.service;

import br.com.abim.bestmeal.domain.Restaurante;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Restaurante}.
 */
public interface RestauranteService {

    /**
     * Save a restaurante.
     *
     * @param restaurante the entity to save.
     * @return the persisted entity.
     */
    Restaurante save(Restaurante restaurante);

    /**
     * Get all the restaurantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Restaurante> findAll(Pageable pageable);


    /**
     * Get the "id" restaurante.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Restaurante> findOne(Long id);

    /**
     * Delete the "id" restaurante.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    Long countWithCpf(String cpf, Long id);

	Long countWithCnpj(String cnpj, Long id);
	Long countWithCpf(String cpf);

	Long countWithCnpj(String cnpj);

}
