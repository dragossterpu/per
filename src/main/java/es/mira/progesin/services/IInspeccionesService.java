package es.mira.progesin.services;

import java.util.List;

import org.hibernate.criterion.Order;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.web.beans.InspeccionBusqueda;

public interface IInspeccionesService {
    
    public Inspeccion save(Inspeccion inspecciones);
    
    public Iterable<Inspeccion> findAll();
    
    public void delete(Inspeccion inspecciones);
    
    /**
     * Busca inspecciones no finalizadas por nombre de unidad o número.
     */
    List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(String infoInspeccion);
    
    /**
     * Busca inspecciones no finalizadas por nombre de unidad o número en las que el usuario sea jefe del equipo
     * asignado a ellas.
     */
    List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(String infoInspeccion,
            String usernameJefeEquipo);
    
    long getCountInspeccionCriteria(InspeccionBusqueda busqueda);
    
    public Inspeccion findInspeccionById(Long id);
    
    List<Inspeccion> buscarInspeccionPorCriteria(int firstResult, int maxResults, InspeccionBusqueda busqueda,
            List<Order> listaOrden);
    
}
