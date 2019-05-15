package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.BestMealApp;
import br.com.abim.bestmeal.domain.Produto;
import br.com.abim.bestmeal.repository.ProdutoRepository;
import br.com.abim.bestmeal.service.ProdutoService;
import br.com.abim.bestmeal.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static br.com.abim.bestmeal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ProdutoResource} REST controller.
 */
@SpringBootTest(classes = BestMealApp.class)
public class ProdutoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAA";
    private static final String UPDATED_CODIGO = "BBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_UNIDADE = "AAAAAAAAAA";
    private static final String UPDATED_UNIDADE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTOQUE_MINIMO = 1;
    private static final Integer UPDATED_ESTOQUE_MINIMO = 2;

    private static final Integer DEFAULT_ESTOQUE_ATUAL = 1;
    private static final Integer UPDATED_ESTOQUE_ATUAL = 2;

    private static final LocalDate DEFAULT_DATA_ULTIMA_COMPRA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ULTIMA_COMPRA = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_VALOR_ULTIMA_COMPRA = 1D;
    private static final Double UPDATED_VALOR_ULTIMA_COMPRA = 2D;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProdutoMockMvc;

    private Produto produto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdutoResource produtoResource = new ProdutoResource(produtoService);
        this.restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produto createEntity(EntityManager em) {
        Produto produto = new Produto()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .unidade(DEFAULT_UNIDADE)
            .estoqueMinimo(DEFAULT_ESTOQUE_MINIMO)
            .estoqueAtual(DEFAULT_ESTOQUE_ATUAL)
            .dataUltimaCompra(DEFAULT_DATA_ULTIMA_COMPRA)
            .valorUltimaCompra(DEFAULT_VALOR_ULTIMA_COMPRA);
        return produto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produto createUpdatedEntity(EntityManager em) {
        Produto produto = new Produto()
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .unidade(UPDATED_UNIDADE)
            .estoqueMinimo(UPDATED_ESTOQUE_MINIMO)
            .estoqueAtual(UPDATED_ESTOQUE_ATUAL)
            .dataUltimaCompra(UPDATED_DATA_ULTIMA_COMPRA)
            .valorUltimaCompra(UPDATED_VALOR_ULTIMA_COMPRA);
        return produto;
    }

    @BeforeEach
    public void initTest() {
        produto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProduto.getUnidade()).isEqualTo(DEFAULT_UNIDADE);
        assertThat(testProduto.getEstoqueMinimo()).isEqualTo(DEFAULT_ESTOQUE_MINIMO);
        assertThat(testProduto.getEstoqueAtual()).isEqualTo(DEFAULT_ESTOQUE_ATUAL);
        assertThat(testProduto.getDataUltimaCompra()).isEqualTo(DEFAULT_DATA_ULTIMA_COMPRA);
        assertThat(testProduto.getValorUltimaCompra()).isEqualTo(DEFAULT_VALOR_ULTIMA_COMPRA);
    }

    @Test
    @Transactional
    public void createProdutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto with an existing ID
        produto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setCodigo(null);

        // Create the Produto, which fails.

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setNome(null);

        // Create the Produto, which fails.

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setUnidade(null);

        // Create the Produto, which fails.

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstoqueMinimoIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setEstoqueMinimo(null);

        // Create the Produto, which fails.

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].unidade").value(hasItem(DEFAULT_UNIDADE.toString())))
            .andExpect(jsonPath("$.[*].estoqueMinimo").value(hasItem(DEFAULT_ESTOQUE_MINIMO)))
            .andExpect(jsonPath("$.[*].estoqueAtual").value(hasItem(DEFAULT_ESTOQUE_ATUAL)))
            .andExpect(jsonPath("$.[*].dataUltimaCompra").value(hasItem(DEFAULT_DATA_ULTIMA_COMPRA.toString())))
            .andExpect(jsonPath("$.[*].valorUltimaCompra").value(hasItem(DEFAULT_VALOR_ULTIMA_COMPRA.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.unidade").value(DEFAULT_UNIDADE.toString()))
            .andExpect(jsonPath("$.estoqueMinimo").value(DEFAULT_ESTOQUE_MINIMO))
            .andExpect(jsonPath("$.estoqueAtual").value(DEFAULT_ESTOQUE_ATUAL))
            .andExpect(jsonPath("$.dataUltimaCompra").value(DEFAULT_DATA_ULTIMA_COMPRA.toString()))
            .andExpect(jsonPath("$.valorUltimaCompra").value(DEFAULT_VALOR_ULTIMA_COMPRA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduto() throws Exception {
        // Initialize the database
        produtoService.save(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        Produto updatedProduto = produtoRepository.findById(produto.getId()).get();
        // Disconnect from session so that the updates on updatedProduto are not directly saved in db
        em.detach(updatedProduto);
        updatedProduto
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .unidade(UPDATED_UNIDADE)
            .estoqueMinimo(UPDATED_ESTOQUE_MINIMO)
            .estoqueAtual(UPDATED_ESTOQUE_ATUAL)
            .dataUltimaCompra(UPDATED_DATA_ULTIMA_COMPRA)
            .valorUltimaCompra(UPDATED_VALOR_ULTIMA_COMPRA);

        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduto)))
            .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProduto.getUnidade()).isEqualTo(UPDATED_UNIDADE);
        assertThat(testProduto.getEstoqueMinimo()).isEqualTo(UPDATED_ESTOQUE_MINIMO);
        assertThat(testProduto.getEstoqueAtual()).isEqualTo(UPDATED_ESTOQUE_ATUAL);
        assertThat(testProduto.getDataUltimaCompra()).isEqualTo(UPDATED_DATA_ULTIMA_COMPRA);
        assertThat(testProduto.getValorUltimaCompra()).isEqualTo(UPDATED_VALOR_ULTIMA_COMPRA);
    }

    @Test
    @Transactional
    public void updateNonExistingProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Create the Produto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduto() throws Exception {
        // Initialize the database
        produtoService.save(produto);

        int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Delete the produto
        restProdutoMockMvc.perform(delete("/api/produtos/{id}", produto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produto.class);
        Produto produto1 = new Produto();
        produto1.setId(1L);
        Produto produto2 = new Produto();
        produto2.setId(produto1.getId());
        assertThat(produto1).isEqualTo(produto2);
        produto2.setId(2L);
        assertThat(produto1).isNotEqualTo(produto2);
        produto1.setId(null);
        assertThat(produto1).isNotEqualTo(produto2);
    }
}
