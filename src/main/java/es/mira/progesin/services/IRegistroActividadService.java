package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.web.beans.RegActividadBusqueda;

/**
 * 
 * Interfaz para el servicio de Registro de Actividad.
 * 
 * @author EZENTIS
 *
 **/

public interface IRegistroActividadService {

  /**
   * Guarda en base de datos un registro de actividad.
   * 
   * @param entity Registro a guardar en base de datos
   * @return Registro guardado
   */
  RegistroActividad save(RegistroActividad entity);

  /**
   * Busca registros de actividad según unos parámetros de forma paginada.
   * 
   * @param first Primer elemento a mostrar
   * @param pageSize Número máximo de registros recuperados
   * @param sortField Campo de ordenación
   * @param sortOrder Sentido de la ordenación
   * @param regActividadBusqueda Objeto que contiene los parámetros de búsqueda
   * @return Listado de los registros que responden a la búsqueda
   */

  List<RegistroActividad> buscarRegActividadCriteria(int first, int pageSize, String sortField,
      SortOrder sortOrder, RegActividadBusqueda regActividadBusqueda);

  /**
   * Devuelve el número total de registros de una búsqueda.
   * 
   * @param busqueda Objeto que contiene los criterios de búsqueda
   * @return Número de registros que responden a la búsqueda
   */
  int getCounCriteria(RegActividadBusqueda busqueda);

  /**
   * Se introduce un registro de error en la base de datos.
   * 
   * @param nombreSeccion Nombre de la sección en la que se produce el error
   * @param e Excepción lanzada por la aplicación cuando se produce el error
   */
  void altaRegActividadError(String nombreSeccion, Exception e);

  /**
   * Se introduce un registro de actividad en la base de datos.
   * 
   * @param descripcion Descripción del registro
   * @param tipoReg Tipo de actividad registrada
   * @param seccion Sección en la que se hace el registro
   */
  void altaRegActividad(String descripcion, String tipoReg, String seccion);

  /**
   * Listado de registros de actividad para una sección.
   * 
   * @param infoSeccion Sección para la que se hace la consulta
   * @return Listado de los registros de la sección
   */
  List<String> buscarPorNombreSeccion(String infoSeccion);

  /**
   * Busca los registros realizados por un usuario.
   * 
   * @param infoUsuario Usuario para el que se hace la consulta
   * @return Listado de los registros resultantes
   */
  List<String> buscarPorUsuarioRegistro(String infoUsuario);
}
