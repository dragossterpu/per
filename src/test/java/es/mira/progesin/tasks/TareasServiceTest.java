/**
 * 
 */
package es.mira.progesin.tasks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * Test para el servicio de Tareas.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, Duration.class, ChronoUnit.class })
public class TareasServiceTest {
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
     * Servicio de cuestionarios enviados.
     */
    @Mock
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Servicio de solicitud de documentación.
     */
    @Mock
    private ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Servicio de documentos.
     */
    @Mock
    private IDocumentoService documentoService;
    
    /**
     * Envío de correos electrónicos.
     */
    @Mock
    private ICorreoElectronico correoElectronico;
    
    /**
     * Servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService registroActividad;
    
    /**
     * Bean de configuración de la aplciación.
     */
    @Mock
    private ApplicationBean applicationBean;
    
    /**
     * Bean de configuración de la aplciación.
     */
    @Mock
    private Properties tareasProperties;
    
    /**
     * Simulación del servicio de tareas.
     */
    @InjectMocks
    private TareasService tareasService = new TareasService();
    
    /**
     * Mock ChronoUnit.
     */
    @Mock
    private ChronoUnit chronoUnit;
    
    /**
     * Configuración inicial del test.
     */
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        PowerMockito.mockStatic(Duration.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioLogueado");
        // when(ChronoUnit.DAYS).thenReturn(chronoUnit);
    }
    
    /**
     * Test method for {@link es.mira.progesin.tasks.TareasService#init()}.
     */
    @Test
    public final void testInit() {
        Map<String, Map<String, String>> mapaParametros = new HashMap<>();
        Map<String, String> parametrosTareas = new HashMap<>();
        parametrosTareas.put("key1", "aaa");
        parametrosTareas.put("key2", "bbb");
        mapaParametros.put("tareas", parametrosTareas);
        when(applicationBean.getMapaParametros()).thenReturn(mapaParametros);
        
        tareasService.init();
        
        verify(tareasProperties, times(1)).put("key1", "aaa");
        verify(tareasProperties, times(1)).put("key2", "bbb");
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.tasks.TareasService#recordatorioEnvioCuestionario()}.
     */
    @Test
    public final void testRecordatorioEnvioCuestionario() {
        List<CuestionarioEnvio> lista = new ArrayList<>();
        CuestionarioEnvio cuestionario = new CuestionarioEnvio();
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        
        LocalDate localDate = LocalDate.of(2025, 10, 10);
        Date fechaLimite = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        cuestionario.setFechaLimiteCuestionario(fechaLimite);
        cuestionario.setInspeccion(inspeccion);
        cuestionario.setCorreoEnvio("correoTest");
        lista.add(cuestionario);
        when(cuestionarioEnvioService.findNoCumplimentados()).thenReturn(lista);
        when(tareasProperties.getProperty("plazoDiasCuestionario")).thenReturn("66");
        
        tareasService.recordatorioEnvioCuestionario();
    }
    
    /**
     * Test method for {@link es.mira.progesin.tasks.TareasService#recordatorioEnvioDocumentacion()}.
     */
    @Ignore
    @Test
    public final void testRecordatorioEnvioDocumentacion() {
    }
    
    /**
     * Test method for {@link es.mira.progesin.tasks.TareasService#limpiarPapelera()}.
     */
    @Test
    public final void testLimpiarPapeleraMas90Dias() {
        LocalDate localDate = LocalDate.of(2013, 1, 15);
        Date fechaBaja = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Documento doc = new Documento();
        doc.setFechaBaja(fechaBaja);
        List<Documento> documentos = new ArrayList<>();
        documentos.add(doc);
        
        when(documentoService.findByFechaBajaIsNotNull()).thenReturn(documentos);
        Instant fixedInstant = Instant.parse("2014-12-03T10:15:30.00Z");
        Clock clock = Clock.fixed(fixedInstant, ZoneId.systemDefault());
        tareasService.setClock(clock);
        
        tareasService.limpiarPapelera();
        verify(documentoService, times(1)).delete(doc);
        verify(registroActividad, never()).altaRegActividadError(eq(SeccionesEnum.ALERTAS.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.tasks.TareasService#limpiarPapelera()}.
     */
    @Test
    public final void testLimpiarPapeleraMasException() {
        LocalDate localDate = LocalDate.of(2013, 1, 15);
        Date fechaBaja = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Documento doc = new Documento();
        doc.setFechaBaja(fechaBaja);
        List<Documento> documentos = new ArrayList<>();
        documentos.add(doc);
        
        when(documentoService.findByFechaBajaIsNotNull()).thenReturn(documentos);
        doThrow(TransientDataAccessResourceException.class).when(documentoService).delete(doc);
        Instant fixedInstant = Instant.parse("2014-12-03T10:15:30.00Z");
        Clock clock = Clock.fixed(fixedInstant, ZoneId.systemDefault());
        tareasService.setClock(clock);
        
        tareasService.limpiarPapelera();
        verify(documentoService, times(1)).delete(doc);
        verify(registroActividad, times(1)).altaRegActividadError(eq(SeccionesEnum.ALERTAS.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
}
