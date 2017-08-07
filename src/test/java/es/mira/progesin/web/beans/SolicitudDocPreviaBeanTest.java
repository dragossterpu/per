package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

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
import org.primefaces.event.FlowEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelSolicitudes;
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.PdfGenerator;

/**
 * Test del bean solicitudDocPrevia.
 * 
 * @author EZENTIS
 */
@RunWith(PowerMockRunner.class)
// Evita conflictos con clases del sistema al enlazar los mocks por tipo
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
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
     * Mock del servicio de tipos de documentación.
     */
    @Mock
    private ITipoDocumentacionService tipoDocumentacionService;
    
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
     * Mock del servicio de documentos.
     */
    @Mock
    private IDocumentoService documentoService;
    
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
    private PdfGenerator pdfGenerator;
    
    /**
     * Mock del servicio de cuestionarios enviados.
     */
    @Mock
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Mock del servicio de tipos de inspección.
     */
    @Mock
    private ITipoInspeccionService tipoInspeccionService;
    
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
     * Mock LazyModel para la visualización de datos paginados en la vista.
     */
    @Mock
    private LazyModelSolicitudes model;
    
    /**
     * Literal para pruebas.
     */
    private static final String CORREO = "correoDestinatario";
    
    /**
     * Literal para pruebas.
     */
    private static final String DOCUMENTACION = "documentacion";
    
    /**
     * Literal para pruebas.
     */
    private static final String APOYO = "apoyo";
    
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
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).correoDestinatario(CORREO).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        solicitudDocPreviaBean.setDocumentosSeleccionados(documentosSeleccionados);
        
        solicitudDocPreviaBean.crearSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                documentosSeleccionados);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud_validacionInspeccionConTareasPendientes() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).correoDestinatario(CORREO).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        solicitudDocPreviaBean.setDocumentosSeleccionados(documentosSeleccionados);
        when(solicitudDocumentacionService.findNoFinalizadaPorInspeccion(inspeccion))
                .thenReturn(mock(SolicitudDocumentacionPrevia.class));
        
        solicitudDocPreviaBean.crearSolicitud();
        
        verify(solicitudDocumentacionService, times(0)).transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                documentosSeleccionados);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud_validacionUsuarioConTareasPendientes() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).correoDestinatario(CORREO).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        solicitudDocPreviaBean.setDocumentosSeleccionados(documentosSeleccionados);
        Inspeccion inspeccion2 = mock(Inspeccion.class);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(CORREO))
                .thenReturn(CuestionarioEnvio.builder().inspeccion(inspeccion2).build());
        
        solicitudDocPreviaBean.crearSolicitud();
        
        verify(solicitudDocumentacionService, times(0)).transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                documentosSeleccionados);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud_Excepcion() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).correoDestinatario(CORREO).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        solicitudDocPreviaBean.setDocumentosSeleccionados(documentosSeleccionados);
        doThrow(TransientDataAccessResourceException.class).when(solicitudDocumentacionService)
                .transaccSaveAltaDocumentos(solicitudDocumentacionPrevia, documentosSeleccionados);
        
        solicitudDocPreviaBean.crearSolicitud();
        
        verify(regActividadService, times(0)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#getFormModificarSolicitud(SolicitudDocumentacionPrevia)}
     * .
     */
    @Test
    public void getFormModificarSolicitud_existe() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        when(solicitudDocumentacionService.findOne(1L)).thenReturn(solicitud);
        
        String ruta_vista = solicitudDocPreviaBean.getFormModificarSolicitud(solicitud);
        
        assertThat(ruta_vista).isEqualTo("/solicitudesPrevia/modificarSolicitud?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#getFormModificarSolicitud(SolicitudDocumentacionPrevia)}
     * .
     */
    @Test
    public void getFormModificarSolicitud_noExiste() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        when(solicitudDocumentacionService.findOne(1L)).thenReturn(null);
        
        String ruta_vista = solicitudDocPreviaBean.getFormModificarSolicitud(solicitud);
        
        assertThat(ruta_vista).isNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#visualizarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void visualizarSolicitud_existe() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        when(solicitudDocumentacionService.findByIdConDocumentos(solicitud.getId())).thenReturn(solicitud);
        when(solicitudDocumentacionService.findOne(1L)).thenReturn(solicitud);
        
        String ruta_vista = solicitudDocPreviaBean.visualizarSolicitud(solicitud);
        assertThat(solicitudDocPreviaBean.getSolicitudDocumentacionPrevia().getInspeccion().getNumero())
                .isEqualTo("1/2017");
        verify(solicitudDocumentacionService, times(1)).findByIdConDocumentos(1L);
        verify(tipoDocumentacionService, times(1)).findByIdSolicitud(1L);
        assertThat(ruta_vista).isEqualTo("/solicitudesPrevia/vistaSolicitud?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#visualizarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void visualizarSolicitud_noExiste() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        when(solicitudDocumentacionService.findByIdConDocumentos(solicitud.getId())).thenReturn(solicitud);
        when(solicitudDocumentacionService.findOne(1L)).thenReturn(null);
        
        String ruta_vista = solicitudDocPreviaBean.visualizarSolicitud(solicitud);
        
        assertThat(ruta_vista).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#validacionApoyo()}.
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
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#validacionApoyo()}.
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
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#validacionJefeEquipo()}.
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
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#validacionJefeEquipo()}.
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
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#getFormularioCrearSolicitud()}.
     */
    
    @Test
    public void getFormularioCrearSolicitud() {
        Map<String, String> datosApoyo = new HashMap<>();
        datosApoyo.put("ApoyoCorreo", "apoyoCorreo");
        datosApoyo.put("ApoyoNombre", "apoyoNombre");
        datosApoyo.put("ApoyoPuesto", "apoyoPuesto");
        datosApoyo.put("ApoyoTelefono", "apoyoTelefono");
        solicitudDocPreviaBean.setDatosApoyo(datosApoyo);
        
        solicitudDocPreviaBean.getFormularioCrearSolicitud();
        assertThat(solicitudDocPreviaBean.getSolicitudDocumentacionPrevia().getApoyoCorreo()).isEqualTo("apoyoCorreo");
        assertThat(solicitudDocPreviaBean.getSolicitudDocumentacionPrevia().getApoyoNombre()).isEqualTo("apoyoNombre");
        assertThat(solicitudDocPreviaBean.getSolicitudDocumentacionPrevia().getApoyoPuesto()).isEqualTo("apoyoPuesto");
        assertThat(solicitudDocPreviaBean.getSolicitudDocumentacionPrevia().getApoyoTelefono())
                .isEqualTo("apoyoTelefono");
        assertThat(solicitudDocPreviaBean.getDocumentosSeleccionados()).isNotNull();
        assertThat(solicitudDocPreviaBean.isSkip()).isFalse();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#init()}.
     */
    @Test
    public void init() {
        Map<String, String> datosApoyo = new HashMap<>();
        Map<String, Map<String, String>> mapaParametros = new HashMap<>();
        mapaParametros.put("datosApoyo", datosApoyo);
        when(applicationBean.getMapaParametros()).thenReturn(mapaParametros);
        when(tipoInspeccionService.buscaTodos()).thenReturn(new ArrayList<>());
        
        solicitudDocPreviaBean.init();
        
        assertThat(solicitudDocPreviaBean.getSolicitudDocPreviaBusqueda()).isNotNull();
        assertThat(solicitudDocPreviaBean.getDatosApoyo()).isNotNull();
        assertThat(solicitudDocPreviaBean.getModel()).isNotNull();
        assertThat(solicitudDocPreviaBean.getListaTiposInspeccion()).isNotNull();
        assertThat(solicitudDocPreviaBean.getListaColumnToggler()).isNotNull();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#descargarFichero(Long)}.
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
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#descargarFichero(Long)}.
     * @throws ProgesinException lanza excepción
     */
    @Test
    public void descargarFichero_ficheroNoNulo() throws ProgesinException {
        solicitudDocPreviaBean.descargarFichero(1L);
        
        verify(documentoService, times(1)).descargaDocumento(1L);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoGeneralADocumentacion() {
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.PN;
        Inspeccion inspeccion = Inspeccion.builder().ambito(ambito).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().inspeccion(inspeccion).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "general", DOCUMENTACION);
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        verify(tipoDocumentacionService, times(1)).findByAmbito(ambito);
        assertThat(nombre_paso).isEqualTo(DOCUMENTACION);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoGeneralADocumentacion_TareasPendientes() {
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.PN;
        Inspeccion inspeccion = Inspeccion.builder().ambito(ambito).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().inspeccion(inspeccion).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "general", DOCUMENTACION);
        when(solicitudDocumentacionService.findNoFinalizadaPorInspeccion(inspeccion))
                .thenReturn(mock(SolicitudDocumentacionPrevia.class));
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("general");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoDocumentacionAApoyo() {
        solicitudDocPreviaBean.setDocumentosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), DOCUMENTACION, APOYO);
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo(DOCUMENTACION);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoDocumentacionAApoyo_validacionSaltarPaso() {
        solicitudDocPreviaBean.setSkip(Boolean.TRUE);
        solicitudDocPreviaBean.setDocumentosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), DOCUMENTACION, APOYO);
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo(APOYO);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoDocumentacionAApoyo_validacionDocumentosNoSeleccionados() {
        solicitudDocPreviaBean.setSkip(Boolean.FALSE);
        solicitudDocPreviaBean.setDocumentosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), DOCUMENTACION, APOYO);
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(""));
        assertThat(nombre_paso).isEqualTo(DOCUMENTACION);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoApoyoAConfirm() {
        FlowEvent event = new FlowEvent(mock(UIComponent.class), APOYO, "confirm");
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("confirm");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onToggle(ToggleEvent)}.
     */
    @Test
    public void onToggle() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        solicitudDocPreviaBean.setListaColumnToggler(listaToogle);
        
        solicitudDocPreviaBean.onToggle(eventMock);
        
        assertThat(listaToogle.get(0)).isFalse();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#eliminarSolicitud(SolicitudDocumentacionPrevia)}.
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
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#eliminarSolicitud(SolicitudDocumentacionPrevia)}.
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
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#eliminarSolicitud(SolicitudDocumentacionPrevia)}.
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
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#eliminarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void eliminarSolicitud_ConFechaEnvio_Logica() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .fechaEnvio(new Date()).correoDestinatario(CORREO).build();
        when(authentication.getPrincipal()).thenReturn(User.builder().role(RoleEnum.ROLE_JEFE_INSPECCIONES).build());
        
        solicitudDocPreviaBean.eliminarSolicitud(solicitud);
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveElimUsuarioProv(solicitud, CORREO);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#modificarSolicitud()}.
     * @throws ParseException error al generar la fecha en el test
     */
    @Test
    public void modificarSolicitud_SinFechaLimiteCambiada() throws ParseException {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SimpleDateFormat sdf = solicitudDocPreviaBean.getSdf();
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
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#modificarSolicitud()}.
     * @throws ParseException fecha
     */
    @Test
    public void modificarSolicitud_ConFechaLimiteCambiada() throws ParseException {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SimpleDateFormat sdf = solicitudDocPreviaBean.getSdf();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .fechaEnvio(sdf.parse("1/1/2017")).fechaLimiteEnvio(sdf.parse("11/1/2017")).correoDestinatario(CORREO)
                .build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        solicitudDocPreviaBean.setBackupFechaLimiteEnvio(sdf.parse("10/1/2017"));
        
        solicitudDocPreviaBean.modificarSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).save(solicitud);
        verify(correoElectronico, times(1)).envioCorreo(eq(CORREO), any(String.class), any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#enviarSolicitud()}.
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
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#enviarSolicitud()}.
     */
    @Test
    public void enviarSolicitud() {
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.GC;
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).ambito(ambito).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .correoDestinatario(CORREO).asunto("asunto").build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        when(userService.exists(CORREO)).thenReturn(Boolean.FALSE);
        Map<String, Map<String, String>> mapa = new HashMap<>();
        Map<String, String> submapa = new HashMap<>();
        mapa.put("URLPROGESIN", submapa);
        submapa.put(ambito.name(), "url");
        when(applicationBean.getMapaParametros()).thenReturn(mapa);
        List<RoleEnum> listRoles = new ArrayList<>();
        listRoles.add(RoleEnum.ROLE_SERVICIO_APOYO);
        listRoles.add(RoleEnum.ROLE_EQUIPO_INSPECCIONES);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        solicitudDocPreviaBean.enviarSolicitud();
        
        verify(userService, times(1)).exists(CORREO);
        verify(solicitudDocumentacionService, times(1)).transaccSaveCreaUsuarioProv(eq(solicitud), any(User.class));
        verify(correoElectronico, times(1)).envioCorreo(eq(CORREO), any(String.class), contains("url"));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
        verify(notificacionService, times(1)).crearNotificacionRol(any(String.class),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()), eq(listRoles));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#finalizarSolicitud()}.
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
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#noConformeSolicitud(String)}.
     */
    @Test
    public void noConformeSolicitud() {
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.GC;
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).ambito(ambito).build();
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
        verify(correoElectronico, times(1)).envioCorreo(eq(CORREO), any(String.class), contains(motivosNoConforme));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#getFormBusquedaSolicitudes()}.
     */
    @Test
    public void getFormBusquedaSolicitudes() {
        String ruta = solicitudDocPreviaBean.getFormBusquedaSolicitudes();
        verify(model, times(1)).setRowCount(0);
        assertThat(solicitudDocPreviaBean.getSolicitudDocPreviaBusqueda()).isNotNull();
        assertThat(ruta).isEqualTo("/solicitudesPrevia/busquedaSolicitudesDocPrevia?faces-redirect=true");
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#limpiarBusqueda()}.
     */
    @Test
    public void limpiarBusqueda() {
        solicitudDocPreviaBean.limpiarBusqueda();
        verify(model, times(1)).setRowCount(0);
        assertThat(solicitudDocPreviaBean.getSolicitudDocPreviaBusqueda()).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#buscarSolicitudDocPrevia()}.
     */
    @Test
    public void buscarSolicitudDocPrevia() {
        solicitudDocPreviaBean.buscarSolicitudDocPrevia();
        verify(model, times(1)).setBusqueda(solicitudDocPreviaBean.getSolicitudDocPreviaBusqueda());
        verify(model, times(1)).load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#autocompletarInspeccion(String)}.
     */
    @Test
    public void autocompletarInspeccion() {
        String infoInspeccion = "1/2017";
        
        solicitudDocPreviaBean.autocompletarInspeccion(infoInspeccion);
        
        verify(inspeccionesService, times(1)).buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#imprimirPdf()}.
     * @throws ProgesinException al generar el archivo
     */
    @Test
    public void imprimirPdf() throws ProgesinException {
        SolicitudDocumentacionPrevia solicitud = mock(SolicitudDocumentacionPrevia.class);
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        List<DocumentacionPrevia> listDoc = new ArrayList<>();
        solicitudDocPreviaBean.setListadoDocumentosPrevios(listDoc);
        
        solicitudDocPreviaBean.imprimirPdf();
        
        verify(pdfGenerator, times(1)).imprimirSolicitudDocumentacionPrevia(eq(solicitud), eq(listDoc));
    }
    
}
