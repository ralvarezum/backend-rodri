package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AdicionalTestSamples.*;
import static com.mycompany.myapp.domain.DispositivoTestSamples.*;
import static com.mycompany.myapp.domain.PersonalizacionTestSamples.*;
import static com.mycompany.myapp.domain.VentaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venta.class);
        Venta venta1 = getVentaSample1();
        Venta venta2 = new Venta();
        assertThat(venta1).isNotEqualTo(venta2);

        venta2.setId(venta1.getId());
        assertThat(venta1).isEqualTo(venta2);

        venta2 = getVentaSample2();
        assertThat(venta1).isNotEqualTo(venta2);
    }

    @Test
    void dispositivoTest() {
        Venta venta = getVentaRandomSampleGenerator();
        Dispositivo dispositivoBack = getDispositivoRandomSampleGenerator();

        venta.setDispositivo(dispositivoBack);
        assertThat(venta.getDispositivo()).isEqualTo(dispositivoBack);

        venta.dispositivo(null);
        assertThat(venta.getDispositivo()).isNull();
    }

    @Test
    void adicionalTest() {
        Venta venta = getVentaRandomSampleGenerator();
        Adicional adicionalBack = getAdicionalRandomSampleGenerator();

        venta.addAdicional(adicionalBack);
        assertThat(venta.getAdicionals()).containsOnly(adicionalBack);

        venta.removeAdicional(adicionalBack);
        assertThat(venta.getAdicionals()).doesNotContain(adicionalBack);

        venta.adicionals(new HashSet<>(Set.of(adicionalBack)));
        assertThat(venta.getAdicionals()).containsOnly(adicionalBack);

        venta.setAdicionals(new HashSet<>());
        assertThat(venta.getAdicionals()).doesNotContain(adicionalBack);
    }

    @Test
    void personalizacionTest() {
        Venta venta = getVentaRandomSampleGenerator();
        Personalizacion personalizacionBack = getPersonalizacionRandomSampleGenerator();

        venta.addPersonalizacion(personalizacionBack);
        assertThat(venta.getPersonalizacions()).containsOnly(personalizacionBack);

        venta.removePersonalizacion(personalizacionBack);
        assertThat(venta.getPersonalizacions()).doesNotContain(personalizacionBack);

        venta.personalizacions(new HashSet<>(Set.of(personalizacionBack)));
        assertThat(venta.getPersonalizacions()).containsOnly(personalizacionBack);

        venta.setPersonalizacions(new HashSet<>());
        assertThat(venta.getPersonalizacions()).doesNotContain(personalizacionBack);
    }
}
