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
     * Devuelve el número de cuestionarios enviados que tienen adjunta la plantilla pasada como parámetro.
     * 
     * @param idDocumento Identificador de la plantilla
     * @return Número de cuestionarios en los que está adjunta la plantilla
     */
    @Query(value = "select count(id_cuest_env) from cuest_env_plantilla where id_plantilla=?1", nativeQuery = true)
    Long plantillaPerteneceACuestionario(Long idDocumento);
    
    /**
     * Devuelve los documentos que corresponden a un tipo de documento.
     * 
     * @param tipo Nombre del tipo de documento
     * @return Listado de documentos
     */
    @Query("select a from Documento a, TipoDocumento b where a.tipoDocumento=b.id and b.nombre=?1")
    List<Documento> buscaNombreTipoDocumento(String tipo);
    
    /**
     * Devuelve los documentos de tipo plantilla asociados a un cuestionario enviado.
     * 
     * @param idCuestionarioEnviado Identificador del cuestionario.
     * @return Listado de plantillas.
     */
    @Query(value = "select * from documentos a, cuest_env_plantilla b where a.id=b.id_plantilla and b.id_cuest_env=?1", nativeQuery = true)
    List<Documento> findPlantillas(Long idCuestionarioEnviado);
}
