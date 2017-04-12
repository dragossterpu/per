package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Inspeccion;

public abstract interface IInspeccionesRepository extends CrudRepository<Inspeccion, Long> {
    @Query("SELECT i FROM Inspeccion i WHERE i.fechaFinalizacion IS NULL AND i.fechaBaja IS NULL AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) ORDER BY i.nombreUnidad, i.id DESC")
    public abstract List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(
            @Param("infoInspeccion") String paramString);
    //
    // @Query("SELECT i FROM Inspeccion i WHERE i.fechaFinalizacion IS NULL AND i.fechaBaja IS NULL AND i.id <>
    // :idInspeccion AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) ORDER BY
    // i.nombreUnidad, i.id DESC")
    // public abstract List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroIdDistinto(
    // @Param("infoInspeccion") String paramString, @Param("idInspeccion") Long paramLong);
    
    @Query("SELECT i FROM Inspeccion i WHERE EXISTS (SELECT e FROM Equipo e WHERE e.id = i.equipo AND e.jefeEquipo = :usernameJefeEquipo) AND i.fechaFinalizacion IS NULL AND i.fechaBaja IS NULL AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) ORDER BY i.nombreUnidad, i.id DESC")
    public abstract List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(
            @Param("infoInspeccion") String paramString1, @Param("usernameJefeEquipo") String paramString2);
    
    @Query(value = "select ins.* from documentos_inspeccion di, inspecciones ins where di.id_inspeccion=ins.id and di.id_documento=?1", nativeQuery = true)
    public abstract List<Inspeccion> cargaInspecciones(Long paramLong);
    
    @Query(value = "select ins.* from inspecciones_asociadas i, inspecciones ins where i.id_inspeccion=ins.id and i.id_inspeccion= :idInspeccion", nativeQuery = true)
    public abstract List<Inspeccion> cargaInspeccionesAsociadas(@Param("idInspeccion") Long idInspeccion);
}