package br.com.abim.bestmeal.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.abim.bestmeal.domain.enumeration.UF;

/**
 * A Municipio.
 */
@Entity
@Table(name = "municipio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nome_municipio", length = 100, nullable = false)
    private String nomeMunicipio;

    @Enumerated(EnumType.STRING)
    @Column(name = "uf")
    private UF uf;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeMunicipio() {
        return nomeMunicipio;
    }

    public Municipio nomeMunicipio(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
        return this;
    }

    public void setNomeMunicipio(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }

    public UF getUf() {
        return uf;
    }

    public Municipio uf(UF uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Municipio)) {
            return false;
        }
        return id != null && id.equals(((Municipio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Municipio{" +
            "id=" + getId() +
            ", nomeMunicipio='" + getNomeMunicipio() + "'" +
            ", uf='" + getUf() + "'" +
            "}";
    }
}
