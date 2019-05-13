package br.com.abim.bestmeal.service.impl;

import br.com.abim.bestmeal.service.CartaoCreditoService;
import br.com.abim.bestmeal.domain.CartaoCredito;
import br.com.abim.bestmeal.repository.CartaoCreditoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CartaoCredito}.
 */
@Service
@Transactional
public class CartaoCreditoServiceImpl implements CartaoCreditoService {

    private final Logger log = LoggerFactory.getLogger(CartaoCreditoServiceImpl.class);

    private final CartaoCreditoRepository cartaoCreditoRepository;

    public CartaoCreditoServiceImpl(CartaoCreditoRepository cartaoCreditoRepository) {
        this.cartaoCreditoRepository = cartaoCreditoRepository;
    }

    /**
     * Save a cartaoCredito.
     *
     * @param cartaoCredito the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CartaoCredito save(CartaoCredito cartaoCredito) {
        log.debug("Request to save CartaoCredito : {}", cartaoCredito);
        return cartaoCreditoRepository.save(cartaoCredito);
    }

    /**
     * Get all the cartaoCreditos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CartaoCredito> findAll(Pageable pageable) {
        log.debug("Request to get all CartaoCreditos");
        return cartaoCreditoRepository.findAll(pageable);
    }


    /**
     * Get one cartaoCredito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartaoCredito> findOne(Long id) {
        log.debug("Request to get CartaoCredito : {}", id);
        return cartaoCreditoRepository.findById(id);
    }

    /**
     * Delete the cartaoCredito by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CartaoCredito : {}", id);
        cartaoCreditoRepository.deleteById(id);
    }
}
