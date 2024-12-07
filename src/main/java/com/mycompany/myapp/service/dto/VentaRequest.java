package com.mycompany.myapp.service.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class VentaRequest {

    private Long idDispositivo;
    private ZonedDateTime fechaVenta;
    private List<PersonalizacionRequest> personalizaciones;
    private List<AdicionalRequest> adicionales;

    public static class PersonalizacionRequest {

        private Long id; // ID de la opción seleccionada
        private BigDecimal precio; // Precio de la opción

        // Constructor vacío (opcional si es necesario)
        public PersonalizacionRequest() {}

        // Constructor con parámetro
        public PersonalizacionRequest(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public BigDecimal getPrecio() {
            return precio;
        }

        public void setPrecio(BigDecimal precio) {
            this.precio = precio;
        }
    }

    public static class AdicionalRequest {

        private Long id;
        private BigDecimal precio;

        // Constructor vacío (opcional, si lo necesitas en otras partes)
        public AdicionalRequest() {}

        // Constructor con parámetros
        public AdicionalRequest(Long id, BigDecimal precio) {
            this.id = id;
            this.precio = precio;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public BigDecimal getPrecio() {
            return precio;
        }

        public void setPrecio(BigDecimal precio) {
            this.precio = precio;
        }
    }

    // Getters y Setters
    public Long getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Long idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public List<PersonalizacionRequest> getPersonalizaciones() {
        return personalizaciones;
    }

    public void setPersonalizaciones(List<PersonalizacionRequest> personalizaciones) {
        this.personalizaciones = personalizaciones;
    }

    public List<AdicionalRequest> getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(List<AdicionalRequest> adicionales) {
        this.adicionales = adicionales;
    }

    public ZonedDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(ZonedDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
}
