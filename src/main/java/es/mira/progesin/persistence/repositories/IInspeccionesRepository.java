package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Inspeccion;

public interface IInspeccionesRepository extends CrudRepository<Inspeccion, Long> {
    
    @Query("SELECT i FROM Inspeccion i WHERE i.fechaFinalizacion IS NULL AND i.fechaBaja IS NULL "
            + "AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) "
            + "ORDER BY i.nombreUnidad, i.id DESC")
    List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(@Param("infoInspeccion") String infoInspeccion);
    
    @Query("SELECT i FROM Inspeccion i WHERE EXISTS (SELECT e FROM Equipo e WHERE e.id = i.equipo AND e.jefeEquipo = :usernameJefeEquipo) "
            + "AND i.fechaFinalizacion IS NULL AND i.fechaBaja IS NULL "
            + "AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) "
            + "ORDER BY i.nombreUnidad, i.id DESC")
    List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(@Param("infoInspeccion") String infoInspeccion,
            @Param("usernameJefeEquipo") String usernameJefeEquipo);
    
}