package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.gd.Documento;

/**
 * Repositorio de operaciones de base de datos para la entidad Documento.
 * 
 * @author EZENTIS
 *
 */
public interface IDocumentoRepository extends CrudRepository<Documento, Long> {
    
    /**
     * Recupera el listado de documentos que no han sido dados de baja.
     * 
     * @return List<Documento> lista de documentos encontrados
     */
    List<Documento> findByFechaBajaIsNull();
    
    /**
     * Recupera el listado de documentos que han sido dados de baja.
     * 
     * @return List<Documento> lista de documentos encontrados
     */
    List<Documento> findByFechaBajaIsNotNull();
    
    /**
     * Recupera documento de la base de datos identificado por el id recibido como parámetro.
     * 
     * @param id Long Identificador del fichero
     * @return Documento encontrado
     */
    @EntityGraph(value = "Documento.fichero", type = EntityGraph.EntityGraphType.LOAD)
    Documento findById(Long id);
    
    /**
     * Elimina todos los registros cuya fecha de baja no sea null.
     */
    @Transactional(readOnly = false)
    void deleteByFechaBajaIsNotNull();
    
    /**
     * Devuelve el id del cuestionario que tenga adjunto a una respuesta el documento recibido como parámetro.
     * 
     * @param idDocumento Id del documento a buscar
     * @return id del cuestionario si existe
     */
    @Query(value = "select id_cuestionario_enviado from respuestas_cuest_docs where id_documento=?1", nativeQuery = true)
    Long perteneceACuestionario(Long idDocumento);
    
    /**
     * Devuelve el id de la solicitud que tenga adjunto el documento recibido como parámetro.
     * 
     * @param idDocumento Id del documento a buscar
     * @return id de la solicitud si existe
     */
    @Query(value = "select id_solicitud_previa from solicitud_previa_docs where id_documento=?1", nativeQuery = true)
    Long perteneceASolicitud(Long idDocumento);
}
