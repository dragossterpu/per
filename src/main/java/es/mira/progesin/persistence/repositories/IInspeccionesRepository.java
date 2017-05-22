package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.TipoInspeccion;

/**
 * @author Ezentis
 *
 */
public interface IInspeccionesRepository extends CrudRepository<Inspeccion, Long> {
    /**
     * @param paramString nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    @Query("SELECT i FROM Inspeccion i WHERE i.fechaFinalizacion IS NULL AND i.fechaBaja IS NULL AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) ORDER BY i.nombreUnidad, i.id DESC")
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(@Param("infoInspeccion") String paramString);
    
    /**
     * @param paramString1 puede ser nombre de unidad o número de inspección
     * @param paramString2 jefe del equipo de la inspección
     * @return devuelve una lista con todas las inspecciones filtradas por nombre de la unidad y jefe de equiopo o por
     * el número de inspección y el jefe de equipo
     */
    @Query("SELECT i FROM Inspeccion i WHERE EXISTS (SELECT e FROM Equipo e WHERE e.id = i.equipo AND e.jefeEquipo = :usernameJefeEquipo) AND i.fechaFinalizacion IS NULL AND i.fechaBaja IS NULL AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) ORDER BY i.nombreUnidad, i.id DESC")
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(
            @Param("infoInspeccion") String paramString1, @Param("usernameJefeEquipo") String paramString2);
    
    /**
     * @param idDocumento id del documento
     * @return devuelve lista de inspecciones asociadas a un documento
     */
    @Query(value = "select ins.* from documentos_inspeccion di, inspecciones ins where di.id_inspeccion=ins.id and di.id_documento=?1", nativeQuery = true)
    public List<Inspeccion> cargaInspeccionesDocumento(Long idDocumento);
    
    @Query(value = "select ins.* from guia_inspeccion di, inspecciones ins where di.id_inspeccion=ins.id and di.id_guia=?1", nativeQuery = true)
    public List<Inspeccion> cargaInspeccionesGuia(Long idGuia);
    
    /**
     * @param idInspeccion
     * @return devuelve una lista con las inspecciones asociads de otra indicando su id
     */
    @Query(value = "select insAsociadas.* from inspecciones_asociadas i, inspecciones insAsociadas where i.id_inspeccion_asociada=insAsociadas.id and i.id_inspeccion= :idInspeccion ORDER BY insAsociadas.id,insAsociadas.anio", nativeQuery = true)
    public List<Inspeccion> cargaInspeccionesAsociadas(@Param("idInspeccion") Long idInspeccion);
    
    /**
     * @param tipo de inspección
     * @return true o false dependiendo de si existe una inspección del tipo pasado por parámetro
     */
    public boolean existsByTipoInspeccion(TipoInspeccion tipo);
}