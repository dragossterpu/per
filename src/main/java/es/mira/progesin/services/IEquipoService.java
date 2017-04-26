package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.web.beans.EquipoBusqueda;

public interface IEquipoService {
    
    Iterable<Equipo> findAll();
    
    List<Equipo> findByFechaBajaIsNotNull();
    
    Miembro save(Miembro miembro);
    
    List<Miembro> save(List<Miembro> listaMiembros);
    
    List<Miembro> findByEquipo(Equipo equipo);
    
    Equipo save(Equipo entity);
    
    List<Equipo> buscarEquipoCriteria(EquipoBusqueda equipoBusqueda);
    
    boolean existsByTipoEquipo(TipoEquipo tipo);
    
}
