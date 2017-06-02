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
     * Devuelve una lista con los id de los cuestionarios que tengan adjunto el documento recibido como parámetro.
     * 
     * @param idDocumento Id del documento a buscar
     * @return Lista de los id de los cuestionarios que tienen adjunto este documento
     */
    @Query(value = "select id_cuestionario_enviado from respuestas_cuest_docs where id_documento=?1", nativeQuery = true)
    List<Long> buscaRespuestaDocumento(Long idDocumento);
    
    /**
     * Elimina todos los registros cuya fecha de baja no sea null.
     */
    @Transactional(readOnly = false)
    void deleteByFechaBajaIsNotNull();
    
    /**
     * Devuelve una lista con los id de las solicitudes que tengan adjunto el documento recibido como parámetro.
     * 
     * @param idDocumento Id del documento a buscar
     * @return Lista de los id de las solicitudes que tienen adjunto este documento
     */
    @Query(value = "select id_cuestionario_enviado from solicitud_previa_docs where id_documento=?1", nativeQuery = true)
    List<Long> buscaSolicitudDocumento(Long idDocumento);
}
