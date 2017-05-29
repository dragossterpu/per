package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.TipoInspeccion;
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
    public Inspeccion save(Inspeccion inspecciones);
    
    /**
     * Borra una inspección pasada por parámetro.
     * @param inspecciones a borrar
     */
    public void delete(Inspeccion inspecciones);
    
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
    public Inspeccion findInspeccionById(Long id);
    
    /**
     * Devuelve la lista de inspecciones asociadas a otra.
     * 
     * @param inspeccion pasada por parámetro
     * @return devuelve lista de inspecciones asociadas
     */
    public List<Inspeccion> listaInspeccionesAsociadas(Inspeccion inspeccion);
    
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
    
}
