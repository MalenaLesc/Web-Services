import React, { useState } from "react";
import { useLazyQuery, useMutation, useQuery } from "@apollo/client/react";
import { INFORME_DONACIONES } from "../graphql/queries/informeDonaciones";
import { INFORME_DONACIONES_AGRUPADO } from "../graphql/queries/informeDonacionesAgrupado";
import { OBTENER_FILTROS_POR_USUARIO } from "../graphql/queries/obtenerFiltros";
import { GUARDAR_FILTRO } from "../graphql/mutations/guardarFiltro";
import { ELIMINAR_FILTRO } from "../graphql/mutations/eliminarFiltro";
import { EDITAR_FILTRO } from "../graphql/mutations/editarFiltro";

import "./informeDonaciones.css";

export default function InformeDonaciones() {
  const [categoria, setCategoria] = useState("");
  const [fechaInicio, setFechaInicio] = useState("");
  const [fechaFin, setFechaFin] = useState("");
  const [eliminado, setEliminado] = useState("");

  const [activeTab, setActiveTab] = useState("informeAgrupado");

  const [editandoId, setEditandoId] = useState(null);
  const [filtroEditado, setFiltroEditado] = useState({});

  const usuarioId = 1;
  const validEnums = ["ROPA", "ALIMENTOS", "JUGUETES", "UTILES_ESCOLARES"];

  const [fetchInforme, { data, loading }] = useLazyQuery(
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
    setEliminado(filtro.eliminado === null ? "" : filtro.eliminado.toString());
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

  const handleEditarFiltro = (filtro) => {
    setEditandoId(filtro.id);
    setFiltroEditado({
      ...filtro,
      fechaInicio: filtro.fechaInicio || "",
      fechaFin: filtro.fechaFin || "",
      eliminado: filtro.eliminado?.toString() || "",
    });
  };

  const handleGuardarEdicion = async () => {
    try {
      await editarFiltro({
        variables: {
          id: filtroEditado.id,
          nombreFiltro: filtroEditado.nombreFiltro,
          categoria: filtroEditado.categoria || null,
          fechaInicio: filtroEditado.fechaInicio || null,
          fechaFin: filtroEditado.fechaFin || null,
          eliminado:
            filtroEditado.eliminado === ""
              ? null
              : filtroEditado.eliminado === "true",
        },
      });
      alert("Filtro actualizado correctamente.");
      setEditandoId(null);
      refetch();
    } catch (err) {
      console.error(err);
      alert("Error al editar el filtro.");
    }
  };

  return (
    <div className="informe-container">
      <h2>Informe de Donaciones</h2>

      <div className="tabs">
        <button
          className={`tab-btn ${
            activeTab === "informeDetallado" ? "active" : ""
          }`}
          onClick={() => setActiveTab("informeDetallado")}
        >
          Vista Detallada
        </button>
        <button
          className={`tab-btn ${
            activeTab === "informeAgrupado" ? "active" : ""
          }`}
          onClick={() => setActiveTab("informeAgrupado")}
        >
          Vista Agrupada
        </button>
        <button
          className={`tab-btn ${
            activeTab === "filtrosGuardados" ? "active" : ""
          }`}
          onClick={() => {
            setActiveTab("filtrosGuardados");
            refetch();
          }}
        >
          Ver filtros guardados
        </button>
      </div>

      <div className="filter-grid">
        <select
          value={categoria}
          onChange={(e) => setCategoria(e.target.value)}
        >
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

        <select
          value={eliminado}
          onChange={(e) => setEliminado(e.target.value)}
        >
          <option value="">Eliminado (ambos)</option>
          <option value="true">Sí</option>
          <option value="false">No</option>
        </select>

        <button onClick={handleBuscar}>Buscar</button>
        <button className="btn-guardar" onClick={handleGuardarFiltro}>
          Guardar Filtros
        </button>
      </div>

      {activeTab === "filtrosGuardados" ? (
        <table className="table-container">
          <thead>
            <tr className="edit-row">
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
                <>
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
                    <td className="td-btn-container">
                      <button
                        onClick={() => handleAplicarFiltro(f)}
                        className="action-btn"
                      >
                        ✅ Aplicar
                      </button>
                      <button
                        onClick={() => handleEditarFiltro(f)}
                        className="action-btn"
                      >
                        ✏️ Editar
                      </button>
                      <button
                        onClick={() => handleEliminarFiltro(f.id)}
                        className="action-btn"
                      >
                        🗑️ Borrar
                      </button>
                    </td>
                  </tr>
                  {editandoId === f.id && (
                    <tr key={`${f.id}-edit`}>
                      <td colSpan="6" className="edit-row">
                        <div className="edit-form">
                          <input
                            type="text"
                            value={filtroEditado.nombreFiltro}
                            onChange={(e) =>
                              setFiltroEditado({
                                ...filtroEditado,
                                nombreFiltro: e.target.value,
                              })
                            }
                            placeholder="Nombre"
                          />
                          <select
                            value={filtroEditado.categoria || ""}
                            onChange={(e) =>
                              setFiltroEditado({
                                ...filtroEditado,
                                categoria: e.target.value,
                              })
                            }
                          >
                            <option value="">Categoría</option>
                            <option value="ALIMENTOS">ALIMENTOS</option>
                            <option value="ROPA">ROPA</option>
                            <option value="UTILES_ESCOLARES">
                              UTILES_ESCOLARES
                            </option>
                            <option value="JUGUETES">JUGUETES</option>
                          </select>
                          <input
                            type="datetime-local"
                            value={filtroEditado.fechaInicio}
                            onChange={(e) =>
                              setFiltroEditado({
                                ...filtroEditado,
                                fechaInicio: e.target.value,
                              })
                            }
                          />
                          <input
                            type="datetime-local"
                            value={filtroEditado.fechaFin}
                            onChange={(e) =>
                              setFiltroEditado({
                                ...filtroEditado,
                                fechaFin: e.target.value,
                              })
                            }
                          />
                          <select
                            value={filtroEditado.eliminado}
                            onChange={(e) =>
                              setFiltroEditado({
                                ...filtroEditado,
                                eliminado: e.target.value,
                              })
                            }
                          >
                            <option value="">Ambos</option>
                            <option value="true">Sí</option>
                            <option value="false">No</option>
                          </select>
                          <button onClick={handleGuardarEdicion}>
                            Guardar
                          </button>
                          <button onClick={() => setEditandoId(null)}>
                            Cancelar
                          </button>
                        </div>
                      </td>
                    </tr>
                  )}
                </>
              ))
            ) : (
              <tr>
                <td colSpan="6" className="table-empty">
                  No hay filtros guardados.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      ) : data?.informeDonaciones?.length > 0 ||
        data?.informeDonacionesAgrupado?.length > 0 ? (
        <table className="table-container">
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
