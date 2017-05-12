/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.lazydata.LazyModelEquipos;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IMiembroService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoEquipoService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;

/**
 * @author EZENTIS
 * 
 * Test del bean Equipos
 *
 */
@RunWith(PowerMockRunner.class)
// Evita conflictos con clases del sistema al enlazar los mocks por tipo
@PowerMockIgnore("javax.security.*")
// @PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
// @ContextConfiguration // @SpringBootTest
// @TestExecutionListeners(listeners = { WithSecurityContextTestExecutionListener.class })
public class EquiposBeanTest {
    
    @Mock
    private FacesUtilities facesUtilities;
    
    @Mock
    private SecurityContextHolder securityContextHolder;
    
    @Mock
    private SecurityContext securityContext;
    
    @Mock
    private Authentication authentication;
    
    @Mock
    private ITipoEquipoService tipoEquipoService;
    
    @Mock
    private IEquipoService equipoService;
    
    @Mock
    private IMiembroService miembroService;
    
    @Mock
    private IUserService userService;
    
    @Mock
    private IRegistroActividadService regActividadService;
    
    @Mock
    private INotificacionService notificacionService;
    
    @Mock
    private EquipoBusqueda equipoBusqueda;
    
    @Captor
    private ArgumentCaptor<Equipo> equipoCaptor;
    
    @InjectMocks
    private EquiposBean equipoBean;
    
    /**
     * Configuración inicial del test
     */
    @SuppressWarnings("static-access")
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
        when(securityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
    
    /**
     * Comprobación clase existe
     */
    @Test
    public void type() {
        assertThat(EquiposBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta
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
        when(userService.buscarNoJefeNoMiembroEquipo(null)).thenReturn(usuarios);
        
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
        equipoBean.setEquipo(Equipo.builder().nombreEquipo("nombreEquipo").build());
        equipoBean.setJefeSeleccionado(User.builder().username("jefe").nombre("nombreJefe").apellido1("apellido1Jefe")
                .apellido2("apellido2Jefe").build());
        equipoBean.setTipoEquipo(TipoEquipo.builder().codigo("codigo").descripcion("descripcion").build());
        List<User> miembrosSeleccionados = new ArrayList<>();
        miembrosSeleccionados.add(User.builder().username("miembro1").nombre("nombreMiembro1")
                .apellido1("apellido1Miembro1").apellido2("apellido2Miembro1").build());
        equipoBean.setMiembrosSeleccionados(miembrosSeleccionados);
        
        equipoBean.altaEquipo();
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        assertThat(equipoCaptor.getValue().getNombreJefe()).isEqualTo("nombreJefe apellido1Jefe apellido2Jefe");
        assertThat(equipoCaptor.getValue().getMiembros()).hasSize(2);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.INSPECCION.name()));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INSPECCION.name()), eq(equipoCaptor.getValue()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormularioBusquedaEquipos()}.
     */
    @Test
    public void getFormularioBusquedaEquipos() {
        equipoBean.setVieneDe("menu");
        equipoBean.setModel(mock(LazyModelEquipos.class));
        equipoBean.getFormularioBusquedaEquipos();
        verify(tipoEquipoService, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#limpiarBusqueda()}.
     */
    @Test
    public void limpiarBusqueda() {
        equipoBean.setModel(mock(LazyModelEquipos.class));
        equipoBean.limpiarBusqueda();
        verify(equipoBusqueda, times(1)).resetValues();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#buscarEquipo()}.
     */
    @Test
    @Ignore
    public void buscarEquipo() {
        // equipoBean.buscarEquipo();
        // verify(equipoService, times(1)).buscarEquipoCriteria(equipoBusqueda);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#eliminarEquipo(Equipo)}.
     */
    @Test
    public void eliminarEquipo() {
        when(authentication.getName()).thenReturn("jefeInspecciones");
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo("nombreEquipo").build();
        
        equipoBean.eliminarEquipo(equipo);
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        assertThat(equipoCaptor.getValue().getFechaBaja()).isNotNull();
        assertThat(equipoCaptor.getValue().getUsernameBaja()).isNotNull();
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.INSPECCION.name()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormModificarEquipo(Equipo)}.
     */
    @Test
    public void getFormModificarEquipo() {
        Equipo equipo = mock(Equipo.class);
        
        String ruta_vista = equipoBean.getFormModificarEquipo(equipo);
        
        verify(miembroService, times(1)).findByEquipo(equipo);
        assertThat(ruta_vista).isEqualTo("/equipos/modificarEquipo?faces-redirect=true");
    }
    
    // /**
    // * Test method for {@link es.mira.progesin.web.beans.EquiposBean#modificarEquipo()}.
    // */
    // @Test
    // public void modificarEquipo() {
    // Equipo equipo = Equipo.builder().id(1L).nombreEquipo("nombreEquipo").build();
    // equipoBean.setEquipo(equipo);
    //
    // equipoBean.modificarEquipo();
    //
    // verify(equipoService, times(1)).save(equipoCaptor.capture());
    // verify(regActividadService, times(1)).altaRegActividad(any(String.class),
    // eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.INSPECCION.name()));
    // verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
    // eq(SeccionesEnum.INSPECCION.name()), eq(equipoCaptor.getValue()));
    // verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.name()),
    // any(Exception.class));
    // }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#eliminarEquipo(Equipo)}.
     */
    @Test
    public void eliminarMiembro() {
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo("nombreEquipo").build();
        Miembro miembro = Miembro.builder().id(1L).username("miembro")
                .nombreCompleto("nombreMiembro apellido1Miembro apellido2Miembro").posicion(RolEquipoEnum.MIEMBRO)
                .equipo(equipo).build();
        List<Miembro> miembros = new ArrayList<>();
        miembros.add(miembro);
        equipo.setMiembros(miembros);
        equipoBean.setEquipo(equipo);
        
        equipoBean.eliminarMiembro(miembro);
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        assertThat(equipoCaptor.getValue().getMiembros()).isEmpty();
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.INSPECCION.name()));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INSPECCION.name()), eq(equipoCaptor.getValue()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.name()),
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
        
        verify(userService, times(1)).buscarNoJefeNoMiembroEquipo(equipo);
        assertThat(ruta_vista).isEqualTo("/equipos/cambiarJefeEquipo?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#cambiarJefeEquipo()}.
     */
    @Test
    public void cambiarJefeEquipo() {
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo("nombreEquipo").build();
        List<Miembro> miembros = new ArrayList<>();
        miembros.add(Miembro.builder().username("jefe").nombreCompleto("nombreJefe apellido1Jefe apellido2Jefe")
                .posicion(RolEquipoEnum.JEFE_EQUIPO).equipo(equipo).build());
        miembros.add(Miembro.builder().username("miembro1")
                .nombreCompleto("nombreMiembro1 apellido1Miembro1 apellido2Miembro1").posicion(RolEquipoEnum.MIEMBRO)
                .equipo(equipo).build());
        equipo.setMiembros(miembros);
        equipoBean.setEquipo(equipo);
        equipoBean.setJefeSeleccionado(User.builder().username("nuevoJefe").nombre("nombreNuevoJefe")
                .apellido1("apellido1NuevoJefe").apellido2("apellido2NuevoJefe").build());
        
        equipoBean.cambiarJefeEquipo();
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        assertThat(equipoCaptor.getValue().getNombreJefe())
                .isEqualTo("nombreNuevoJefe apellido1NuevoJefe apellido2NuevoJefe");
        assertThat(equipoCaptor.getValue().getMiembros().size()).isEqualTo(2);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.INSPECCION.name()));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INSPECCION.name()), eq(equipoCaptor.getValue()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#guardarMiembros(RolEquipoEnum)}.
     */
    @SuppressWarnings("static-access")
    @Test
    public void cambiarJefeEquipo_validacionJefeNoSeleccionado() {
        equipoBean.setJefeSeleccionado(null);
        
        equipoBean.cambiarJefeEquipo();
        
        PowerMockito.verifyStatic(times(1));
        facesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), eq("Modificación"), any(String.class),
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
        
        verify(userService, times(1)).buscarNoJefeNoMiembroEquipo(equipo);
        assertThat(ruta_vista).isEqualTo("/equipos/anadirMiembroEquipo?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#guardarMiembros(RolEquipoEnum)}.
     */
    @Test
    public void guardarMiembros() {
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo("nombreEquipo").build();
        List<Miembro> miembros = new ArrayList<>();
        miembros.add(Miembro.builder().username("jefe").nombreCompleto("nombreJefe apellido1Jefe apellido2Jefe")
                .posicion(RolEquipoEnum.JEFE_EQUIPO).equipo(equipo).build());
        miembros.add(Miembro.builder().username("miembro1")
                .nombreCompleto("nombreMiembro1 apellido1Miembro1 apellido2Miembro1").posicion(RolEquipoEnum.MIEMBRO)
                .equipo(equipo).build());
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
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.INSPECCION.name()));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INSPECCION.name()), eq(equipoCaptor.getValue()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onToggle(ToggleEvent)}.
     */
    @Ignore
    @Test
    public void onToggle() {
        
        // equipoBean.setColumnasVisibles(new ArrayList<>());
        // ToggleEvent e = mock(ToggleEvent.class);
        //
        // equipoBean.onToggle(e);
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
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "jefeEquipo", "miembros");
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        assertThat(equipoBean.getListadoPotencialesMiembros()).doesNotContain(jefeSeleccionado);
        assertThat(equipoBean.getMiembrosSeleccionados()).doesNotContain(jefeSeleccionado);
        assertThat(nombre_paso).isEqualTo("miembros");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @SuppressWarnings("static-access")
    @Test
    public void onFlowProcess_pasoJefeEquipoAMiembros_validacionJefeNoSeleccionado() {
        equipoBean.setJefeSeleccionado(null);
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "jefeEquipo", "miembros");
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        PowerMockito.verifyStatic(times(1));
        facesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(""));
        assertThat(nombre_paso).isEqualTo("jefeEquipo");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void onFlowProcess_pasoMiembrosAConfirm() {
        equipoBean.setMiembrosSeleccionados(mock(List.class));
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "miembros", "confirm");
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("confirm");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoMiembrosAConfirm_validacionSaltarPaso() {
        equipoBean.setSkip(true);
        equipoBean.setMiembrosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "miembros", "confirm");
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("confirm");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @SuppressWarnings("static-access")
    @Test
    public void onFlowProcess_pasoMiembrosAConfirm_validacionMiembrosNoSeleccionados() {
        equipoBean.setSkip(false);
        equipoBean.setMiembrosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "miembros", "confirm");
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        PowerMockito.verifyStatic(times(1));
        facesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(""));
        assertThat(nombre_paso).isEqualTo("miembros");
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
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "miembros", "jefeEquipo");
        
        String nombre_paso = equipoBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("jefeEquipo");
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
