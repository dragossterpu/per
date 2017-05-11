package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.web.beans.InspeccionBusqueda;

/**
 * @author Ezentis
 *
 */
public interface IInspeccionesService {
    
    /**
     * Método que guarada una inspección
     * @param inspecciones a guardar
     * @return devuelve el resultado del guardado de la inspección
     */
    public Inspeccion save(Inspeccion inspecciones);
    
    /**
     * @return devuelve un Iterable con el total de inspecciones de la base de datos
     */
    public Iterable<Inspeccion> findAll();
    
    /**
     * Borra una inspección pasada por parámetro
     * @param inspecciones a borrar
     */
    public void delete(Inspeccion inspecciones);
    
    /**
     * @param infoInspeccion nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(String infoInspeccion);
    
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
     * @return busqueda bean InspeccionBusqueda que define el filtro de la consulta realizada
     */
    int getCountInspeccionCriteria(InspeccionBusqueda busqueda);
    
    /**
     * @param id d la inspección
     * @return devuelve inspección si es encontrada
     */
    public Inspeccion findInspeccionById(Long id);
    
    /**
     * @param inspeccion pasada por parámetro
     * @return devuelve lista de inspecciones asociadas a la que se pasa por parámetro
     */
    public List<Inspeccion> listaInspeccionesAsociadas(Inspeccion inspeccion);
    
    /**
     * @param tipo de inspección
     * @return true o false dependiendo de si existe una inspección del tipo pasado por parámetro
     */
    boolean existeByTipoInspeccion(TipoInspeccion tipo);
    
    /**
     * Método que realiza una consulta de inspecciones a través criteria coincidente con determinados parámetros
     * 
     * @param first primer registro
     * @param pageSize tamaño de la página (número de registros que queremos)
     * @param sortField campo por el que ordenamos
     * @param sortOrder si la ordenación es ascendente o descendente
     * @param busqueda bean InspeccionBusqueda que define el filtro de la consulta realizada
     * @return resultado de la consulta
     */
    List<Inspeccion> buscarInspeccionPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            InspeccionBusqueda busqueda);
    
}
