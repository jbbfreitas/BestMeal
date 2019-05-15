package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.domain.Produto;
import br.com.abim.bestmeal.service.ProdutoService;
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
 * REST controller for managing {@link br.com.abim.bestmeal.domain.Produto}.
 */
@RestController
@RequestMapping("/api")
public class ProdutoResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoResource.class);

    private static final String ENTITY_NAME = "produto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdutoService produtoService;

    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    /**
     * {@code POST  /produtos} : Create a new produto.
     *
     * @param produto the produto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produto, or with status {@code 400 (Bad Request)} if the produto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produtos")
    public ResponseEntity<Produto> createProduto(@Valid @RequestBody Produto produto) throws URISyntaxException {
        log.debug("REST request to save Produto : {}", produto);
        if (produto.getId() != null) {
            throw new BadRequestAlertException("A new produto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Produto result = produtoService.save(produto);
        return ResponseEntity.created(new URI("/api/produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produtos} : Updates an existing produto.
     *
     * @param produto the produto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produto,
     * or with status {@code 400 (Bad Request)} if the produto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produtos")
    public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto produto) throws URISyntaxException {
        log.debug("REST request to update Produto : {}", produto);
        if (produto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Produto result = produtoService.save(produto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /produtos} : get all the produtos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produtos in body.
     */
    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> getAllProdutos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Produtos");
        Page<Produto> page = produtoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /produtos/:id} : get the "id" produto.
     *
     * @param id the id of the produto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
        log.debug("REST request to get Produto : {}", id);
        Optional<Produto> produto = produtoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produto);
    }

    /**
     * {@code DELETE  /produtos/:id} : delete the "id" produto.
     *
     * @param id the id of the produto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        log.debug("REST request to delete Produto : {}", id);
        produtoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
