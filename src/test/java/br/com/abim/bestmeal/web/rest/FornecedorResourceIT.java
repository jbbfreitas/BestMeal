package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.BestMealApp;
import br.com.abim.bestmeal.domain.Fornecedor;
import br.com.abim.bestmeal.repository.FornecedorRepository;
import br.com.abim.bestmeal.service.FornecedorService;
import br.com.abim.bestmeal.service.PessoaService;
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
import java.util.List;

import static br.com.abim.bestmeal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.abim.bestmeal.domain.enumeration.TipoPessoa;
import br.com.abim.bestmeal.domain.enumeration.TipoLogradouro;
/**
 * Integration tests for the {@Link FornecedorResource} REST controller.
 */
@SpringBootTest(classes = BestMealApp.class)
public class FornecedorResourceIT {

    private static final TipoPessoa DEFAULT_TIPO = TipoPessoa.FISICA;
    private static final TipoPessoa UPDATED_TIPO = TipoPessoa.JURIDICA;

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMEIRO_NOME = "AAAAAAAAAA";
    private static final String UPDATED_PRIMEIRO_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_MEIO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MEIO = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRE_NOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRE_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SAUDACAO = "AAAAAAAAAA";
    private static final String UPDATED_SAUDACAO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "20<616-668";
    private static final String UPDATED_CEP = "00:302-548";

    private static final TipoLogradouro DEFAULT_TIPO_LOGRADOURO = TipoLogradouro.RUA;
    private static final TipoLogradouro UPDATED_TIPO_LOGRADOURO = TipoLogradouro.AVENIDA;

    private static final String DEFAULT_NOME_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private PessoaService pessoaService;
  
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

    private MockMvc restFornecedorMockMvc;

    private Fornecedor fornecedor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FornecedorResource fornecedorResource = new FornecedorResource(fornecedorService, pessoaService);
        this.restFornecedorMockMvc = MockMvcBuilders.standaloneSetup(fornecedorResource)
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
    public static Fornecedor createEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .tipo(DEFAULT_TIPO)
            .cpf(DEFAULT_CPF)
            .cnpj(DEFAULT_CNPJ)
            .primeiroNome(DEFAULT_PRIMEIRO_NOME)
            .nomeMeio(DEFAULT_NOME_MEIO)
            .sobreNome(DEFAULT_SOBRE_NOME)
            .saudacao(DEFAULT_SAUDACAO)
            .titulo(DEFAULT_TITULO)
            .cep(DEFAULT_CEP)
            .tipoLogradouro(DEFAULT_TIPO_LOGRADOURO)
            .nomeLogradouro(DEFAULT_NOME_LOGRADOURO)
            .complemento(DEFAULT_COMPLEMENTO);
        return fornecedor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createUpdatedEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .tipo(UPDATED_TIPO)
            .cpf(UPDATED_CPF)
            .cnpj(UPDATED_CNPJ)
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .nomeMeio(UPDATED_NOME_MEIO)
            .sobreNome(UPDATED_SOBRE_NOME)
            .saudacao(UPDATED_SAUDACAO)
            .titulo(UPDATED_TITULO)
            .cep(UPDATED_CEP)
            .tipoLogradouro(UPDATED_TIPO_LOGRADOURO)
            .nomeLogradouro(UPDATED_NOME_LOGRADOURO)
            .complemento(UPDATED_COMPLEMENTO);
        return fornecedor;
    }

    @BeforeEach
    public void initTest() {
        fornecedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createFornecedor() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isCreated());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate + 1);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testFornecedor.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testFornecedor.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testFornecedor.getPrimeiroNome()).isEqualTo(DEFAULT_PRIMEIRO_NOME);
        assertThat(testFornecedor.getNomeMeio()).isEqualTo(DEFAULT_NOME_MEIO);
        assertThat(testFornecedor.getSobreNome()).isEqualTo(DEFAULT_SOBRE_NOME);
        assertThat(testFornecedor.getSaudacao()).isEqualTo(DEFAULT_SAUDACAO);
        assertThat(testFornecedor.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testFornecedor.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testFornecedor.getTipoLogradouro()).isEqualTo(DEFAULT_TIPO_LOGRADOURO);
        assertThat(testFornecedor.getNomeLogradouro()).isEqualTo(DEFAULT_NOME_LOGRADOURO);
        assertThat(testFornecedor.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void createFornecedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor with an existing ID
        fornecedor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPrimeiroNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setPrimeiroNome(null);

        // Create the Fornecedor, which fails.

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeMeioIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setNomeMeio(null);

        // Create the Fornecedor, which fails.

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSobreNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setSobreNome(null);

        // Create the Fornecedor, which fails.

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setTitulo(null);

        // Create the Fornecedor, which fails.

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setNomeLogradouro(null);

        // Create the Fornecedor, which fails.

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComplementoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setComplemento(null);

        // Create the Fornecedor, which fails.

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFornecedors() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList
        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].primeiroNome").value(hasItem(DEFAULT_PRIMEIRO_NOME.toString())))
            .andExpect(jsonPath("$.[*].nomeMeio").value(hasItem(DEFAULT_NOME_MEIO.toString())))
            .andExpect(jsonPath("$.[*].sobreNome").value(hasItem(DEFAULT_SOBRE_NOME.toString())))
            .andExpect(jsonPath("$.[*].saudacao").value(hasItem(DEFAULT_SAUDACAO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].tipoLogradouro").value(hasItem(DEFAULT_TIPO_LOGRADOURO.toString())))
            .andExpect(jsonPath("$.[*].nomeLogradouro").value(hasItem(DEFAULT_NOME_LOGRADOURO.toString())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedor.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.toString()))
            .andExpect(jsonPath("$.primeiroNome").value(DEFAULT_PRIMEIRO_NOME.toString()))
            .andExpect(jsonPath("$.nomeMeio").value(DEFAULT_NOME_MEIO.toString()))
            .andExpect(jsonPath("$.sobreNome").value(DEFAULT_SOBRE_NOME.toString()))
            .andExpect(jsonPath("$.saudacao").value(DEFAULT_SAUDACAO.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()))
            .andExpect(jsonPath("$.tipoLogradouro").value(DEFAULT_TIPO_LOGRADOURO.toString()))
            .andExpect(jsonPath("$.nomeLogradouro").value(DEFAULT_NOME_LOGRADOURO.toString()))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFornecedor() throws Exception {
        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFornecedor() throws Exception {
        // Initialize the database
        fornecedorService.save(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor
        Fornecedor updatedFornecedor = fornecedorRepository.findById(fornecedor.getId()).get();
        // Disconnect from session so that the updates on updatedFornecedor are not directly saved in db
        em.detach(updatedFornecedor);
        updatedFornecedor
            .tipo(UPDATED_TIPO)
            .cpf(UPDATED_CPF)
            .cnpj(UPDATED_CNPJ)
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .nomeMeio(UPDATED_NOME_MEIO)
            .sobreNome(UPDATED_SOBRE_NOME)
            .saudacao(UPDATED_SAUDACAO)
            .titulo(UPDATED_TITULO)
            .cep(UPDATED_CEP)
            .tipoLogradouro(UPDATED_TIPO_LOGRADOURO)
            .nomeLogradouro(UPDATED_NOME_LOGRADOURO)
            .complemento(UPDATED_COMPLEMENTO);

        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFornecedor)))
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testFornecedor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testFornecedor.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testFornecedor.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testFornecedor.getNomeMeio()).isEqualTo(UPDATED_NOME_MEIO);
        assertThat(testFornecedor.getSobreNome()).isEqualTo(UPDATED_SOBRE_NOME);
        assertThat(testFornecedor.getSaudacao()).isEqualTo(UPDATED_SAUDACAO);
        assertThat(testFornecedor.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testFornecedor.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testFornecedor.getTipoLogradouro()).isEqualTo(UPDATED_TIPO_LOGRADOURO);
        assertThat(testFornecedor.getNomeLogradouro()).isEqualTo(UPDATED_NOME_LOGRADOURO);
        assertThat(testFornecedor.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Create the Fornecedor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFornecedor() throws Exception {
        // Initialize the database
        fornecedorService.save(fornecedor);

        int databaseSizeBeforeDelete = fornecedorRepository.findAll().size();

        // Delete the fornecedor
        restFornecedorMockMvc.perform(delete("/api/fornecedors/{id}", fornecedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fornecedor.class);
        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setId(1L);
        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setId(fornecedor1.getId());
        assertThat(fornecedor1).isEqualTo(fornecedor2);
        fornecedor2.setId(2L);
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
        fornecedor1.setId(null);
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
    }
}
