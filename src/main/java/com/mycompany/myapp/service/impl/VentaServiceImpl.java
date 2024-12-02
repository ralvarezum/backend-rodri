package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.repository.VentaRepository;
import com.mycompany.myapp.service.VentaService;
import com.mycompany.myapp.service.dto.VentaDTO;
import com.mycompany.myapp.service.mapper.VentaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Venta}.
 */
@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    private static final Logger log = LoggerFactory.getLogger(VentaServiceImpl.class);

    private final VentaRepository ventaRepository;

    private final VentaMapper ventaMapper;

    private final WebClient webClient;

    private final String jwtToken =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2RyaWFsdmEiLCJleHAiOjE3NDEzNzk5MDMsImF1dGgiOiJST0xFX1VTRVIiLCJpYXQiOjE3MzI3Mzk5MDN9.T3pVYff40aEQSlf7MRP1p9giUEN3Nx19HMIwKI5gQ9DHGtsneMQg2QDMCAFIwObHvFGAeCcQFd4houIDejjRLQ";

    public VentaServiceImpl(VentaRepository ventaRepository, VentaMapper ventaMapper, WebClient.Builder webClientBuilder) {
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.webClient = webClientBuilder.baseUrl("http://192.168.194.254:8080/api/catedra").build();
    }

    @Override
    public VentaDTO save(VentaDTO ventaDTO) {
        log.debug("Request to save Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }

    @Override
    public VentaDTO update(VentaDTO ventaDTO) {
        log.debug("Request to update Venta : {}", ventaDTO);
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }

    @Override
    public Optional<VentaDTO> partialUpdate(VentaDTO ventaDTO) {
        log.debug("Request to partially update Venta : {}", ventaDTO);

        return ventaRepository
            .findById(ventaDTO.getId())
            .map(existingVenta -> {
                ventaMapper.partialUpdate(existingVenta, ventaDTO);

                return existingVenta;
            })
            .map(ventaRepository::save)
            .map(ventaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaDTO> findAll() {
        log.debug("Request to get all Ventas");
        return ventaRepository.findAll().stream().map(ventaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<VentaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ventaRepository.findAllWithEagerRelationships(pageable).map(ventaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VentaDTO> findOne(Long id) {
        log.debug("Request to get Venta : {}", id);
        return ventaRepository.findOneWithEagerRelationships(id).map(ventaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
    }

    @Override
    public List<VentaDTO> fetchVentas() {
        log.debug("Request to synchronize Ventas with external service");

        // Consumir el endpoint
        List<VentaDTO> VentasExternas = webClient
            .get()
            .uri("/ventas")
            .header("Authorization", "Bearer " + jwtToken)
            .retrieve()
            .bodyToFlux(VentaDTO.class)
            .collectList()
            .block();

        if (VentasExternas == null || VentasExternas.isEmpty()) {
            log.warn("No ventas received from external service");
            return List.of();
        }

        // Actualizar o guardar los ventas en la base de datos
        List<Venta> ventasActualizadas = VentasExternas.stream()
            .map(ventaMapper::toEntity)
            .map(ventaRepository::save)
            .collect(Collectors.toList());

        log.info("Synchronized {} VENTAS from external service", ventasActualizadas.size());

        return ventasActualizadas.stream().map(ventaMapper::toDto).collect(Collectors.toList());
    }
}
