package br.com.abim.bestmeal.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.abim.bestmeal.domain.enumeration.GrupoMenu;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "grupo")
    private GrupoMenu grupo;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "20000")
    @Column(name = "valor_normal", nullable = false)
    private Double valorNormal;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "60")
    @Column(name = "tempo_preparo", nullable = false)
    private Double tempoPreparo;

    @NotNull
    @Column(name = "is_disponivel", nullable = false)
    private Boolean isDisponivel;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "2000")
    @Column(name = "valor_reduzido", nullable = false)
    private Double valorReduzido;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Menu nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GrupoMenu getGrupo() {
        return grupo;
    }

    public Menu grupo(GrupoMenu grupo) {
        this.grupo = grupo;
        return this;
    }

    public void setGrupo(GrupoMenu grupo) {
        this.grupo = grupo;
    }

    public Double getValorNormal() {
        return valorNormal;
    }

    public Menu valorNormal(Double valorNormal) {
        this.valorNormal = valorNormal;
        return this;
    }

    public void setValorNormal(Double valorNormal) {
        this.valorNormal = valorNormal;
    }

    public Double getTempoPreparo() {
        return tempoPreparo;
    }

    public Menu tempoPreparo(Double tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
        return this;
    }

    public void setTempoPreparo(Double tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public Boolean isIsDisponivel() {
        return isDisponivel;
    }

    public Menu isDisponivel(Boolean isDisponivel) {
        this.isDisponivel = isDisponivel;
        return this;
    }

    public void setIsDisponivel(Boolean isDisponivel) {
        this.isDisponivel = isDisponivel;
    }

    public Double getValorReduzido() {
        return valorReduzido;
    }

    public Menu valorReduzido(Double valorReduzido) {
        this.valorReduzido = valorReduzido;
        return this;
    }

    public void setValorReduzido(Double valorReduzido) {
        this.valorReduzido = valorReduzido;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", grupo='" + getGrupo() + "'" +
            ", valorNormal=" + getValorNormal() +
            ", tempoPreparo=" + getTempoPreparo() +
            ", isDisponivel='" + isIsDisponivel() + "'" +
            ", valorReduzido=" + getValorReduzido() +
            "}";
    }
}
