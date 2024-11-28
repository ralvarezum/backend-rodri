import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './personalizacion.reducer';

export const PersonalizacionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const personalizacionEntity = useAppSelector(state => state.personalizacion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personalizacionDetailsHeading">
          <Translate contentKey="backendRodriApp.personalizacion.detail.title">Personalizacion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personalizacionEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="backendRodriApp.personalizacion.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{personalizacionEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="backendRodriApp.personalizacion.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{personalizacionEntity.descripcion}</dd>
          <dt>
            <span id="idCatedra">
              <Translate contentKey="backendRodriApp.personalizacion.idCatedra">Id Catedra</Translate>
            </span>
          </dt>
          <dd>{personalizacionEntity.idCatedra}</dd>
          <dt>
            <Translate contentKey="backendRodriApp.personalizacion.dispositivo">Dispositivo</Translate>
          </dt>
          <dd>{personalizacionEntity.dispositivo ? personalizacionEntity.dispositivo.id : ''}</dd>
          <dt>
            <Translate contentKey="backendRodriApp.personalizacion.venta">Venta</Translate>
          </dt>
          <dd>
            {personalizacionEntity.ventas
              ? personalizacionEntity.ventas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {personalizacionEntity.ventas && i === personalizacionEntity.ventas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/personalizacion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/personalizacion/${personalizacionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonalizacionDetail;
