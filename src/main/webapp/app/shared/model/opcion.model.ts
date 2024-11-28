import { IPersonalizacion } from 'app/shared/model/personalizacion.model';

export interface IOpcion {
  id?: number;
  codigo?: string | null;
  nombre?: string | null;
  descripcion?: string | null;
  precioAdicional?: number | null;
  idCatedra?: number | null;
  personalizacion?: IPersonalizacion | null;
}

export const defaultValue: Readonly<IOpcion> = {};
