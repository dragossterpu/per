package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.web.beans.DocumentoBusqueda;

/**
 * Interfaz del servicio de Documentos.
 * 
 * @author EZENTIS
 *
 */
public interface IDocumentoService {
    
    /**
     * Elimina una serie de documentos de la base de datos. El documento a eliminar se pasa como parámetro.
     * 
     * @param entity Documento a eliminar
     * 
     */
    
    void delete(Documento entity);
    
    /**
     * Guarda una serie de documentos en base de datos. Como parámetro recibe los documentos a guardar y devuelve los
     * documentos guardados.
     * 
     * @param entities Documentos a salvar
     * @return Iterable<Documento> Documentos salvado
     * 
     */
    
    Iterable<Documento> save(Iterable<Documento> entities);
    
    /**
     * Guarda un documento en base de datos. Como parámetro recibe el documento a guardar y devuelve el documento
     * guardado.
     * 
     * @param entity Documento Documento a guardar
     * @return Documento Documento guardado
     */
    
    Documento save(Documento entity);
    
    /**
     * Recibe un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * @param entity Documento a descargar
     * @return DefaultStreamed Content Flujo de descarga
     * @throws ProgesinException Excepción lanzada
     */
    
    DefaultStreamedContent descargaDocumento(Documento entity) throws ProgesinException;
    
    /**
     * Recibe el id de un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * 
     * @param id Documento a descargar
     * @return DefaultStreamedContent Flujo de descarga
     * @throws ProgesinException Excepción lanzada
     */
    
    DefaultStreamedContent descargaDocumento(Long id) throws ProgesinException;
    
    /**
     * Recibe un archivo UploadedFile del que recupera los datos para generar un Documento que se almacenará en base de
     * datos. Devuelve el documento almacenado.
     * 
     * 
     * @param file fichero a cargar en BDD
     * @param tipo tipo de documentp
     * @param inspeccion inspección asociada al documento
     * @return Documento documento cargado en base de datos
     * @throws ProgesinException Excepción lanzada
     * 
     */
    
    Documento cargaDocumento(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion) throws ProgesinException;
    
    /**
     * Recibe un archivo UploadedFile y los datos necesarios para general un Documento pero no lo almacena en base de
     * datos. Sólo deja el objeto preparado para guardarlo.
     * 
     * @param file fichero a cargar en BDD
     * @param tipo tipo de documentp
     * @param inspeccion inspección asociada al documento
     * @return documento cargado en base de datos
     * @return Documento documento cargado en base de datos
     * @throws ProgesinException Excepción lanzada
     */
    public Documento cargaDocumentoSinGuardar(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws ProgesinException;
    
    /**
     * Busca todos los documentos no dados de baja lógica.
     * 
     * 
     * @return Iterable<Documento> Documentos seleccionados
     * 
     */
    
    List<Documento> findByFechaBajaIsNull();
    
    /**
     * Busca todos los documentos eliminados (dados de baja lógica).
     * 
     * 
     * @return Iterable<Documento> Documentos seleccionados
     * 
     */
    
    List<Documento> findByFechaBajaIsNotNull();
    
    /**
     * Consulta en base de datos en base a los parámetros recibidos. La consulta se hace paginada.
     * 
     * @param first Primer elemento a devolver de la búsqueda
     * @param pageSize Número máximo de registros a mostrar
     * @param sortField Campo por el cual se ordena la búsqueda
     * @param sortOrder Sentido de la ordenación
     * @param busqueda Objeto que contiene los criterios de búsqueda
     * @return Lista de los documentos que corresponden a los criterios recibidos
     * 
     */
    public List<Documento> buscarDocumentoPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            DocumentoBusqueda busqueda);
    
    /**
     * Consulta el número de registros en base de datos que corresponden a los criterios de búsqueda.
     * 
     * @param busqueda Objeto que contiene los criterios de búsqueda
     * @return número de registros correspondientes a la búsqueda
     */
    public int getCounCriteria(DocumentoBusqueda busqueda);
    
    /**
     * Devuelve el nombre del fichero contenido en el objeto Documento.
     * 
     * @param documento del cual quiere extraerse el nombre del fichero contenido
     * @return nombre del fichero
     */
    public String obtieneNombreFichero(Documento documento);
    
    /**
     * Devuelve la lista de tipos de documentos.
     * 
     * @return lista de tipos de documentos
     */
    public List<TipoDocumento> listaTiposDocumento();
    
    /**
     * Recupera un documento de la papelera.
     * 
     * @param documento Es el documento a recuperar de la papelera
     */
    public void recuperarDocumento(Documento documento);
    
    /**
     * 
     * Devuelve una lista de las inspecciones asociadas al documento pasado como parámetro.
     * 
     * @param documento del que se desean recuperar las inspecciones
     * @return lista de inspecciones asociadas al documento
     */
    public List<Inspeccion> listaInspecciones(Documento documento);
    
    /**
     * Devuelve el id del Cuestionarios que tiene asociado a una respuesta el documento pasado como parámetro.
     * 
     * @param documento del que se desea recuperar el cuestionario
     * @return id del cuestionario
     */
    public Long perteneceACuestionario(Documento documento);
    
    /**
     * Devuelve el id de la solicitud de documentación que tiene asociado el documento pasado como parámetro.
     * 
     * @param documento del que se desea recuperar la solicitud
     * @return id de la solicitud
     */
    public Long perteneceASolicitud(Documento documento);
    
    /**
     * Elimina todos los documentos almacenados en la papelera.
     */
    public void vaciarPapelera();
    
    /**
     * Recupera un tipo de documento a partir de su nombre.
     * 
     * @param nombre nombre del tipo
     * @return tipo de documento
     */
    public TipoDocumento buscaTipoDocumentoPorNombre(String nombre);
    
}
