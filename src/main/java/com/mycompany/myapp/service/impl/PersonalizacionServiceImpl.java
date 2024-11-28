package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Personalizacion;
import com.mycompany.myapp.repository.PersonalizacionRepository;
import com.mycompany.myapp.service.PersonalizacionService;
import com.mycompany.myapp.service.dto.PersonalizacionDTO;
import com.mycompany.myapp.service.mapper.PersonalizacionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Personalizacion}.
 */
@Service
@Transactional
public class PersonalizacionServiceImpl implements PersonalizacionService {

    private static final Logger log = LoggerFactory.getLogger(PersonalizacionServiceImpl.class);

    private final PersonalizacionRepository personalizacionRepository;

    private final PersonalizacionMapper personalizacionMapper;

    public PersonalizacionServiceImpl(PersonalizacionRepository personalizacionRepository, PersonalizacionMapper personalizacionMapper) {
        this.personalizacionRepository = personalizacionRepository;
        this.personalizacionMapper = personalizacionMapper;
    }

    @Override
    public PersonalizacionDTO save(PersonalizacionDTO personalizacionDTO) {
        log.debug("Request to save Personalizacion : {}", personalizacionDTO);
        Personalizacion personalizacion = personalizacionMapper.toEntity(personalizacionDTO);
        personalizacion = personalizacionRepository.save(personalizacion);
        return personalizacionMapper.toDto(personalizacion);
    }

    @Override
    public PersonalizacionDTO update(PersonalizacionDTO personalizacionDTO) {
        log.debug("Request to update Personalizacion : {}", personalizacionDTO);
        Personalizacion personalizacion = personalizacionMapper.toEntity(personalizacionDTO);
        personalizacion = personalizacionRepository.save(personalizacion);
        return personalizacionMapper.toDto(personalizacion);
    }

    @Override
    public Optional<PersonalizacionDTO> partialUpdate(PersonalizacionDTO personalizacionDTO) {
        log.debug("Request to partially update Personalizacion : {}", personalizacionDTO);

        return personalizacionRepository
            .findById(personalizacionDTO.getId())
            .map(existingPersonalizacion -> {
                personalizacionMapper.partialUpdate(existingPersonalizacion, personalizacionDTO);

                return existingPersonalizacion;
            })
            .map(personalizacionRepository::save)
            .map(personalizacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonalizacionDTO> findAll() {
        log.debug("Request to get all Personalizacions");
        return personalizacionRepository
            .findAll()
            .stream()
            .map(personalizacionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalizacionDTO> findOne(Long id) {
        log.debug("Request to get Personalizacion : {}", id);
        return personalizacionRepository.findById(id).map(personalizacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personalizacion : {}", id);
        personalizacionRepository.deleteById(id);
    }
}
