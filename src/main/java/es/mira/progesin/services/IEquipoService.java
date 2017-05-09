package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
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
    
    Iterable<Equipo> findAll();
    
    List<Equipo> findByFechaBajaIsNotNull();
    
    Miembro save(Miembro miembro);
    
    List<Miembro> save(List<Miembro> listaMiembros);
    
    List<Miembro> findByEquipo(Equipo equipo);
    
    Equipo save(Equipo entity);
    
    List<Equipo> buscarEquipoCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            EquipoBusqueda equipoBusqueda);
    
    int getCounCriteria(EquipoBusqueda busqueda);
    
    boolean existsByTipoEquipo(TipoEquipo tipo);
    
}
