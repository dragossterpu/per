package es.mira.progesin.web.beans.solicitudes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;

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
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.Utilities;

/**
 * Test del bean solicitudDocPrevia.
 * 
 * @author EZENTIS
 */
@RunWith(PowerMockRunner.class)
// Evita conflictos con clases del sistema al enlazar los mocks por tipo
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ Utilities.class, FacesUtilities.class, SecurityContextHolder.class })
public class SolicitudDocPreviaBeanTest {
    
    /**
     * Mock de security context.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Mock del objeto authentication.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Mock del servicio del registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Mock del servicio de alertas.
     */
    @Mock
    private IAlertaService alertaService;
    
    /**
     * Mock del servicio de solicitudes de documentación.
     */
    @Mock
    private ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Mock del servicio de documentos.
     */
    @Mock
    private IDocumentoService documentoService;
    
    /**
     * Mock para el servicio de correos electrónicos.
     */
    @Mock
    private ICorreoElectronico correoElectronico;
    
    /**
     * Instancia de prueba del bean de solicitudes de documentación.
     */
    @InjectMocks
    private SolicitudDocPreviaBean solicitudDocPreviaBean;
    
    /**
     * Captura de parametros solicitud.
     */
    @Captor
    private ArgumentCaptor<SolicitudDocumentacionPrevia> solicitudCaptor;
    
    /**
     * Literal para pruebas.
     */
    private static final String CORREO = "correoDestinatario";
    
    /**
     * Literal provincia/municipio.
     */
    private static final String PROVINCIA = "Toledo";
    
    /**
     * Literal Tipo de Unidad.
     */
    private static final String TIPOUNIDAD = "tipoUnidad";
    
    /**
     * Literal Tipo de Unidad.
     */
    private static final String NOMBREUNIDAD = "nombreUnidad";
    
    /**
     * Captura de parametros plantilla.
     */
    @Captor
    private ArgumentCaptor<Map<String, String>> paramCaptor;
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Utilities.class);
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        //
        when(Utilities.getFechaFormateada(any(Date.class), eq("dd/MM/yyyy"))).thenCallRealMethod();
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioLogueado");
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(SolicitudDocPreviaBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        SolicitudDocPreviaBean target = new SolicitudDocPreviaBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#validacionApoyo()}.
     */
    @Test
    public void validacionApoyo() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        
        solicitudDocPreviaBean.validacionApoyo();
        
        verify(solicitudDocumentacionService, times(1)).save(solicitudCaptor.capture());
        assertThat(solicitudCaptor.getValue().getFechaValidApoyo()).isNotNull();
        assertThat(solicitudCaptor.getValue().getUsernameValidApoyo()).isNotNull();
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
        verify(alertaService, times(1)).crearAlertaJefeEquipo(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(String.class), eq(inspeccion));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#validacionApoyo()}.
     */
    @Test
    public void validacionApoyo_Excepcion() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        when(solicitudDocumentacionService.save(solicitudCaptor.capture()))
                .thenThrow(TransientDataAccessResourceException.class);
        
        solicitudDocPreviaBean.validacionApoyo();
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#validacionJefeEquipo()}.
     */
    @Test
    public void validacionJefeEquipo() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        
        solicitudDocPreviaBean.validacionJefeEquipo();
        
        verify(solicitudDocumentacionService, times(1)).save(solicitudCaptor.capture());
        assertThat(solicitudCaptor.getValue().getFechaValidJefeEquipo()).isNotNull();
        assertThat(solicitudCaptor.getValue().getUsernameValidJefeEquipo()).isNotNull();
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
        verify(alertaService, times(1)).crearAlertaRol(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(String.class), eq(RoleEnum.ROLE_JEFE_INSPECCIONES));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#validacionJefeEquipo()}.
     */
    @Test
    public void validacionJefeEquipo_Excepcion() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        when(solicitudDocumentacionService.save(solicitudCaptor.capture()))
                .thenThrow(TransientDataAccessResourceException.class);
        
        solicitudDocPreviaBean.validacionJefeEquipo();
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#descargarFichero(Long)}.
     * @throws ProgesinException lanza excepción
     */
    @Test
    public void descargarFichero_ficheroNulo() throws ProgesinException {
        when(documentoService.descargaDocumento(1L)).thenThrow(ProgesinException.class);
        
        solicitudDocPreviaBean.descargarFichero(1L);
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(ProgesinException.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#descargarFichero(Long)}.
     * @throws ProgesinException lanza excepción
     */
    @Test
    public void descargarFichero_ficheroNoNulo() throws ProgesinException {
        solicitudDocPreviaBean.descargarFichero(1L);
        
        verify(documentoService, times(1)).descargaDocumento(1L);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#eliminarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void eliminarSolicitud_validacionConFechaBaja() {
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().fechaBaja(new Date()).build();
        when(authentication.getPrincipal()).thenReturn(User.builder().role(RoleEnum.ROLE_JEFE_INSPECCIONES).build());
        
        solicitudDocPreviaBean.eliminarSolicitud(solicitud);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_WARN), eq("Eliminación abortada"),
                any(String.class), eq(null));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#eliminarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void eliminarSolicitud_validacionRoleNoPermitido() {
        SolicitudDocumentacionPrevia solicitud = mock(SolicitudDocumentacionPrevia.class);
        when(authentication.getPrincipal()).thenReturn(User.builder().role(RoleEnum.ROLE_GABINETE).build());
        
        solicitudDocPreviaBean.eliminarSolicitud(solicitud);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_WARN), eq("Eliminación abortada"),
                any(String.class), eq(null));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#eliminarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void eliminarSolicitud_SinFechaEnvio_Fisica() {
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).fechaEnvio(null).build();
        when(authentication.getPrincipal())
                .thenReturn(User.builder().username("username").role(RoleEnum.ROLE_ADMIN).build());
        
        solicitudDocPreviaBean.eliminarSolicitud(solicitud);
        
        verify(solicitudDocumentacionService, times(1)).transaccDeleteElimDocPrevia(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#eliminarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void eliminarSolicitud_ConFechaEnvio_Logica() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        TipoUnidad tipoUnidad = new TipoUnidad();
        tipoUnidad.setDescripcion(TIPOUNIDAD);
        inspeccion.setTipoUnidad(tipoUnidad);
        inspeccion.setNombreUnidad(NOMBREUNIDAD);
        Provincia provincia = new Provincia();
        provincia.setNombre(PROVINCIA);
        Municipio municipio = new Municipio();
        municipio.setName(PROVINCIA);
        municipio.setProvincia(provincia);
        inspeccion.setMunicipio(municipio);
        
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .fechaEnvio(new Date()).correoDestinatario(CORREO).build();
        when(authentication.getPrincipal()).thenReturn(User.builder().role(RoleEnum.ROLE_JEFE_INSPECCIONES).build());
        
        solicitudDocPreviaBean.eliminarSolicitud(solicitud);
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveElimUsuarioProv(eq(solicitud),
                eq(solicitud.getCorreoDestinatario()));
        
        verify(correoElectronico, times(1)).envioCorreo(eq(solicitud.getCorreoDestinatario()), any(String.class),
                eq(Constantes.TEMPLATEBAJASOLICITUD), paramCaptor.capture());
        
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
    }
    
}
