import { gql } from "@apollo/client";

export const GUARDAR_FILTRO = gql`
  mutation GuardarFiltro(
    $usuarioId: ID!
    $nombreFiltro: String!
    $categoria: CategoriaDonacion
    $fechaInicio: String
    $fechaFin: String
    $eliminado: Boolean
  ) {
    guardarFiltro(
      usuarioId: $usuarioId
      nombreFiltro: $nombreFiltro
      categoria: $categoria
      fechaInicio: $fechaInicio
      fechaFin: $fechaFin
      eliminado: $eliminado
    ) {
      id
      nombreFiltro
      categoria
      fechaInicio
      fechaFin
      eliminado
    }
  }
`;
