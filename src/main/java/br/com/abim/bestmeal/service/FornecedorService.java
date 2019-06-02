package br.com.abim.bestmeal.service;

import br.com.abim.bestmeal.domain.Fornecedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Fornecedor}.
 */
public interface FornecedorService {

    /**
     * Save a fornecedor.
     *
     * @param fornecedor the entity to save.
     * @return the persisted entity.
     */
    Fornecedor save(Fornecedor fornecedor);

    /**
     * Get all the fornecedors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Fornecedor> findAll(Pageable pageable);


    /**
     * Get the "id" fornecedor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Fornecedor> findOne(Long id);

    /**
     * Delete the "id" fornecedor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    public Long countWithCpf(String cpf, Long id);

    public Long countWithCnpj(String cnpj, Long id);

    public Long countWithCpf(String cpf);

    public Long countWithCnpj(String cnpj);
}
