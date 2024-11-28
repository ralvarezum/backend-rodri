import { Moneda } from 'app/shared/model/enumerations/moneda.model';

export interface IDispositivo {
  id?: number;
  codigo?: string | null;
  nombre?: string | null;
  descripcion?: string | null;
  precioBase?: number | null;
  moneda?: keyof typeof Moneda | null;
  idCatedra?: number | null;
}

export const defaultValue: Readonly<IDispositivo> = {};
