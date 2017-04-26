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

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IEquipoService;
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
    SecurityContextHolder securityContextHolder;
    
    @Mock
    SecurityContext securityContext;
    
    @Mock
    Authentication authentication;
    
    @Mock
    private ITipoEquipoService tipoEquipoService;
    
    @Mock
    private IEquipoService equipoService;
    
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
        String rutaVista = equipoBean.getFormAltaEquipo();
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
        assertThat(equipoCaptor.getValue().getMiembros().size()).isEqualTo(2);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.INSPECCION.name()));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INSPECCION.name()), eq(equipoCaptor.getValue()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormularioBusquedaEquipos()}.
     */
    @Test
    public void getFormularioBusquedaEquipos() {
        equipoBean.setVieneDe("menu");
        equipoBean.getFormularioBusquedaEquipos();
        verify(tipoEquipoService, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#limpiarBusqueda()}.
     */
    @Test
    public void limpiarBusqueda() {
        equipoBean.limpiarBusqueda();
        verify(equipoBusqueda, times(1)).resetValues();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#buscarEquipo()}.
     */
    @Test
    public void buscarEquipo() {
        equipoBean.buscarEquipo();
        verify(equipoService, times(1)).buscarEquipoCriteria(equipoBusqueda);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#eliminarEquipo(Equipo)}.
     */
    @Test
    public void eliminarEquipo() {
        // User usuarioLogueado = User.builder().username("jefeInspecciones").role(RoleEnum.ROLE_JEFE_INSPECCIONES)
        // .build();
        // when(authentication.getPrincipal()).thenReturn(usuarioLogueado);
        // when(authentication.getName()).thenReturn(usuarioLogueado.getUsername());
        when(authentication.getName()).thenReturn("jefeInspecciones");
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo("nombreEquipo").build();
        
        equipoBean.eliminarEquipo(equipo);
        
        verify(equipoService, times(1)).save(equipoCaptor.capture());
        assertThat(equipoCaptor.getValue().getFechaBaja()).isNotNull();
        assertThat(equipoCaptor.getValue().getUsernameBaja()).isNotNull();
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.INSPECCION.name()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormModificarEquipo(Equipo)}.
     */
    @Test
    public void getFormModificarEquipo() {
        Equipo equipo = Equipo.builder().id(1L).nombreEquipo("nombreEquipo").build();
        
        String ruta_vista = equipoBean.getFormModificarEquipo(equipo);
        
        verify(equipoService, times(1)).findByEquipo(equipo);
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
        assertThat(equipoCaptor.getValue().getMiembros().size()).isEqualTo(0);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.INSPECCION.name()));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INSPECCION.name()), eq(equipoCaptor.getValue()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormCambiarJefeEquipo()}.
     */
    @Ignore
    @Test
    public void getFormCambiarJefeEquipo() {
        // TODO auto-generated by JUnit Helper.
        EquiposBean target = new EquiposBean();
        // given
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        String actual = target.getFormCambiarJefeEquipo();
        // then
        // e.g. : verify(mocked).called();
        String expected = null;
        assertThat(actual).isEqualTo(expected);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#cambiarJefeEquipo()}.
     */
    @Ignore
    @Test
    public void cambiarJefeEquipo() {
        // TODO auto-generated by JUnit Helper.
        EquiposBean target = new EquiposBean();
        // given
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        target.cambiarJefeEquipo();
        // then
        // e.g. : verify(mocked).called();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#getFormAniadirMiembroEquipo()}.
     */
    @Ignore
    @Test
    public void getFormAniadirMiembroEquipo() {
        // TODO auto-generated by JUnit Helper.
        EquiposBean target = new EquiposBean();
        // given
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        String actual = target.getFormAniadirMiembroEquipo();
        // then
        // e.g. : verify(mocked).called();
        String expected = null;
        assertThat(actual).isEqualTo(expected);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#guardarMiembros(RolEquipoEnum)}.
     */
    @Ignore
    @Test
    public void guardarMiembros() {
        // TODO auto-generated by JUnit Helper.
        EquiposBean target = new EquiposBean();
        // given
        RolEquipoEnum posicion = mock(RolEquipoEnum.class);
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        target.guardarMiembros(posicion);
        // then
        // e.g. : verify(mocked).called();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onToggle(ToggleEvent)}.
     */
    @Ignore
    @Test
    public void onToggle() {
        // TODO auto-generated by JUnit Helper.
        EquiposBean target = new EquiposBean();
        // given
        ToggleEvent e = mock(ToggleEvent.class);
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        target.onToggle(e);
        // then
        // e.g. : verify(mocked).called();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#onFlowProcess(FlowEvent)}.
     */
    @Ignore
    @Test
    public void onFlowProcess() {
        // TODO auto-generated by JUnit Helper.
        EquiposBean target = new EquiposBean();
        // given
        FlowEvent event = mock(FlowEvent.class);
        // e.g. : given(mocked.called()).willReturn(1);
        // when
        String actual = target.onFlowProcess(event);
        // then
        // e.g. : verify(mocked).called();
        String expected = null;
        assertThat(actual).isEqualTo(expected);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EquiposBean#init()}.
     */
    @Ignore
    @Test
    public void init() {
        equipoBean.init();
    }
    
}
