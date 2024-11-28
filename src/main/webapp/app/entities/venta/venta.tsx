import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './venta.reducer';

export const Venta = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const ventaList = useAppSelector(state => state.venta.entities);
  const loading = useAppSelector(state => state.venta.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="venta-heading" data-cy="VentaHeading">
        <Translate contentKey="backendRodriApp.venta.home.title">Ventas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="backendRodriApp.venta.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/venta/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="backendRodriApp.venta.home.createLabel">Create new Venta</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ventaList && ventaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="backendRodriApp.venta.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('fechaVenta')}>
                  <Translate contentKey="backendRodriApp.venta.fechaVenta">Fecha Venta</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('fechaVenta')} />
                </th>
                <th className="hand" onClick={sort('precioFinal')}>
                  <Translate contentKey="backendRodriApp.venta.precioFinal">Precio Final</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('precioFinal')} />
                </th>
                <th>
                  <Translate contentKey="backendRodriApp.venta.dispositivo">Dispositivo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="backendRodriApp.venta.adicional">Adicional</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="backendRodriApp.venta.personalizacion">Personalizacion</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ventaList.map((venta, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/venta/${venta.id}`} color="link" size="sm">
                      {venta.id}
                    </Button>
                  </td>
                  <td>{venta.fechaVenta ? <TextFormat type="date" value={venta.fechaVenta} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{venta.precioFinal}</td>
                  <td>{venta.dispositivo ? <Link to={`/dispositivo/${venta.dispositivo.id}`}>{venta.dispositivo.id}</Link> : ''}</td>
                  <td>
                    {venta.adicionals
                      ? venta.adicionals.map((val, j) => (
                          <span key={j}>
                            <Link to={`/adicional/${val.id}`}>{val.id}</Link>
                            {j === venta.adicionals.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {venta.personalizacions
                      ? venta.personalizacions.map((val, j) => (
                          <span key={j}>
                            <Link to={`/personalizacion/${val.id}`}>{val.id}</Link>
                            {j === venta.personalizacions.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/venta/${venta.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/venta/${venta.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/venta/${venta.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="backendRodriApp.venta.home.notFound">No Ventas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Venta;
