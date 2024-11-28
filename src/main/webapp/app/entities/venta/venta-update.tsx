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
import { IAdicional } from 'app/shared/model/adicional.model';
import { getEntities as getAdicionals } from 'app/entities/adicional/adicional.reducer';
import { IPersonalizacion } from 'app/shared/model/personalizacion.model';
import { getEntities as getPersonalizacions } from 'app/entities/personalizacion/personalizacion.reducer';
import { IVenta } from 'app/shared/model/venta.model';
import { getEntity, updateEntity, createEntity, reset } from './venta.reducer';

export const VentaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dispositivos = useAppSelector(state => state.dispositivo.entities);
  const adicionals = useAppSelector(state => state.adicional.entities);
  const personalizacions = useAppSelector(state => state.personalizacion.entities);
  const ventaEntity = useAppSelector(state => state.venta.entity);
  const loading = useAppSelector(state => state.venta.loading);
  const updating = useAppSelector(state => state.venta.updating);
  const updateSuccess = useAppSelector(state => state.venta.updateSuccess);

  const handleClose = () => {
    navigate('/venta');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDispositivos({}));
    dispatch(getAdicionals({}));
    dispatch(getPersonalizacions({}));
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
    values.fechaVenta = convertDateTimeToServer(values.fechaVenta);
    if (values.precioFinal !== undefined && typeof values.precioFinal !== 'number') {
      values.precioFinal = Number(values.precioFinal);
    }

    const entity = {
      ...ventaEntity,
      ...values,
      dispositivo: dispositivos.find(it => it.id.toString() === values.dispositivo?.toString()),
      adicionals: mapIdList(values.adicionals),
      personalizacions: mapIdList(values.personalizacions),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fechaVenta: displayDefaultDateTime(),
        }
      : {
          ...ventaEntity,
          fechaVenta: convertDateTimeFromServer(ventaEntity.fechaVenta),
          dispositivo: ventaEntity?.dispositivo?.id,
          adicionals: ventaEntity?.adicionals?.map(e => e.id.toString()),
          personalizacions: ventaEntity?.personalizacions?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="backendRodriApp.venta.home.createOrEditLabel" data-cy="VentaCreateUpdateHeading">
            <Translate contentKey="backendRodriApp.venta.home.createOrEditLabel">Create or edit a Venta</Translate>
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
                  id="venta-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('backendRodriApp.venta.fechaVenta')}
                id="venta-fechaVenta"
                name="fechaVenta"
                data-cy="fechaVenta"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('backendRodriApp.venta.precioFinal')}
                id="venta-precioFinal"
                name="precioFinal"
                data-cy="precioFinal"
                type="text"
              />
              <ValidatedField
                id="venta-dispositivo"
                name="dispositivo"
                data-cy="dispositivo"
                label={translate('backendRodriApp.venta.dispositivo')}
                type="select"
                required
              >
                <option value="" key="0" />
                {dispositivos
                  ? dispositivos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                label={translate('backendRodriApp.venta.adicional')}
                id="venta-adicional"
                data-cy="adicional"
                type="select"
                multiple
                name="adicionals"
              >
                <option value="" key="0" />
                {adicionals
                  ? adicionals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('backendRodriApp.venta.personalizacion')}
                id="venta-personalizacion"
                data-cy="personalizacion"
                type="select"
                multiple
                name="personalizacions"
              >
                <option value="" key="0" />
                {personalizacions
                  ? personalizacions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/venta" replace color="info">
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

export default VentaUpdate;
