package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Inspeccion;

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
    
}
