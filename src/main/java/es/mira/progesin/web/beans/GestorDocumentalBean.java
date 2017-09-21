package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelDocumentos;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
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
import es.mira.progesin.util.VerificadorExtensiones;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para el gestor documental.
 * 
 * @author EZENTIS
 * 
 */

@Setter
@Getter
@Controller("gestorDocumentalBean")
@Scope("session")

public class GestorDocumentalBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto de tipo Documento para el alta de nuevos documentos.
     */
    private Documento documento;
    
    /**
     * Objeto que contiene los parámetros de búsqueda de documentos.
     */
    private DocumentoBusqueda documentoBusqueda;
    
    /**
     * Objeto que contendrá el fichero a descargar.
     */
    private transient StreamedContent file;
    
    /**
     * Nombre del documento.
     */
    private String nombreDoc;
    
    /**
     * Lista donde se almacenan las inspecciones que se van a asociar a un documento.
     */
    private List<Inspeccion> listaInspecciones = new ArrayList<>();
    
    /**
     * Mapa que relaciona los documentos y las inspecciones asociadas.
     */
    private Map<Long, String> mapaInspecciones;
    
    /**
     * Mapa que relaciona los documentos y las inspecciones asociadas.
     */
    private Map<Long, Boolean> mapaEdicion;
    
    /**
     * Lista de valores booleanos para la visualización de las columnas de la vista.
     */
    private List<Boolean> list;
    
    /**
     * Verificador de extensiones.
     */
    @Autowired
    private transient VerificadorExtensiones verificadorExtensiones;
    
    /**
     * Servicio de documentos.
     */
    @Autowired
    private transient IDocumentoService documentoService;
    
    /**
     * Servicio de alertas.
     */
    @Autowired
    private transient IAlertaService alertaService;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    private transient INotificacionService notificacionService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService registroActividadService;
    
    /**
     * Servicio de inspecciones.
     */
    @Autowired
    private transient IInspeccionesService inspeccionesService;
    
    /**
     * Repositorio de tipos de documento.
     */
    @Autowired
    private transient ITipoDocumentoRepository tipoDocumentoRepository;
    
    /**
     * Lazy Model para la consulta paginada de documentos en base de datos.
     */
    private LazyModelDocumentos model;
    
    /**
     * Constante para evitar literales repetidos.
     */
    private static final String CARGAFICHEROS = "Carga de ficheros";
    
    /**
     * Inicializa el objeto.
     */
    @PostConstruct
    public void init() {
        documentoBusqueda = new DocumentoBusqueda();
        list = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            list.add(Boolean.TRUE);
        }
        model = new LazyModelDocumentos(documentoService);
        mapaInspecciones = new LinkedHashMap<>();
        mapaEdicion = new HashMap<>();
    }
    
    /**
     * Resetea el objeto de búsqueda, limpia la lista de resultados y establece el booleano de eliminado a false para
     * indicar que sólo se van a buscar documentos no eliminados.
     * @return ruta siguiente
     */
    public String resetBusqueda() {
        documentoBusqueda = new DocumentoBusqueda();
        model.setRowCount(0);
        listaInspecciones = new ArrayList<>();
        nombreDoc = "";
        documentoBusqueda.setEliminado(false);
        return "/gestorDocumental/buscarDocumento?faces-redirect=true";
    }
    
    /**
     * Resetea el objeto de búsqueda, limpia la lista de resultados y establece el booleano de eliminado a false para
     * indicar que sólo se van a buscar documentos eliminados.
     * @return ruta
     */
    public String resetBusquedaEliminados() {
        listaInspecciones = new ArrayList<>();
        nombreDoc = "";
        documentoBusqueda.setEliminado(true);
        buscaDocumento();
        return "/administracion/papelera/papelera?faces-redirect=true";
        
    }
    
    /**
     * Muestra/oculta las columnas de la tabla de resultados de la búsqueda.
     * 
     * @param e La columna a mostrar/ocultar
     */
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * Recarga la lista de resultados no eliminados.
     */
    public void recargaLista() {
        documentoBusqueda.setEliminado(false);
        buscaDocumento();
        
    }
    
    /**
     * Recarga la lista de resultados eliminados.
     */
    public void recargaListaEliminados() {
        documentoBusqueda.setEliminado(true);
        buscaDocumento();
    }
    
    /**
     * Inicia la descarga del documento que se recibe como parámetro.
     * 
     * @param document Documento a descargar
     */
    public void descargarFichero(Documento document) {
        
        Documento docAux = documentoService.findOne(document.getId());
        setFile(null);
        
        if (docAux != null) {
            try {
                setFile(documentoService.descargaDocumento(docAux));
            } catch (ProgesinException e) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                        e.getMessage());
                registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
            }
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, CARGAFICHEROS,
                    "Se ha producido un error en la descarga del fichero");
        }
    }
    
    /**
     * Carga un documento que se recibe a través de un evento FileUploadEvent. Esta carga se realiza sobre el objeto
     * documento y no se guarda en base de datos. Se hace una comprobación para verificar si el tipo de documento se
     * corresponde a la realidad.
     * 
     * @param event Evento que se lanza en la carga del documento y que contiene el mismo
     */
    public void cargaFichero(FileUploadEvent event) {
        try {
            TipoDocumento tipo = tipoDocumentoRepository.findByNombre("OTROS");
            UploadedFile uFile = event.getFile();
            
            if (verificadorExtensiones.extensionCorrecta(uFile)) {
                
                documento = documentoService.cargaDocumentoSinGuardar(uFile, tipo, null);
                nombreDoc = uFile.getFileName();
            } else {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, CARGAFICHEROS,
                        "La extensión del fichero '" + event.getFile().getFileName()
                                + "' no corresponde a su tipo real");
                registroActividadService.altaRegActividad("La extensión del fichero no corresponde a su tipo real",
                        TipoRegistroEnum.ERROR.name(), SeccionesEnum.GESTOR.getDescripcion());
            }
            
        } catch (ProgesinException ex) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), ex);
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, CARGAFICHEROS,
                    "Se ha producido un error en la cargad el fichero");
        }
    }
    
    /**
     * Realiza la baja lógica del documento que podrá ser recuperado desde la papelera.
     * 
     * @param document Documento al que se dará de baja lógica
     */
    public void eliminarDocumento(Documento document) {
        try {
            document.setFechaBaja(new Date());
            document.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            documentoService.save(document);
            buscaDocumento();
            registroActividadService.altaRegActividad(
                    "Se ha enviado a la papelera el documento ".concat(document.getNombre()),
                    TipoRegistroEnum.BAJA.name(), SeccionesEnum.GESTOR.getDescripcion());
        } catch (DataAccessException e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
        }
    }
    
    /**
     * Reseteo del objeto de búsqueda y limpieza de la lista de resultados.
     */
    public void limpiarBusqueda() {
        documentoBusqueda = new DocumentoBusqueda();
        model.setRowCount(0);
    }
    
    /**
     * Lanza la búsqueda de documentos en la base de datos que correspondan con los parámetros contenidos en el objeto
     * de búsqueda. SE realiza paginación desde el servidor.
     * 
     */
    public void buscaDocumento() {
        model.setBusqueda(documentoBusqueda);
        model.load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
        cargaMapaInspecciones();
        listaInspecciones = new ArrayList<>();
        nombreDoc = "";
    }
    
    /**
     * Inicia la creación de un nuevo documento.
     * @return ruta de la vista
     */
    public String nuevoDocumento() {
        documento = new Documento();
        listaInspecciones = new ArrayList<>();
        nombreDoc = "";
        
        return "/gestorDocumental/nuevoDocumento?faces-redirect=true";
    }
    
    /**
     * Función para la implementación del autocompletado del input de inspecciones.
     * 
     * @param infoInspeccion Cadena de búsqueda
     * @return Resultados coincidentes con la cadena de búsqueda
     */
    public List<Inspeccion> autocompletarInspeccion(String infoInspeccion) {
        
        User usuario = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Inspeccion> respuesta = new ArrayList<>();
        
        if (RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuario.getRole())) {
            respuesta = inspeccionesService.buscarPorNombreUnidadONumeroUsuario(infoInspeccion, usuario);
        } else {
            respuesta = inspeccionesService.buscarPorNombreUnidadONumero(infoInspeccion);
        }
        
        return respuesta;
    }
    
    /**
     * Graba un nuevo documento en la base de datos.
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
                documentoService.save(documento);
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO,
                        SeccionesEnum.GESTOR.getDescripcion(), "Se ha guardado su documento con éxito");
                recargaLista();
                registroActividadService.altaRegActividad("Se ha creado el documento ".concat(nombreDocumento),
                        TipoRegistroEnum.ALTA.name(), SeccionesEnum.GESTOR.getDescripcion());
                RequestContext.getCurrentInstance().reset("formAlta:asociado");
                nombreDoc = "";
                listaInspecciones = new ArrayList<>();
            } catch (DataAccessException e) {
                registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
            }
        } else {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Alta de documentos",
                    "Complete los campos obligatorios antes de continuar.", null);
            
        }
    }
    
    /**
     * Recupera el documento a modificar e inicia el proceso de modificación.
     * 
     * @param doc Documento a modificar
     * @return URL de la vista de edición
     */
    public String editarDocumento(Documento doc) {
        Documento docAux = documentoService.findOne(doc.getId());
        String redireccion = null;
        
        if (docAux != null) {
            documento = docAux;
            nombreDoc = documentoService.obtieneNombreFichero(documento);
            List<Inspeccion> listado = documentoService.listaInspecciones(documento);
            documento.setInspeccion(listado);
            redireccion = "/gestorDocumental/editarDocumento?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, CARGAFICHEROS,
                    "Se ha producido un error al acceder al documento");
        }
        return redireccion;
    }
    
    /**
     * Graba el documento modificado.
     */
    public void modificaDocumento() {
        try {
            documentoService.save(documento);
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO,
                    SeccionesEnum.GESTOR.getDescripcion(), "Se ha modificado el documento");
            recargaLista();
            registroActividadService.altaRegActividad("Se ha modificado el documento ".concat(documento.getNombre()),
                    TipoRegistroEnum.MODIFICACION.name(), SeccionesEnum.GESTOR.getDescripcion());
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR,
                    SeccionesEnum.GESTOR.getDescripcion(), "Se ha producido un error al modificar el documento");
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
        }
    }
    
    /**
     * Añade una nueva inspección a la lista de inspecciones del documento. Se comprueba que la inspección existe y no
     * está ya añadida.
     * 
     * @param inspeccion La inspección a añadir
     */
    public void asignarNuevaInspeccion(Inspeccion inspeccion) {
        if (inspeccion != null && !listaInspecciones.contains(inspeccion)) {
            if (documento.getInspeccion() == null) {
                listaInspecciones = new ArrayList<>();
            } else {
                listaInspecciones = new ArrayList<>(documento.getInspeccion());
            }
            if (!listaInspecciones.contains(inspeccion)) {
                listaInspecciones.add(inspeccion);
                documento.setInspeccion(listaInspecciones);
            }
        }
    }
    
    /**
     * Elimina una inspección de la lista del documento.
     * 
     * @param inspeccion Inspección a desasociar
     */
    public void desAsociarInspeccion(Inspeccion inspeccion) {
        List<Inspeccion> inspecciones = new ArrayList<>(documento.getInspeccion());
        inspecciones.remove(inspeccion);
        documento.setInspeccion(inspecciones);
        listaInspecciones.remove(inspeccion);
    }
    
    /**
     * Recupera un documento desde la papelera.
     * 
     * @param doc Documento a recuperar
     */
    public void recuperarDocumento(Documento doc) {
        try {
            documentoService.recuperarDocumento(doc);
            registroActividadService.altaRegActividad("Se ha recuperado el documento ".concat(doc.getNombre()),
                    TipoRegistroEnum.MODIFICACION.name(), SeccionesEnum.GESTOR.getDescripcion());
            buscaDocumento();
        } catch (DataAccessException e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
        }
        
    }
    
    /**
     * Elimina un documento definitivamente.
     * 
     * @param doc Documento a eliminar
     */
    public void borrarDocumento(Documento doc) {
        try {
            doc.setInspeccion(null);
            documentoService.delete(doc);
            registroActividadService.altaRegActividad("Se ha eliminado el documento ".concat(doc.getNombre()),
                    TipoRegistroEnum.BAJA.name(), SeccionesEnum.GESTOR.getDescripcion());
            buscaDocumento();
        } catch (DataAccessException e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
        }
    }
    
    /**
     * Carga un mapa con los documentos que se visualizan y las inspecciones que cada uno de ellos tiene asignadas.
     */
    private void cargaMapaInspecciones() {
        
        for (Documento doc : model.getDatasource()) {
            String cadenaInspecciones = "";
            for (Inspeccion inspe : documentoService.listaInspecciones(doc)) {
                cadenaInspecciones = cadenaInspecciones.concat(inspe.getNumero()).concat("\n");
            }
            mapaInspecciones.put(doc.getId(), cadenaInspecciones);
            mapaEdicion.put(doc.getId(), deshabilitaEdicion(doc));
        }
    }
    
    /**
     * Vacía la papelera de reciclaje.
     */
    public void vaciarPapelera() {
        try {
            List<Documento> documentosEliminados = documentoService.vaciarPapelera();
            StringBuffer nombreFicherosEliminados = new StringBuffer().append("\n\n");
            for (Documento docu : documentosEliminados) {
                nombreFicherosEliminados.append('-').append(docu.getNombre()).append("\n");
                
            }
            registroActividadService.altaRegActividad(
                    "Se ha vaciado la papelera eliminando los documentos siguientes :"
                            .concat(nombreFicherosEliminados.toString()),
                    TipoRegistroEnum.BAJA.name(), SeccionesEnum.GESTOR.getDescripcion());
            buscaDocumento();
        } catch (DataAccessException e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.GESTOR.getDescripcion(), e);
        }
    }
    
    /**
     * Verifica si el documento pasado como parámetro puede o no editarse. En función de ello indica que debe
     * deshabilitarse la posibilidad de edición.
     * 
     * @param doc Documento del que se desea verificar si es editable.
     * @return Indicación de la necesidad de deshabilitar la edición del documento.
     */
    private boolean deshabilitaEdicion(Documento doc) {
        
        User usuario = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        boolean perteneceACuestionario = doc.getTipoDocumento().getNombre().equalsIgnoreCase("cuestionario");
        boolean perteneceASolicitud = doc.getTipoDocumento().getNombre().equalsIgnoreCase("documentación salida ipss");
        boolean plantillaAdjuntaCuestionario = documentoService.plantillaPerteneceACuestionario(doc) > 0;
        boolean documentoEnInspeccionUsuario = documentoService.documentoEnInspeccionUsuario(usuario, doc);
        
        return perteneceACuestionario || perteneceASolicitud || !documentoEnInspeccionUsuario
                || plantillaAdjuntaCuestionario;
        
    }
    
}
