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

import javax.sql.rowset.serial.SerialException;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.web.beans.DocumentoBusqueda;

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
    
    DefaultStreamedContent descargaDocumento(Documento entity) throws SerialException;
    
    DefaultStreamedContent descargaDocumento(Long id) throws SerialException;
    
    Documento cargaDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException;
    
    public Documento cargaDocumentoSinGuardar(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException;
    
    Documento crearDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws SQLException, IOException;
    
    List<Documento> findByFechaBajaIsNull();
    
    List<Documento> findByFechaBajaIsNotNull();
    
    boolean extensionCorrecta(UploadedFile file);
    
    public List<Documento> buscarGuiaPorCriteria(int firstResult, int maxResults, DocumentoBusqueda busqueda);
    
    public long getCounCriteria(DocumentoBusqueda busqueda);
    
    public String obtieneNombreFichero(Documento documento);
    
    public List<TipoDocumento> listaTiposDocumento();
    
    public void recuperarDocumento(Documento documento);
    
    public void borrarDocumento(Documento documento);
    
    public List<Inspeccion> listaInspecciones(Documento documento);
    
    public List<Long> buscaDocumentoEnCuestionarios(Documento documento);
    
    public List<GestDocSolicitudDocumentacion> buscaDocumentoEnSolicitudes(Documento documento);
    
    public void vaciarPapelera();
    
}
