package br.com.abim.bestmeal.service.impl;

import br.com.abim.bestmeal.service.CartaoRecompensaService;
import br.com.abim.bestmeal.domain.CartaoRecompensa;
import br.com.abim.bestmeal.repository.CartaoRecompensaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CartaoRecompensa}.
 */
@Service
@Transactional
public class CartaoRecompensaServiceImpl implements CartaoRecompensaService {

    private final Logger log = LoggerFactory.getLogger(CartaoRecompensaServiceImpl.class);

    private final CartaoRecompensaRepository cartaoRecompensaRepository;

    public CartaoRecompensaServiceImpl(CartaoRecompensaRepository cartaoRecompensaRepository) {
        this.cartaoRecompensaRepository = cartaoRecompensaRepository;
    }

    /**
     * Save a cartaoRecompensa.
     *
     * @param cartaoRecompensa the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CartaoRecompensa save(CartaoRecompensa cartaoRecompensa) {
        log.debug("Request to save CartaoRecompensa : {}", cartaoRecompensa);
        return cartaoRecompensaRepository.save(cartaoRecompensa);
    }

    /**
     * Get all the cartaoRecompensas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CartaoRecompensa> findAll(Pageable pageable) {
        log.debug("Request to get all CartaoRecompensas");
        return cartaoRecompensaRepository.findAll(pageable);
    }


    /**
     * Get one cartaoRecompensa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartaoRecompensa> findOne(Long id) {
        log.debug("Request to get CartaoRecompensa : {}", id);
        return cartaoRecompensaRepository.findById(id);
    }

    /**
     * Delete the cartaoRecompensa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CartaoRecompensa : {}", id);
        cartaoRecompensaRepository.deleteById(id);
    }

    @Override
    public Page<CartaoRecompensa> findAllClienteCartaoRecompensa(Long id, Pageable pageable) {
        log.debug("Request to get all  CartaoRecompensa of Cliente: {}", id);
        return cartaoRecompensaRepository.findAllClienteCartaoRecompensa(id, pageable);
    }
}
