import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { IVenta } from 'app/shared/model/venta.model';

export interface IPersonalizacion {
  id?: number;
  nombre?: string | null;
  descripcion?: string | null;
  idCatedra?: number | null;
  dispositivo?: IDispositivo | null;
  ventas?: IVenta[] | null;
}

export const defaultValue: Readonly<IPersonalizacion> = {};
