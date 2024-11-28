package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Opcion.
 */
@Entity
@Table(name = "opcion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Opcion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_adicional", precision = 21, scale = 2)
    private BigDecimal precioAdicional;

    @Column(name = "id_catedra")
    private Long idCatedra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "opcions", "dispositivo", "ventas" }, allowSetters = true)
    private Personalizacion personalizacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Opcion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Opcion codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Opcion nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Opcion descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioAdicional() {
        return this.precioAdicional;
    }

    public Opcion precioAdicional(BigDecimal precioAdicional) {
        this.setPrecioAdicional(precioAdicional);
        return this;
    }

    public void setPrecioAdicional(BigDecimal precioAdicional) {
        this.precioAdicional = precioAdicional;
    }

    public Long getIdCatedra() {
        return this.idCatedra;
    }

    public Opcion idCatedra(Long idCatedra) {
        this.setIdCatedra(idCatedra);
        return this;
    }

    public void setIdCatedra(Long idCatedra) {
        this.idCatedra = idCatedra;
    }

    public Personalizacion getPersonalizacion() {
        return this.personalizacion;
    }

    public void setPersonalizacion(Personalizacion personalizacion) {
        this.personalizacion = personalizacion;
    }

    public Opcion personalizacion(Personalizacion personalizacion) {
        this.setPersonalizacion(personalizacion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opcion)) {
            return false;
        }
        return getId() != null && getId().equals(((Opcion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opcion{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precioAdicional=" + getPrecioAdicional() +
            ", idCatedra=" + getIdCatedra() +
            "}";
    }
}
