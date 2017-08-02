/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.context.RequestContext;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IAlertasNotificacionesUsuarioService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test para AlertasNotificacionesUsuarioBean.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, RequestContext.class })
public class AlertasNotificacionesUsuarioBeanTest {
    
    /**
     * Simulación del securityContext.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Simulación del RequestContext.
     */
    
    /**
     * Simulación de la autenticación.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Mock Servicio de alertas y notificaciones.
     */
    @Mock
    private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    /**
     * Mock Servicio del registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividad;
    
    /**
     * Simulación de AlertasNotificacionesUsuarioBean.
     */
    @InjectMocks
    private AlertasNotificacionesUsuarioBean alertasNotificacionesUsuarioBean;
    
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
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(AlertasNotificacionesUsuarioBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        AlertasNotificacionesUsuarioBean target = new AlertasNotificacionesUsuarioBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.AlertasNotificacionesUsuarioBean#eliminarAlertas(es.mira.progesin.persistence.entities.Alerta)}.
     */
    @Test
    public final void testEliminarAlertas() {
        Alerta alerta = new Alerta();
        alerta.setIdAlerta(1L);
        alertasNotificacionesUsuarioBean.eliminarAlertas(alerta);
        verify(alertasNotificacionesUsuarioService, times(1)).delete(
                SecurityContextHolder.getContext().getAuthentication().getName(), alerta.getIdAlerta(),
                TipoMensajeEnum.ALERTA);
        verify(regActividad, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.AlertasNotificacionesUsuarioBean#eliminarAlertas(es.mira.progesin.persistence.entities.Alerta)}.
     */
    @Test
    public final void testEliminarAlertasException() {
        Alerta alerta = new Alerta();
        alerta.setIdAlerta(1L);
        String userActual = SecurityContextHolder.getContext().getAuthentication().getName();
        doThrow(TransientDataAccessResourceException.class).when(alertasNotificacionesUsuarioService)
                .delete(eq(userActual), eq(alerta.getIdAlerta()), eq(TipoMensajeEnum.ALERTA));
        alertasNotificacionesUsuarioBean.eliminarAlertas(alerta);
        verify(alertasNotificacionesUsuarioService, times(1)).delete(eq(userActual), eq(alerta.getIdAlerta()),
                eq(TipoMensajeEnum.ALERTA));
        verify(regActividad, times(1)).altaRegActividadError(any(String.class),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.AlertasNotificacionesUsuarioBean#eliminarNotificacion(es.mira.progesin.persistence.entities.Notificacion)}.
     */
    @Test
    public final void testEliminarNotificacion() {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(1L);
        alertasNotificacionesUsuarioBean.eliminarNotificacion(notificacion);
        verify(alertasNotificacionesUsuarioService, times(1)).delete(
                SecurityContextHolder.getContext().getAuthentication().getName(), notificacion.getIdNotificacion(),
                TipoMensajeEnum.NOTIFICACION);
        verify(regActividad, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.AlertasNotificacionesUsuarioBean#eliminarNotificacion(es.mira.progesin.persistence.entities.Notificacion)}.
     */
    @Test
    public final void testEliminarNotificacionException() {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(1L);
        String userActual = SecurityContextHolder.getContext().getAuthentication().getName();
        doThrow(TransientDataAccessResourceException.class).when(alertasNotificacionesUsuarioService).delete(userActual,
                notificacion.getIdNotificacion(), TipoMensajeEnum.NOTIFICACION);
        alertasNotificacionesUsuarioBean.eliminarNotificacion(notificacion);
        verify(alertasNotificacionesUsuarioService, times(1)).delete(userActual, notificacion.getIdNotificacion(),
                TipoMensajeEnum.NOTIFICACION);
        verify(regActividad, times(1)).altaRegActividadError(any(String.class),
                any(TransientDataAccessResourceException.class));
    }
    
}
