import { gql } from "@apollo/client";

export const EDITAR_FILTRO = gql`
  mutation EditarFiltro(
    $id: ID!
    $nombreFiltro: String
    $categoria: CategoriaDonacion
    $fechaInicio: String
    $fechaFin: String
    $eliminado: Boolean
  ) {
    editarFiltro(
      id: $id
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
