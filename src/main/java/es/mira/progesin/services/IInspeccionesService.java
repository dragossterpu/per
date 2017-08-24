package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.web.beans.InspeccionBusqueda;

/**
 * Interfaz del servicio de inspeciones.
 * @author EZENTIS
 *
 */
public interface IInspeccionesService {
    
    /**
     * Método que guarda una inspección.
     * @param inspecciones a guardar
     * @return devuelve la inspección guardada
     */
    Inspeccion save(Inspeccion inspecciones);
    
    /**
     * Borra una inspección pasada por parámetro.
     * @param inspecciones a borrar
     */
    void delete(Inspeccion inspecciones);
    
    /**
     * Busca inspecciones no finalizadas filtrando por el nombre de la unidad o el número de la inspección.
     * 
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(String infoInspeccion);
    
    /**
     * Busca inspecciones filtrando por el nombre de la unidad o el número de la inspección.
     * 
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    List<Inspeccion> buscarPorNombreUnidadONumero(String infoInspeccion);
    
    /**
     * @param infoInspeccion puede ser nombre de unidad o número de inspección
     * @param usernameJefeEquipo jefe del equipo de la inspección
     * @return devuelve una lista con todas las inspecciones filtradas por nombre de la unidad y jefe de equiopo o por
     * el número de inspección y el jefe de equipo
     */
    List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(String infoInspeccion,
            String usernameJefeEquipo);
    
    /**
     * @param busqueda bean InspeccionBusqueda que define el filtro de la consulta realizada
     * @return número de registros encontrados
     */
    int getCountInspeccionCriteria(InspeccionBusqueda busqueda);
    
    /**
     * Busca una inspección con cierto id pasado por parámetro.
     * 
     * @param id de la inspección
     * @return devuelve inspección si es encontrada.
     */
    Inspeccion findInspeccionById(Long id);
    
    /**
     * Devuelve la lista de inspecciones asociadas a otra.
     * 
     * @param inspeccion pasada por parámetro
     * @return devuelve lista de inspecciones asociadas
     */
    List<Inspeccion> listaInspeccionesAsociadas(Inspeccion inspeccion);
    
    /**
     * Comprueba si existe una inspección de determinado tipo.
     * 
     * @param tipo de inspección
     * @return valor booleando de la comprobación
     */
    boolean existeByTipoInspeccion(TipoInspeccion tipo);
    
    /**
     * Método que realiza una consulta de inspecciones, usando criteria, coincidente con determinados parámetros.
     * 
     * @param first primer registro
     * @param pageSize tamaño de la página (número de registros que queremos)
     * @param sortField campo por el que ordenamos
     * @param sortOrder si la ordenación es ascendente o descendente
     * @param busqueda bean InspeccionBusqueda que define el filtro de la consulta realizada
     * @return lista de inspecciones resultado de la consulta
     */
    List<Inspeccion> buscarInspeccionPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            InspeccionBusqueda busqueda);
    
    /**
     * Cambia el estado de una inspección.
     * 
     * @param inspeccion a cambiar
     * @param estado a poner
     */
    void cambiarEstado(Inspeccion inspeccion, EstadoInspeccionEnum estado);
    
    /**
     * Devuelve un listado de inspecciones que están en un estado recibido como parámetro.
     * 
     * @param estado Estado de inspección a buscar
     * @return Resultado de la búsqueda
     */
    List<Inspeccion> findByEstadoInspeccion(EstadoInspeccionEnum estado);
    
    /**
     * Devuelve las inspecciones en las que ha participado un usuario.
     * 
     * @param usuario Usuario consultado
     * @return Listado de las inspecciones en las que ha participado.
     */
    List<Inspeccion> findInspeccionesByUsuario(String usuario);
    
    /**
     * Devuelve un listado de inspecciones a partir de su nombre de unidad o año. Los resultados se filtran para
     * devolver sólo aquellos en los que el usuario está implicado.
     * 
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @param usuario Usuario por el que se filtra
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    List<Inspeccion> buscarPorNombreUnidadONumeroUsuario(String infoInspeccion, User usuario);
    
    /**
     * Método que devuelve si existen inspecciones sin finalizar para un determinado equipo.
     * 
     * @param equipo de la inspección
     * @return valor booleano dependiendo de si existe una inspección finalizada o no
     */
    public boolean existenInspeccionesNoFinalizadas(Equipo equipo);
    
    /**
     * Devuelve una cadena con el texto del registro utilizado en la modificación de una inspección.
     * 
     * @param insp inspección actual
     * @param inspMod inspección modificada
     * @param inspecciones inspecciones asociadas actuales
     * @param inspeccionesMod inspecciones asociadas modificadas
     * @return descripcion
     */
    String getTextoRegistro(Inspeccion insp, Inspeccion inspMod, List<Inspeccion> inspecciones,
            List<Inspeccion> inspeccionesMod);
    
    /**
     * Método que realiza una consulta de inspecciones, usando criteria, coincidente con determinados parámetros.
     * 
     * @param busqueda bean InspeccionBusqueda que define el filtro de la consulta realizada
     * @return lista de inspecciones resultado de la consulta
     */
    public List<Inspeccion> buscarInspeccionPorCriteriaEstadisticas(InspeccionBusqueda busqueda);
    
}
