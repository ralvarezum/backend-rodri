import dayjs from 'dayjs';
import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { IAdicional } from 'app/shared/model/adicional.model';
import { IPersonalizacion } from 'app/shared/model/personalizacion.model';

export interface IVenta {
  id?: number;
  fechaVenta?: dayjs.Dayjs | null;
  precioFinal?: number | null;
  dispositivo?: IDispositivo;
  adicionals?: IAdicional[] | null;
  personalizacions?: IPersonalizacion[] | null;
}

export const defaultValue: Readonly<IVenta> = {};
