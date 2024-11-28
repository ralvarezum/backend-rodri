package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DispositivoRepository;
import com.mycompany.myapp.service.DispositivoService;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Dispositivo}.
 */
@RestController
@RequestMapping("/api/dispositivos")
public class DispositivoResource {

    private static final Logger log = LoggerFactory.getLogger(DispositivoResource.class);

    private static final String ENTITY_NAME = "dispositivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispositivoService dispositivoService;

    private final DispositivoRepository dispositivoRepository;

    public DispositivoResource(DispositivoService dispositivoService, DispositivoRepository dispositivoRepository) {
        this.dispositivoService = dispositivoService;
        this.dispositivoRepository = dispositivoRepository;
    }

    /**
     * {@code POST  /dispositivos} : Create a new dispositivo.
     *
     * @param dispositivoDTO the dispositivoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispositivoDTO, or with status {@code 400 (Bad Request)} if the dispositivo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DispositivoDTO> createDispositivo(@RequestBody DispositivoDTO dispositivoDTO) throws URISyntaxException {
        log.debug("REST request to save Dispositivo : {}", dispositivoDTO);
        if (dispositivoDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispositivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dispositivoDTO = dispositivoService.save(dispositivoDTO);
        return ResponseEntity.created(new URI("/api/dispositivos/" + dispositivoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dispositivoDTO.getId().toString()))
            .body(dispositivoDTO);
    }

    /**
     * {@code PUT  /dispositivos/:id} : Updates an existing dispositivo.
     *
     * @param id the id of the dispositivoDTO to save.
     * @param dispositivoDTO the dispositivoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositivoDTO,
     * or with status {@code 400 (Bad Request)} if the dispositivoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispositivoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DispositivoDTO> updateDispositivo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispositivoDTO dispositivoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Dispositivo : {}, {}", id, dispositivoDTO);
        if (dispositivoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispositivoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositivoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        dispositivoDTO = dispositivoService.update(dispositivoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositivoDTO.getId().toString()))
            .body(dispositivoDTO);
    }

    /**
     * {@code PATCH  /dispositivos/:id} : Partial updates given fields of an existing dispositivo, field will ignore if it is null
     *
     * @param id the id of the dispositivoDTO to save.
     * @param dispositivoDTO the dispositivoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositivoDTO,
     * or with status {@code 400 (Bad Request)} if the dispositivoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dispositivoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dispositivoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DispositivoDTO> partialUpdateDispositivo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispositivoDTO dispositivoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dispositivo partially : {}, {}", id, dispositivoDTO);
        if (dispositivoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispositivoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositivoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DispositivoDTO> result = dispositivoService.partialUpdate(dispositivoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositivoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dispositivos} : get all the dispositivos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispositivos in body.
     */
    @GetMapping("")
    public List<DispositivoDTO> getAllDispositivos() {
        log.debug("REST request to get all Dispositivos");
        return dispositivoService.findAll();
    }

    /**
     * {@code GET  /dispositivos/:id} : get the "id" dispositivo.
     *
     * @param id the id of the dispositivoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispositivoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DispositivoDTO> getDispositivo(@PathVariable("id") Long id) {
        log.debug("REST request to get Dispositivo : {}", id);
        Optional<DispositivoDTO> dispositivoDTO = dispositivoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispositivoDTO);
    }

    /**
     * {@code DELETE  /dispositivos/:id} : delete the "id" dispositivo.
     *
     * @param id the id of the dispositivoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDispositivo(@PathVariable("id") Long id) {
        log.debug("REST request to delete Dispositivo : {}", id);
        dispositivoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
