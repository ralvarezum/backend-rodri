package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.DispositivoAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Dispositivo;
import com.mycompany.myapp.domain.enumeration.Moneda;
import com.mycompany.myapp.repository.DispositivoRepository;
import com.mycompany.myapp.service.dto.DispositivoDTO;
import com.mycompany.myapp.service.mapper.DispositivoMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DispositivoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DispositivoResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRECIO_BASE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_BASE = new BigDecimal(2);

    private static final Moneda DEFAULT_MONEDA = Moneda.ARS;
    private static final Moneda UPDATED_MONEDA = Moneda.USD;

    private static final Long DEFAULT_ID_CATEDRA = 1L;
    private static final Long UPDATED_ID_CATEDRA = 2L;

    private static final String ENTITY_API_URL = "/api/dispositivos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private DispositivoMapper dispositivoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDispositivoMockMvc;

    private Dispositivo dispositivo;

    private Dispositivo insertedDispositivo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dispositivo createEntity(EntityManager em) {
        Dispositivo dispositivo = new Dispositivo()
            .codigo(DEFAULT_CODIGO)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .precioBase(DEFAULT_PRECIO_BASE)
            .moneda(DEFAULT_MONEDA)
            .idCatedra(DEFAULT_ID_CATEDRA);
        return dispositivo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dispositivo createUpdatedEntity(EntityManager em) {
        Dispositivo dispositivo = new Dispositivo()
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precioBase(UPDATED_PRECIO_BASE)
            .moneda(UPDATED_MONEDA)
            .idCatedra(UPDATED_ID_CATEDRA);
        return dispositivo;
    }

    @BeforeEach
    public void initTest() {
        dispositivo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDispositivo != null) {
            dispositivoRepository.delete(insertedDispositivo);
            insertedDispositivo = null;
        }
    }

    @Test
    @Transactional
    void createDispositivo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Dispositivo
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);
        var returnedDispositivoDTO = om.readValue(
            restDispositivoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DispositivoDTO.class
        );

        // Validate the Dispositivo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDispositivo = dispositivoMapper.toEntity(returnedDispositivoDTO);
        assertDispositivoUpdatableFieldsEquals(returnedDispositivo, getPersistedDispositivo(returnedDispositivo));

        insertedDispositivo = returnedDispositivo;
    }

    @Test
    @Transactional
    void createDispositivoWithExistingId() throws Exception {
        // Create the Dispositivo with an existing ID
        dispositivo.setId(1L);
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositivoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dispositivo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDispositivos() throws Exception {
        // Initialize the database
        insertedDispositivo = dispositivoRepository.saveAndFlush(dispositivo);

        // Get all the dispositivoList
        restDispositivoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispositivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].precioBase").value(hasItem(sameNumber(DEFAULT_PRECIO_BASE))))
            .andExpect(jsonPath("$.[*].moneda").value(hasItem(DEFAULT_MONEDA.toString())))
            .andExpect(jsonPath("$.[*].idCatedra").value(hasItem(DEFAULT_ID_CATEDRA.intValue())));
    }

    @Test
    @Transactional
    void getDispositivo() throws Exception {
        // Initialize the database
        insertedDispositivo = dispositivoRepository.saveAndFlush(dispositivo);

        // Get the dispositivo
        restDispositivoMockMvc
            .perform(get(ENTITY_API_URL_ID, dispositivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dispositivo.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.precioBase").value(sameNumber(DEFAULT_PRECIO_BASE)))
            .andExpect(jsonPath("$.moneda").value(DEFAULT_MONEDA.toString()))
            .andExpect(jsonPath("$.idCatedra").value(DEFAULT_ID_CATEDRA.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDispositivo() throws Exception {
        // Get the dispositivo
        restDispositivoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDispositivo() throws Exception {
        // Initialize the database
        insertedDispositivo = dispositivoRepository.saveAndFlush(dispositivo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositivo
        Dispositivo updatedDispositivo = dispositivoRepository.findById(dispositivo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDispositivo are not directly saved in db
        em.detach(updatedDispositivo);
        updatedDispositivo
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precioBase(UPDATED_PRECIO_BASE)
            .moneda(UPDATED_MONEDA)
            .idCatedra(UPDATED_ID_CATEDRA);
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(updatedDispositivo);

        restDispositivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositivoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositivoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dispositivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDispositivoToMatchAllProperties(updatedDispositivo);
    }

    @Test
    @Transactional
    void putNonExistingDispositivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivo.setId(longCount.incrementAndGet());

        // Create the Dispositivo
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositivoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dispositivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDispositivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivo.setId(longCount.incrementAndGet());

        // Create the Dispositivo
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositivoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dispositivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dispositivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDispositivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivo.setId(longCount.incrementAndGet());

        // Create the Dispositivo
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositivoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dispositivoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dispositivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDispositivoWithPatch() throws Exception {
        // Initialize the database
        insertedDispositivo = dispositivoRepository.saveAndFlush(dispositivo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositivo using partial update
        Dispositivo partialUpdatedDispositivo = new Dispositivo();
        partialUpdatedDispositivo.setId(dispositivo.getId());

        partialUpdatedDispositivo.precioBase(UPDATED_PRECIO_BASE).idCatedra(UPDATED_ID_CATEDRA);

        restDispositivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositivo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDispositivo))
            )
            .andExpect(status().isOk());

        // Validate the Dispositivo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDispositivoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDispositivo, dispositivo),
            getPersistedDispositivo(dispositivo)
        );
    }

    @Test
    @Transactional
    void fullUpdateDispositivoWithPatch() throws Exception {
        // Initialize the database
        insertedDispositivo = dispositivoRepository.saveAndFlush(dispositivo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dispositivo using partial update
        Dispositivo partialUpdatedDispositivo = new Dispositivo();
        partialUpdatedDispositivo.setId(dispositivo.getId());

        partialUpdatedDispositivo
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precioBase(UPDATED_PRECIO_BASE)
            .moneda(UPDATED_MONEDA)
            .idCatedra(UPDATED_ID_CATEDRA);

        restDispositivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositivo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDispositivo))
            )
            .andExpect(status().isOk());

        // Validate the Dispositivo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDispositivoUpdatableFieldsEquals(partialUpdatedDispositivo, getPersistedDispositivo(partialUpdatedDispositivo));
    }

    @Test
    @Transactional
    void patchNonExistingDispositivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivo.setId(longCount.incrementAndGet());

        // Create the Dispositivo
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dispositivoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dispositivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dispositivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDispositivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivo.setId(longCount.incrementAndGet());

        // Create the Dispositivo
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositivoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dispositivoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dispositivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDispositivo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dispositivo.setId(longCount.incrementAndGet());

        // Create the Dispositivo
        DispositivoDTO dispositivoDTO = dispositivoMapper.toDto(dispositivo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositivoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dispositivoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dispositivo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDispositivo() throws Exception {
        // Initialize the database
        insertedDispositivo = dispositivoRepository.saveAndFlush(dispositivo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dispositivo
        restDispositivoMockMvc
            .perform(delete(ENTITY_API_URL_ID, dispositivo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dispositivoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Dispositivo getPersistedDispositivo(Dispositivo dispositivo) {
        return dispositivoRepository.findById(dispositivo.getId()).orElseThrow();
    }

    protected void assertPersistedDispositivoToMatchAllProperties(Dispositivo expectedDispositivo) {
        assertDispositivoAllPropertiesEquals(expectedDispositivo, getPersistedDispositivo(expectedDispositivo));
    }

    protected void assertPersistedDispositivoToMatchUpdatableProperties(Dispositivo expectedDispositivo) {
        assertDispositivoAllUpdatablePropertiesEquals(expectedDispositivo, getPersistedDispositivo(expectedDispositivo));
    }
}
