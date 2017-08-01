/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.ToggleEvent;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IAlertasNotificacionesUsuarioService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test para el servicio de alertas.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class AlertasBeanTest {
    /**
     * Simulación del securityContext.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Simulación de la autenticación.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Mock Repositorio de alertas.
     */
    @Mock
    private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    /**
     * Mock Repositorio de alertas.
     */
    @Mock
    private IRegistroActividadService regActividad;
    
    /**
     * Simulación del Gestor Documental.
     */
    @InjectMocks
    private AlertasBean alertasBeanMock;
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(AlertasBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        AlertasBean target = new AlertasBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioLogueado");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.AlertasBean#eliminarAlertas(es.mira.progesin.persistence.entities.Alerta)}.
     */
    @Test
    public final void testEliminarAlertas() {
        List<Alerta> alertas = new ArrayList<>();
        Alerta alerta1 = new Alerta();
        alerta1.setIdAlerta(1L);
        Alerta alerta2 = new Alerta();
        alerta2.setIdAlerta(2L);
        alertas.add(alerta1);
        alertas.add(alerta2);
        alertasBeanMock.setListaAlertas(alertas);
        
        alertasBeanMock.eliminarAlertas(alerta1);
        
        verify(alertasNotificacionesUsuarioService, times(1)).delete(
                SecurityContextHolder.getContext().getAuthentication().getName(), alerta1.getIdAlerta(),
                TipoMensajeEnum.ALERTA);
        verify(regActividad, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.ALERTAS.getDescripcion()));
        assertThat(alertasBeanMock.getListaAlertas()).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.AlertasBean#eliminarAlertas(es.mira.progesin.persistence.entities.Alerta)}.
     */
    @Test
    public final void testEliminarAlertasException() {
        List<Alerta> alertas = new ArrayList<>();
        Alerta alerta1 = new Alerta();
        alerta1.setIdAlerta(1L);
        Alerta alerta2 = new Alerta();
        alerta2.setIdAlerta(2L);
        alertas.add(alerta1);
        alertas.add(alerta2);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        doThrow(TransientDataAccessResourceException.class).when(alertasNotificacionesUsuarioService).delete(username,
                alerta1.getIdAlerta(), TipoMensajeEnum.ALERTA);
        
        alertasBeanMock.setListaAlertas(alertas);
        alertasBeanMock.eliminarAlertas(alerta1);
        
        verify(alertasNotificacionesUsuarioService, times(1)).delete(username, alerta1.getIdAlerta(),
                TipoMensajeEnum.ALERTA);
        verify(regActividad, times(1)).altaRegActividadError(eq(SeccionesEnum.ALERTAS.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        assertThat(alertasBeanMock.getListaAlertas()).hasSize(2);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.AlertasBean#onToggle(org.primefaces.event.ToggleEvent)}.
     */
    @Test
    public final void testOnToggle() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        alertasBeanMock.setList(listaToogle);
        alertasBeanMock.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isFalse();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.AlertasBean#init()}.
     */
    @Test
    public final void testInit() {
        List<Alerta> alertas = new ArrayList<>();
        when(alertasNotificacionesUsuarioService
                .findAlertasByUser(SecurityContextHolder.getContext().getAuthentication().getName()))
                        .thenReturn(alertas);
        alertasBeanMock.init();
        assertThat(alertasBeanMock.getListaAlertas()).isNotNull();
        assertThat(alertasBeanMock.getList()).hasSize(6);
    }
    
}
