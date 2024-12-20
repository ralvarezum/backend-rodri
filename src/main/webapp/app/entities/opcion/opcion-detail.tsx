import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './opcion.reducer';

export const OpcionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const opcionEntity = useAppSelector(state => state.opcion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opcionDetailsHeading">
          <Translate contentKey="backendRodriApp.opcion.detail.title">Opcion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{opcionEntity.id}</dd>
          <dt>
            <span id="codigo">
              <Translate contentKey="backendRodriApp.opcion.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{opcionEntity.codigo}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="backendRodriApp.opcion.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{opcionEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="backendRodriApp.opcion.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{opcionEntity.descripcion}</dd>
          <dt>
            <span id="precioAdicional">
              <Translate contentKey="backendRodriApp.opcion.precioAdicional">Precio Adicional</Translate>
            </span>
          </dt>
          <dd>{opcionEntity.precioAdicional}</dd>
          <dt>
            <span id="idCatedra">
              <Translate contentKey="backendRodriApp.opcion.idCatedra">Id Catedra</Translate>
            </span>
          </dt>
          <dd>{opcionEntity.idCatedra}</dd>
          <dt>
            <Translate contentKey="backendRodriApp.opcion.personalizacion">Personalizacion</Translate>
          </dt>
          <dd>{opcionEntity.personalizacion ? opcionEntity.personalizacion.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/opcion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/opcion/${opcionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpcionDetail;
