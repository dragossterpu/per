/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.persistence.entities.Sugerencia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISugerenciaService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;

/**
 * Test del bean de sugerencias.
 * @author EZENTIS
 *
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class SugerenciasBeanTest {
    
    /**
     * Instancia de prueba del bean de equipos.
     */
    @InjectMocks
    private SugerenciasBean sugerenciasBeanMock;
    
    /**
     * Simulación del servicio de registro de actividad.
     */
    @Mock
    private ISugerenciaService sugerenciasServiceMock;
    
    /**
     * Simulación del servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadServiceMock;
    
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
     * Simulación del user.
     */
    @Mock
    private IUserService userService;
    
    /**
     * Simulación del correo.
     */
    @Mock
    private ICorreoElectronico correoService;
    
    /**
     * Literal para pruebas.
     */
    private static final String RUTA = "/administracion/sugerencias/contestarSugerencia?faces-redirect=true";
    
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
        assertThat(SugerenciasBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        SugerenciasBean target = new SugerenciasBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SugerenciasBean#guardarSugerencia(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testGuardarSugerencia() {
        String modulo = "INFORMES";
        String descripcion = "descripcion_informes";
        
        Sugerencia sugerencia = Sugerencia.builder().modulo(modulo).descripcion(descripcion).build();
        
        when(sugerenciasServiceMock.save(sugerencia)).thenReturn(sugerencia);
        String ruta = sugerenciasBeanMock.guardarSugerencia(modulo, descripcion);
        
        verify(sugerenciasServiceMock, times(1)).save(sugerencia);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
        
        assertThat(ruta).isEqualTo("/principal/crearSugerencia?faces-redirect=true");
        
        PowerMockito.verifyStatic(never());
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        
        verify(regActividadServiceMock, never()).altaRegActividadError(eq(SeccionesEnum.SUGERENCIAS.name()),
                any(Exception.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SugerenciasBean#guardarSugerencia(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testGuardarSugerencia_exception() {
        String modulo = "GESTOR DOCUMENTAL";
        String descripcion = "test_documental";
        
        Sugerencia sugerencia = Sugerencia.builder().modulo(modulo).descripcion(descripcion).build();
        
        when(sugerenciasServiceMock.save(sugerencia)).thenThrow(TransientDataAccessResourceException.class);
        String ruta = sugerenciasBeanMock.guardarSugerencia(modulo, descripcion);
        
        verify(sugerenciasServiceMock, times(1)).save(sugerencia);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        
        verify(regActividadServiceMock, times(1)).altaRegActividadError(eq(SeccionesEnum.SUGERENCIAS.name()),
                any(Exception.class));
        assertThat(ruta).isEqualTo("/principal/crearSugerencia?faces-redirect=true");
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SugerenciasBean#sugerenciasListado()}.
     */
    @Test
    public final void testSugerenciasListado() {
        List<Sugerencia> sugerencias = new ArrayList<>();
        Sugerencia sug1 = Sugerencia.builder().descripcion("descripcion_test").modulo("MODULO_TEST").build();
        sugerencias.add(sug1);
        when(sugerenciasServiceMock.findAll()).thenReturn(sugerencias);
        sugerenciasBeanMock.sugerenciasListado();
        verify(sugerenciasServiceMock, times(1)).findAll();
        assertThat(sugerencias).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SugerenciasBean#eliminarSugerencia(es.mira.progesin.persistence.entities.Sugerencia)}
     * .
     */
    @Test
    public final void testEliminarSugerencia() {
        String modulo = "MODULO_SUGERENCIA";
        String descripcion = "descripcion_sugerencia";
        int id = 3;
        
        Sugerencia sugerencia = Sugerencia.builder().modulo(modulo).descripcion(descripcion).idSugerencia(id).build();
        List<Sugerencia> sugerencias = new ArrayList<>();
        sugerencias.add(sugerencia);
        sugerenciasBeanMock.setSugerenciasListado(sugerencias);
        
        sugerenciasBeanMock.eliminarSugerencia(sugerencia);
        
        verify(sugerenciasServiceMock, times(1)).delete(id);
        PowerMockito.verifyStatic(never());
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
        
        verify(regActividadServiceMock, never()).altaRegActividadError(eq(SeccionesEnum.SUGERENCIAS.name()),
                any(Exception.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SugerenciasBean#eliminarSugerencia(es.mira.progesin.persistence.entities.Sugerencia)}
     * .
     */
    @Test
    public final void testEliminarSugerencia_excepcion() {
        String modulo = "INSPECCIONES";
        String descripcion = "SUGERENCIA INSPECCIONES";
        int id = 3;
        
        Sugerencia sugerencia = Sugerencia.builder().modulo(modulo).descripcion(descripcion).idSugerencia(id).build();
        List<Sugerencia> sugerencias = new ArrayList<>();
        sugerencias.add(sugerencia);
        sugerenciasBeanMock.setSugerenciasListado(sugerencias);
        doThrow(TransientDataAccessResourceException.class).when(sugerenciasServiceMock).delete(3);
        
        sugerenciasBeanMock.eliminarSugerencia(sugerencia);
        
        verify(sugerenciasServiceMock, times(1)).delete(id);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
        
        verify(regActividadServiceMock, times(1)).altaRegActividadError(eq(SeccionesEnum.SUGERENCIAS.name()),
                any(Exception.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SugerenciasBean#contestarSugerencia(es.mira.progesin.persistence.entities.Sugerencia)}
     * .
     */
    @Test
    public final void testContestarSugerencia() {
        String modulo = "CUESTIONARIOS";
        String descripcion = "descripción cuestionario";
        int id = 3;
        
        Sugerencia sugerenciaSeleccionada = Sugerencia.builder().modulo(modulo).descripcion(descripcion)
                .idSugerencia(id).build();
        sugerenciasBeanMock.setSugerencia(sugerenciaSeleccionada);
        String ruta = sugerenciasBeanMock.contestarSugerencia(sugerenciaSeleccionada);
        
        assertThat(sugerenciaSeleccionada).isEqualTo(sugerenciasBeanMock.getSugerencia());
        assertThat(ruta).isEqualTo(RUTA);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SugerenciasBean#contestar(es.mira.progesin.persistence.entities.Sugerencia, java.lang.String)}
     * .
     */
    @Test
    public final void testContestar() {
        String modulo = "SOLICITUDES";
        String descripcion = "descripcion_solicitud";
        int id = 3;
        
        Sugerencia sugerenciaSeleccionada = Sugerencia.builder().modulo(modulo).descripcion(descripcion)
                .idSugerencia(id).fechaRegistro(new Date()).usuarioRegistro("usuarioRegistro").build();
        sugerenciasBeanMock.setSugerencia(sugerenciaSeleccionada);
        
        String contestacion = "contestación_test";
        when(sugerenciasServiceMock.save(sugerenciaSeleccionada)).thenReturn(sugerenciaSeleccionada);
        when(userService.findOne("usuarioRegistro")).thenReturn(User.builder().correo("correo@test.test").build());
        
        String ruta = sugerenciasBeanMock.contestar(sugerenciaSeleccionada, contestacion);
        
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
        
        verify(sugerenciasServiceMock, times(1)).save(sugerenciaSeleccionada);
        verify(userService, times(1)).findOne(any(String.class));
        verify(correoService, times(1)).envioCorreo(any(String.class), any(String.class), any(String.class));
        
        verify(regActividadServiceMock, times(0)).altaRegActividadError(eq(SeccionesEnum.SUGERENCIAS.name()),
                any(Exception.class));
        assertThat(ruta).isEqualTo(RUTA);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SugerenciasBean#contestar(es.mira.progesin.persistence.entities.Sugerencia, java.lang.String)}
     * .
     */
    @Test
    public final void testContestar_correoException() {
        String modulo = "MODULO_TEST";
        String descripcion = "descripcion_test";
        int id = 3;
        
        Sugerencia sugerenciaSeleccionada = Sugerencia.builder().modulo(modulo).descripcion(descripcion)
                .idSugerencia(id).fechaRegistro(new Date()).usuarioRegistro("usuarioRegistroTest").build();
        sugerenciasBeanMock.setSugerencia(sugerenciaSeleccionada);
        
        String contestacion = "contestación_test";
        when(sugerenciasServiceMock.save(sugerenciaSeleccionada)).thenReturn(sugerenciaSeleccionada);
        when(userService.findOne("usuarioRegistroTest")).thenReturn(User.builder().correo("correo@test.test").build());
        doThrow(CorreoException.class).when(correoService).envioCorreo(any(String.class), any(String.class),
                any(String.class));
        
        String ruta = sugerenciasBeanMock.contestar(sugerenciaSeleccionada, contestacion);
        
        verify(sugerenciasServiceMock, times(1)).save(sugerenciaSeleccionada);
        verify(userService, times(1)).findOne(any(String.class));
        verify(correoService, times(1)).envioCorreo(any(String.class), any(String.class), any(String.class));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        verify(regActividadServiceMock, times(1)).altaRegActividadError(eq(SeccionesEnum.SUGERENCIAS.name()),
                any(Exception.class));
        
        assertThat(ruta).isEqualTo(RUTA);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SugerenciasBean#contestar(es.mira.progesin.persistence.entities.Sugerencia, java.lang.String)}
     * .
     */
    @Test
    public final void testContestar_otraException() {
        String modulo = "GUÍAS";
        String descripcion = "descripcion_guias";
        int id = 3;
        
        Sugerencia sugerenciaSeleccionada = Sugerencia.builder().modulo(modulo).descripcion(descripcion)
                .idSugerencia(id).fechaRegistro(new Date()).usuarioRegistro("usuarioRegistroTest").build();
        sugerenciasBeanMock.setSugerencia(sugerenciaSeleccionada);
        
        String contestacion = "contestación_test";
        doThrow(TransientDataAccessResourceException.class).when(sugerenciasServiceMock).save(sugerenciaSeleccionada);
        
        String ruta = sugerenciasBeanMock.contestar(sugerenciaSeleccionada, contestacion);
        
        verify(sugerenciasServiceMock, times(1)).save(sugerenciaSeleccionada);
        verify(userService, times(0)).findOne(any(String.class));
        verify(correoService, times(0)).envioCorreo(any(String.class), any(String.class), any(String.class));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        verify(regActividadServiceMock, times(1)).altaRegActividadError(eq(SeccionesEnum.SUGERENCIAS.name()),
                any(Exception.class));
        
        assertThat(ruta).isEqualTo(RUTA);
    }
    
}
