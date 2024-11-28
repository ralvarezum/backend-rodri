import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './venta.reducer';

export const VentaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ventaEntity = useAppSelector(state => state.venta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ventaDetailsHeading">
          <Translate contentKey="backendRodriApp.venta.detail.title">Venta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.id}</dd>
          <dt>
            <span id="fechaVenta">
              <Translate contentKey="backendRodriApp.venta.fechaVenta">Fecha Venta</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.fechaVenta ? <TextFormat value={ventaEntity.fechaVenta} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="precioFinal">
              <Translate contentKey="backendRodriApp.venta.precioFinal">Precio Final</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.precioFinal}</dd>
          <dt>
            <Translate contentKey="backendRodriApp.venta.dispositivo">Dispositivo</Translate>
          </dt>
          <dd>{ventaEntity.dispositivo ? ventaEntity.dispositivo.id : ''}</dd>
          <dt>
            <Translate contentKey="backendRodriApp.venta.adicional">Adicional</Translate>
          </dt>
          <dd>
            {ventaEntity.adicionals
              ? ventaEntity.adicionals.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {ventaEntity.adicionals && i === ventaEntity.adicionals.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="backendRodriApp.venta.personalizacion">Personalizacion</Translate>
          </dt>
          <dd>
            {ventaEntity.personalizacions
              ? ventaEntity.personalizacions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {ventaEntity.personalizacions && i === ventaEntity.personalizacions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/venta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/venta/${ventaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VentaDetail;
