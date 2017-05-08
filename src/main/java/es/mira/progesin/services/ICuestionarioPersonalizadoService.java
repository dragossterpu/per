package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda;

public interface ICuestionarioPersonalizadoService {
    void delete(Long id);
    
    void delete(Iterable<CuestionarioPersonalizado> entities);
    
    void delete(CuestionarioPersonalizado entity);
    
    void deleteAll();
    
    boolean exists(Long id);
    
    Iterable<CuestionarioPersonalizado> findAll();
    
    Iterable<CuestionarioPersonalizado> findAll(Iterable<Long> ids);
    
    CuestionarioPersonalizado findOne(Long id);
    
    Iterable<CuestionarioPersonalizado> save(Iterable<CuestionarioPersonalizado> entities);
    
    CuestionarioPersonalizado save(CuestionarioPersonalizado entity);
    
    List<CuestionarioPersonalizado> buscarCuestionarioPersonalizadoCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, CuestionarioPersonalizadoBusqueda cuestionarioBusqueda);
    
    int getCountCuestionarioCriteria(CuestionarioPersonalizadoBusqueda cuestionarioBusqueda);
    
}
