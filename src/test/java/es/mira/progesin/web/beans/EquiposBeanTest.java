package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelEquipos;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IMiembroService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;

/**
 * Test del bean Equipos.
 * 
 * @author EZENTIS
 */
@RunWith(PowerMockRunner.class)
// Evita conflictos con clases del sistema al enlazar los mocks por tipo
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ Utilities.class, FacesUtilities.class, SecurityContextHolder.class })
public class EquiposBeanTest {
    
    /**
     * Mock del security context.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Mock del objeto authentication.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Mock del servicio de equipos.
     */
    @Mock
    private IEquipoService equipoService;
    
    /**
     * Mock del servicio de miembros de equipo.
     */
    @Mock
    private IMiembroService miembroService;
    
    /**
     * Mock del servicio de usuarios.
     */
    @Mock
    private IUserService userService;
    
    /**
     * Mock del servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Mock del servicio de notificaciones.
     */
    @Mock
    private INotificacionService notificacionService;
    
    /**
     * Mock del servicio de inspecciones.
     */
    @Mock
    private IInspeccionesService inspeccionesService;
    
    /**
     * Mock del servicio de inspecciones.
     */
    @Mock
    private LazyModelEquipos model;
    
    /**
     * Captor del objeto equipo.
     */
    @Captor
    private ArgumentCaptor<Equipo> equipoCaptor;
    
    /**
     * Instancia de prueba del bean de equipos.
     */
    @InjectMocks
    private EquiposBean equipoBean;
    
    /**
     * Literal para pruebas.
     */
    private static final String JEFEEQUIPO = "jefeEquipo";
    
    /**
     * Literal para pruebas.
     */
    private static final String MIEMBROS = "miembros";
    
    /**
     * Literal para pruebas.
     */
    private static final String CONFIRM = "confirm";
    
    /**
     * Literal para nombre equipo.
     */
    private static final String NOMBREQUIPO = "nombreEquipo";
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Utilities.class);
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(EquiposBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        EquiposBean target = new EquiposBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormAltaEquipo()}.
     */
    @Test
    public void getFormAltaEquipo() {
        List<User> usuarios = new ArrayList<>();
        usuarios.add(User.builder().username("miembro1").nombre("nombreMiembro1").apellido1("apellido1Miembro1")
                .apellido2("apellido2Miembro1").build());
        when(userService.buscarNoMiembroEquipoNoJefe(null)).thenReturn(usuarios);
        when(userService.findByfechaBajaIsNullAndRole(RoleEnum.ROLE_EQUIPO_INSPECCIONES)).thenReturn(usuarios);
        
        String rutaVista = equipoBean.getFormAltaEquipo();
        
        assertThat(equipoBean.getListadoPotencialesJefes()).isEqualTo(usuarios);
        assertThat(equipoBean.getListadoPotencialesMiembros()).isEqualTo(usuarios);
        assertThat(rutaVista).isEqualTo("/equipos/altaEquipo?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#altaEquipo()}.
     */
    @Test
    public void altaEquipo() {
        equipoBean.setEquipo(Equipo.builder().nombreEquipo(NOMBREQUIPO).build());
        equipoBean.setJefeSeleccionado(User.builder().username("jefe").nombre("nombreJefe").apellido1("apellido1Jefe")
                .apellido2("apellido2Jefe").build());
        equipoBean.setTipoEquipo(TipoEquipo.builder().codigo("codigo").descripcion("descripcion").build());
        List<User> miembrosSeleccionados = new ArrayList<>();
        miembrosSeleccionados.add(User.builder().username("miembro1").nombre("nombreMiembro1")
                .apellido1("apellido1Miembro1").apellido2("apellido2Miembro1").build());
        equipoBean.setMiembrosSeleccionados(miembrosSeleccionados);
        
        equipoBean.altaEquipo();
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        assertThat(equipoCaptor.getValue().getJefeEquipo().getNombreCompleto())
                .isEqualTo("nombreJefe apellido1Jefe apellido2Jefe");
        assertThat(equipoCaptor.getValue().getMiembros()).hasSize(2);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.INSPECCION.getDescripcion()));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INSPECCION.getDescripcion()), eq(equipoCaptor.getValue()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.getDescripcion()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormularioBusquedaEquipos()}.
     */
    @Test
    public void getFormularioBusquedaEquipos() {
        equipoBean.setModel(mock(LazyModelEquipos.class));
        String ruta = equipoBean.getFormularioBusquedaEquipos();
        assertThat(ruta).isEqualTo("/equipos/equipos?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#limpiarBusqueda()}.
     */
    @Test
    public void limpiarBusqueda() {
        equipoBean.setModel(mock(LazyModelEquipos.class));
        equipoBean.limpiarBusqueda();
        assertThat(equipoBean.getEquipoBusqueda()).isNotNull();
        assertThat(equipoBean.getEstado()).isNull();
        assertThat(equipoBean.getModel().getRowCount()).isEqualTo(0);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#buscarEquipo()}.
     */
    @Test
    public void buscarEquipo() {
        EquipoBusqueda busqueda = mock(EquipoBusqueda.class);
        equipoBean.setEquipoBusqueda(busqueda);
        equipoBean.buscarEquipo();
        verify(model, times(1)).setBusqueda(busqueda);
        verify(model, times(1)).load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#eliminarEquipo(Equipo)}.
     */
    @Test
    public void eliminarEquipoConInspecciones() {
        when(authentication.getName()).thenReturn("jefeInspecciones");
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo(NOMBREQUIPO).build();
        when(inspeccionesService.existenInspeccionesNoFinalizadas(equipo)).thenReturn(true);
        equipoBean.eliminarEquipo(equipo);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class), eq(null));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#eliminarEquipo(Equipo)}.
     */
    @Test
    public void eliminarEquipo() {
        when(authentication.getName()).thenReturn("jefeInspecciones");
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo(NOMBREQUIPO).build();
        when(inspeccionesService.existenInspeccionesNoFinalizadas(equipo)).thenReturn(false);
        equipoBean.eliminarEquipo(equipo);
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        assertThat(equipoCaptor.getValue().getFechaBaja()).isNotNull();
        assertThat(equipoCaptor.getValue().getUsernameBaja()).isNotNull();
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.INSPECCION.getDescripcion()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.getDescripcion()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#eliminarEquipo(Equipo)}.
     */
    @Test
    public void eliminarEquipoException() {
        when(authentication.getName()).thenReturn("jefeInspecciones");
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo(NOMBREQUIPO).build();
        when(inspeccionesService.existenInspeccionesNoFinalizadas(equipo)).thenReturn(false);
        when(equipoService.save(equipo)).thenThrow(TransientDataAccessResourceException.class);
        equipoBean.eliminarEquipo(equipo);
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class), eq(null));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormModificarEquipo(Equipo)}.
     */
    @Test
    public void getFormModificarEquipo() {
        Equipo equipo = new Equipo();
        equipo.setId(1L);
        when(equipoService.findOne(equipo.getId())).thenReturn(equipo);
        String ruta_vista = equipoBean.getFormModificarEquipo(equipo);
        
        verify(miembroService, times(1)).findByEquipo(equipo);
        assertThat(ruta_vista).isEqualTo("/equipos/modificarEquipo?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormModificarEquipo(Equipo)}.
     */
    @Test
    public void getFormModificarEquipoNoExiste() {
        Equipo equipo = mock(Equipo.class);
        equipo.setId(1L);
        when(equipoService.findOne(equipo.getId())).thenReturn(null);
        String ruta_vista = equipoBean.getFormModificarEquipo(equipo);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        assertThat(ruta_vista).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#eliminarEquipo(Equipo)}.
     */
    @Test
    public void eliminarMiembro() {
        User userMiembro = User.builder().username("miembro").build();
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo(NOMBREQUIPO).build();
        Miembro miembro = Miembro.builder().id(1L).usuario(userMiembro).posicion(RolEquipoEnum.MIEMBRO).equipo(equipo)
                .build();
        List<Miembro> miembros = new ArrayList<>();
        miembros.add(miembro);
        equipo.setMiembros(miembros);
        equipoBean.setEquipo(equipo);
        
        equipoBean.eliminarMiembro(miembro);
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        assertThat(equipoCaptor.getValue().getMiembros()).isEmpty();
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.INSPECCION.getDescripcion()));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INSPECCION.getDescripcion()), eq(equipoCaptor.getValue()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.getDescripcion()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormCambiarJefeEquipo()}.
     */
    @Test
    public void getFormCambiarJefeEquipo() {
        Equipo equipo = mock(Equipo.class);
        equipoBean.setEquipo(equipo);
        
        String ruta_vista = equipoBean.getFormCambiarJefeEquipo();
        
        verify(userService, times(1)).findByfechaBajaIsNullAndRole(RoleEnum.ROLE_EQUIPO_INSPECCIONES);
        assertThat(ruta_vista).isEqualTo("/equipos/cambiarJefeEquipo?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#cambiarJefeEquipo()}.
     */
    @Test
    public void cambiarJefeEquipo() {
        User userJefe = User.builder().username("jefe").build();
        User miembro1 = User.builder().username("miembro1").build();
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo(NOMBREQUIPO).build();
        List<Miembro> miembros = new ArrayList<>();
        miembros.add(Miembro.builder().usuario(userJefe).posicion(RolEquipoEnum.JEFE_EQUIPO).equipo(equipo).build());
        miembros.add(Miembro.builder().usuario(miembro1).posicion(RolEquipoEnum.MIEMBRO).equipo(equipo).build());
        equipo.setMiembros(miembros);
        equipoBean.setEquipo(equipo);
        equipoBean.setJefeSeleccionado(User.builder().username("nuevoJefe").nombre("nombreNuevoJefe")
                .apellido1("apellido1NuevoJefe").apellido2("apellido2NuevoJefe").build());
        
        equipoBean.cambiarJefeEquipo();
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        assertThat(equipoCaptor.getValue().getJefeEquipo().getNombreCompleto())
                .isEqualTo("nombreNuevoJefe apellido1NuevoJefe apellido2NuevoJefe");
        assertThat(equipoCaptor.getValue().getMiembros().size()).isEqualTo(2);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.INSPECCION.getDescripcion()));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INSPECCION.getDescripcion()), eq(equipoCaptor.getValue()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.getDescripcion()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#cambiarJefeEquipo()}.
     */
    @Test
    public void cambiarJefeEquipo_validacionJefeNoSeleccionado() {
        equipoBean.setJefeSeleccionado(null);
        
        equipoBean.cambiarJefeEquipo();
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormAniadirMiembroEquipo()}.
     */
    @Test
    public void getFormAniadirMiembroEquipo() {
        Equipo equipo = mock(Equipo.class);
        equipoBean.setEquipo(equipo);
        
        String ruta_vista = equipoBean.getFormAniadirMiembroEquipo();
        
        // verify(userService, times(1)).buscarNoJefeNoMiembroEquipo(equipo);
        assertThat(ruta_vista).isEqualTo("/equipos/anadirMiembroEquipo?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#guardarMiembros(RolEquipoEnum)}.
     */
    @Test
    public void guardarMiembros() {
        User userJefe = User.builder().username("jefe").build();
        User userMiembro = User.builder().username("miembro").build();
        
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo(NOMBREQUIPO).build();
        List<Miembro> miembros = new ArrayList<>();
        miembros.add(Miembro.builder().usuario(userJefe).posicion(RolEquipoEnum.JEFE_EQUIPO).equipo(equipo).build());
        miembros.add(Miembro.builder().usuario(userMiembro).posicion(RolEquipoEnum.MIEMBRO).equipo(equipo).build());
        equipo.setMiembros(miembros);
        equipoBean.setEquipo(equipo);
        List<User> miembrosSeleccionados = new ArrayList<>();
        miembrosSeleccionados.add(User.builder().username("nuevoMiembro").nombre("nombreNuevoMiembro")
                .apellido1("apellido1NuevoMiembro").apellido2("apellido2NuevoMiembro").build());
        equipoBean.setMiembrosSeleccionados(miembrosSeleccionados);
        
        equipoBean.guardarMiembros(RolEquipoEnum.MIEMBRO);
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        assertThat(equipoCaptor.getValue().getMiembros().size()).isEqualTo(3);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.INSPECCION.getDescripcion()));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INSPECCION.getDescripcion()), eq(equipoCaptor.getValue()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.getDescripcion()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onToggle(ToggleEvent)}.
     */
    @Test
    public void onToggle() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        equipoBean.setColumnasVisibles(listaToogle);
        
        equipoBean.onToggle(eventMock);
        
        assertThat(listaToogle.get(0)).isFalse();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoJefeEquipoAMiembros() {
        User jefeSeleccionado = User.builder().username("potencialJefe").nombre("nombrePotencialJefe")
                .apellido1("apellido1PotencialJefe").apellido2("apellido2PotencialJefe").build();
        List<User> listadoPotencialesMiembros = new ArrayList<>();
        listadoPotencialesMiembros.add(jefeSeleccionado);
        equipoBean.setListadoPotencialesMiembros(listadoPotencialesMiembros);
        equipoBean.setJefeSeleccionado(jefeSeleccionado);
        List<User> miembrosSeleccionados = new ArrayList<>();
        miembrosSeleccionados.add(jefeSeleccionado);
        equipoBean.setMiembrosSeleccionados(miembrosSeleccionados);
        FlowEvent event = new FlowEvent(mock(UIComponent.class), JEFEEQUIPO, MIEMBROS);
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        assertThat(equipoBean.getListadoPotencialesMiembros()).doesNotContain(jefeSeleccionado);
        assertThat(equipoBean.getMiembrosSeleccionados()).doesNotContain(jefeSeleccionado);
        assertThat(nombre_paso).isEqualTo(MIEMBROS);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoJefeEquipoAMiembros_validacionJefeNoSeleccionado() {
        equipoBean.setJefeSeleccionado(null);
        FlowEvent event = new FlowEvent(mock(UIComponent.class), JEFEEQUIPO, MIEMBROS);
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(""));
        assertThat(nombre_paso).isEqualTo(JEFEEQUIPO);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void onFlowProcess_pasoMiembrosAConfirm() {
        equipoBean.setMiembrosSeleccionados(mock(List.class));
        FlowEvent event = new FlowEvent(mock(UIComponent.class), MIEMBROS, CONFIRM);
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo(CONFIRM);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoMiembrosAConfirm_validacionSaltarPaso() {
        equipoBean.setSkip(true);
        equipoBean.setMiembrosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), MIEMBROS, CONFIRM);
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo(CONFIRM);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoMiembrosAConfirm_validacionMiembrosNoSeleccionados() {
        equipoBean.setSkip(false);
        equipoBean.setMiembrosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), MIEMBROS, CONFIRM);
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(""));
        assertThat(nombre_paso).isEqualTo(MIEMBROS);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoMiembrosAJefeEquipo() {
        equipoBean.setListadoPotencialesMiembros(new ArrayList<>());
        User jefeSeleccionado = User.builder().username("potencialJefe").nombre("nombrePotencialJefe")
                .apellido1("apellido1PotencialJefe").apellido2("apellido2PotencialJefe").build();
        equipoBean.setJefeSeleccionado(jefeSeleccionado);
        FlowEvent event = new FlowEvent(mock(UIComponent.class), MIEMBROS, JEFEEQUIPO);
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo(JEFEEQUIPO);
        assertThat(equipoBean.getListadoPotencialesMiembros()).contains(jefeSeleccionado);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#init()}.
     */
    @Test
    public void init() {
        equipoBean.init();
        assertThat(equipoBean.getColumnasVisibles()).hasSize(5);
    }
    
}
