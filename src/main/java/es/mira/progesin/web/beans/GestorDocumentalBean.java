package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentoRepository;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**********************************************************
 * Bean para el gestor documental
 * 
 * @author EZENTIS
 * 
 ********************************************************/

@Setter
@Getter
@Controller("gestorDocumentalBean")
@Scope("session")
@Slf4j
public class GestorDocumentalBean {
    
    /**
     * Listado de los documentos que se mostrarán en la vista
     */
    public List<Documento> listadoDocumentos = new ArrayList<>();
    
    /**
     * Objeto de tipo Documento para el alta de nuevos documentos
     */
    public Documento documento;
    
    /**
     * Objeto que contiene los parámetros de búsqueda de documentos
     */
    public DocumentoBusquedaBean documentoBusqueda;
    
    /**
     * Listado de los tipos de documento disponibles
     */
    public List<TipoDocumento> listaTipos;
    
    private StreamedContent file;
    
    private String nombreDoc;
    
    @Autowired
    private IDocumentoService documentoService;
    
    @Autowired
    private IAlertaService alertaService;
    
    @Autowired
    private INotificacionService notificacionService;
    
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    @Autowired
    transient IInspeccionesService inspeccionesService;
    
    @Autowired
    private transient ITipoDocumentoRepository tipoDocumentoRepository;
    
    /**********************************************************
     * Inicializa el bean
     * 
     ********************************************************/
    
    @PostConstruct
    public void init() {
        documentoBusqueda = new DocumentoBusquedaBean();
        listaTipos = documentoService.listaTiposDocumento();
    }
    
    /**********************************************************
     * Carga la lista de documentos
     * 
     ********************************************************/
    
    public void recargaLista() {
        documentoBusqueda.setEliminado(false);
        buscaDocumento();
    }
    
    /**********************************************************
     * Carga la lista de documentos
     * 
     ********************************************************/
    
    public void recargaListaEliminados() {
        documentoBusqueda.setEliminado(true);
        buscaDocumento();
    }
    
    /**********************************************************
     * Descarga el fichero contenido en la base de datos
     * 
     * @param documento
     * 
     * 
     ********************************************************/
    
    public void descargarFichero(Documento documento) {
        try {
            file = documentoService.descargaDocumento(documento);
        } catch (Exception e) {
            log.error("Error en la descarga de documentos: ", e);
        }
    }
    
    /**********************************************************
     * Carga el fichero seleccionado en la base de datos
     * 
     * @param event event FileUploadEvent
     * @throws Exception
     * 
     ********************************************************/
    
    public void cargaFichero(FileUploadEvent event) throws Exception {
        try {
            TipoDocumento tipo = tipoDocumentoRepository.findByNombre("OTROS");
            UploadedFile uFile = event.getFile();
            nombreDoc = uFile.getFileName();
            if (documentoService.extensionCorrecta(uFile)) {
                documento = documentoService.cargaDocumento(uFile, tipo, null);
                
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Carga de ficheros",
                        "La extensión del fichero '" + event.getFile().getFileName()
                                + "' no corresponde a su tipo real");
                FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
                context.execute("PF('dialogMessage').show()");
                registroActividadService.altaRegActividad("La extensión del fichero no corresponde a su tipo real",
                        TipoRegistroEnum.ERROR.name(), "Gestor documental");
                
            }
            
            recargaLista();
        } catch (Exception ex) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), ex);
            throw ex;
        }
    }
    
    /**********************************************************
     * Elimina el fichero seleccionado de la base de datos
     * 
     * @param documento
     * 
     ********************************************************/
    
    public void eliminarDocumento(Documento documento) {
        documento.setFechaBaja(new Date());
        documentoService.save(documento);
        recargaLista();
    }
    
    /**
     * Limpia el objeto de búsqueda y el listado de documentos
     */
    public void limpiarBusqueda() {
        documentoBusqueda.resetValues();
        listadoDocumentos.clear();
    }
    
    /**
     * Ejecuta la búsqueda de documentos en la base de datos según los parámetros almacenados en el objeto de búsqueda
     */
    public void buscaDocumento() {
        listadoDocumentos = documentoService.buscarGuiaPorCriteria(documentoBusqueda);
    }
    
    /**
     * Inicializa el objeto documento para el alta
     */
    public void nuevoDocumento() {
        documento = new Documento();
        
    }
    
    /**
     * 
     * Método que implementa la función de autocompletado de una caja de texto con los valores de inspecciones
     * almacenados en la base de datos
     * 
     * @param infoInspeccion Cadena de búsqueda
     * @return Listado de coincidencias con la búsqueda
     * 
     */
    public List<Inspeccion> autocompletarInspeccion(String infoInspeccion) {
        return inspeccionesService.buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion);
    }
    
    /**
     * Crea un documento
     * 
     * @param nombreDocumento Nombre del documento
     * @param tipoDocumento Tipo del documento
     * @param descripcion Descripción breve del contenido
     * @param materiaIndexada Claves de búsqueda
     * @param inspeccion Inspección a la que está asignado el documento
     */
    public void creaDocumento(String nombreDocumento, TipoDocumento tipoDocumento, String descripcion,
            String materiaIndexada, Inspeccion inspeccion) {
        try {
            documento.setNombre(nombreDocumento);
            documento.setTipoDocumento(tipoDocumento);
            documento.setDescripcion(descripcion);
            documento.setMateriaIndexada(materiaIndexada);
            documento.setInspeccion(inspeccion);
            if (documentoService.save(documento) != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Gestor documental",
                        "Se ha guardado su documento con éxito");
                recargaLista();
                nombreDoc = "";
                RequestContext.getCurrentInstance().reset("formAlta:asociado");
            } else {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Gestor documental",
                        "Se ha producido un error al guardar su documento");
                documentoService.delete(documento);
            }
        } catch (Exception e) {
            documentoService.delete(documento);
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
        }
    }
    
    /**
     * Inicia el proceso de edición de un documento
     * 
     * @param doc El documento a editar
     * @return URL de la vista de edición
     */
    public String editarDocumento(Documento doc) {
        documento = doc;
        nombreDoc = documentoService.obtieneNombreFichero(documento);
        return "/gestorDocumental/editarDocumento?faces-redirect=true";
    }
    
    /**
     * Guarda los cambios realizados en la edición de documentos
     */
    public void modificaDocumento() {
        try {
            if (documentoService.save(documento) != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Gestor documental",
                        "Se ha modificado el documento");
                recargaLista();
            } else {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Gestor documental",
                        "Se ha producido un error al modificar el documento");
            }
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
        }
    }
    
    /**
     * Incia la asignación de un documento a una inspección. Recoge como parámetro el documento y lanza el dialog en el
     * que se rfecogerá la inspección
     * 
     * @param doc El documento que será asignado
     */
    public void asignar(Documento doc) {
        documento = doc;
        
        RequestContext.getCurrentInstance().execute("PF('inspeccionDialogo').show()");
    }
    
    /**
     * Recoge el valor de la inspección y se lo asigna al documento previamente seleccionado.
     * 
     * @param inspeccion Inspección a la que se asigna el documento
     */
    public void asignarInspeccion(Inspeccion inspeccion) {
        try {
            RequestContext.getCurrentInstance().execute("PF('inspeccionDialogo').hide()");
            documento.setInspeccion(inspeccion);
            documentoService.save(documento);
            recargaLista();
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Se ha producido un error al asignar una inspección al documento");
            registroActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
        }
        
    }
    
    /**
     * Recupera un documento de la papelera de reciclaje.
     * 
     * @param doc El documento a recuperar
     */
    public void recuperarDocumento(Documento doc) {
        documentoService.recuperarDocumento(doc);
        recargaListaEliminados();
    }
    
    /**
     * Elimina definitivamente un documento de la base de datos.
     * 
     * @param doc El documento a eliminar definitivamente
     */
    public void borrarDocumento(Documento doc) {
        if (doc.getInspeccion() == null) {
            documentoService.borrarDocumento(doc);
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
                    "No puede eliminarse este documento. Está asignado a la inspección "
                            .concat(doc.getInspeccion().getNumero()));
        }
        
        recargaListaEliminados();
    }
}
