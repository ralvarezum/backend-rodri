package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DispositivoTestSamples.*;
import static com.mycompany.myapp.domain.OpcionTestSamples.*;
import static com.mycompany.myapp.domain.PersonalizacionTestSamples.*;
import static com.mycompany.myapp.domain.VentaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PersonalizacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personalizacion.class);
        Personalizacion personalizacion1 = getPersonalizacionSample1();
        Personalizacion personalizacion2 = new Personalizacion();
        assertThat(personalizacion1).isNotEqualTo(personalizacion2);

        personalizacion2.setId(personalizacion1.getId());
        assertThat(personalizacion1).isEqualTo(personalizacion2);

        personalizacion2 = getPersonalizacionSample2();
        assertThat(personalizacion1).isNotEqualTo(personalizacion2);
    }

    @Test
    void opcionTest() {
        Personalizacion personalizacion = getPersonalizacionRandomSampleGenerator();
        Opcion opcionBack = getOpcionRandomSampleGenerator();

        personalizacion.addOpcion(opcionBack);
        assertThat(personalizacion.getOpcions()).containsOnly(opcionBack);
        assertThat(opcionBack.getPersonalizacion()).isEqualTo(personalizacion);

        personalizacion.removeOpcion(opcionBack);
        assertThat(personalizacion.getOpcions()).doesNotContain(opcionBack);
        assertThat(opcionBack.getPersonalizacion()).isNull();

        personalizacion.opcions(new HashSet<>(Set.of(opcionBack)));
        assertThat(personalizacion.getOpcions()).containsOnly(opcionBack);
        assertThat(opcionBack.getPersonalizacion()).isEqualTo(personalizacion);

        personalizacion.setOpcions(new HashSet<>());
        assertThat(personalizacion.getOpcions()).doesNotContain(opcionBack);
        assertThat(opcionBack.getPersonalizacion()).isNull();
    }

    @Test
    void dispositivoTest() {
        Personalizacion personalizacion = getPersonalizacionRandomSampleGenerator();
        Dispositivo dispositivoBack = getDispositivoRandomSampleGenerator();

        personalizacion.setDispositivo(dispositivoBack);
        assertThat(personalizacion.getDispositivo()).isEqualTo(dispositivoBack);

        personalizacion.dispositivo(null);
        assertThat(personalizacion.getDispositivo()).isNull();
    }

    @Test
    void ventaTest() {
        Personalizacion personalizacion = getPersonalizacionRandomSampleGenerator();
        Venta ventaBack = getVentaRandomSampleGenerator();

        personalizacion.addVenta(ventaBack);
        assertThat(personalizacion.getVentas()).containsOnly(ventaBack);
        assertThat(ventaBack.getPersonalizacions()).containsOnly(personalizacion);

        personalizacion.removeVenta(ventaBack);
        assertThat(personalizacion.getVentas()).doesNotContain(ventaBack);
        assertThat(ventaBack.getPersonalizacions()).doesNotContain(personalizacion);

        personalizacion.ventas(new HashSet<>(Set.of(ventaBack)));
        assertThat(personalizacion.getVentas()).containsOnly(ventaBack);
        assertThat(ventaBack.getPersonalizacions()).containsOnly(personalizacion);

        personalizacion.setVentas(new HashSet<>());
        assertThat(personalizacion.getVentas()).doesNotContain(ventaBack);
        assertThat(ventaBack.getPersonalizacions()).doesNotContain(personalizacion);
    }
}
