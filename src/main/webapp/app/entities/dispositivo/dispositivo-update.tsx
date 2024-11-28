import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { Moneda } from 'app/shared/model/enumerations/moneda.model';
import { getEntity, updateEntity, createEntity, reset } from './dispositivo.reducer';

export const DispositivoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dispositivoEntity = useAppSelector(state => state.dispositivo.entity);
  const loading = useAppSelector(state => state.dispositivo.loading);
  const updating = useAppSelector(state => state.dispositivo.updating);
  const updateSuccess = useAppSelector(state => state.dispositivo.updateSuccess);
  const monedaValues = Object.keys(Moneda);

  const handleClose = () => {
    navigate('/dispositivo');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.precioBase !== undefined && typeof values.precioBase !== 'number') {
      values.precioBase = Number(values.precioBase);
    }
    if (values.idCatedra !== undefined && typeof values.idCatedra !== 'number') {
      values.idCatedra = Number(values.idCatedra);
    }

    const entity = {
      ...dispositivoEntity,
      ...values,
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
          moneda: 'ARS',
          ...dispositivoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="backendRodriApp.dispositivo.home.createOrEditLabel" data-cy="DispositivoCreateUpdateHeading">
            <Translate contentKey="backendRodriApp.dispositivo.home.createOrEditLabel">Create or edit a Dispositivo</Translate>
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
                  id="dispositivo-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('backendRodriApp.dispositivo.codigo')}
                id="dispositivo-codigo"
                name="codigo"
                data-cy="codigo"
                type="text"
              />
              <ValidatedField
                label={translate('backendRodriApp.dispositivo.nombre')}
                id="dispositivo-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('backendRodriApp.dispositivo.descripcion')}
                id="dispositivo-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                label={translate('backendRodriApp.dispositivo.precioBase')}
                id="dispositivo-precioBase"
                name="precioBase"
                data-cy="precioBase"
                type="text"
              />
              <ValidatedField
                label={translate('backendRodriApp.dispositivo.moneda')}
                id="dispositivo-moneda"
                name="moneda"
                data-cy="moneda"
                type="select"
              >
                {monedaValues.map(moneda => (
                  <option value={moneda} key={moneda}>
                    {translate('backendRodriApp.Moneda.' + moneda)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('backendRodriApp.dispositivo.idCatedra')}
                id="dispositivo-idCatedra"
                name="idCatedra"
                data-cy="idCatedra"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/dispositivo" replace color="info">
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

export default DispositivoUpdate;
