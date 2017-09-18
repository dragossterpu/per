package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelUsuarios;
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Empleo;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IEmpleoRepository;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador de las operaciones relacionadas con la gestión de usuarios. Alta de usuario, modificación de usuario,
 * eliminación de usuario, búsqueda de usuario, búsqueda de empleo y restaurar clave.
 * 
 * @author EZENTIS
 */

@Setter
@Getter
@Controller("userBean")
@Scope("session")
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Usuario.
     */
    private User user;
    
    /**
     * Lista de empleos.
     */
    private List<Empleo> listaEmpleos;
    
    /**
     * Empleo seleccionado en la vista.
     */
    private Empleo empleoSeleccionado;
    
    /**
     * Departamento seleccionado en la vista.
     */
    private Departamento departamentoSeleccionado;
    
    /**
     * Cuerpo de estado seleccionado en la vista.
     */
    private CuerpoEstado cuerpoEstadoSeleccionado;
    
    /**
     * Puesto de trabajo seleccionado en la vista.
     */
    private PuestoTrabajo puestoTrabajoSeleccionado;
    
    /**
     * Objeto de búsqueda de usuario.
     */
    private UserBusqueda userBusqueda;
    
    /**
     * Lista de booleanos para el control de la visualización de columnas en la vista.
     */
    private List<Boolean> list;
    
    /**
     * Número máximo de columnas visibles en la vista.
     */
    private int numeroColumnasListadoUsarios = 9;
    
    /**
     * Array que contiene los niveles seleccionables.
     */
    private int[] nivelesSelect = IntStream.rangeClosed(12, 30).toArray();
    
    /**
     * LazyModel para la paginación desde servidor de los datos de la búsqueda de usuarios.
     */
    private LazyModelUsuarios model;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    private transient INotificacionService notificacionService;
    
    /**
     * Encriptador de palabras clave.
     */
    @Autowired
    private transient PasswordEncoder passwordEncoder;
    
    /**
     * Envío de correos electrónicos.
     */
    @Autowired
    private transient ICorreoElectronico correo;
    
    /**
     * Repostitorio de empleos.
     */
    @Autowired
    private transient IEmpleoRepository empleoRepository;
    
    /**
     * Dias inactividad de un usuario.
     */
    private long diasInactividad;
    
    /**
     * Muestra el perfil del usuario.
     * 
     * @return URL de la página donde se visualiza el perfil
     */
    public String getUserPerfil() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        user = userService.findOne(username);
        return "/principal/miPerfil?faces-redirect=true";
    }
    
    /**
     * Método que nos lleva al formulario de alta de nuevos usuarios, inicializando todo lo necesario para mostrar
     * correctamente la página (cuerpos de estado, puestos de trabajo, usuario nuevo). Se llama desde la página de
     * búsqueda de usuarios.
     * 
     * @return url de la página de alta de usuarios
     */
    public String nuevoUsuario() {
        user = new User();
        user.setFechaAlta(new Date());
        user.setEstado(EstadoEnum.ACTIVO);
        // para que en el select cargue por defecto la opción "Seleccine uno..."
        puestoTrabajoSeleccionado = null;
        cuerpoEstadoSeleccionado = null;
        empleoSeleccionado = null;
        departamentoSeleccionado = null;
        return "/users/altaUsuario?faces-redirect=true";
    }
    
    /**
     * Método que recoge los valores introducidos en el formulario y da de alta al usuario en la BBDD.
     * 
     */
    public void altaUsuario() {
        if (userService.exists(user.getUsername())) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Ya existe un usuario con ese nombre de usuario. Pruebe con otro.", "", "username");
        } else {
            
            try {
                user.setCuerpoEstado(getCuerpoEstadoSeleccionado());
                user.setPuestoTrabajo(getPuestoTrabajoSeleccionado());
                user.setEmpleo(getEmpleoSeleccionado());
                user.setDepartamento(getDepartamentoSeleccionado());
                String password = Utilities.getPassword();
                user.setPassword(passwordEncoder.encode(password));
                
                userService.save(user);
                Map<String, String> paramPlantilla = new HashMap<>();
                paramPlantilla.put("user", user.getUsername());
                paramPlantilla.put("password", password);
                correo.envioCorreo(user.getCorreo(),
                        "Alta aplicación software, Programa de Gestión de Inspecciones “PROGESIN”.",
                        Constantes.TEMPLATEALTAPLICACION, paramPlantilla);
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "El usuario ha sido creado con éxito");
                String descripcion = "Alta nuevo usuario " + user.getUsername();
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                        SeccionesEnum.USUARIOS.getDescripcion());
            } catch (DataAccessException e1) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Alta",
                        "Se ha producido un error al dar de alta el usuario. Inténtelo de nuevo más tarde");
                regActividadService.altaRegActividadError(SeccionesEnum.USUARIOS.getDescripcion(), e1);
            } catch (CorreoException e2) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                        Constantes.FALLOCORREO);
                regActividadService.altaRegActividadError(SeccionesEnum.USUARIOS.getDescripcion(), e2);
            }
            
        }
        
    }
    
    /**
     * Devuelve al formulario de búsqueda de usuarios a su estado inicial y borra los resultados de búsquedas anteriores
     * si se navega desde el menú u otra sección.
     *
     * @return página del buscador de usuarios
     */
    public String getFormularioBusquedaUsuarios() {
        limpiarBusqueda();
        return "/users/usuarios.xhtml?faces-redirect=true";
    }
    
    /**
     * Borra los resultados de búsquedas anteriores.
     * 
     */
    public void limpiarBusqueda() {
        userBusqueda = new UserBusqueda();
        model.setRowCount(0);
    }
    
    /**
     * Busca los usuarios según los filtros introducidos en el formulariod de búsqueda.
     */
    public void buscarUsuario() {
        model.setBusqueda(userBusqueda);
        model.load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
        auditoriaBusqueda(userBusqueda);
    }
    
    /**
     * Realiza una eliminación lógica del usuario (le pone fecha de baja).
     * 
     * @param usuario El usuario seleccionado de la tabla del resultado de la búsqueda
     */
    public void eliminarUsuario(User usuario) {
        
        usuario.setFechaBaja(new Date());
        usuario.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            userService.save(usuario);
            String descripcion = "Se ha eliminado el usuario " + usuario.getUsername();
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                    SeccionesEnum.USUARIOS.getDescripcion());
        } catch (DataAccessException e) {
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.USUARIOS.getDescripcion(), e);
        }
        
    }
    
    /**
     * Pasa los datos del usuario que queremos modificar al formulario de modificación para que cambien los valores que
     * quieran.
     * 
     * @param usuario usuario recuperado del formulario de búsqueda de usuarios
     * @return URL de la página de modificar usuario
     */
    public String getFormModificarUsuario(User usuario) {
        
        User usu = userService.findOne(usuario.getUsername());
        String redireccion = null;
        
        if (usu != null) {
            this.user = usu;
            setDiasInactivo(usu.getFechaInactivo());
            auditoriaVisualizacion(usu);
            buscarEmpleo();
            redireccion = "/users/modificarUsuario?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Modificación",
                    "Se ha producido un error al acceder al usuario. El usuario no existe");
        }
        return redireccion;
    }
    
    /**
     * Modifica los datos del usuario en función de los valores recuperados del formulario.
     */
    public void modificarUsuario() {
        try {
            User original = userService.findOne(user.getUsername());
            if (original.getEstado() != user.getEstado()) {
                cambiarEstado(user);
            }
            User modificado = userService.save(user);
            // User modificado = userService.findOne(user.getUsername());
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                    "El usuario ha sido modificado con éxito");
            
            String descripcionCorreo = Utilities.camposModificados(original, modificado);
            
            if (!descripcionCorreo.isEmpty()) {
                StringBuilder descripcion = new StringBuilder("Modificación del usuario : ").append(user.getUsername());
                
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion.toString(), TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.USUARIOS.getDescripcion());
                
                descripcion.append("<br>").append(descripcionCorreo);
                
                descripcion.toString().replace("<br>", "\n").replace("<strong>", "").replace("</strong>", "");
                
                // Enviamos correo
                correo.envioCorreo(user.getCorreo(), "Usuario modificado",
                        "<p>Se han realizado cambios en su perfil de usuario en la herramienta PROGESIN</p>"
                                + descripcion);
            }
            
        } catch (DataAccessException | ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Modificación",
                    "Se ha producido un error al modificar el usuario. Inténtelo de nuevo más tarde");
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.USUARIOS.getDescripcion(), e);
        }
        
    }
    
    /**
     * Cambia el estado de un usuario.
     * 
     * @param usuario usuario a modificar
     */
    private void cambiarEstado(User usuario) {
        String descripcionEstado = "Modificación del estado del usuario :" + " " + usuario.getUsername();
        regActividadService.altaRegActividad(descripcionEstado, TipoRegistroEnum.MODIFICACION.name(),
                SeccionesEnum.USUARIOS.getDescripcion());
        if (EstadoEnum.INACTIVO.equals(usuario.getEstado())) {
            usuario.setFechaInactivo(new Date());
        } else {
            usuario.setFechaInactivo(null);
        }
    }
    
    /**
     * Genera una contraseña nueva y se la envía por correo al usuario.
     */
    public void restaurarClave() {
        try {
            String password = Utilities.getPassword();
            
            List<User> lista = userService.listaUsuariosProvisionalesCorreo(this.user.getCorreo());
            
            if (lista.isEmpty()) {
                this.user.setPassword(passwordEncoder.encode(password));
                userService.save(user);
            } else {
                String encodedPassword = passwordEncoder.encode(password);
                for (User usu : lista) {
                    usu.setPassword(encodedPassword);
                    userService.save(usu);
                }
            }
            
            Map<String, String> paramPlantilla = new HashMap<>();
            paramPlantilla.put("password", password);
            correo.envioCorreo(user.getCorreo(),
                    "Restauración contraseña aplicación software, Programa de Gestión de Inspecciones “PROGESIN”.",
                    Constantes.TEMPLATECORREORESTAURARPASSWORD, paramPlantilla);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Clave",
                    "Se ha enviado un correo al usuario con la nueva contraseña");
        } catch (DataAccessException | CorreoException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Clave",
                    "Se ha producido un error en la regeneración o envío de la contraseña");
            // Guardamos loe posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.USUARIOS.getDescripcion(), e);
        }
    }
    
    /**
     * Activa/desactiva la visibilidad de las columnas de la tabla de resultados.
     * 
     * @param e checkbox de la columna seleccionada
     */
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * Inicializa el bean.
     */
    @PostConstruct
    public void init() {
        userBusqueda = new UserBusqueda();
        
        // para que en el select cargue por defecto la opción "Seleccine uno..."
        this.puestoTrabajoSeleccionado = null;
        this.cuerpoEstadoSeleccionado = null;
        this.empleoSeleccionado = null;
        this.departamentoSeleccionado = null;
        list = new ArrayList<>();
        for (int i = 0; i <= numeroColumnasListadoUsarios; i++) {
            list.add(Boolean.TRUE);
        }
        
        model = new LazyModelUsuarios(userService);
    }
    
    /**
     * Busca el objeto Empleo en base de datos a partir del seleccionado en el combo de la vista.
     */
    public void buscarEmpleo() {
        CuerpoEstado cuerpo;
        if (this.cuerpoEstadoSeleccionado != null) {
            cuerpo = this.cuerpoEstadoSeleccionado;
        } else {
            cuerpo = this.user.getCuerpoEstado();
        }
        setListaEmpleos(empleoRepository.findByCuerpoOrderByDescripcionAsc(cuerpo));
    }
    
    /**
     * Realiza una auditoría del uso de la búsqueda de usuarios.
     * 
     * @param usuario Objeto de búsqueda de usuario.
     */
    private void auditoriaBusqueda(UserBusqueda usuario) {
        
        DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        String puesto = "";
        String estado = "";
        String fechaDesde = "";
        String fechaHasta = "";
        String rol = "";
        String cuerpo = "";
        
        if (usuario.getPuestoTrabajo() != null) {
            puesto = usuario.getPuestoTrabajo().getDescripcion();
        }
        if (usuario.getEstado() != null) {
            estado = usuario.getEstado().name();
        }
        if (usuario.getFechaDesde() != null) {
            fechaDesde = fecha.format(usuario.getFechaDesde());
        }
        if (usuario.getFechaHasta() != null) {
            fechaHasta = fecha.format(usuario.getFechaHasta());
        }
        if (usuario.getRole() != null) {
            rol = usuario.getRole().name();
        }
        if (usuario.getCuerpoEstado() != null) {
            cuerpo = usuario.getCuerpoEstado().getDescripcion();
        }
        
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("El usuario ");
        descripcion.append(SecurityContextHolder.getContext().getAuthentication().getName());
        descripcion.append(
                " ha realizado una consulta de usuarios.\nLa consulta realizada ha sido la siguiente: \n Nombre de usuario: ");
        descripcion.append(Utilities.nullToBlank(usuario.getUsername()));
        descripcion.append("\n Nombre: ");
        descripcion.append(Utilities.nullToBlank(usuario.getNombre()));
        descripcion.append("\n Primer apellido: ");
        descripcion.append(Utilities.nullToBlank(usuario.getApellido1()));
        descripcion.append("\n Segundo apellido: ");
        descripcion.append(Utilities.nullToBlank(usuario.getApellido2()));
        descripcion.append("\n Puesto de trabajo: ");
        descripcion.append(puesto);
        descripcion.append("\n Estado: ");
        descripcion.append(estado);
        descripcion.append("\n Fecha alta desde: ");
        descripcion.append(fechaDesde);
        descripcion.append("\n Fecha alta desde: ");
        descripcion.append(fechaHasta);
        descripcion.append("\n Rol: ");
        descripcion.append(rol);
        descripcion.append("\n Cuerpo del estado: ");
        descripcion.append(cuerpo);
        
        regActividadService.altaRegActividad(descripcion.toString(), TipoRegistroEnum.AUDITORIA.name(),
                SeccionesEnum.USUARIOS.getDescripcion());
        
    }
    
    /**
     * Realiza una auditoría de la visualización de usuarios.
     * 
     * @param usuario Usuario que realiza la visualización.
     */
    
    private void auditoriaVisualizacion(User usuario) {
        String descripcion = "El usuario " + SecurityContextHolder.getContext().getAuthentication().getName()
                + " ha visualizado un usuario." + "El usuario consultado es: " + usuario.getUsername();
        
        regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.AUDITORIA.name(),
                SeccionesEnum.USUARIOS.getDescripcion());
    }
    
    /**
     * Guarda en una varible el número de días de inactividad de un usuario pasado por parámetro.
     * 
     * @param fecha usuario a consultar
     */
    public void setDiasInactivo(Date fecha) {
        setDiasInactividad(Utilities.getDiasHastaHoy(fecha));
    }
    
}
