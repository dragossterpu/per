package es.mira.progesin.web.beans.cuestionarios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * 
 * Test del bean EnvioCuestionarioBean parte 2.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class CuestionarioEnviadoBean2Test {
    
    /**
     * Constante user.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
    /**
     * Constante tipo unidad.
     */
    private static final String TIPOUNIDAD = "tipoUnidad";
    
    /**
     * Constante correo envio.
     */
    private static final String CORREOENVIO = "correo_test";
    
    /**
     * Constante Provincia/Ciudad.
     */
    private static final String PROVINCIA = "Toledo";
    
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
     * Mock Bean para mostrar formulario del cuestionario.
     */
    @Mock
    private VisualizarCuestionario visualizarCuestionario;
    
    /**
     * Mock Servicio de cuestionarios enviados.
     */
    @Mock
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Mock Bean para el envio de cuestionarios.
     */
    @Mock
    private EnvioCuestionarioBean envioCuestionarioBean;
    
    /**
     * Mock Servicio de correos electrónicos.
     */
    @Mock
    private ICorreoElectronico correoElectronico;
    
    /**
     * Mock Servicio del registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Mock Servicio de notificaciones.
     */
    @Mock
    private INotificacionService notificacionService;
    
    /**
     * Mock Bean de datos comunes de la aplicación.
     */
    @Mock
    private ApplicationBean applicationBean;
    
    /**
     * Simulación de CuestionarioEnviadoBean.
     */
    @InjectMocks
    private CuestionarioEnviadoBean cuestionarioEnviadoBeanMock;
    
    /**
     * Inicializa el test.
     */
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USUARIOLOGUEADO);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#getFormModificarCuestionario(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio)}.
     */
    @Test
    public final void testGetFormModificarCuestionario() {
        cuestionarioEnviadoBeanMock.setEnvioCuestionarioBean(new EnvioCuestionarioBean());
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setFechaLimiteCuestionario(new Date(3L));
        
        String redireccion = cuestionarioEnviadoBeanMock.getFormModificarCuestionario(cuestionarioEnviado);
        assertThat(cuestionarioEnviadoBeanMock.getEnvioCuestionarioBean().getCuestionarioEnvio())
                .isEqualTo(cuestionarioEnviado);
        assertThat(cuestionarioEnviadoBeanMock.getBackupFechaLimiteCuestionario())
                .isEqualTo(cuestionarioEnviado.getFechaLimiteCuestionario());
        assertThat(redireccion).isEqualTo("/cuestionarios/enviarCuestionario?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#modificarCuestionario()}.
     */
    @Test
    public final void testModificarCuestionarioEnviado() {
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        TipoUnidad tipoUnidad = new TipoUnidad();
        tipoUnidad.setDescripcion(TIPOUNIDAD);
        inspeccion.setTipoUnidad(tipoUnidad);
        Provincia provincia = new Provincia();
        provincia.setNombre(PROVINCIA);
        Municipio municipio = new Municipio();
        municipio.setName(PROVINCIA);
        municipio.setProvincia(provincia);
        inspeccion.setMunicipio(municipio);
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setFechaEnvio(new Date(1L));
        cuestionarioEnviado.setFechaLimiteCuestionario(new Date(3L));
        cuestionarioEnviado.setInspeccion(inspeccion);
        cuestionarioEnviado.setCorreoEnvio(CORREOENVIO);
        cuestionarioEnviadoBeanMock.setBackupFechaLimiteCuestionario(new Date(86400403L));
        when(envioCuestionarioBean.getCuestionarioEnvio()).thenReturn(cuestionarioEnviado);
        
        cuestionarioEnviadoBeanMock.modificarCuestionario();
        
        verify(cuestionarioEnvioService, times(1)).save(cuestionarioEnviado);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#modificarCuestionario()}.
     */
    @Test
    public final void testModificarCuestionarioExceptionData() {
        CuestionarioEnvio cuestionarioEnviado = mock(CuestionarioEnvio.class);
        when(envioCuestionarioBean.getCuestionarioEnvio()).thenReturn(cuestionarioEnviado);
        doThrow(TransientDataAccessResourceException.class).when(cuestionarioEnvioService).save(cuestionarioEnviado);
        
        cuestionarioEnviadoBeanMock.modificarCuestionario();
        
        verify(cuestionarioEnvioService, times(1)).save(cuestionarioEnviado);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#modificarCuestionario()}.
     */
    @Test
    public final void testModificarCuestionarioExceptionCorreo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        TipoUnidad tipoUnidad = new TipoUnidad();
        tipoUnidad.setDescripcion(TIPOUNIDAD);
        inspeccion.setTipoUnidad(tipoUnidad);
        Provincia provincia = new Provincia();
        provincia.setNombre(PROVINCIA);
        Municipio municipio = new Municipio();
        municipio.setName(PROVINCIA);
        municipio.setProvincia(provincia);
        inspeccion.setMunicipio(municipio);
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setFechaEnvio(new Date(1L));
        cuestionarioEnviado.setFechaLimiteCuestionario(new Date(3L));
        cuestionarioEnviado.setInspeccion(inspeccion);
        cuestionarioEnviado.setCorreoEnvio(CORREOENVIO);
        cuestionarioEnviadoBeanMock.setBackupFechaLimiteCuestionario(new Date(86400403L));
        when(envioCuestionarioBean.getCuestionarioEnvio()).thenReturn(cuestionarioEnviado);
        Map<String, String> paramPlantilla = new HashMap<>();
        paramPlantilla.put("fechaAnterior", sdf.format(cuestionarioEnviadoBeanMock.getBackupFechaLimiteCuestionario()));
        paramPlantilla.put("fechaActual", sdf.format(cuestionarioEnviado.getFechaLimiteCuestionario()));
        doThrow(CorreoException.class).when(correoElectronico).envioCorreo(eq(cuestionarioEnviado.getCorreoEnvio()),
                any(String.class), eq(Constantes.TEMPLATEMODIFICACIONFECHACUESTIONARIO), eq(paramPlantilla));
        
        cuestionarioEnviadoBeanMock.modificarCuestionario();
        
        verify(cuestionarioEnvioService, times(1)).save(cuestionarioEnviado);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                eq(Constantes.FALLOCORREO));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(CorreoException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#onToggle(org.primefaces.event.ToggleEvent)}.
     */
    @Test
    public final void testOnToggle() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        when(eventMock.getVisibility()).thenReturn(Visibility.HIDDEN);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        cuestionarioEnviadoBeanMock.setList(listaToogle);
        cuestionarioEnviadoBeanMock.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#noConformeCuestionario(java.lang.String)}.
     */
    @Test
    public final void testNoConformeCuestionario() {
        String motivo = "no_coforme";
        
        Equipo equipo = mock(Equipo.class);
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setEquipo(equipo);
        inspeccion.setAmbito(AmbitoInspeccionEnum.GC);
        TipoUnidad tipoUnidad = new TipoUnidad();
        tipoUnidad.setDescripcion(TIPOUNIDAD);
        inspeccion.setTipoUnidad(tipoUnidad);
        Provincia provincia = new Provincia();
        provincia.setNombre(PROVINCIA);
        Municipio municipio = new Municipio();
        municipio.setName(PROVINCIA);
        municipio.setProvincia(provincia);
        inspeccion.setMunicipio(municipio);
        
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setInspeccion(inspeccion);
        cuestionarioEnviado.setCorreoEnvio(CORREOENVIO);
        cuestionarioEnviado.setFechaCumplimentacion(new Date());
        
        when(visualizarCuestionario.getCuestionarioEnviado()).thenReturn(cuestionarioEnviado);
        Map<String, Map<String, String>> mapaParametros = new HashMap<>();
        mapaParametros.put("URLPROGESIN", new HashMap<>());
        when(applicationBean.getMapaParametros()).thenReturn(mapaParametros);
        Map<String, String> paramPlantilla = new HashMap<>();
        paramPlantilla.put("textoNoValidacion", motivo);
        
        cuestionarioEnviadoBeanMock.noConformeCuestionario(motivo);
        verify(cuestionarioEnvioService, times(1)).transaccSaveActivaUsuariosProv(cuestionarioEnviado);
        verify(correoElectronico, times(1)).envioCorreo(eq(cuestionarioEnviado.getCorreoEnvio()), any(String.class),
                eq(Constantes.TEMPLATENOCONFORMECUESTIONARIO), eq(paramPlantilla));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
        verify(notificacionService, times(1)).crearNotificacionRol(any(String.class),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), eq(RoleEnum.ROLE_SERVICIO_APOYO));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), eq(cuestionarioEnviado.getInspeccion().getEquipo()));
        assertThat(cuestionarioEnviado.getFechaCumplimentacion()).isNull();
        assertThat(cuestionarioEnviado.getFechaNoConforme()).isNotNull();
        assertThat(cuestionarioEnviado.getUsernameNoConforme()).isNotNull();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#noConformeCuestionario(java.lang.String)}.
     */
    @Test
    public final void testNoConformeCuestionarioExceptionData() {
        String motivo = "no_coforme";
        CuestionarioEnvio cuestionarioEnviado = mock(CuestionarioEnvio.class);
        when(visualizarCuestionario.getCuestionarioEnviado()).thenReturn(cuestionarioEnviado);
        doThrow(TransientDataAccessResourceException.class).when(cuestionarioEnvioService)
                .transaccSaveActivaUsuariosProv(cuestionarioEnviado);
        
        cuestionarioEnviadoBeanMock.noConformeCuestionario(motivo);
        
        verify(cuestionarioEnvioService, times(1)).transaccSaveActivaUsuariosProv(cuestionarioEnviado);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#noConformeCuestionario(java.lang.String)}.
     */
    @Test
    public final void testNoConformeCuestionarioExceptionCorreo() {
        String motivo = "no_coforme";
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setAmbito(AmbitoInspeccionEnum.GC);
        TipoUnidad tipoUnidad = new TipoUnidad();
        tipoUnidad.setDescripcion(TIPOUNIDAD);
        inspeccion.setTipoUnidad(tipoUnidad);
        Provincia provincia = new Provincia();
        provincia.setNombre(PROVINCIA);
        Municipio municipio = new Municipio();
        municipio.setName(PROVINCIA);
        municipio.setProvincia(provincia);
        inspeccion.setMunicipio(municipio);
        
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setInspeccion(inspeccion);
        cuestionarioEnviado.setCorreoEnvio(CORREOENVIO);
        cuestionarioEnviado.setFechaCumplimentacion(new Date());
        
        when(visualizarCuestionario.getCuestionarioEnviado()).thenReturn(cuestionarioEnviado);
        Map<String, Map<String, String>> mapaParametros = new HashMap<>();
        mapaParametros.put("URLPROGESIN", new HashMap<>());
        when(applicationBean.getMapaParametros()).thenReturn(mapaParametros);
        
        Map<String, String> paramPlantilla = new HashMap<>();
        paramPlantilla.put("textoNoValidacion", motivo);
        doThrow(CorreoException.class).when(correoElectronico).envioCorreo(eq(cuestionarioEnviado.getCorreoEnvio()),
                any(String.class), eq(Constantes.TEMPLATENOCONFORMECUESTIONARIO), eq(paramPlantilla));
        
        cuestionarioEnviadoBeanMock.noConformeCuestionario(motivo);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                eq(Constantes.FALLOCORREO));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(CorreoException.class));
        
    }
    
}
