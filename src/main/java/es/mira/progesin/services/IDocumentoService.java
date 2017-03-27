package es.mira.progesin.services;

/**
 *
 * Intefaz para el servicio de documentos
 * 
 * @author Ezentis
 * 
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.web.beans.DocumentoBusquedaBean;

/**
 * Interfaz del servicio de Documentos
 * 
 * @author Ezentis
 *
 */
public interface IDocumentoService {
    void delete(Long id);
    
    void delete(Iterable<Documento> entities);
    
    void delete(Documento entity);
    
    void deleteAll();
    
    boolean exists(Long id);
    
    Iterable<Documento> findAll();
    
    Iterable<Documento> findAll(Iterable<Long> ids);
    
    Documento findOne(Long id);
    
    Iterable<Documento> save(Iterable<Documento> entities);
    
    Documento save(Documento entity);
    
    DefaultStreamedContent descargaDocumento(Documento entity) throws Exception;
    
    DefaultStreamedContent descargaDocumento(Long id) throws Exception;
    
    Documento cargaDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException;
    
    Documento crearDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException;
    
    List<Documento> findByFechaBajaIsNull();
    
    List<Documento> findByFechaBajaIsNotNull();
    
    boolean extensionCorrecta(UploadedFile file);
    
    List<Documento> buscarGuiaPorCriteria(DocumentoBusquedaBean busqueda);
    
    public String obtieneNombreFichero(Documento documento);
    
    public List<TipoDocumento> listaTiposDocumento();
    
    public void recuperarDocumento(Documento documento);
    
    public void borrarDocumento(Documento documento);
    
}
