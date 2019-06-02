package br.com.abim.bestmeal.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.abim.bestmeal.domain.enumeration.TipoPessoa;

import br.com.abim.bestmeal.domain.enumeration.TipoLogradouro;

/**
 * A Restaurante.
 */
@Entity
@Table(name = "restaurante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Restaurante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Column(name = "logo", nullable = false)
    private byte[] logo;

    @Column(name = "logo_content_type", nullable = false)
    private String logoContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoPessoa tipo;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "cnpj")
    private String cnpj;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "primeiro_nome", length = 20, nullable = false)
    private String primeiroNome;

    @Size(min = 2, max = 30)
    @Column(name = "nome_meio", length = 30)
    private String nomeMeio;

    @Size(min = 2, max = 30)
    @Column(name = "sobre_nome", length = 30)
    private String sobreNome;

    @Column(name = "saudacao")
    private String saudacao;

    @Size(min = 3, max = 15)
    @Column(name = "titulo", length = 15)
    private String titulo;

    @Pattern(regexp = "^[0-9]{2}.[0-9]{3}-[0-9]{3}$")
    @Column(name = "cep")
    private String cep;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_logradouro")
    private TipoLogradouro tipoLogradouro;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nome_logradouro", length = 100, nullable = false)
    private String nomeLogradouro;

    @NotNull
    @Size(min = 0, max = 30)
    @Column(name = "complemento", length = 30, nullable = false)
    private String complemento;

    @ManyToOne
    @JsonIgnoreProperties("restaurantes")
    private Municipio municipio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Restaurante logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Restaurante logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public Restaurante tipo(TipoPessoa tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    public String getCpf() {
        return cpf;
    }

    public Restaurante cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Restaurante cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public Restaurante primeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
        return this;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getNomeMeio() {
        return nomeMeio;
    }

    public Restaurante nomeMeio(String nomeMeio) {
        this.nomeMeio = nomeMeio;
        return this;
    }

    public void setNomeMeio(String nomeMeio) {
        this.nomeMeio = nomeMeio;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public Restaurante sobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
        return this;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getSaudacao() {
        return saudacao;
    }

    public Restaurante saudacao(String saudacao) {
        this.saudacao = saudacao;
        return this;
    }

    public void setSaudacao(String saudacao) {
        this.saudacao = saudacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public Restaurante titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCep() {
        return cep;
    }

    public Restaurante cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public TipoLogradouro getTipoLogradouro() {
        return tipoLogradouro;
    }

    public Restaurante tipoLogradouro(TipoLogradouro tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
        return this;
    }

    public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getNomeLogradouro() {
        return nomeLogradouro;
    }

    public Restaurante nomeLogradouro(String nomeLogradouro) {
        this.nomeLogradouro = nomeLogradouro;
        return this;
    }

    public void setNomeLogradouro(String nomeLogradouro) {
        this.nomeLogradouro = nomeLogradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public Restaurante complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public Restaurante municipio(Municipio municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurante)) {
            return false;
        }
        return id != null && id.equals(((Restaurante) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Restaurante{" +
            "id=" + getId() +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", primeiroNome='" + getPrimeiroNome() + "'" +
            ", nomeMeio='" + getNomeMeio() + "'" +
            ", sobreNome='" + getSobreNome() + "'" +
            ", saudacao='" + getSaudacao() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", cep='" + getCep() + "'" +
            ", tipoLogradouro='" + getTipoLogradouro() + "'" +
            ", nomeLogradouro='" + getNomeLogradouro() + "'" +
            ", complemento='" + getComplemento() + "'" +
            "}";
    }
}
