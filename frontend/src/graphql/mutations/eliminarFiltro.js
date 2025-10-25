import { gql } from "@apollo/client";

export const ELIMINAR_FILTRO = gql`
  mutation EliminarFiltro($id: ID!) {
    eliminarFiltro(id: $id)
  }
`;
