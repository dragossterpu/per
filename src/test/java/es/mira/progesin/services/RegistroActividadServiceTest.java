/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IRegActividadRepository;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test para el servicio RegistroActividadService.
 * 
 * @author EZENTIS
 *
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, LoggerFactory.class })
public class RegistroActividadServiceTest {
    
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
     * Mock para el repositorio de registro de actividad.
     */
    @Mock
    private IRegActividadRepository regActividadRepository;
    
    /**
     * Instancia de prueba del registro de actividad.
     */
    @InjectMocks
    private IRegistroActividadService registroActividadServiceMock = new RegistroActividadService();
    
    /**
     * Mock para la simulación del log.
     */
    @Mock
    private Logger log;
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(SugerenciaService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        SugerenciaService target = new SugerenciaService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioLogueado");
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(log);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RegistroActividadService#save(es.mira.progesin.persistence.entities.RegistroActividad)}
     * .
     */
    @Test
    public final void testSave() {
        RegistroActividad regTest = mock(RegistroActividad.class);
        registroActividadServiceMock.save(regTest);
        verify(regActividadRepository, times(1)).save(regTest);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RegistroActividadService#altaRegActividadError(java.lang.String, java.lang.Exception)}
     * .
     */
    @Test
    public final void testAltaRegActividadError() {
        Exception exception = new Exception("exception_test");
        registroActividadServiceMock.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), exception);
        verify(regActividadRepository, times(1)).save(any(RegistroActividad.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RegistroActividadService#altaRegActividadError(java.lang.String, java.lang.Exception)}
     * .
     */
    @Test
    @Ignore
    public final void testAltaRegActividadErrorException() {
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RegistroActividadService#altaRegActividad(java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public final void testAltaRegActividad() {
        registroActividadServiceMock.altaRegActividad("descripcion_test", TipoRegistroEnum.ALTA.name(),
                SeccionesEnum.INSPECCION.getDescripcion());
        verify(regActividadRepository, times(1)).save(any(RegistroActividad.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RegistroActividadService#altaRegActividad(java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testAltaRegActividadException() {
        DataAccessException exception1 = new DataAccessException("DataAccesException_test") {
            private static final long serialVersionUID = 1L;
        };
        when(regActividadRepository.save(any(RegistroActividad.class))).thenThrow(exception1);
        
        registroActividadServiceMock.altaRegActividad("descripcion_test", TipoRegistroEnum.ALTA.name(),
                SeccionesEnum.INSPECCION.name());
        // verify(regActividadRepository, times(1)).save(any(RegistroActividad.class));
        // verify(log, times(1)).error(any(String.class), any(Exception.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RegistroActividadService#buscarPorNombreSeccion(java.lang.String)}.
     */
    @Test
    public final void testBuscarPorNombreSeccion() {
        String nombreSeccion = "nombre_test";
        registroActividadServiceMock.buscarPorNombreSeccion(nombreSeccion);
        verify(regActividadRepository, times(1)).buscarPorNombreSeccion("%" + nombreSeccion + "%");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RegistroActividadService#buscarPorUsuarioRegistro(java.lang.String)}.
     */
    @Test
    public final void testBuscarPorUsuarioRegistro() {
        String userRegistro = "usuario_registro";
        registroActividadServiceMock.buscarPorUsuarioRegistro(userRegistro);
        verify(regActividadRepository, times(1)).buscarPorUsuarioRegistro("%" + userRegistro + "%");
    }
    
}
