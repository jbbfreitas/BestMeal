package br.com.abim.bestmeal.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 5)
    @Column(name = "codigo", length = 5, nullable = false)
    private String codigo;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @NotNull
    @Column(name = "unidade", nullable = false)
    private String unidade;

    @NotNull
    @Column(name = "estoque_minimo", nullable = false)
    private Integer estoqueMinimo;

    @Column(name = "estoque_atual")
    private Integer estoqueAtual;

    @Column(name = "data_ultima_compra")
    private LocalDate dataUltimaCompra;

    @Column(name = "valor_ultima_compra")
    private Double valorUltimaCompra;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Produto codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public Produto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade() {
        return unidade;
    }

    public Produto unidade(String unidade) {
        this.unidade = unidade;
        return this;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public Produto estoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
        return this;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public Integer getEstoqueAtual() {
        return estoqueAtual;
    }

    public Produto estoqueAtual(Integer estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
        return this;
    }

    public void setEstoqueAtual(Integer estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public LocalDate getDataUltimaCompra() {
        return dataUltimaCompra;
    }

    public Produto dataUltimaCompra(LocalDate dataUltimaCompra) {
        this.dataUltimaCompra = dataUltimaCompra;
        return this;
    }

    public void setDataUltimaCompra(LocalDate dataUltimaCompra) {
        this.dataUltimaCompra = dataUltimaCompra;
    }

    public Double getValorUltimaCompra() {
        return valorUltimaCompra;
    }

    public Produto valorUltimaCompra(Double valorUltimaCompra) {
        this.valorUltimaCompra = valorUltimaCompra;
        return this;
    }

    public void setValorUltimaCompra(Double valorUltimaCompra) {
        this.valorUltimaCompra = valorUltimaCompra;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        return id != null && id.equals(((Produto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", unidade='" + getUnidade() + "'" +
            ", estoqueMinimo=" + getEstoqueMinimo() +
            ", estoqueAtual=" + getEstoqueAtual() +
            ", dataUltimaCompra='" + getDataUltimaCompra() + "'" +
            ", valorUltimaCompra=" + getValorUltimaCompra() +
            "}";
    }
}
