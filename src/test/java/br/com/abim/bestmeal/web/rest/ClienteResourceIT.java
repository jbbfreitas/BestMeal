package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.BestMealApp;
import br.com.abim.bestmeal.domain.Cliente;
import br.com.abim.bestmeal.repository.ClienteRepository;
import br.com.abim.bestmeal.service.ClienteService;
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
 * Integration tests for the {@Link ClienteResource} REST controller.
 */
@SpringBootTest(classes = BestMealApp.class)
public class ClienteResourceIT {

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

    private static final String DEFAULT_CEP = "95z313-367";
    private static final String UPDATED_CEP = "50X812-648";

    private static final TipoLogradouro DEFAULT_TIPO_LOGRADOURO = TipoLogradouro.RUA;
    private static final TipoLogradouro UPDATED_TIPO_LOGRADOURO = TipoLogradouro.AVENIDA;

    private static final String DEFAULT_NOME_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

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

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteService);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
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
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
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
        return cliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
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
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testCliente.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testCliente.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testCliente.getPrimeiroNome()).isEqualTo(DEFAULT_PRIMEIRO_NOME);
        assertThat(testCliente.getNomeMeio()).isEqualTo(DEFAULT_NOME_MEIO);
        assertThat(testCliente.getSobreNome()).isEqualTo(DEFAULT_SOBRE_NOME);
        assertThat(testCliente.getSaudacao()).isEqualTo(DEFAULT_SAUDACAO);
        assertThat(testCliente.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testCliente.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testCliente.getTipoLogradouro()).isEqualTo(DEFAULT_TIPO_LOGRADOURO);
        assertThat(testCliente.getNomeLogradouro()).isEqualTo(DEFAULT_NOME_LOGRADOURO);
        assertThat(testCliente.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPrimeiroNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setPrimeiroNome(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNomeLogradouro(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComplementoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setComplemento(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
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
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
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
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
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

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCliente)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testCliente.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testCliente.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testCliente.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testCliente.getNomeMeio()).isEqualTo(UPDATED_NOME_MEIO);
        assertThat(testCliente.getSobreNome()).isEqualTo(UPDATED_SOBRE_NOME);
        assertThat(testCliente.getSaudacao()).isEqualTo(UPDATED_SAUDACAO);
        assertThat(testCliente.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testCliente.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testCliente.getTipoLogradouro()).isEqualTo(UPDATED_TIPO_LOGRADOURO);
        assertThat(testCliente.getNomeLogradouro()).isEqualTo(UPDATED_NOME_LOGRADOURO);
        assertThat(testCliente.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(cliente1.getId());
        assertThat(cliente1).isEqualTo(cliente2);
        cliente2.setId(2L);
        assertThat(cliente1).isNotEqualTo(cliente2);
        cliente1.setId(null);
        assertThat(cliente1).isNotEqualTo(cliente2);
    }
}
