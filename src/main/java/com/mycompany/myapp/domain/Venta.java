package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_venta")
    private ZonedDateTime fechaVenta;

    @Column(name = "precio_final", precision = 21, scale = 2)
    private BigDecimal precioFinal;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "adicionals", "caracteristicas", "personalizacions" }, allowSetters = true)
    private Dispositivo dispositivo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_venta__adicional",
        joinColumns = @JoinColumn(name = "venta_id"),
        inverseJoinColumns = @JoinColumn(name = "adicional_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dispositivo", "ventas" }, allowSetters = true)
    private Set<Adicional> adicionals = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_venta__personalizacion",
        joinColumns = @JoinColumn(name = "venta_id"),
        inverseJoinColumns = @JoinColumn(name = "personalizacion_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "opcions", "dispositivo", "ventas" }, allowSetters = true)
    private Set<Personalizacion> personalizacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaVenta() {
        return this.fechaVenta;
    }

    public Venta fechaVenta(ZonedDateTime fechaVenta) {
        this.setFechaVenta(fechaVenta);
        return this;
    }

    public void setFechaVenta(ZonedDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getPrecioFinal() {
        return this.precioFinal;
    }

    public Venta precioFinal(BigDecimal precioFinal) {
        this.setPrecioFinal(precioFinal);
        return this;
    }

    public void setPrecioFinal(BigDecimal precioFinal) {
        this.precioFinal = precioFinal;
    }

    public Dispositivo getDispositivo() {
        return this.dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Venta dispositivo(Dispositivo dispositivo) {
        this.setDispositivo(dispositivo);
        return this;
    }

    public Set<Adicional> getAdicionals() {
        return this.adicionals;
    }

    public void setAdicionals(Set<Adicional> adicionals) {
        this.adicionals = adicionals;
    }

    public Venta adicionals(Set<Adicional> adicionals) {
        this.setAdicionals(adicionals);
        return this;
    }

    public Venta addAdicional(Adicional adicional) {
        this.adicionals.add(adicional);
        return this;
    }

    public Venta removeAdicional(Adicional adicional) {
        this.adicionals.remove(adicional);
        return this;
    }

    public Set<Personalizacion> getPersonalizacions() {
        return this.personalizacions;
    }

    public void setPersonalizacions(Set<Personalizacion> personalizacions) {
        this.personalizacions = personalizacions;
    }

    public Venta personalizacions(Set<Personalizacion> personalizacions) {
        this.setPersonalizacions(personalizacions);
        return this;
    }

    public Venta addPersonalizacion(Personalizacion personalizacion) {
        this.personalizacions.add(personalizacion);
        return this;
    }

    public Venta removePersonalizacion(Personalizacion personalizacion) {
        this.personalizacions.remove(personalizacion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return getId() != null && getId().equals(((Venta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", precioFinal=" + getPrecioFinal() +
            "}";
    }
}
