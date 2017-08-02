package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;
import org.springframework.data.repository.query.Param;

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
    List<Equipo> findAll();
    
    /**
     * Recupera sólo los equipos activos.
     * 
     * @return lista de equipos
     */
    List<Equipo> findByFechaBajaIsNull();
    
    /**
     * Devuelve un equipo localizado por su id.
     * 
     * @param id Identificador del equipo
     * @return Equipo
     */
    Equipo findOne(Long id);
    
    /**
     * Guarda la información de un equipo en la bdd.
     * 
     * @param entity equipo
     * @return equipo con id
     */
    Equipo save(Equipo entity);
    
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
    List<Equipo> buscarEquipoCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            EquipoBusqueda equipoBusqueda);
    
    /**
     * Método que devuelve el número de equipos en una consulta basada en criteria.
     * 
     * @param equipoBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de una consulta criteria.
     */
    int getCounCriteria(EquipoBusqueda equipoBusqueda);
    
    /**
     * Comprueba si existe algún equipo del tipo proporcionado.
     * 
     * @param tipo de equipo
     * @return boolean valor booleano
     */
    boolean existsByTipoEquipo(TipoEquipo tipo);
    
    /**
     * Búsqueda de equipos por nombre de usuario.
     * 
     * @param paramLogin nombre usuario (username)
     * @return listado de equipos a los que pertenece el usuario
     */
    List<Equipo> buscarEquiposByUsername(String paramLogin);
    
    /**
     * Devuelve el equipo de un miembro con Rol jefe.
     * 
     * @param paramLogin username jefe
     * @return Equipo devuelto
     */
    Equipo buscarEquipoByJefe(@Param("infoLogin") String paramLogin);
    
}
