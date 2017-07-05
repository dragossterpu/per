package es.mira.progesin.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.primefaces.model.StreamedContent;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.web.beans.InspeccionBusqueda;

/**
 * Servicio de estadísticas.
 * 
 * @author EZENTIS
 *
 */
public interface IEstadisticaService {

  /**
   * Obtiene los datos de estadísticas agrupados por el estado de la inspección.
   * 
   * @param filtro Filtro que se aplica a la búsqueda.
   * @return Mapa de las inspecciones agrupadas por su estado.
   */
  Map<EstadoInspeccionEnum, Integer> obtenerEstadisticas(InspeccionBusqueda filtro);

  /**
   * Exporta las estadísticas a un fichero PDF descargable.
   * 
   * @param filtro Filtro que se aplica a la búsqueda.
   * @param listaEstadosSeleccionados Lista de los estados que serán exportados
   * @param fileImg Fichero con la imagen que se incrustará en el PDF.
   * @return Flujo de datos con el PDF generado
   * @throws ProgesinException Posible excepción
   */
  StreamedContent exportar(InspeccionBusqueda filtro,
      List<EstadoInspeccionEnum> listaEstadosSeleccionados, File fileImg) throws ProgesinException;

  /**
   * Carga la lista de inspecciones que se encuentran en el estado solicitado.
   * 
   * @param filtro Filtro que se aplica a la búsqueda.
   * @param estado Estado que se desea consultar
   * @return Listado de las inspecciones que corresponden a la búsqueda.
   */
  List<Inspeccion> verListaEstado(InspeccionBusqueda filtro, EstadoInspeccionEnum estado);

  /**
   * Obtiene el total de resultados de la consulta estadística.
   * 
   * @param estadistica Mapa de valores de la consulta.
   * @return Número total de inspecciones de las que se hace estadística
   */
  int obtenerTotal(Map<EstadoInspeccionEnum, Integer> estadistica);
}
