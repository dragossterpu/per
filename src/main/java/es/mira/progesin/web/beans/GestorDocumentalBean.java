package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;
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
    public DocumentoBusqueda documentoBusqueda;
    
    private StreamedContent file;
    
    private String nombreDoc;
    
    private String vieneDe;
    
    /**
     * Lista donde se almacenan las inspecciones que se van a asociar a un documento
     */
    public List<Inspeccion> listaInspecciones = new ArrayList<>();
    
    /**
     * Mapa que relaciona los documentos y las inspecciones asociadas
     */
    public Map<Long, String> mapaInspecciones = new HashMap<>();
    
    public Map<Long, Boolean> mapaEdicion = new HashMap<>();
    
    private List<Boolean> list;
    
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
    
    // Para el paginador
    
    private static final int MAX_RESULTS_PAGE = 20;
    
    private static final int FIRST_PAGE = 1;
    
    private long numeroRegistros;
    
    private int primerRegistro;
    
    private long actualPage;
    
    private long numPages;
    
    /**
     * Copia del objeto de búsqueda
     */
    public DocumentoBusqueda documentoBusquedaCopia;
    
    // paginador
    
    /**
     * Inicializa el objeto
     */
    @PostConstruct
    public void init() {
        documentoBusqueda = new DocumentoBusqueda();
        list = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            list.add(Boolean.TRUE);
        }
    }
    
    /**
     * Resetea el objeto de búsqueda, limpia la lista de resultados y establece el booleano de eliminado a false para
     * indicar que sólo se van a buscr documentos no eliminados
     */
    public void resetBusqueda() {
        if ("menu".equalsIgnoreCase(this.vieneDe)) {
            documentoBusqueda.resetValues();
            listadoDocumentos.clear();
            this.vieneDe = null;
        }
        listaInspecciones = new ArrayList<>();
        nombreDoc = "";
        documentoBusqueda.setEliminado(false);
    }
    
    /**
     * Resetea el objeto de búsqueda, limpia la lista de resultados y establece el booleano de eliminado a false para
     * indicar que sólo se van a buscar documentos eliminados
     */
    public void resetBusquedaEliminados() {
        if ("menu".equalsIgnoreCase(this.vieneDe)) {
            documentoBusqueda.resetValues();
            listadoDocumentos.clear();
            this.vieneDe = null;
        }
        listaInspecciones = new ArrayList<>();
        nombreDoc = "";
        documentoBusqueda.setEliminado(true);
    }
    
    /**
     * Muestra/oculta las columnas de la tabla de resultados de la búsqueda
     * 
     * @param e La columna a mostrar/ocultar
     */
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * Recarga la lista de resultados no eliminados
     */
    public void recargaLista() {
        documentoBusqueda.setEliminado(false);
        buscaDocumento();
        
    }
    
    /**
     * Recarga la lista de resultados eliminados
     */
    public void recargaListaEliminados() {
        documentoBusqueda.setEliminado(true);
        buscaDocumento();
    }
    
    /**
     * Inicia la descarga del documento que se recibe como parámetro
     * 
     * @param documento
     */
    public void descargarFichero(Documento documento) {
        try {
            file = documentoService.descargaDocumento(documento);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
        }
    }
    
    /**
     * Carga un documento que se recibe a través de un evento FileUploadEvent. Esta carga se realiza sobre el objeto
     * documento y no se guarda en base de datos. Se hace una comprobación para verificar si el tipo de documento se
     * corresponde a la realidad.
     * 
     * @param event Evento que se lanza en la carga del documento y que contiene el mismo
     * @throws Exception Se genera una excepción
     */
    public void cargaFichero(FileUploadEvent event) throws Exception {
        try {
            TipoDocumento tipo = tipoDocumentoRepository.findByNombre("OTROS");
            UploadedFile uFile = event.getFile();
            nombreDoc = uFile.getFileName();
            if (documentoService.extensionCorrecta(uFile)) {
                documento = documentoService.cargaDocumentoSinGuardar(uFile, tipo, null);
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Carga de ficheros",
                        "La extensión del fichero '" + event.getFile().getFileName()
                                + "' no corresponde a su tipo real");
                FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
                context.execute("PF('dialogMessage').show()");
                registroActividadService.altaRegActividad("La extensión del fichero no corresponde a su tipo real",
                        TipoRegistroEnum.ERROR.name(), SeccionesEnum.GESTOR.getDescripcion());
            }
            
        } catch (Exception ex) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), ex);
            throw ex;
        }
    }
    
    /**
     * Realiza la baja lógica del documento que podrá ser recuperado desde la papelera.
     * 
     * @param documento Documento al que se dará de baja lógica
     */
    public void eliminarDocumento(Documento documento) {
        documento.setFechaBaja(new Date());
        documentoService.save(documento);
        recargaLista();
    }
    
    /**
     * Reseteo del objeto de búsqueda y limpieza de la lista de resultados
     */
    public void limpiarBusqueda() {
        documentoBusqueda.resetValues();
        listadoDocumentos.clear();
    }
    
    /**
     * Lanza la búsqueda de documentos en la base de datos que correspondan con los parámetros contenidos en el objeto
     * de búsqueda. SE realiza paginación desde el servidor.
     * 
     */
    public void buscaDocumento() {
        
        primerRegistro = 0;
        actualPage = FIRST_PAGE;
        numeroRegistros = getCountRegistros();
        numPages = getCountPages(numeroRegistros);
        documentoBusquedaCopia = new DocumentoBusqueda(documentoBusqueda);
        listadoDocumentos = documentoService.buscarGuiaPorCriteria(0, MAX_RESULTS_PAGE, documentoBusquedaCopia);
        cargaMapaInspecciones();
        listaInspecciones = new ArrayList<>();
        nombreDoc = "";
    }
    
    /**
     * Devuelve el número de registros de la búsqueda.
     * 
     * @return Número de registros
     */
    public long getCountRegistros() {
        return documentoService.getCounCriteria(documentoBusqueda);
    }
    
    /**
     * A partir del número de registros y del tamaño de página devuelve el número de las mismas.
     * 
     * @param countRegistros Número de registros totales
     * @return Número de páginas
     */
    public long getCountPages(long countRegistros) {
        
        if (countRegistros % MAX_RESULTS_PAGE == 0)
            return countRegistros / MAX_RESULTS_PAGE;
        else
            return countRegistros / MAX_RESULTS_PAGE + 1;
    }
    
    /**
     * Avanza en uno la paginación de la tabla de resultados
     */
    public void next() {
        
        if (actualPage < numPages) {
            
            primerRegistro += MAX_RESULTS_PAGE;
            actualPage++;
            
            listadoDocumentos = documentoService.buscarGuiaPorCriteria(primerRegistro, MAX_RESULTS_PAGE,
                    documentoBusquedaCopia);
            cargaMapaInspecciones();
        }
        
    }
    
    /**
     * Retrocede en uno la paginación de la tabla de resultados
     * 
     */
    public void previous() {
        
        if (actualPage > FIRST_PAGE) {
            
            primerRegistro -= MAX_RESULTS_PAGE;
            actualPage--;
            
            listadoDocumentos = documentoService.buscarGuiaPorCriteria(primerRegistro, MAX_RESULTS_PAGE,
                    documentoBusquedaCopia);
            cargaMapaInspecciones();
        }
    }
    
    /**
     * Inicia la creación de un nuevo documento
     */
    public void nuevoDocumento() {
        documento = new Documento();
        listaInspecciones = new ArrayList<>();
        nombreDoc = "";
        
    }
    
    /**
     * Función para la implementación del autocompletado del input de inspecciones
     * 
     * @param infoInspeccion Cadena de búsqueda
     * @return Resultados coincidentes con la cadena de búsqueda
     */
    public List<Inspeccion> autocompletarInspeccion(String infoInspeccion) {
        return inspeccionesService.buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion);
    }
    
    /**
     * Graba un nuevo documento en la base de datos
     * 
     * @param nombreDocumento Nombre del documento
     * @param tipoDocumento Tipo al que pertenece el documento
     * @param descripcion Breve descripción del documento
     * @param materiaIndexada Palabras clave por las que se podrá buscar el documento
     */
    public void creaDocumento(String nombreDocumento, TipoDocumento tipoDocumento, String descripcion,
            String materiaIndexada) {
        if (!nombreDoc.isEmpty() && !nombreDocumento.isEmpty() && tipoDocumento != null) {
            try {
                documento.setNombre(nombreDocumento);
                documento.setTipoDocumento(tipoDocumento);
                documento.setDescripcion(descripcion);
                documento.setInspeccion(listaInspecciones);
                documento.setMateriaIndexada(materiaIndexada);
                documento.setFechaBaja(null);
                if (documentoService.save(documento) != null) {
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO,
                            SeccionesEnum.GESTOR.getDescripcion(), "Se ha guardado su documento con éxito");
                    recargaLista();
                    
                    RequestContext.getCurrentInstance().reset("formAlta:asociado");
                } else {
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR,
                            SeccionesEnum.GESTOR.getDescripcion(), "Se ha producido un error al guardar su documento");
                    documentoService.delete(documento);
                }
                nombreDoc = "";
                listaInspecciones = new ArrayList<>();
            } catch (Exception e) {
                documentoService.delete(documento);
                registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta de documentos",
                    "Complete los campos obligatorios antes de continuar.");
            FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
            
        }
    }
    
    /**
     * Recupera el documento a modificar e inicia el proceso de modificación.
     * 
     * @param doc Documento a modificar
     * @return URL de la vista de edición
     */
    public String editarDocumento(Documento doc) {
        documento = doc;
        nombreDoc = documentoService.obtieneNombreFichero(documento);
        List<Inspeccion> listado = documentoService.listaInspecciones(documento);
        documento.setInspeccion(listado);
        return "/gestorDocumental/editarDocumento?faces-redirect=true";
    }
    
    /**
     * Graba el documento modificado
     */
    public void modificaDocumento() {
        try {
            if (documentoService.save(documento) != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO,
                        SeccionesEnum.GESTOR.getDescripcion(), "Se ha modificado el documento");
                recargaLista();
            } else {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR,
                        SeccionesEnum.GESTOR.getDescripcion(), "Se ha producido un error al modificar el documento");
            }
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
        }
    }
    
    private boolean contieneInspeccion(List<Inspeccion> lista, Inspeccion inspeccion) {
        boolean respuesta = false;
        
        for (Inspeccion i : lista) {
            if (i.getNumero().equals(inspeccion.getNumero())) {
                respuesta = true;
            }
            
        }
        
        return respuesta;
    }
    
    /**
     * Añade una nueva inspección a la lista de inspecciones del documento. Se comprueba que la inspección existe y no
     * está ya añadida.
     * 
     * @param inspeccion La inspección a añadir
     */
    public void asignarNuevaInspeccion(Inspeccion inspeccion) {
        if (inspeccion != null && !contieneInspeccion(listaInspecciones, inspeccion)) {
            try {
                if (documento.getInspeccion() == null || documento.getInspeccion().isEmpty()) {
                    listaInspecciones = new ArrayList<>();
                } else {
                    listaInspecciones = new ArrayList<>(documento.getInspeccion());
                }
                if (!contieneInspeccion(listaInspecciones, inspeccion)) {
                    listaInspecciones.add(inspeccion);
                    documento.setInspeccion(listaInspecciones);
                }
                RequestContext.getCurrentInstance().reset("formAlta:tablaInspecciones:infoInspeccion");
            } catch (Exception e) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR ",
                        "Se ha producido un error al asignar una inspección al documento");
                registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
            }
        }
    }
    
    /**
     * Elimina una inspección de la lista del documento
     * 
     * @param inspeccion Inspección a desasociar
     */
    public void desAsociarInspeccion(Inspeccion inspeccion) {
        try {
            List<Inspeccion> inspecciones = new ArrayList<>(documento.getInspeccion());
            inspecciones.remove(inspeccion);
            documento.setInspeccion(inspecciones);
            listaInspecciones.remove(inspeccion);
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Se ha producido un error al desasociar una inspección del documento");
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
        }
    }
    
    /**
     * Recupera un documento desde la papelera.
     * 
     * @param doc Documento a recuperar
     */
    public void recuperarDocumento(Documento doc) {
        documentoService.recuperarDocumento(doc);
        recargaListaEliminados();
    }
    
    /**
     * Elimina un documento definitivamente
     * 
     * @param doc Documento a eliminar
     */
    public void borrarDocumento(Documento doc) {
        String error = "";
        if (!documentoService.buscaDocumentoEnCuestionarios(doc).isEmpty()) {
            error = "No es posible eliminar este documento ya que está asociado a un cuestionario\nElimine el cuestionario antes de proseguir con la eliminación de este documento";
        }
        if (!documentoService.buscaDocumentoEnSolicitudes(doc).isEmpty()) {
            error = "No es posible eliminar este documento ya que está asociado a una solicitud de documentación previa\nElimine la solicitud antes de proseguir con la eliminación de este documento";
            
        }
        if (error.isEmpty()) {
            try {
                
                if (doc.getInspeccion() == null) {
                    documentoService.borrarDocumento(doc);
                } else {
                    doc.setInspeccion(null);
                    documentoService.save(doc);
                    documentoService.borrarDocumento(doc);
                    registroActividadService.altaRegActividad("Se ha eliminado el documento ".concat(doc.getNombre()),
                            TipoRegistroEnum.BAJA.name(), SeccionesEnum.GESTOR.getDescripcion());
                }
                
                recargaListaEliminados();
            } catch (Exception e) {
                registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
            }
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR", error);
        }
    }
    
    private void cargaMapaInspecciones() {
        
        for (Documento doc : listadoDocumentos) {
            String cadenaInspecciones = "";
            for (Inspeccion inspe : documentoService.listaInspecciones(doc)) {
                cadenaInspecciones = cadenaInspecciones.concat(inspe.getNumero().concat("\n"));
            }
            mapaInspecciones.put(doc.getId(), cadenaInspecciones);
            
            mapaEdicion.put(doc.getId(), documentoService.buscaDocumentoEnCuestionarios(doc).isEmpty()
                    && documentoService.buscaDocumentoEnSolicitudes(doc).isEmpty());
        }
    }
    
}
