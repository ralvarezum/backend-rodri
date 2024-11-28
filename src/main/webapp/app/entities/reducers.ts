import dispositivo from 'app/entities/dispositivo/dispositivo.reducer';
import caracteristica from 'app/entities/caracteristica/caracteristica.reducer';
import adicional from 'app/entities/adicional/adicional.reducer';
import personalizacion from 'app/entities/personalizacion/personalizacion.reducer';
import opcion from 'app/entities/opcion/opcion.reducer';
import venta from 'app/entities/venta/venta.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  dispositivo,
  caracteristica,
  adicional,
  personalizacion,
  opcion,
  venta,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
