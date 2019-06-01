package br.com.abim.bestmeal.service.impl;

import br.com.abim.bestmeal.service.FornecedorService;
import br.com.abim.bestmeal.domain.Fornecedor;
import br.com.abim.bestmeal.repository.FornecedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fornecedor}.
 */
@Service
@Transactional
public class FornecedorServiceImpl implements FornecedorService {

    private final Logger log = LoggerFactory.getLogger(FornecedorServiceImpl.class);

    private final FornecedorRepository fornecedorRepository;

    public FornecedorServiceImpl(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    /**
     * Save a fornecedor.
     *
     * @param fornecedor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Fornecedor save(Fornecedor fornecedor) {
        log.debug("Request to save Fornecedor : {}", fornecedor);
        return fornecedorRepository.save(fornecedor);
    }

    /**
     * Get all the fornecedors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Fornecedor> findAll(Pageable pageable) {
        log.debug("Request to get all Fornecedors");
        return fornecedorRepository.findAll(pageable);
    }


    /**
     * Get one fornecedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Fornecedor> findOne(Long id) {
        log.debug("Request to get Fornecedor : {}", id);
        return fornecedorRepository.findById(id);
    }

    /**
     * Delete the fornecedor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fornecedor : {}", id);
        fornecedorRepository.deleteById(id);
    }
}
