package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AdicionalTestSamples.*;
import static com.mycompany.myapp.domain.CaracteristicaTestSamples.*;
import static com.mycompany.myapp.domain.DispositivoTestSamples.*;
import static com.mycompany.myapp.domain.PersonalizacionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DispositivoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dispositivo.class);
        Dispositivo dispositivo1 = getDispositivoSample1();
        Dispositivo dispositivo2 = new Dispositivo();
        assertThat(dispositivo1).isNotEqualTo(dispositivo2);

        dispositivo2.setId(dispositivo1.getId());
        assertThat(dispositivo1).isEqualTo(dispositivo2);

        dispositivo2 = getDispositivoSample2();
        assertThat(dispositivo1).isNotEqualTo(dispositivo2);
    }

    @Test
    void adicionalTest() {
        Dispositivo dispositivo = getDispositivoRandomSampleGenerator();
        Adicional adicionalBack = getAdicionalRandomSampleGenerator();

        dispositivo.addAdicional(adicionalBack);
        assertThat(dispositivo.getAdicionals()).containsOnly(adicionalBack);
        assertThat(adicionalBack.getDispositivo()).isEqualTo(dispositivo);

        dispositivo.removeAdicional(adicionalBack);
        assertThat(dispositivo.getAdicionals()).doesNotContain(adicionalBack);
        assertThat(adicionalBack.getDispositivo()).isNull();

        dispositivo.adicionals(new HashSet<>(Set.of(adicionalBack)));
        assertThat(dispositivo.getAdicionals()).containsOnly(adicionalBack);
        assertThat(adicionalBack.getDispositivo()).isEqualTo(dispositivo);

        dispositivo.setAdicionals(new HashSet<>());
        assertThat(dispositivo.getAdicionals()).doesNotContain(adicionalBack);
        assertThat(adicionalBack.getDispositivo()).isNull();
    }

    @Test
    void caracteristicaTest() {
        Dispositivo dispositivo = getDispositivoRandomSampleGenerator();
        Caracteristica caracteristicaBack = getCaracteristicaRandomSampleGenerator();

        dispositivo.addCaracteristica(caracteristicaBack);
        assertThat(dispositivo.getCaracteristicas()).containsOnly(caracteristicaBack);
        assertThat(caracteristicaBack.getDispositivo()).isEqualTo(dispositivo);

        dispositivo.removeCaracteristica(caracteristicaBack);
        assertThat(dispositivo.getCaracteristicas()).doesNotContain(caracteristicaBack);
        assertThat(caracteristicaBack.getDispositivo()).isNull();

        dispositivo.caracteristicas(new HashSet<>(Set.of(caracteristicaBack)));
        assertThat(dispositivo.getCaracteristicas()).containsOnly(caracteristicaBack);
        assertThat(caracteristicaBack.getDispositivo()).isEqualTo(dispositivo);

        dispositivo.setCaracteristicas(new HashSet<>());
        assertThat(dispositivo.getCaracteristicas()).doesNotContain(caracteristicaBack);
        assertThat(caracteristicaBack.getDispositivo()).isNull();
    }

    @Test
    void personalizacionTest() {
        Dispositivo dispositivo = getDispositivoRandomSampleGenerator();
        Personalizacion personalizacionBack = getPersonalizacionRandomSampleGenerator();

        dispositivo.addPersonalizacion(personalizacionBack);
        assertThat(dispositivo.getPersonalizacions()).containsOnly(personalizacionBack);
        assertThat(personalizacionBack.getDispositivo()).isEqualTo(dispositivo);

        dispositivo.removePersonalizacion(personalizacionBack);
        assertThat(dispositivo.getPersonalizacions()).doesNotContain(personalizacionBack);
        assertThat(personalizacionBack.getDispositivo()).isNull();

        dispositivo.personalizacions(new HashSet<>(Set.of(personalizacionBack)));
        assertThat(dispositivo.getPersonalizacions()).containsOnly(personalizacionBack);
        assertThat(personalizacionBack.getDispositivo()).isEqualTo(dispositivo);

        dispositivo.setPersonalizacions(new HashSet<>());
        assertThat(dispositivo.getPersonalizacions()).doesNotContain(personalizacionBack);
        assertThat(personalizacionBack.getDispositivo()).isNull();
    }
}
