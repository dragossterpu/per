package es.mira.progesin.persistence.repositories.gd;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.gd.TipoDocumento;

/**
 * 
 * Repositorio con las operaciones de BDD para la entidad TipoDocumento
 * 
 * @author Ezentis
 *
 */
public interface ITipoDocumentoRepository extends CrudRepository<TipoDocumento, Long> {
    
    /**
     * Devuelve desde la BDD un objeto TipoDocumento identificado por su nombre
     * 
     * @param nombre Nombre del tipo de documento
     * @return El objeto almacenado en base de datos
     */
    TipoDocumento findByNombre(String nombre);
    
}
