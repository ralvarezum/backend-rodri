package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.repository.DispositivoRepository;
import com.mycompany.myapp.service.DispositivoService;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import com.mycompany.myapp.service.mapper.DispositivoMapper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service Implementation for managing
 * {@link com.mycompany.myapp.domain.Dispositivo}.
 */
@Service
@Transactional
public class DispositivoServiceImpl implements DispositivoService {

    private static final Logger log = LoggerFactory.getLogger(DispositivoServiceImpl.class);

    private final DispositivoRepository dispositivoRepository;

    private final DispositivoMapper dispositivoMapper;

    private final WebClient webClient;

    private final String jwtToken =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2RyaWFsdmEiLCJleHAiOjE3NDEzNzk5MDMsImF1dGgiOiJST0xFX1VTRVIiLCJpYXQiOjE3MzI3Mzk5MDN9.T3pVYff40aEQSlf7MRP1p9giUEN3Nx19HMIwKI5gQ9DHGtsneMQg2QDMCAFIwObHvFGAeCcQFd4houIDejjRLQ";

    public DispositivoServiceImpl(
        DispositivoRepository dispositivoRepository,
        DispositivoMapper dispositivoMapper,
        WebClient.Builder webClientBuilder
    ) {
        this.dispositivoRepository = dispositivoRepository;
        this.dispositivoMapper = dispositivoMapper;
        this.webClient = webClientBuilder.baseUrl("http://192.168.194.254:8080/api/catedra").build();
    }

    @Override
    public DispositivoDTO save(DispositivoDTO dispositivoDTO) {
        log.debug("Request to save Dispositivo : {}", dispositivoDTO);
        Dispositivo dispositivo = dispositivoMapper.toEntity(dispositivoDTO);
        dispositivo = dispositivoRepository.save(dispositivo);
        return dispositivoMapper.toDto(dispositivo);
    }

    @Override
    public DispositivoDTO update(DispositivoDTO dispositivoDTO) {
        log.debug("Request to update Dispositivo : {}", dispositivoDTO);
        Dispositivo dispositivo = dispositivoMapper.toEntity(dispositivoDTO);
        dispositivo = dispositivoRepository.save(dispositivo);
        return dispositivoMapper.toDto(dispositivo);
    }

    @Override
    public Optional<DispositivoDTO> partialUpdate(DispositivoDTO dispositivoDTO) {
        log.debug("Request to partially update Dispositivo : {}", dispositivoDTO);

        return dispositivoRepository
            .findById(dispositivoDTO.getId())
            .map(existingDispositivo -> {
                dispositivoMapper.partialUpdate(existingDispositivo, dispositivoDTO);

                return existingDispositivo;
            })
            .map(dispositivoRepository::save)
            .map(dispositivoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DispositivoDTO> findAll() {
        log.debug("Request to get all Dispositivos");
        return dispositivoRepository.findAll().stream().map(dispositivoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DispositivoDTO> findOne(Long id) {
        log.debug("Request to get Dispositivo : {}", id);
        return dispositivoRepository.findById(id).map(dispositivoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dispositivo : {}", id);
        dispositivoRepository.deleteById(id);
    }

    @Override
    public List<DispositivoDTO> fetchDispositivos() {
        log.debug("Request to synchronize Dispositivos with external service");

        // Consumir el endpoint de la cátedra
        List<DispositivoDTO> dispositivosExternos = webClient
            .get()
            .uri("/dispositivos")
            .header("Authorization", "Bearer " + jwtToken)
            .retrieve()
            .bodyToFlux(DispositivoDTO.class)
            .collectList()
            .block();

        if (dispositivosExternos == null || dispositivosExternos.isEmpty()) {
            log.warn("No dispositivos received from external service");
            return List.of();
        }

        List<Dispositivo> dispositivosActualizados = new ArrayList<>();

        for (DispositivoDTO dispositivoExterno : dispositivosExternos) {
            // Buscar dispositivo por nombre
            Dispositivo dispositivoExistente = dispositivoRepository.findByNombre(dispositivoExterno.getNombre()).orElse(null);

            if (dispositivoExistente == null) {
                // Si no existe, agregarlo como nuevo
                dispositivosActualizados.add(dispositivoMapper.toEntity(dispositivoExterno));
            } else {
                // Si existe, verificar si el precio cambió
                if (!dispositivoExistente.getPrecioBase().equals(dispositivoExterno.getPrecioBase())) {
                    dispositivoExistente.setPrecioBase(dispositivoExterno.getPrecioBase());
                    dispositivosActualizados.add(dispositivoExistente);
                }
            }
        }

        // Guardar nuevos y actualizados
        dispositivoRepository.saveAll(dispositivosActualizados);

        log.info("Synchronized {} dispositivos from external service", dispositivosActualizados.size());

        return dispositivosActualizados.stream().map(dispositivoMapper::toDto).collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 360000)
    public void fetchDispositivosPeriodicamente() {
        log.info("Starting periodic synchronization of dispositivos");
        fetchDispositivos();
    }
}
