import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { IVenta } from 'app/shared/model/venta.model';

export interface IAdicional {
  id?: number;
  nombre?: string | null;
  descripcion?: string | null;
  precio?: number | null;
  precioGratis?: number | null;
  idCatedra?: number | null;
  dispositivo?: IDispositivo | null;
  ventas?: IVenta[] | null;
}

export const defaultValue: Readonly<IAdicional> = {};
