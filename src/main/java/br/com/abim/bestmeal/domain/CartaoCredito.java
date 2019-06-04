package br.com.abim.bestmeal.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.abim.bestmeal.domain.enumeration.Bandeira;

/**
 * A CartaoCredito.
 */
@Entity
@Table(name = "cartao_credito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CartaoCredito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 10, max = 40)
    @Column(name = "nome_cartao", length = 40, nullable = false)
    private String nomeCartao;

    @Enumerated(EnumType.STRING)
    @Column(name = "bandeira")
    private Bandeira bandeira;

    @Pattern(regexp = "(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$")
    @Column(name = "numero")
    private String numero;

    @NotNull
    @Pattern(regexp = "^[0-9]{3,4}$")
    @Column(name = "cvv", nullable = false)
    private String cvv;

    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])/?([0-9]{4})$")
    @Column(name = "validade", nullable = false)
    private String validade;

    @ManyToOne
    @JsonIgnoreProperties("cliente")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCartao() {
        return nomeCartao;
    }

    public CartaoCredito nomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
        return this;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    public Bandeira getBandeira() {
        return bandeira;
    }

    public CartaoCredito bandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
        return this;
    }

    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }

    public String getNumero() {
        return numero;
    }

    public CartaoCredito numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCvv() {
        return cvv;
    }

    public CartaoCredito cvv(String cvv) {
        this.cvv = cvv;
        return this;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getValidade() {
        return validade;
    }

    public CartaoCredito validade(String validade) {
        this.validade = validade;
        return this;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public CartaoCredito cliente(Cliente Cliente) {
        this.cliente = Cliente;
        return this;
    }

    public void setCliente(Cliente Cliente) {
        this.cliente = Cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartaoCredito)) {
            return false;
        }
        return id != null && id.equals(((CartaoCredito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CartaoCredito{" +
            "id=" + getId() +
            ", nomeCartao='" + getNomeCartao() + "'" +
            ", bandeira='" + getBandeira() + "'" +
            ", numero='" + getNumero() + "'" +
            ", cvv='" + getCvv() + "'" +
            ", validade='" + getValidade() + "'" +
            "}";
    }
}
