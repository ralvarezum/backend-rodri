package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Venta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class VentaRepositoryWithBagRelationshipsImpl implements VentaRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String VENTAS_PARAMETER = "ventas";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Venta> fetchBagRelationships(Optional<Venta> venta) {
        return venta.map(this::fetchAdicionals).map(this::fetchPersonalizacions);
    }

    @Override
    public Page<Venta> fetchBagRelationships(Page<Venta> ventas) {
        return new PageImpl<>(fetchBagRelationships(ventas.getContent()), ventas.getPageable(), ventas.getTotalElements());
    }

    @Override
    public List<Venta> fetchBagRelationships(List<Venta> ventas) {
        return Optional.of(ventas).map(this::fetchAdicionals).map(this::fetchPersonalizacions).orElse(Collections.emptyList());
    }

    Venta fetchAdicionals(Venta result) {
        return entityManager
            .createQuery("select venta from Venta venta left join fetch venta.adicionals where venta.id = :id", Venta.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Venta> fetchAdicionals(List<Venta> ventas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, ventas.size()).forEach(index -> order.put(ventas.get(index).getId(), index));
        List<Venta> result = entityManager
            .createQuery("select venta from Venta venta left join fetch venta.adicionals where venta in :ventas", Venta.class)
            .setParameter(VENTAS_PARAMETER, ventas)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Venta fetchPersonalizacions(Venta result) {
        return entityManager
            .createQuery("select venta from Venta venta left join fetch venta.personalizacions where venta.id = :id", Venta.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Venta> fetchPersonalizacions(List<Venta> ventas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, ventas.size()).forEach(index -> order.put(ventas.get(index).getId(), index));
        List<Venta> result = entityManager
            .createQuery("select venta from Venta venta left join fetch venta.personalizacions where venta in :ventas", Venta.class)
            .setParameter(VENTAS_PARAMETER, ventas)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
