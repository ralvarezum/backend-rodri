import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { getEntities as getDispositivos } from 'app/entities/dispositivo/dispositivo.reducer';
import { ICaracteristica } from 'app/shared/model/caracteristica.model';
import { getEntity, updateEntity, createEntity, reset } from './caracteristica.reducer';

export const CaracteristicaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dispositivos = useAppSelector(state => state.dispositivo.entities);
  const caracteristicaEntity = useAppSelector(state => state.caracteristica.entity);
  const loading = useAppSelector(state => state.caracteristica.loading);
  const updating = useAppSelector(state => state.caracteristica.updating);
  const updateSuccess = useAppSelector(state => state.caracteristica.updateSuccess);

  const handleClose = () => {
    navigate('/caracteristica');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDispositivos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.idCatedra !== undefined && typeof values.idCatedra !== 'number') {
      values.idCatedra = Number(values.idCatedra);
    }

    const entity = {
      ...caracteristicaEntity,
      ...values,
      dispositivo: dispositivos.find(it => it.id.toString() === values.dispositivo?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...caracteristicaEntity,
          dispositivo: caracteristicaEntity?.dispositivo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="backendRodriApp.caracteristica.home.createOrEditLabel" data-cy="CaracteristicaCreateUpdateHeading">
            <Translate contentKey="backendRodriApp.caracteristica.home.createOrEditLabel">Create or edit a Caracteristica</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="caracteristica-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('backendRodriApp.caracteristica.nombre')}
                id="caracteristica-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('backendRodriApp.caracteristica.descripcion')}
                id="caracteristica-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                label={translate('backendRodriApp.caracteristica.idCatedra')}
                id="caracteristica-idCatedra"
                name="idCatedra"
                data-cy="idCatedra"
                type="text"
              />
              <ValidatedField
                id="caracteristica-dispositivo"
                name="dispositivo"
                data-cy="dispositivo"
                label={translate('backendRodriApp.caracteristica.dispositivo')}
                type="select"
              >
                <option value="" key="0" />
                {dispositivos
                  ? dispositivos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/caracteristica" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CaracteristicaUpdate;
