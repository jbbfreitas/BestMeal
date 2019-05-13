package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.domain.CartaoRecompensa;
import br.com.abim.bestmeal.service.CartaoRecompensaService;
import br.com.abim.bestmeal.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.abim.bestmeal.domain.CartaoRecompensa}.
 */
@RestController
@RequestMapping("/api")
public class CartaoRecompensaResource {

    private final Logger log = LoggerFactory.getLogger(CartaoRecompensaResource.class);

    private static final String ENTITY_NAME = "cartaoRecompensa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartaoRecompensaService cartaoRecompensaService;

    public CartaoRecompensaResource(CartaoRecompensaService cartaoRecompensaService) {
        this.cartaoRecompensaService = cartaoRecompensaService;
    }

    /**
     * {@code POST  /cartao-recompensas} : Create a new cartaoRecompensa.
     *
     * @param cartaoRecompensa the cartaoRecompensa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartaoRecompensa, or with status {@code 400 (Bad Request)} if the cartaoRecompensa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cartao-recompensas")
    public ResponseEntity<CartaoRecompensa> createCartaoRecompensa(@Valid @RequestBody CartaoRecompensa cartaoRecompensa) throws URISyntaxException {
        log.debug("REST request to save CartaoRecompensa : {}", cartaoRecompensa);
        if (cartaoRecompensa.getId() != null) {
            throw new BadRequestAlertException("A new cartaoRecompensa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartaoRecompensa result = cartaoRecompensaService.save(cartaoRecompensa);
        return ResponseEntity.created(new URI("/api/cartao-recompensas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cartao-recompensas} : Updates an existing cartaoRecompensa.
     *
     * @param cartaoRecompensa the cartaoRecompensa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartaoRecompensa,
     * or with status {@code 400 (Bad Request)} if the cartaoRecompensa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartaoRecompensa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cartao-recompensas")
    public ResponseEntity<CartaoRecompensa> updateCartaoRecompensa(@Valid @RequestBody CartaoRecompensa cartaoRecompensa) throws URISyntaxException {
        log.debug("REST request to update CartaoRecompensa : {}", cartaoRecompensa);
        if (cartaoRecompensa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CartaoRecompensa result = cartaoRecompensaService.save(cartaoRecompensa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cartaoRecompensa.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cartao-recompensas} : get all the cartaoRecompensas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cartaoRecompensas in body.
     */
    @GetMapping("/cartao-recompensas")
    public ResponseEntity<List<CartaoRecompensa>> getAllCartaoRecompensas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of CartaoRecompensas");
        Page<CartaoRecompensa> page = cartaoRecompensaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cartao-recompensas/:id} : get the "id" cartaoRecompensa.
     *
     * @param id the id of the cartaoRecompensa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartaoRecompensa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cartao-recompensas/{id}")
    public ResponseEntity<CartaoRecompensa> getCartaoRecompensa(@PathVariable Long id) {
        log.debug("REST request to get CartaoRecompensa : {}", id);
        Optional<CartaoRecompensa> cartaoRecompensa = cartaoRecompensaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cartaoRecompensa);
    }

    /**
     * {@code DELETE  /cartao-recompensas/:id} : delete the "id" cartaoRecompensa.
     *
     * @param id the id of the cartaoRecompensa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cartao-recompensas/{id}")
    public ResponseEntity<Void> deleteCartaoRecompensa(@PathVariable Long id) {
        log.debug("REST request to delete CartaoRecompensa : {}", id);
        cartaoRecompensaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
