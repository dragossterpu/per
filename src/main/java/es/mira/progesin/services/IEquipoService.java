package es.mira.progesin.services;

import java.util.List;

import org.hibernate.Criteria;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.web.beans.EquipoBusqueda;

/**
 * Interfaz del servicio que gestiona los equipos.
 * 
 * @author EZENTIS
 */
public interface IEquipoService {
    
    /**
     * Recupera todos los equipos activos y dados de baja.
     * 
     * @return lista de equipos
     */
    public List<Equipo> findAll();
    
    /**
     * Recupera sólo los equipos activos.
     * 
     * @return lista de equipos
     */
    public List<Equipo> findByFechaBajaIsNull();
    
    /**
     * Devuelve un equipo localizado por su id.
     * 
     * @param id Identificador del equipo
     * @return Equipo
     */
    public Equipo findOne(Long id);
    
    /**
     * Guarda la información de un equipo en la bdd.
     * 
     * @param entity equipo
     * @return equipo con id
     */
    public Equipo save(Equipo entity);
    
    /**
     * Método que devuelve la lista de equipos en una consulta basada en criteria.
     * 
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
     * Método que devuelve el número de equipos en una consulta basada en criteria.
     * 
     * @param equipoBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de una consulta criteria.
     */
    public int getCounCriteria(EquipoBusqueda equipoBusqueda);
    
    /**
     * Comprueba si existe algún equipo del tipo proporcionado.
     * 
     * @param tipo de equipo
     * @return boolean valor booleano
     */
    public boolean existsByTipoEquipo(TipoEquipo tipo);
    
    /**
     * Búsqueda de equipos por nombre de usuario.
     * 
     * @param paramLogin nombre usuario (username)
     * @return listado de equipos a los que pertenece el usuario
     */
    public List<Equipo> buscarEquiposByUsername(String paramLogin);
    
    /**
     * Añade al criteria als restricciones necesarias para realizar las consultas en caso de que el usuario conectado
     * tenga el rol EQUIPO_INSPECCIONES.
     * @param criteria criteria al que se desea añadir la restricción
     */
    public void setCriteriaEquipo(Criteria criteria);
    
}
