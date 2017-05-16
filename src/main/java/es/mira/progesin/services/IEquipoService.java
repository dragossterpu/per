package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.web.beans.EquipoBusqueda;

/**
 * 
 * Interfaz del servicio de equipo
 * 
 * @author Ezentis
 *
 */
public interface IEquipoService {
    
    /**
     * Recupera todos los equipos activos y dados de baja
     * 
     * @author Ezentis
     * @return lista de equipos
     */
    public List<Equipo> findAll();
    
    /**
     * Recupera sólo los equipos activos
     * 
     * @author Ezentis
     * @return lista de equipos
     */
    public List<Equipo> findByFechaBajaIsNull();
    
    /**
     * Guarda la información de un equipo en la bdd.
     * 
     * @author Ezentis
     * @param entity equipo
     * @return equipo con id
     */
    public Equipo save(Equipo entity);
    
    /**
     * Método que devuelve la lista de equipos en una consulta basada en criteria.
     * 
     * @author EZENTIS
     * @param equipoBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de equipos.
     */
    public List<Equipo> buscarEquipoCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            EquipoBusqueda equipoBusqueda);
    
    /**
     * Método que devuelve el número de equipos en una consulta basada en criteria
     * 
     * @param equipoBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de una consulta criteria.
     * @author EZENTIS
     */
    public int getCounCriteria(EquipoBusqueda equipoBusqueda);
    
    /**
     * Comprueba si existe algún equipo del tipo proporcionado
     * 
     * @author Ezentis
     * @param tipo
     * @return boolean, si o no
     */
    public boolean existsByTipoEquipo(TipoEquipo tipo);
    
    public List<Equipo> buscarEquiposByUsername(String paramLogin);
    
}
