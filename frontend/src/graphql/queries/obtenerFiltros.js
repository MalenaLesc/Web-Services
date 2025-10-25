import { gql } from "@apollo/client";

export const OBTENER_FILTROS_POR_USUARIO = gql`
  query FiltrosPorUsuario($usuarioId: ID!) {
    filtrosPorUsuario(usuarioId: $usuarioId) {
      id
      nombreFiltro
      categoria
      fechaInicio
      fechaFin
      eliminado
    }
}`;
