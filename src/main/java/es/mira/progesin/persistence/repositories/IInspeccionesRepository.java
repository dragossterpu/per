package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;

/**
 * Repositorio de inspecciones.
 * 
 * @author Ezentis
 *
 */
public interface IInspeccionesRepository extends CrudRepository<Inspeccion, Long> {
    /**
     * @param paramString nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     * 
     */
    @Query("SELECT i FROM Inspeccion i WHERE (upper(i.nombreUnidad) LIKE upper(:inspeccionInfo) OR (i.id||'/'||i.anio) LIKE :inspeccionInfo) ORDER BY i.nombreUnidad, i.id DESC")
    public List<Inspeccion> buscarPorNombreUnidadONumero(@Param("inspeccionInfo") String paramString);
    
    /**
     * @param paramString nombre de la unidad o número de inspección
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     * 
     */
    @Query("SELECT i FROM Inspeccion i WHERE i.fechaFinalizacion IS NULL AND i.fechaBaja IS NULL AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR (i.id||'/'||i.anio) LIKE :infoInspeccion) ORDER BY i.nombreUnidad, i.id DESC")
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(@Param("infoInspeccion") String paramString);
    
    /**
     * @param paramString1 puede ser nombre de unidad o número de inspección
     * @param paramString2 jefe del equipo de la inspección
     * @return devuelve una lista con todas las inspecciones filtradas por nombre de la unidad y jefe de equipo o por el
     * número de inspección y el jefe de equipo
     */
    @Query("SELECT i FROM Inspeccion i WHERE EXISTS (SELECT e FROM Equipo e WHERE e.id = i.equipo AND e.jefeEquipo.username = :usernameJefeEquipo) AND i.fechaFinalizacion IS NULL AND i.fechaBaja IS NULL AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR (i.id||'/'||i.anio) LIKE :infoInspeccion) ORDER BY i.nombreUnidad, i.id DESC")
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(
            @Param("infoInspeccion") String paramString1, @Param("usernameJefeEquipo") String paramString2);
    
    /**
     * @param paramString1 puede ser nombre de unidad o número de inspección
     * @param paramString2 miembro del equipo de la inspección
     * @return devuelve una lista con todas las inspecciones filtradas por nombre de la unidad y miembro de equipo o por
     * el número de inspección y el miembro de equipo
     */
    @Query("SELECT i FROM Inspeccion i WHERE EXISTS (SELECT m FROM Miembro m WHERE m.equipo = i.equipo AND m.usuario.username = :usernameMiembroEquipo) AND i.fechaFinalizacion IS NULL AND i.fechaBaja IS NULL AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR (i.id||'/'||i.anio) LIKE :infoInspeccion) ORDER BY i.nombreUnidad, i.id DESC")
    public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroYMiembroEquipo(
            @Param("infoInspeccion") String paramString1, @Param("usernameMiembroEquipo") String paramString2);
    
    /**
     * @param idDocumento id del documento
     * @return devuelve lista de inspecciones asociadas a un documento
     */
    @Query(value = "select ins.* from documentos_inspeccion di, inspecciones ins where di.id_inspeccion=ins.id and di.id_documento=?1", nativeQuery = true)
    public List<Inspeccion> cargaInspeccionesDocumento(Long idDocumento);
    
    /**
     * @param idGuia identificador de la guía
     * @return lista de inspecciones asociadas a una guía
     */
    @Query(value = "select ins.* from guia_inspeccion di, inspecciones ins where di.id_inspeccion=ins.id and di.id_guia=?1", nativeQuery = true)
    public List<Inspeccion> cargaInspeccionesGuia(Long idGuia);
    
    /**
     * @param idInspeccion identificador de la inspección
     * @return devuelve una lista con las inspecciones asociads de otra indicando su id
     */
    @Query(value = "select insAsociadas.* from inspecciones_asociadas i, inspecciones insAsociadas where i.id_inspeccion_asociada=insAsociadas.id and i.id_inspeccion= :idInspeccion ORDER BY insAsociadas.id,insAsociadas.anio", nativeQuery = true)
    public List<Inspeccion> cargaInspeccionesAsociadas(@Param("idInspeccion") Long idInspeccion);
    
    /**
     * @param tipo de inspección
     * @return valor booleano dependiendo de si existe una inspección del tipo pasado por parámetro
     */
    public boolean existsByTipoInspeccion(TipoInspeccion tipo);
    
    /**
     * Devuelve un listado de inspecciones que están en un estado recibido como parámetro.
     * 
     * @param estado Estado de inspección a buscar
     * @return Resultado de la búsqueda
     */
    public List<Inspeccion> findByEstadoInspeccion(EstadoInspeccionEnum estado);
    
    /**
     * Devuelve las inspecciones en las que ha participado un usuario.
     * 
     * @param usuario Usuario consultado
     * @return Listado de las inspecciones en las que ha participado.
     */
    @Query(value = "select a.* from inspecciones a, miembros b where a.id_equipo= b.id_equipo and b.usuario=?1", nativeQuery = true)
    public List<Inspeccion> findInspeccionesByUsuario(String usuario);
    
    /**
     * Devuelve un listado de inspecciones a partir de su nombre de unidad o año. Los resultados se filtran para
     * devolver sólo aquellos en los que el usuario está implicado.
     * 
     * @param paramString nombre de la unidad o número de inspección
     * @param userConsulta Usuario por el que se filtra
     * @return devuelve una lista con todas las inspecciones filtradas indicando el nombre de la unidad o el número de
     * inspección
     */
    @Query("SELECT i FROM Inspeccion i, Miembro m WHERE (upper(i.nombreUnidad) LIKE upper(:inspeccionInfo) OR (i.id||'/'||i.anio) LIKE :inspeccionInfo) AND i.equipo=m.equipo and m.usuario=:userConsulta ORDER BY i.nombreUnidad, i.id DESC")
    public List<Inspeccion> buscarPorNombreUnidadONumeroYUsuario(@Param("inspeccionInfo") String paramString,
            @Param("userConsulta") User userConsulta);
    
    /**
     * Método que devuelve si existen inspecciones sin finalizar para un determinado equipo.
     * 
     * @param equipo de la inspección
     * @return valor booleano dependiendo de si existe una inspección finalizada o no
     */
    public boolean existsByEquipoAndFechaFinalizacionIsNull(Equipo equipo);
    
}