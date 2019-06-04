package br.com.abim.bestmeal.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.abim.bestmeal.domain.enumeration.SituacaoCartao;

/**
 * A CartaoRecompensa.
 */
@Entity
@Table(name = "cartao_recompensa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CartaoRecompensa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 10, max = 40)
    @Column(name = "nome_cartao", length = 40, nullable = false)
    private String nomeCartao;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$")
    @Column(name = "validade", nullable = false)
    private String validade;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "pontuacao", nullable = false)
    private Integer pontuacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoCartao situacao;

    @ManyToOne
    @JsonIgnoreProperties("cartaoRecompensas")
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

    public CartaoRecompensa nomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
        return this;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    public String getNumero() {
        return numero;
    }

    public CartaoRecompensa numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValidade() {
        return validade;
    }

    public CartaoRecompensa validade(String validade) {
        this.validade = validade;
        return this;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public CartaoRecompensa pontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
        return this;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public SituacaoCartao getSituacao() {
        return situacao;
    }

    public CartaoRecompensa situacao(SituacaoCartao situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(SituacaoCartao situacao) {
        this.situacao = situacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public CartaoRecompensa cliente(Cliente Cliente) {
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
        if (!(o instanceof CartaoRecompensa)) {
            return false;
        }
        return id != null && id.equals(((CartaoRecompensa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CartaoRecompensa{" +
            "id=" + getId() +
            ", nomeCartao='" + getNomeCartao() + "'" +
            ", numero='" + getNumero() + "'" +
            ", validade='" + getValidade() + "'" +
            ", pontuacao=" + getPontuacao() +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
