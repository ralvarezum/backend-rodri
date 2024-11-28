import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './adicional.reducer';

export const AdicionalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const adicionalEntity = useAppSelector(state => state.adicional.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adicionalDetailsHeading">
          <Translate contentKey="backendRodriApp.adicional.detail.title">Adicional</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="backendRodriApp.adicional.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="backendRodriApp.adicional.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.descripcion}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="backendRodriApp.adicional.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.precio}</dd>
          <dt>
            <span id="precioGratis">
              <Translate contentKey="backendRodriApp.adicional.precioGratis">Precio Gratis</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.precioGratis}</dd>
          <dt>
            <span id="idCatedra">
              <Translate contentKey="backendRodriApp.adicional.idCatedra">Id Catedra</Translate>
            </span>
          </dt>
          <dd>{adicionalEntity.idCatedra}</dd>
          <dt>
            <Translate contentKey="backendRodriApp.adicional.dispositivo">Dispositivo</Translate>
          </dt>
          <dd>{adicionalEntity.dispositivo ? adicionalEntity.dispositivo.id : ''}</dd>
          <dt>
            <Translate contentKey="backendRodriApp.adicional.venta">Venta</Translate>
          </dt>
          <dd>
            {adicionalEntity.ventas
              ? adicionalEntity.ventas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {adicionalEntity.ventas && i === adicionalEntity.ventas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/adicional" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adicional/${adicionalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdicionalDetail;
