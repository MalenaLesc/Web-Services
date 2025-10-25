import { gql } from "@apollo/client";

export const INFORME_PARTICIPACION = gql`
  query InformeParticipacion($usuarioId: ID!, $fechaInicio: String, $fechaFin: String) {
    informeParticipacion(usuarioId: $usuarioId, fechaInicio: $fechaInicio, fechaFin: $fechaFin) {
      mes
      eventos {
        dia
        nombreEvento
        descripcion
        totalDonaciones
      }
    }
  }
`;
