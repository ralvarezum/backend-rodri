package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.config.ExternalApiConfig;
import com.mycompany.myapp.domain.Adicional;
import com.mycompany.myapp.domain.Caracteristica;
import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.domain.Opcion;
import com.mycompany.myapp.domain.Personalizacion;
import com.mycompany.myapp.repository.DispositivoRepository;
import com.mycompany.myapp.service.DispositivoService;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import com.mycompany.myapp.service.mapper.DispositivoMapper;
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

@Service
@Transactional
public class DispositivoServiceImpl implements DispositivoService {

    private static final Logger log = LoggerFactory.getLogger(DispositivoServiceImpl.class);

    private final DispositivoRepository dispositivoRepository;
    private final DispositivoMapper dispositivoMapper;
    private final WebClient webClient;
    private final ExternalApiConfig externalApiConfig;

    public DispositivoServiceImpl(
        DispositivoRepository dispositivoRepository,
        DispositivoMapper dispositivoMapper,
        WebClient.Builder webClientBuilder,
        ExternalApiConfig externalApiConfig
    ) {
        this.dispositivoRepository = dispositivoRepository;
        this.dispositivoMapper = dispositivoMapper;
        this.externalApiConfig = externalApiConfig;

        // Configurar WebClient con baseUrl y el token
        this.webClient = webClientBuilder
            .baseUrl(externalApiConfig.getBaseUrl())
            .defaultHeader("Authorization", "Bearer " + externalApiConfig.getToken())
            .build();
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

        // Obtener dispositivos desde el servicio externo
        List<DispositivoDTO> dispositivosExternos = webClient
            .get()
            .uri("/dispositivos")
            .retrieve()
            .bodyToFlux(DispositivoDTO.class)
            .collectList()
            .block();

        if (dispositivosExternos == null || dispositivosExternos.isEmpty()) {
            log.warn("No dispositivos received from external service");
            return List.of();
        }

        List<Dispositivo> dispositivosActualizados = dispositivosExternos
            .stream()
            .map(dto -> {
                Optional<Dispositivo> dispositivoExistente = dispositivoRepository.findByIdCatedra(dto.getId());
                Dispositivo dispositivo;

                if (dispositivoExistente.isPresent()) {
                    dispositivo = dispositivoExistente.get();
                    dispositivoMapper.updateEntityFromDto(dto, dispositivo);
                } else {
                    dispositivo = dispositivoMapper.toEntity(dto);
                    dispositivo.setIdCatedra(dto.getId());
                }

                // Actualizar características
                actualizarCaracteristicas(dispositivo, dto);

                // Actualizar personalizaciones y opciones
                actualizarPersonalizaciones(dispositivo, dto);

                // Actualizar adicionales
                actualizarAdicionales(dispositivo, dto);

                return dispositivoRepository.save(dispositivo);
            })
            .collect(Collectors.toList());

        log.info("Synchronized {} dispositivos and related entities from external service", dispositivosActualizados.size());

        return dispositivosActualizados.stream().map(dispositivoMapper::toDto).collect(Collectors.toList());
    }

    private void actualizarCaracteristicas(Dispositivo dispositivo, DispositivoDTO dto) {
        dispositivo.getCaracteristicas().clear();
        dto
            .getCaracteristicas()
            .forEach(cDTO -> {
                Caracteristica caracteristica = new Caracteristica();
                caracteristica.setIdCatedra(cDTO.getId());
                caracteristica.setNombre(cDTO.getNombre());
                caracteristica.setDescripcion(cDTO.getDescripcion());
                caracteristica.setDispositivo(dispositivo);
                dispositivo.getCaracteristicas().add(caracteristica);
            });
    }

    private void actualizarPersonalizaciones(Dispositivo dispositivo, DispositivoDTO dto) {
        dispositivo.getPersonalizacions().clear();
        dto
            .getPersonalizaciones()
            .forEach(pDTO -> {
                Personalizacion personalizacion = new Personalizacion();
                personalizacion.setIdCatedra(pDTO.getId());
                personalizacion.setNombre(pDTO.getNombre());
                personalizacion.setDescripcion(pDTO.getDescripcion());
                personalizacion.setDispositivo(dispositivo);

                pDTO
                    .getOpciones()
                    .forEach(oDTO -> {
                        Opcion opcion = new Opcion();
                        opcion.setIdCatedra(oDTO.getId());
                        opcion.setCodigo(oDTO.getCodigo());
                        opcion.setNombre(oDTO.getNombre());
                        opcion.setDescripcion(oDTO.getDescripcion());
                        opcion.setPrecioAdicional(oDTO.getPrecioAdicional());
                        opcion.setPersonalizacion(personalizacion);
                        personalizacion.getOpcions().add(opcion);
                    });

                dispositivo.getPersonalizacions().add(personalizacion);
            });
    }

    private void actualizarAdicionales(Dispositivo dispositivo, DispositivoDTO dto) {
        dispositivo.getAdicionals().clear();
        dto
            .getAdicionales()
            .forEach(aDTO -> {
                Adicional adicional = new Adicional();
                adicional.setIdCatedra(aDTO.getId());
                adicional.setNombre(aDTO.getNombre());
                adicional.setDescripcion(aDTO.getDescripcion());
                adicional.setPrecio(aDTO.getPrecio());
                adicional.setPrecioGratis(aDTO.getPrecioGratis());
                adicional.setDispositivo(dispositivo);
                dispositivo.getAdicionals().add(adicional);
            });
    }

    @Scheduled(fixedRate = 360000)
    public void fetchDispositivosPeriodicamente() {
        log.info("Starting periodic synchronization of dispositivos");
        fetchDispositivos();
    }
}
