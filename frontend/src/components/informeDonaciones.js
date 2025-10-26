import React, { useState } from "react";
import { useLazyQuery, useMutation, useQuery } from "@apollo/client/react";
import { INFORME_DONACIONES } from "../graphql/queries/informeDonaciones";
import { INFORME_DONACIONES_AGRUPADO } from "../graphql/queries/informeDonacionesAgrupado";
import { OBTENER_FILTROS_POR_USUARIO } from "../graphql/queries/obtenerFiltros";
import { GUARDAR_FILTRO } from "../graphql/mutations/guardarFiltro";
import { ELIMINAR_FILTRO } from "../graphql/mutations/eliminarFiltro";
import { EDITAR_FILTRO } from "../graphql/mutations/editarFiltro";


export default function InformeDonaciones() {
  const [categoria, setCategoria] = useState("");
  const [fechaInicio, setFechaInicio] = useState("");
  const [fechaFin, setFechaFin] = useState("");
  const [eliminado, setEliminado] = useState("");

  const [activeTab, setActiveTab] = useState("informeAgrupado");

  const usuarioId = 1;
  const validEnums = ["ROPA", "ALIMENTOS", "JUGUETES", "UTILES_ESCOLARES"];

  const [fetchInforme, { data, loading, error }] = useLazyQuery(
    activeTab === "informeAgrupado"
      ? INFORME_DONACIONES_AGRUPADO
      : INFORME_DONACIONES
  );

  const [editarFiltro] = useMutation(EDITAR_FILTRO);
  const [eliminarFiltro] = useMutation(ELIMINAR_FILTRO);
  const [guardarFiltro] = useMutation(GUARDAR_FILTRO);

  const handleGuardarFiltro = async () => {
    const nombreFiltro = prompt("Ingrese un nombre para este filtro:");
    if (!nombreFiltro) return;

    try {
      const { data } = await guardarFiltro({
        variables: {
          usuarioId,
          nombreFiltro,
          categoria: validEnums.includes(categoria.toUpperCase())
            ? categoria.toUpperCase()
            : null,
          fechaInicio: fechaInicio || null,
          fechaFin: fechaFin || null,
          eliminado:
            eliminado === ""
              ? null
              : eliminado === "true"
              ? true
              : eliminado === "false"
              ? false
              : null,
        },
      });
      console.log("Filtro guardado:", data);
    } catch (err) {
      console.error(err);
    }
  };

  
  const { data: filtrosData, refetch } = useQuery(OBTENER_FILTROS_POR_USUARIO, {
    variables: { usuarioId },
  });

  const handleBuscar = () => {
    fetchInforme({
      variables: {
        categoria: validEnums.includes(categoria.toUpperCase())
          ? categoria.toUpperCase()
          : null,
        fechaInicio: fechaInicio || null,
        fechaFin: fechaFin || null,
        eliminado:
          eliminado === ""
            ? null
            : eliminado === "true"
            ? true
            : eliminado === "false"
            ? false
            : null,
      },
    });
  };


  const handleAplicarFiltro = (filtro) => {
    setCategoria(filtro.categoria || "");
    setFechaInicio(filtro.fechaInicio || "");
    setFechaFin(filtro.fechaFin || "");
    setEliminado(
      filtro.eliminado === null ? "" : filtro.eliminado.toString()
    );
    setActiveTab("informeAgrupado");
  };

  const handleEliminarFiltro = async (id) => {

    try {
      await eliminarFiltro({ variables: { id } });
      alert("Filtro eliminado correctamente.");
      refetch();
    } catch (err) {
      console.error(err);
    }
  };

  const handleEditarFiltro = async (filtro) => {
  const nuevoNombre = prompt("Nuevo nombre del filtro:", filtro.nombreFiltro);
  if (!nuevoNombre) return;

  try {
    await editarFiltro({
      variables: {
        id: filtro.id,
        nombreFiltro: nuevoNombre,
        categoria: filtro.categoria || null,
        fechaInicio: filtro.fechaInicio || null,
        fechaFin: filtro.fechaFin || null,
        eliminado: filtro.eliminado,
      },
    });
    alert("✏️ Filtro actualizado correctamente.");
    refetch();
  } catch (err) {
    console.error(err);
    alert("❌ Error al editar el filtro.");
  }
};


  return (
    <div style={{ maxWidth: "900px", margin: "2rem auto" }}>
      <h2>Informe de Donaciones</h2>


      <div style={{ marginBottom: "1rem", display: "flex", gap: "1rem" }}>
        <button
          onClick={() => setActiveTab("informeDetallado")}
          style={{
            backgroundColor:
              activeTab === "informeDetallado" ? "#1976d2" : "#ccc",
            color: "white",
            border: "none",
            padding: "0.5rem 1rem",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          Vista Detallada
        </button>
        <button
          onClick={() => setActiveTab("informeAgrupado")}
          style={{
            backgroundColor:
              activeTab === "informeAgrupado" ? "#1976d2" : "#ccc",
            color: "white",
            border: "none",
            padding: "0.5rem 1rem",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          Vista Agrupada
        </button>
        <button
          onClick={() => {
            setActiveTab("filtrosGuardados");
            refetch();
          }}
          style={{
            backgroundColor:
              activeTab === "filtrosGuardados" ? "#1976d2" : "#ccc",
            color: "white",
            border: "none",
            padding: "0.5rem 1rem",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          Ver filtros guardados
        </button>
      </div>

      <div
        style={{
          display: "grid",
          gridTemplateColumns: "1fr 1fr 1fr 1fr auto",
          gap: "0.5rem",
          marginBottom: "1rem",
        }}
      >
        <select value={categoria} onChange={(e) => setCategoria(e.target.value)}>
          <option value="">Categoría (todas)</option>
          <option value="ALIMENTOS">ALIMENTOS</option>
          <option value="ROPA">ROPA</option>
          <option value="UTILES_ESCOLARES">UTILES_ESCOLARES</option>
          <option value="JUGUETES">JUGUETES</option>
        </select>

        <input
          type="datetime-local"
          value={fechaInicio}
          onChange={(e) => setFechaInicio(e.target.value)}
        />

        <input
          type="datetime-local"
          value={fechaFin}
          onChange={(e) => setFechaFin(e.target.value)}
        />

        <select value={eliminado} onChange={(e) => setEliminado(e.target.value)}>
          <option value="">Eliminado (ambos)</option>
          <option value="true">Sí</option>
          <option value="false">No</option>
        </select>

        <button onClick={handleBuscar}>Buscar</button>
        <button
          onClick={handleGuardarFiltro}
          style={{
            backgroundColor: "#1976d2",
            color: "white",
            border: "none",
            padding: "0.5rem 1rem",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          Guardar Filtros
        </button>
      </div>

      {activeTab === "filtrosGuardados" ? (
        <table
          style={{
            width: "100%",
            borderCollapse: "collapse",
            textAlign: "left",
          }}
        >
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Categoría</th>
              <th>Fecha inicio</th>
              <th>Fecha fin</th>
              <th>Eliminado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {filtrosData?.filtrosPorUsuario?.length > 0 ? (
              filtrosData.filtrosPorUsuario.map((f) => (
                <tr key={f.id}>
                  <td>{f.nombreFiltro}</td>
                  <td>{f.categoria || "—"}</td>
                  <td>{f.fechaInicio || "—"}</td>
                  <td>{f.fechaFin || "—"}</td>
                  <td>
                    {f.eliminado === null
                      ? "Ambos"
                      : f.eliminado
                      ? "Sí"
                      : "No"}
                  </td>
                  <td>
                    <button onClick={() => handleAplicarFiltro(f)}>✅ Aplicar</button>
                    <button
                      onClick={() => handleEditarFiltro(f)}
                      style={{ color: "orange", marginLeft: "0.5rem" }}
                    >
                      ✏️ Editar
                    </button>
                    <button
                      onClick={() => handleEliminarFiltro(f.id)}
                      style={{ color: "red", marginLeft: "0.5rem" }}
                    >
                      🗑️ Borrar
                    </button>
                  </td>

                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="6" style={{ textAlign: "center", padding: "1rem" }}>
                  No hay filtros guardados.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      ) :data?.informeDonaciones?.length > 0 ||
  data?.informeDonacionesAgrupado?.length > 0 ? (
  <table
    style={{
      width: "100%",
      borderCollapse: "collapse",
      textAlign: "left",
    }}
  >
    <thead>
      <tr>
        <th>Categoría</th>
        <th>Eliminado</th>
        <th>Total</th>
      </tr>
    </thead>
    <tbody>
      {(activeTab === "informeAgrupado"
        ? data?.informeDonacionesAgrupado
        : data?.informeDonaciones
      )?.map((item, index) => (
        <tr key={index}>
          <td>{item.categoria}</td>
          <td>{item.eliminado ? "Sí" : "No"}</td>
          <td>{item.total}</td>
        </tr>
      ))}
    </tbody>
  </table>
) : (
  !loading && <p>No se encontraron resultados.</p>
)}
    </div>
  );
}