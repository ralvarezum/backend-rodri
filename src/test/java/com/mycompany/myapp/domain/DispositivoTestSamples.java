package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DispositivoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Dispositivo getDispositivoSample1() {
        return new Dispositivo().id(1L).codigo("codigo1").nombre("nombre1").descripcion("descripcion1").idCatedra(1L);
    }

    public static Dispositivo getDispositivoSample2() {
        return new Dispositivo().id(2L).codigo("codigo2").nombre("nombre2").descripcion("descripcion2").idCatedra(2L);
    }

    public static Dispositivo getDispositivoRandomSampleGenerator() {
        return new Dispositivo()
            .id(longCount.incrementAndGet())
            .codigo(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .idCatedra(longCount.incrementAndGet());
    }
}
