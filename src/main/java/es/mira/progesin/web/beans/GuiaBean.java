package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelGuias;
import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IGuiaPersonalizadaService;
import es.mira.progesin.services.IGuiaService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.WordGenerator;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean de guías.
 * 
 * @author EZENTIS
 * 
 */

@Setter
@Getter
@Controller("guiaBean")
@Scope("session")
public class GuiaBean {
    /**
     * Guía.
     */
    private Guia guia;
    
    /**
     * Objeto que almacena los criterios de búsquedad.
     */
    private GuiaBusqueda busqueda;
    
    /**
     * Paso seleccionado.
     */
    private GuiaPasos pasoSeleccionado;
    
    /**
     * Lista de booleanos para controlar la visualización de columnas en la vista.
     */
    private List<Boolean> list;
    
    /**
     * Lista de pasos contenidos en la guía.
     */
    private List<GuiaPasos> listaPasos;
    
    /**
     * Lista de pasos que se grabarán en BDD.
     */
    private List<GuiaPasos> listaPasosGrabar;
    
    /**
     * Lista de pasos seleccionados durante la personalización.
     */
    private List<GuiaPasos> listaPasosSeleccionados;
    
    /**
     * Lista de inspecciones a las que se asgna la guía durante la personalización.
     */
    private List<Inspeccion> listaInspecciones;
    
    /**
     * Fichero que contendrá la versión descargable de la guía.
     */
    private StreamedContent file;
    
    /**
     * Discriminador para diferenciar si se está dando de alta una guía.
     */
    boolean alta;
    
    /**
     * LazyModel para la visualización paginada de guías.
     */
    
    private LazyModelGuias model;
    
    /**
     * Lista de los posibles tipos de inspección.
     */
    private List<TipoInspeccion> listaTiposInspeccion;
    
    /**
     * Generador de documentos word.
     */
    @Autowired
    private WordGenerator wordGenerator;
    
    /**
     * Servicio de guías.
     */
    @Autowired
    private IGuiaService guiaService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private IRegistroActividadService regActividadService;
    
    /**
     * Servicio de guiías personalizadas.
     */
    @Autowired
    private IGuiaPersonalizadaService guiaPersonalizadaService;
    
    /**
     * Servicio de inspecciones.
     */
    @Autowired
    private IInspeccionesService inspeccionesService;
    
    /**
     * Servicio de tipos de inspección.
     */
    @Autowired
    private ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Literal usado para mostrar mensaje de error.
     */
    private static final String GUIANOEXISTE = "Se ha producido un error en el acceso a la guía. La guía solicitada ya no existe";
    
    /**
     * Literal usado para mostrar mensaje de error.
     */
    private static final String MODELOSGUIA = "Modelos de guía";
    
    /**
     * Busca las guías según los filtros introducidos en el formulario de búsqueda.
     * 
     */
    public void buscarGuia() {
        model.setBusqueda(busqueda);
        model.load(0, 20, "fechaAlta", SortOrder.DESCENDING, null);
    }
    
    /**
     * Visualiza la guía pasada como parámetro redirigiendo a la vista "visualizaGuía".
     * 
     * @param guide Guia a visualizar
     * @return Ruta de la vista donde se visualiza la guía
     * 
     */
    
    public String visualizaGuia(Guia guide) {
        Guia guiaAux = guiaService.findOne(guia.getId());
        String redireccion = null;
        if (guiaAux != null) {
            this.guia = guiaAux;
            listaPasos = guiaService.listaPasos(guiaAux);
            redireccion = "/guias/visualizaGuia?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, MODELOSGUIA, GUIANOEXISTE);
        }
        return redireccion;
    }
    
    /**
     * Limpia los valores del objeto de búsqueda.
     * 
     */
    
    public void limpiarBusqueda() {
        busqueda = new GuiaBusqueda();
        model.setRowCount(0);
    }
    
    /**
     * Inicia el proceso de creación de una nueva guía redirigiendo a la vista "editarGuia".
     * 
     * @return Ruta de la vista
     * 
     */
    
    public String nuevaGuia() {
        alta = true;
        this.guia = new Guia();
        listaPasos = new ArrayList<>();
        listaPasosGrabar = new ArrayList<>();
        return "/guias/editarGuia?faces-redirect=true";
    }
    
    /**
     * 
     * Inicia el proceso de edición de una guía pasada como parámetro redirigiendo a la vista "editarGuia".
     * 
     * @param guide Guia a editar
     * @return Ruta de la vista
     * 
     */
    
    public String editaGuia(Guia guide) {
        Guia guiaAux = guiaService.findOne(guide.getId());
        String redireccion = null;
        if (guiaAux != null) {
            alta = false;
            this.guia = guide;
            listaPasosGrabar = guiaService.listaPasos(guia);
            listaPasos = guiaService.listaPasosNoNull(guia);
            redireccion = "/guias/editarGuia?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, MODELOSGUIA, GUIANOEXISTE);
        }
        return redireccion;
    }
    
    /**
     * 
     * Añade un nuevo paso a la guía.
     * 
     * @param paso Paso a añadir.
     * 
     */
    
    public void aniadePaso(String paso) {
        GuiaPasos nuevaPregunta = new GuiaPasos();
        nuevaPregunta.setPaso(paso);
        nuevaPregunta.setIdGuia(guia);
        listaPasos.add(nuevaPregunta);
        listaPasosGrabar.add(nuevaPregunta);
    }
    
    /**
     * 
     * Elimina un paso de la guía.
     * 
     * En el caso de que el paso haya sido empleado en alguna guía personalizada la eliminación será lógica y se
     * introducirá una fecha de baja. Si no ha sido usada en ninguna guía se eliminara físicamente de la base de datos
     * 
     * 
     */
    
    public void borraPaso() {
        if (pasoSeleccionado != null) {
            if (pasoSeleccionado.getId() != null) {
                // La pregunta existe en base de datos
                if (guiaService.existePaso(pasoSeleccionado)) {
                    // baja lógica
                    int index = listaPasosGrabar.indexOf(pasoSeleccionado);
                    pasoSeleccionado.setFechaBaja(new Date());
                    pasoSeleccionado.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
                    listaPasosGrabar.set(index, pasoSeleccionado);
                    listaPasos.remove(pasoSeleccionado);
                } else {// Baja física
                    listaPasosGrabar.remove(pasoSeleccionado);
                    listaPasos.remove(pasoSeleccionado);
                    
                }
            } else {
                listaPasos.removeIf(a -> a.getPaso().equals(pasoSeleccionado.getPaso()));
                listaPasosGrabar.removeIf(a -> a.getPaso().equals(pasoSeleccionado.getPaso()));
            }
            
        }
    }
    
    /**
     * Asigna a la variable "pasoSeleccionado" el valor seleccionado por el usuario en la vista.
     * 
     * @param event Paso seleccionado
     * 
     */
    
    public void onSelectPaso(SelectEvent event) {
        pasoSeleccionado = (GuiaPasos) event.getObject();
    }
    
    /**
     * Inicializa el bean.
     * 
     */
    
    @PostConstruct
    public void init() {
        busqueda = new GuiaBusqueda();
        setList(new ArrayList<>());
        for (int i = 0; i <= 4; i++) {
            list.add(Boolean.TRUE);
        }
        model = new LazyModelGuias(guiaService);
        setListaTiposInspeccion(tipoInspeccionService.buscaTodos());
    }
    
    /**
     * Crea un documento Word a partir de una guía pasada como parámetro.
     * 
     * @param guide Guia de la que se desea crear la versión en Word.
     * 
     */
    
    public void crearDocumentoWordGuia(Guia guide) {
        try {
            setFile(wordGenerator.crearDocumentoGuia(guide));
        } catch (ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error en la generación del documento Word");
            regActividadService.altaRegActividadError(TipoRegistroEnum.ERROR.name(), e);
        }
    }
    
    /**
     * 
     * Comprueba que el formulario está relleno. En caso afirmativo se grabará la guía en base de datos. En caso
     * contrario se muestra un mensaje de error
     * 
     */
    
    public void guardaGuia() {
        boolean correcto = true;
        String mensajeError = "";
        if (guia.getNombre() == null || guia.getNombre().isEmpty()) {
            mensajeError = mensajeError.concat("\nEl nombre no puede estar en blanco");
            correcto = false;
        }
        if (listaPasos == null || listaPasos.isEmpty()) {
            mensajeError = mensajeError.concat("\nSe debe incluir al menos un paso en la guía");
            correcto = false;
        }
        if (correcto) {
            ordenaPasos();
            guia.setPasos(listaPasosGrabar);
            grabaGuia();
        } else {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE, mensajeError,
                    null);
            
        }
    }
    
    /**
     * Almacena en base de datos la guía.
     * 
     */
    
    private void grabaGuia() {
        try {
            
            if (guiaService.guardaGuia(guia) != null) {
                if (alta) {
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                            "La guía se ha creado con éxito ");
                    regActividadService.altaRegActividad(
                            "La guía con nombre '".concat(guia.getNombre().concat("' ha sido creada")),
                            TipoRegistroEnum.ALTA.name(), SeccionesEnum.GUIAS.getDescripcion());
                } else {
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificacion",
                            "La guía ha sido modificada con éxito ");
                    regActividadService.altaRegActividad(
                            Constantes.LAGUIA.concat(guia.getNombre().concat("' ha sido modificada")),
                            TipoRegistroEnum.MODIFICACION.name(), SeccionesEnum.GUIAS.getDescripcion());
                }
                
            }
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al grabar la guía");
            regActividadService.altaRegActividadError(TipoRegistroEnum.ERROR.name(), e);
        }
    }
    
    /**
     * Ordena los pasos de una lista pasada como parámetro almacenando el orden en los objetos GuiaPaso contenidos en la
     * misma.
     * 
     */
    
    private void ordenaPasos() {
        
        List<GuiaPasos> listaPasosAux = new ArrayList<>(listaPasosGrabar);
        
        for (int i = 0; i < listaPasos.size(); i++) {
            GuiaPasos paso = listaPasos.get(i);
            paso.setOrden(i);
            
            for (GuiaPasos pasoGrabar : listaPasosGrabar) {
                if (paso.getId() != null && paso.equals(pasoGrabar)) {
                    pasoGrabar.setOrden(i);
                    listaPasosAux.set(i, pasoGrabar);
                }
                if (pasoGrabar.getPaso().equals(paso.getPaso()) && paso.getId() == null
                        && paso.getFechaBaja() == null) {
                    pasoGrabar.setOrden(i);
                    listaPasosAux.set(i, pasoGrabar);
                }
            }
            
        }
        
    }
    
    /**
     * Limpia el menú de búsqueda si se accede a la vista desde el menú lateral.
     * @return ruta siguiente
     * 
     */
    
    public String getFormularioBusqueda() {
        limpiarBusqueda();
        return "/guias/buscaGuias?faces-redirect=true";
    }
    
    /**
     * Inicia el proceso de creación de una guía personalizada y redirige a la vista de personalización.
     *
     * @param guide Modelo que se empleará para hacer una guía personalizada
     * @return Ruta de la vista de personalización.
     * 
     */
    
    public String creaPersonalizada(Guia guide) {
        Guia guiaAux = guiaService.findOne(guide.getId());
        String redireccion = null;
        if (guiaAux != null) {
            alta = false;
            this.guia = guiaAux;
            listaPasosSeleccionados = new ArrayList<>();
            listaInspecciones = new ArrayList<>();
            listaPasos = guiaService.listaPasosNoNull(guiaAux);
            redireccion = "/guias/personalizarGuia?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, MODELOSGUIA, GUIANOEXISTE);
        }
        return redireccion;
    }
    
    /**
     * Almacena en BDD la guía personalizada.
     *
     * @param nombre Nombre de la guía
     * 
     */
    
    public void guardarPersonalizada(String nombre) {
        boolean error = false;
        String mensajeError = "No se puede crear su guía personalizada.";
        try {
            if (nombre.isEmpty()) {
                error = true;
                mensajeError = mensajeError.concat("\nDebe introducirse un nombre para la guía.");
            }
            if (listaPasosSeleccionados.isEmpty()) {
                error = true;
                mensajeError = mensajeError.concat("\nAl menos debe seleccionarse un paso.");
            }
            
            if (!error) {
                GuiaPersonalizada personalizada = new GuiaPersonalizada();
                personalizada.setNombreGuiaPersonalizada(nombre);
                personalizada.setGuia(guia);
                personalizada.setPasosElegidos(listaPasosSeleccionados);
                personalizada.setInspeccion(listaInspecciones);
                if (guiaPersonalizadaService.save(personalizada) != null) {
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Guía",
                            "Se ha guardado su guía personalizada con éxito");
                }
                listaInspecciones = new ArrayList<>();
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, mensajeError, "", "message");
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al guardar la guía personalizada");
            regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
        }
    }
    
    /**
     * Devuelve una lista con las inspecciones cuyo nombre de unidad o número contienen alguno de los caracteres pasado
     * como parámetro. Se usa en los formularios de creación y modificación para el autocompletado.
     * 
     * @param infoInspeccion texto con parte del nombre de unidad o el número de la inspección que teclea el usuario en
     * los formularios de creación y modificación
     * @return Devuelve la lista de inspecciones que contienen algún caracter coincidente con el texto introducido
     */
    public List<Inspeccion> autocompletarInspeccion(String infoInspeccion) {
        return inspeccionesService.buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion);
    }
    
    /**
     * 
     * Permite la anulación de una guía. Una vez anulada no podrá ser usada aunque se mantendrá en la base de datos
     * 
     * @param guiaAnular La guía a anular
     * 
     */
    public void anular(Guia guiaAnular) {
        Guia guiaAux = guiaService.findOne(guiaAnular.getId());
        
        if (guiaAux != null) {
            try {
                guiaAux.setFechaAnulacion(new Date());
                guiaAux.setUsernameAnulacion(SecurityContextHolder.getContext().getAuthentication().getName());
                if (guiaService.guardaGuia(guiaAux) != null) {
                    regActividadService.altaRegActividad("Se ha anulado la guía '".concat(guiaAux.getNombre()),
                            TipoRegistroEnum.BAJA.name(), SeccionesEnum.GUIAS.getDescripcion());
                }
                
            } catch (DataAccessException e) {
                regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
            }
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, MODELOSGUIA, GUIANOEXISTE);
        }
    }
    
    /**
     * Permite la baja lógica de la guía en la aplicación, entendiendo como baja lógica el consignar la fecha de baja de
     * esta aunque no se elimina físicamente.
     * 
     * @param guiaBaja Guía que se desea dar de baja
     */
    public void bajaLogica(Guia guiaBaja) {
        try {
            guiaBaja.setFechaBaja(new Date());
            guiaBaja.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            if (guiaService.guardaGuia(guiaBaja) != null) {
                regActividadService
                        .altaRegActividad(
                                Constantes.LAGUIA
                                        .concat(guiaBaja.getNombre().concat("' ha sido eliminada por el usuario ")
                                                .concat(SecurityContextHolder.getContext().getAuthentication()
                                                        .getName())),
                                TipoRegistroEnum.BAJA.name(), SeccionesEnum.GUIAS.getDescripcion());
            }
            
        } catch (DataAccessException e) {
            regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
        }
    }
    
    /**
     * 
     * Cuando un usuario use la opción de eliminar una guía se procederá de forma diferente si la guía tiene guías
     * personalizadas que depende de ella o no. En el primer caso se hará una anulación, en el caso segundo se eliminará
     * realmente de la base de datos
     * 
     * @param guiaEliminar La guía a anular
     * 
     */
    public void eliminar(Guia guiaEliminar) {
        try {
            if (guiaPersonalizadaService.buscarPorModeloGuia(guiaEliminar)) {
                bajaLogica(guiaEliminar);
            } else {
                guiaService.eliminar(guiaEliminar);
                regActividadService
                        .altaRegActividad(
                                Constantes.LAGUIA
                                        .concat(guiaEliminar.getNombre().concat("' ha sido eliminada por el usuario ")
                                                .concat(SecurityContextHolder.getContext().getAuthentication()
                                                        .getName())),
                                TipoRegistroEnum.BAJA.name(), SeccionesEnum.GUIAS.getDescripcion());
            }
            
        } catch (DataAccessException e) {
            regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
        }
    }
    
    /**
     * 
     * Elimina la fecha de baja de la guía para volver a ponerla activa.
     * 
     * @param guiaActivar Guía que se desea activar
     */
    public void activa(Guia guiaActivar) {
        Guia guiaAux = guiaService.findOne(guiaActivar.getId());
        if (guiaAux != null) {
            try {
                guiaActivar.setFechaAnulacion(null);
                guiaActivar.setUsernameAnulacion(null);
                if (guiaService.guardaGuia(guiaAux) != null) {
                    regActividadService.altaRegActividad(
                            Constantes.LAGUIA.concat(guiaAux.getNombre().concat("' ha sido activada")),
                            TipoRegistroEnum.BAJA.name(), SeccionesEnum.GUIAS.getDescripcion());
                }
                
            } catch (DataAccessException e) {
                regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
            }
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, MODELOSGUIA, GUIANOEXISTE);
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
            listaInspecciones.add(inspeccion);
        }
    }
    
    /**
     * Elimina una inspección de la lista del documento.
     * 
     * @param inspeccion Inspección a desasociar
     */
    public void desAsociarInspeccion(Inspeccion inspeccion) {
        listaInspecciones.remove(inspeccion);
    }
    
}
