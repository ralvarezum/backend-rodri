package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdicionalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Adicional getAdicionalSample1() {
        return new Adicional().id(1L).nombre("nombre1").descripcion("descripcion1").idCatedra(1L);
    }

    public static Adicional getAdicionalSample2() {
        return new Adicional().id(2L).nombre("nombre2").descripcion("descripcion2").idCatedra(2L);
    }

    public static Adicional getAdicionalRandomSampleGenerator() {
        return new Adicional()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .idCatedra(longCount.incrementAndGet());
    }
}
