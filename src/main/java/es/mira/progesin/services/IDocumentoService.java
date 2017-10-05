package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
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
     * @return Lista de documentos salvados
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
     * @throws ProgesinException Posible excepción
     */
    
    DefaultStreamedContent descargaDocumento(Documento entity) throws ProgesinException;
    
    /**
     * Recibe el id de un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * 
     * @param id Documento a descargar
     * @return DefaultStreamedContent Flujo de descarga
     * @throws ProgesinException Posible excepción
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
     * @throws ProgesinException Excepción lanzada
     */
    Documento cargaDocumentoSinGuardar(UploadedFile file, TipoDocumento tipo, Inspeccion inspeccion)
            throws ProgesinException;
    
    /**
     * Busca todos los documentos no dados de baja lógica.
     * 
     * 
     * @return Lista de documentos seleccionados
     * 
     */
    
    List<Documento> findByFechaBajaIsNull();
    
    /**
     * Busca todos los documentos eliminados (dados de baja lógica).
     * 
     * 
     * @return Lista de documentos seleccionados
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
    List<Documento> buscarDocumentoPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            DocumentoBusqueda busqueda);
    
    /**
     * Consulta el número de registros en base de datos que corresponden a los criterios de búsqueda.
     * 
     * @param busqueda Objeto que contiene los criterios de búsqueda
     * @return número de registros correspondientes a la búsqueda
     */
    int getCounCriteria(DocumentoBusqueda busqueda);
    
    /**
     * Devuelve el nombre del fichero contenido en el objeto Documento.
     * 
     * @param documento del cual quiere extraerse el nombre del fichero contenido
     * @return nombre del fichero
     */
    String obtieneNombreFichero(Documento documento);
    
    /**
     * Devuelve la lista de tipos de documentos.
     * 
     * @return lista de tipos de documentos
     */
    List<TipoDocumento> listaTiposDocumento();
    
    /**
     * Recupera un documento de la papelera.
     * 
     * @param documento Es el documento a recuperar de la papelera
     */
    void recuperarDocumento(Documento documento);
    
    /**
     * 
     * Devuelve una lista de las inspecciones asociadas al documento pasado como parámetro.
     * 
     * @param documento del que se desean recuperar las inspecciones
     * @return lista de inspecciones asociadas al documento
     */
    List<Inspeccion> listaInspecciones(Documento documento);
    
    /**
     * Elimina todos los documentos almacenados en la papelera.
     * 
     * @return Lista de documentos eliminados
     */
    List<Documento> vaciarPapelera();
    
    /**
     * Recupera un tipo de documento a partir de su nombre.
     * 
     * @param nombre nombre del tipo
     * @return tipo de documento
     */
    TipoDocumento buscaTipoDocumentoPorNombre(String nombre);
    
    /**
     * Devuelve un documento localizado por su id.
     * 
     * @param id Identificador del documento
     * @return Documento
     */
    Documento findOne(Long id);
    
    /**
     * Devuelve los documentos que corresponden a un tipo de documento.
     * 
     * @param tipoDocumento Nombre del tipo de documento
     * @return Listado de documentos
     */
    List<Documento> buscaNombreTipoDocumento(String tipoDocumento);
    
    /**
     * Devuelve los documentos de tipo plantilla asociados a un cuestionario enviado.
     * 
     * @param idCuestionarioEnviado Identificador del cuestionario.
     * @return Listado de plantillas.
     */
    List<Documento> findPlantillas(Long idCuestionarioEnviado);
    
    /**
     * Devuelve el número de cuestionarios enviados que tienen adjunta la plantilla pasada como parámetro.
     * 
     * @param documento Identificador de la plantilla
     * @return Número de cuestionarios en los que está adjunta la plantilla
     */
    Long plantillaPerteneceACuestionario(Documento documento);
    
    /**
     * Indica si el documento pasado por parámetro está adjunto a alguna inspección en la que el usuario está implicado.
     * 
     * @param usuario Usuario del que se desea conocer si alguna de sus inspecciones tiene el documento asignado.
     * @param documento Documento del que se desea consultar si está asignado a una inspección del usuario.
     * @return Respuesta a la consulta
     */
    boolean documentoEnInspeccionUsuario(User usuario, Documento documento);
    
}
