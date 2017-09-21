package es.mira.progesin.web.beans.solicitudes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.PdfGeneratorSolicitudes;
import es.mira.progesin.util.Utilities;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * Test del bean solicitudDocPrevia parte 2.
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
     * Mock del servicio de notificaciones.
     */
    @Mock
    private INotificacionService notificacionService;
    
    /**
     * Mock del servicio de solicitudes de documentación.
     */
    @Mock
    private ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Mock del servicio de inspecciones.
     */
    @Mock
    private IInspeccionesService inspeccionesService;
    
    /**
     * Mock del servicio de usuarios.
     */
    @Mock
    private IUserService userService;
    
    /**
     * Mock del cifrador de contraseñas.
     */
    @Mock
    private PasswordEncoder passwordEncoder;
    
    /**
     * Mock del servicio de correos electrónicos.
     */
    @Mock
    private ICorreoElectronico correoElectronico;
    
    /**
     * Mock del bean de aplicación.
     */
    @Mock
    private ApplicationBean applicationBean;
    
    /**
     * Mock del generador de pdfs.
     */
    @Mock
    private PdfGeneratorSolicitudes pdfGenerator;
    
    /**
     * Instancia de prueba del bean de solicitudes de documentación.
     */
    @InjectMocks
    private SolicitudDocPreviaBean solicitudDocPreviaBean;
    
    /**
     * Captura de parametros plantilla.
     */
    @Captor
    private ArgumentCaptor<Map<String, String>> paramCaptor;
    
    /**
     * Mock del servicio de documentos.
     */
    @Mock
    private IDocumentoService documentoService;
    
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
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Utilities.class);
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
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
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#modificarSolicitud()}.
     * @throws ParseException error al generar la fecha en el test
     */
    @Test
    public void modificarSolicitud_SinFechaLimiteCambiada() throws ParseException {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .fechaEnvio(sdf.parse("1/1/2017")).fechaLimiteEnvio(sdf.parse("10/1/2017")).correoDestinatario(CORREO)
                .build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        solicitudDocPreviaBean.setBackupFechaLimiteEnvio(sdf.parse("10/1/2017"));
        
        solicitudDocPreviaBean.modificarSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).save(solicitud);
        verify(correoElectronico, times(0)).envioCorreo(eq(CORREO), any(String.class), any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#modificarSolicitud()}.
     * @throws ParseException fecha
     */
    @Test
    public void modificarSolicitud_ConFechaLimiteCambiada() throws ParseException {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        TipoUnidad tipoUnidad = new TipoUnidad();
        tipoUnidad.setDescripcion(TIPOUNIDAD);
        inspeccion.setTipoUnidad(tipoUnidad);
        TipoInspeccion tipoInspeccion = new TipoInspeccion();
        tipoInspeccion.setDescripcion("tipoInspeccion");
        inspeccion.setNombreUnidad(NOMBREUNIDAD);
        Provincia provincia = new Provincia();
        provincia.setNombre(PROVINCIA);
        Municipio municipio = new Municipio();
        municipio.setName(PROVINCIA);
        municipio.setProvincia(provincia);
        inspeccion.setMunicipio(municipio);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .fechaEnvio(sdf.parse("1/1/2017")).fechaLimiteEnvio(sdf.parse("11/1/2017")).correoDestinatario(CORREO)
                .build();
        
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        solicitudDocPreviaBean.setBackupFechaLimiteEnvio(sdf.parse("10/1/2017"));
        
        solicitudDocPreviaBean.modificarSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).save(solicitud);
        verify(correoElectronico, times(1)).envioCorreo(eq(CORREO), any(String.class),
                eq(Constantes.TEMPLATEMODIFICACIONFECHASOLICITUD), paramCaptor.capture());
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#enviarSolicitud()}.
     */
    @Test
    public void enviarSolicitud_validacionUsuarioProvExiste() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .correoDestinatario(CORREO).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        when(userService.exists(CORREO)).thenReturn(Boolean.TRUE);
        
        solicitudDocPreviaBean.enviarSolicitud();
        
        verify(userService, times(1)).exists(CORREO);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq("Envío abortado"),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#enviarSolicitud()}.
     */
    @Test
    public void enviarSolicitud() {
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.GC;
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).ambito(ambito).build();
        
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .correoDestinatario(CORREO).asunto("asunto").build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        when(userService.exists(CORREO)).thenReturn(Boolean.FALSE);
        when(Utilities.getPassword()).thenCallRealMethod();
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        
        solicitudDocPreviaBean.enviarSolicitud();
        
        verify(userService, times(1)).exists(CORREO);
        verify(solicitudDocumentacionService, times(1)).transaccSaveCreaUsuarioProv(eq(solicitud), any(User.class),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
        verify(notificacionService, times(1)).crearNotificacionRol(any(String.class),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()), eq(RoleEnum.ROLE_SERVICIO_APOYO));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()), eq(solicitud.getInspeccion().getEquipo()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#finalizarSolicitud()}.
     */
    @Test
    public void finalizarSolicitud() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .correoDestinatario(CORREO).asunto("asunto").build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        
        solicitudDocPreviaBean.finalizarSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveElimUsuarioProv(eq(solicitud), eq(CORREO));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
        verify(notificacionService, times(1)).crearNotificacionRol(any(String.class),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()), eq(RoleEnum.ROLE_SERVICIO_APOYO));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()), eq(solicitud.getInspeccion().getEquipo()));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#noConformeSolicitud(String)}.
     */
    @Test
    public void noConformeSolicitud() {
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.GC;
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).ambito(ambito).build();
        TipoUnidad tipoUnidad = new TipoUnidad();
        tipoUnidad.setDescripcion(TIPOUNIDAD);
        inspeccion.setTipoUnidad(tipoUnidad);
        TipoInspeccion tipoInspeccion = new TipoInspeccion();
        tipoInspeccion.setDescripcion("tipoInspeccion");
        inspeccion.setNombreUnidad(NOMBREUNIDAD);
        Provincia provincia = new Provincia();
        provincia.setNombre(PROVINCIA);
        Municipio municipio = new Municipio();
        municipio.setName(PROVINCIA);
        municipio.setProvincia(provincia);
        inspeccion.setMunicipio(municipio);
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .correoDestinatario(CORREO).asunto("asunto").build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        @SuppressWarnings("unchecked")
        Map<String, Map<String, String>> mapa = mock(Map.class);
        when(applicationBean.getMapaParametros()).thenReturn(mapa);
        @SuppressWarnings("unchecked")
        Map<String, String> submapa = mock(Map.class);
        when(mapa.get("URLPROGESIN")).thenReturn(submapa);
        when(submapa.get(ambito.name())).thenReturn("url");
        String motivosNoConforme = "faltan datos";
        
        solicitudDocPreviaBean.noConformeSolicitud(motivosNoConforme);
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveActivaUsuarioProv(eq(solicitud), eq(CORREO));
        verify(correoElectronico, times(1)).envioCorreo(eq(CORREO), any(String.class),
                eq(Constantes.TEMPLATENOCONFORMESOLICITUD), paramCaptor.capture());
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#autocompletarInspeccion(String)}.
     */
    @Test
    public void autocompletarInspeccion() {
        String infoInspeccion = "1/2017";
        
        solicitudDocPreviaBean.autocompletarInspeccion(infoInspeccion);
        
        verify(inspeccionesService, times(1)).buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBean#imprimirPdf()}.
     * @throws ProgesinException al generar el archivo
     */
    @Test
    public void imprimirPdf() throws ProgesinException {
        SolicitudDocumentacionPrevia solicitud = mock(SolicitudDocumentacionPrevia.class);
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        List<DocumentacionPrevia> listDoc = new ArrayList<>();
        solicitudDocPreviaBean.setListadoDocumentosPrevios(listDoc);
        
        solicitudDocPreviaBean.imprimirPdf();
        
        verify(pdfGenerator, times(1)).exportarPdf();
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
    
}
