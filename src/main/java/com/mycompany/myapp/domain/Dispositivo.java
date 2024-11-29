package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Moneda;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Dispositivo.
 */
@Entity
@Table(name = "dispositivo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dispositivo implements Serializable {

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

    @Column(name = "precio_base", precision = 21, scale = 2)
    private BigDecimal precioBase;

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda")
    private Moneda moneda;

    @Column(name = "id_catedra")
    private Long idCatedra;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dispositivo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dispositivo", "ventas" }, allowSetters = true)
    private Set<Adicional> adicionals = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dispositivo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dispositivo" }, allowSetters = true)
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dispositivo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "opcions", "dispositivo", "ventas" }, allowSetters = true)
    private Set<Personalizacion> personalizacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dispositivo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Dispositivo codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Dispositivo nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Dispositivo descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioBase() {
        return this.precioBase;
    }

    public Dispositivo precioBase(BigDecimal precioBase) {
        this.setPrecioBase(precioBase);
        return this;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public Moneda getMoneda() {
        return this.moneda;
    }

    public Dispositivo moneda(Moneda moneda) {
        this.setMoneda(moneda);
        return this;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Long getIdCatedra() {
        return this.idCatedra;
    }

    public Dispositivo idCatedra(Long idCatedra) {
        this.setIdCatedra(idCatedra);
        return this;
    }

    public void setIdCatedra(Long idCatedra) {
        this.idCatedra = idCatedra;
    }

    public Set<Adicional> getAdicionals() {
        return this.adicionals;
    }

    public void setAdicionals(Set<Adicional> adicionals) {
        if (this.adicionals != null) {
            this.adicionals.forEach(i -> i.setDispositivo(null));
        }
        if (adicionals != null) {
            adicionals.forEach(i -> i.setDispositivo(this));
        }
        this.adicionals = adicionals;
    }

    public Dispositivo adicionals(Set<Adicional> adicionals) {
        this.setAdicionals(adicionals);
        return this;
    }

    public Dispositivo addAdicional(Adicional adicional) {
        this.adicionals.add(adicional);
        adicional.setDispositivo(this);
        return this;
    }

    public Dispositivo removeAdicional(Adicional adicional) {
        this.adicionals.remove(adicional);
        adicional.setDispositivo(null);
        return this;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return this.caracteristicas;
    }

    public void setCaracteristicas(Set<Caracteristica> caracteristicas) {
        if (this.caracteristicas != null) {
            this.caracteristicas.forEach(i -> i.setDispositivo(null));
        }
        if (caracteristicas != null) {
            caracteristicas.forEach(i -> i.setDispositivo(this));
        }
        this.caracteristicas = caracteristicas;
    }

    public Dispositivo caracteristicas(Set<Caracteristica> caracteristicas) {
        this.setCaracteristicas(caracteristicas);
        return this;
    }

    public Dispositivo addCaracteristica(Caracteristica caracteristica) {
        this.caracteristicas.add(caracteristica);
        caracteristica.setDispositivo(this);
        return this;
    }

    public Dispositivo removeCaracteristica(Caracteristica caracteristica) {
        this.caracteristicas.remove(caracteristica);
        caracteristica.setDispositivo(null);
        return this;
    }

    public Set<Personalizacion> getPersonalizacions() {
        return this.personalizacions;
    }

    public void setPersonalizacions(Set<Personalizacion> personalizacions) {
        if (this.personalizacions != null) {
            this.personalizacions.forEach(i -> i.setDispositivo(null));
        }
        if (personalizacions != null) {
            personalizacions.forEach(i -> i.setDispositivo(this));
        }
        this.personalizacions = personalizacions;
    }

    public Dispositivo personalizacions(Set<Personalizacion> personalizacions) {
        this.setPersonalizacions(personalizacions);
        return this;
    }

    public Dispositivo addPersonalizacion(Personalizacion personalizacion) {
        this.personalizacions.add(personalizacion);
        personalizacion.setDispositivo(this);
        return this;
    }

    public Dispositivo removePersonalizacion(Personalizacion personalizacion) {
        this.personalizacions.remove(personalizacion);
        personalizacion.setDispositivo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dispositivo)) {
            return false;
        }
        return getId() != null && getId().equals(((Dispositivo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dispositivo{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precioBase=" + getPrecioBase() +
            ", moneda='" + getMoneda() + "'" +
            ", idCatedra=" + getIdCatedra() +
            "}";
    }
}
