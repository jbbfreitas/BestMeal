package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.domain.Fornecedor;
import br.com.abim.bestmeal.service.FornecedorService;
import br.com.abim.bestmeal.service.PessoaService;
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
 * REST controller for managing {@link br.com.abim.bestmeal.domain.Fornecedor}.
 */
@RestController
@RequestMapping("/api")
public class FornecedorResource {

    private final Logger log = LoggerFactory.getLogger(FornecedorResource.class);

    private static final String ENTITY_NAME = "fornecedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FornecedorService fornecedorService;
    private final PessoaService pessoaService;


    public FornecedorResource(FornecedorService fornecedorService,PessoaService pessoaService) {
        this.fornecedorService = fornecedorService;
        this.pessoaService = pessoaService;
    }

    /**
     * {@code POST  /fornecedors} : Create a new fornecedor.
     *
     * @param fornecedor the fornecedor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fornecedor, or with status {@code 400 (Bad Request)} if the fornecedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fornecedors")
    public ResponseEntity<Fornecedor> createFornecedor(@Valid @RequestBody Fornecedor fornecedor) throws URISyntaxException {
        log.debug("REST request to save Fornecedor : {}", fornecedor);
        if (fornecedor.getId() != null) {
            throw new BadRequestAlertException("A new fornecedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fornecedor result = fornecedorService.save(fornecedor);
        return ResponseEntity.created(new URI("/api/fornecedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fornecedors} : Updates an existing fornecedor.
     *
     * @param fornecedor the fornecedor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedor,
     * or with status {@code 400 (Bad Request)} if the fornecedor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fornecedor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fornecedors")
    public ResponseEntity<Fornecedor> updateFornecedor(@Valid @RequestBody Fornecedor fornecedor) throws URISyntaxException {
        log.debug("REST request to update Fornecedor : {}", fornecedor);
        if (fornecedor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fornecedor result = fornecedorService.save(fornecedor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fornecedors} : get all the fornecedors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fornecedors in body.
     */
    @GetMapping("/fornecedors")
    public ResponseEntity<List<Fornecedor>> getAllFornecedors(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Fornecedors");
        Page<Fornecedor> page = fornecedorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fornecedors/:id} : get the "id" fornecedor.
     *
     * @param id the id of the fornecedor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fornecedor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fornecedors/{id}")
    public ResponseEntity<Fornecedor> getFornecedor(@PathVariable Long id) {
        log.debug("REST request to get Fornecedor : {}", id);
        Optional<Fornecedor> fornecedor = fornecedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fornecedor);
    }

    /**
     * {@code DELETE  /fornecedors/:id} : delete the "id" fornecedor.
     *
     * @param id the id of the fornecedor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fornecedors/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        log.debug("REST request to delete Fornecedor : {}", id);
        fornecedorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/fornecedors/withcpf")
    public ResponseEntity<Long> countAllPessoasWithCPF(@RequestParam MultiValueMap<String, String> queryParams) {
        log.debug("REST request to get Pessoas with  same CPF");
        log.debug("--->O valor do id é:" + queryParams.get("id").get(0));
        Long numero = 0L;
        try {
            String cpf = queryParams.get("cpf").get(0);
            Long id = Long.parseLong(queryParams.get("id").get(0));

            log.debug("REST request to get Pessoas with  same CPF");
            if (id == 0) {
                numero = pessoaService.countWithCpf(cpf);
            } else {
                numero = pessoaService.countWithCpf(cpf, id);
            }
        } catch (Exception e) {
            numero = 0L;
        }
        return ResponseEntity.ok(numero);
    }

    @GetMapping("/fornecedors/withcnpj")
    public ResponseEntity<Long> countAllPessoasWithCNPJ(@RequestParam MultiValueMap<String, String> queryParams) {
        log.debug("REST request to get Pessoas with  same CNPJ");
        log.debug("--->O valor do id é:" + queryParams.get("id").get(0));
        Long numero = 0L;
        try {
            String cnpj = queryParams.get("cnpj").get(0);
            Long id = Long.parseLong(queryParams.get("id").get(0));

            log.debug("REST request to get Pessoas with  same CNPJ");
            if (id == 0) {
                numero = pessoaService.countWithCnpj(cnpj);
            } else {
                numero = pessoaService.countWithCnpj(cnpj, id);
            }
        } catch (Exception e) {
            numero = 0L;
        }
        return ResponseEntity.ok(numero);
    }

    

}
