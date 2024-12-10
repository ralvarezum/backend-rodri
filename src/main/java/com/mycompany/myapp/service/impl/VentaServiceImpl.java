package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.config.ExternalApiConfig;
import com.mycompany.myapp.domain.Adicional;
import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.domain.Opcion;
import com.mycompany.myapp.domain.Personalizacion;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.repository.AdicionalRepository;
import com.mycompany.myapp.repository.DispositivoRepository;
import com.mycompany.myapp.repository.OpcionRepository;
import com.mycompany.myapp.repository.PersonalizacionRepository;
import com.mycompany.myapp.repository.VentaRepository;
import com.mycompany.myapp.service.VentaService;
import com.mycompany.myapp.service.dto.VentaDTO;
import com.mycompany.myapp.service.dto.VentaRequest;
import com.mycompany.myapp.service.mapper.VentaMapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    private final DispositivoRepository dispositivoRepository;
    private final AdicionalRepository adicionalRepository;
    private final OpcionRepository opcionRepository;
    private final VentaMapper ventaMapper;
    private final WebClient webClient;

    public VentaServiceImpl(
        VentaRepository ventaRepository,
        AdicionalRepository adicionalRepository,
        PersonalizacionRepository personalizacionRepository,
        VentaMapper ventaMapper,
        WebClient.Builder webClientBuilder,
        DispositivoRepository dispositivoRepository,
        OpcionRepository opcionRepository,
        ExternalApiConfig externalApiConfig
    ) {
        this.ventaRepository = ventaRepository;
        this.dispositivoRepository = dispositivoRepository;
        this.adicionalRepository = adicionalRepository;
        this.opcionRepository = opcionRepository;
        this.ventaMapper = ventaMapper;
        this.webClient = webClientBuilder
            .baseUrl(externalApiConfig.getBaseUrl()) // Usar baseUrl desde la configuración
            .defaultHeader("Authorization", "Bearer " + externalApiConfig.getToken()) // Usar token desde la configuración
            .build();
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

    @Transactional
    public Venta registrarVenta(VentaRequest request) {
        // 1. Buscar el dispositivo
        Dispositivo dispositivo = dispositivoRepository
            .findById(request.getIdDispositivo())
            .orElseThrow(() -> new IllegalArgumentException("Dispositivo no encontrado con ID: " + request.getIdDispositivo()));

        // 2. Crear la venta
        Venta venta = new Venta();
        venta.setDispositivo(dispositivo);
        venta.setFechaVenta(request.getFechaVenta() != null ? request.getFechaVenta() : ZonedDateTime.now());

        BigDecimal precioCalculado = dispositivo.getPrecioBase();

        // 3. Procesar personalizaciones a partir de las opciones enviadas
        if (request.getPersonalizaciones() != null) {
            for (VentaRequest.PersonalizacionRequest personalizacionRequest : request.getPersonalizaciones()) {
                Opcion opcion = opcionRepository
                    .findById(personalizacionRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Opción no encontrada con ID: " + personalizacionRequest.getId()));

                Personalizacion personalizacion = opcion.getPersonalizacion();
                if (personalizacion == null) {
                    throw new IllegalArgumentException(
                        "La opción con ID " + opcion.getId() + " no está asociada a ninguna personalización."
                    );
                }

                Personalizacion personalizacionVenta = new Personalizacion();
                personalizacionVenta.setId(personalizacion.getId());
                personalizacionVenta.addOpcion(opcion);

                venta.addPersonalizacion(personalizacionVenta);
                precioCalculado = precioCalculado.add(opcion.getPrecioAdicional());
            }
        }

        // 4. Procesar adicionales
        if (request.getAdicionales() != null) {
            for (VentaRequest.AdicionalRequest adicionalRequest : request.getAdicionales()) {
                Adicional adicional = adicionalRepository
                    .findById(adicionalRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Adicional no encontrado con ID: " + adicionalRequest.getId()));

                venta.addAdicional(adicional);

                if (
                    adicional.getPrecioGratis() != null &&
                    adicional.getPrecioGratis().compareTo(BigDecimal.valueOf(-1)) != 0 &&
                    precioCalculado.compareTo(adicional.getPrecioGratis()) >= 0
                ) {
                    continue;
                }

                precioCalculado = precioCalculado.add(adicional.getPrecio());
            }
        }

        // **Nueva validación**: Verificar que el precio final no sea negativo
        if (precioCalculado.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio final de la venta no puede ser negativo.");
        }

        // 5. Guardar el precio final
        venta.setPrecioFinal(precioCalculado);

        // 6. Persistir la venta en la base de datos
        Venta ventaGuardada = ventaRepository.save(venta);

        // 7. Enviar el detalle de la venta al servicio externo
        registrarVentaEnServicioExterno(ventaGuardada);

        return ventaGuardada;
    }

    @Override
    public void registrarVentaEnServicioExterno(Venta venta) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idDispositivo", venta.getDispositivo().getIdCatedra()); // Usar idCatedra

        // Construir el objeto de personalizaciones
        List<Map<String, Object>> personalizaciones = venta
            .getPersonalizacions()
            .stream()
            .map(personalizacion -> {
                Opcion opcionSeleccionada = personalizacion
                    .getOpcions()
                    .stream()
                    .findFirst()
                    .orElseThrow(
                        () ->
                            new IllegalArgumentException(
                                "No se encontró una opción válida para la personalización: " + personalizacion.getId()
                            )
                    );

                return Map.<String, Object>of(
                    "id",
                    opcionSeleccionada.getIdCatedra(), // Usar idCatedra
                    "precio",
                    opcionSeleccionada.getPrecioAdicional()
                );
            })
            .collect(Collectors.toList());
        requestBody.put("personalizaciones", personalizaciones);

        // Construir el objeto de adicionales
        List<Map<String, Object>> adicionales = venta
            .getAdicionals()
            .stream()
            .map(adicional ->
                Map.<String, Object>of(
                    "id",
                    adicional.getIdCatedra(), // Usar idCatedra
                    "precio",
                    adicional.getPrecio()
                ))
            .collect(Collectors.toList());
        requestBody.put("adicionales", adicionales);

        // Agregar el precio final y la fecha de venta
        requestBody.put("precioFinal", venta.getPrecioFinal());
        requestBody.put("fechaVenta", venta.getFechaVenta().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        // Enviar la solicitud al servicio externo usando el WebClient configurado
        webClient
            .post()
            .uri("/vender")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .doOnSuccess(response -> log.info("Venta registrada exitosamente en el servicio externo: {}", response))
            .doOnError(error -> log.error("Error al registrar la venta en el servicio externo: {}", error.getMessage()))
            .block();
    }
}
