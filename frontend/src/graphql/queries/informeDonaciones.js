import { gql } from "@apollo/client";

export const INFORME_DONACIONES = gql`
  query InformeDonaciones(
    $categoria: CategoriaDonacion
    $fechaInicio: String
    $fechaFin: String
    $eliminado: Boolean
  ) {
    informeDonaciones(
      categoria: $categoria
      fechaInicio: $fechaInicio
      fechaFin: $fechaFin
      eliminado: $eliminado
    ) {
      categoria
      eliminado
      total
    }
  }
`;
