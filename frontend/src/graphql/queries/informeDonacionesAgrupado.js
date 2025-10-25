import { gql } from "@apollo/client";

export const INFORME_DONACIONES_AGRUPADO = gql`
  query InformeDonacionesAgrupado(
    $categoria: CategoriaDonacion
    $fechaInicio: String
    $fechaFin: String
    $eliminado: Boolean
  ) {
    informeDonacionesAgrupado(
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
