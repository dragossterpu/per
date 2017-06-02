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

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import es.mira.progesin.exceptions.ProgesinException;
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
     * Mock del objeto de parámetros de búsqueda.
     */
    @Mock
    private SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda;
    
    /**
     * Mock del servicio del registro de actividad.
     */
    @Mock
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Mock del servicio de notificaciones.
     */
    @Mock
    private transient INotificacionService notificacionService;
    
    /**
     * Mock del servicio de alertas.
     */
    @Mock
    private transient IAlertaService alertaService;
    
    /**
     * Mock del servicio de solicitudes de documentación.
     */
    @Mock
    private transient ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Mock del servicio de tipos de documentación.
     */
    @Mock
    private transient ITipoDocumentacionService tipoDocumentacionService;
    
    /**
     * Mock del servicio de inspecciones.
     */
    @Mock
    private transient IInspeccionesService inspeccionesService;
    
    /**
     * Mock del servicio de usuarios.
     */
    @Mock
    private transient IUserService userService;
    
    /**
     * Mock del servicio de documentos.
     */
    @Mock
    private transient IDocumentoService documentoService;
    
    /**
     * Mock del cifrador de contraseñas.
     */
    @Mock
    private transient PasswordEncoder passwordEncoder;
    
    /**
     * Mock del servicio de correos electrónicos.
     */
    @Mock
    private transient ICorreoElectronico correoElectronico;
    
    /**
     * Mock del bean de aplicación.
     */
    @Mock
    private transient ApplicationBean applicationBean;
    
    /**
     * Mock del generador de pdfs.
     */
    @Mock
    private transient PdfGenerator pdfGenerator;
    
    /**
     * Mock del servicio de cuestionarios enviados.
     */
    @Mock
    private transient ICuestionarioEnvioService cuestionarioEnvioService;
    
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
        String correoDestinatario = "correoDestinatario";
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).correoDestinatario(correoDestinatario).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        solicitudDocPreviaBean.setDocumentosSeleccionados(documentosSeleccionados);
        
        solicitudDocPreviaBean.crearSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                documentosSeleccionados);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud_validacionInspeccionConTareasPendientes() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        String correoDestinatario = "correoDestinatario";
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).correoDestinatario(correoDestinatario).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        solicitudDocPreviaBean.setDocumentosSeleccionados(documentosSeleccionados);
        when(solicitudDocumentacionService.findNoFinalizadaPorInspeccion(inspeccion))
                .thenReturn(mock(SolicitudDocumentacionPrevia.class));
        
        solicitudDocPreviaBean.crearSolicitud();
        
        verify(solicitudDocumentacionService, times(0)).transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                documentosSeleccionados);
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud_validacionUsuarioConTareasPendientes() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        String correoDestinatario = "correoDestinatario";
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).correoDestinatario(correoDestinatario).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        solicitudDocPreviaBean.setDocumentosSeleccionados(documentosSeleccionados);
        Inspeccion inspeccion2 = mock(Inspeccion.class);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correoDestinatario))
                .thenReturn(CuestionarioEnvio.builder().inspeccion(inspeccion2).build());
        
        solicitudDocPreviaBean.crearSolicitud();
        
        verify(solicitudDocumentacionService, times(0)).transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                documentosSeleccionados);
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud_Excepcion() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        String correoDestinatario = "correoDestinatario";
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).correoDestinatario(correoDestinatario).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        solicitudDocPreviaBean.setDocumentosSeleccionados(documentosSeleccionados);
        doThrow(SQLException.class).when(solicitudDocumentacionService)
                .transaccSaveAltaDocumentos(solicitudDocumentacionPrevia, documentosSeleccionados);
        
        solicitudDocPreviaBean.crearSolicitud();
        
        verify(regActividadService, times(0)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(SQLException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#getFormModificarSolicitud(SolicitudDocumentacionPrevia)}
     * .
     */
    @Test
    public void getFormModificarSolicitud() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        
        String ruta_vista = solicitudDocPreviaBean.getFormModificarSolicitud(solicitud);
        
        assertThat(ruta_vista).isEqualTo("/solicitudesPrevia/modificarSolicitud?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#visualizarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void visualizarSolicitud() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        when(solicitudDocumentacionService.findByIdConDocumentos(solicitud.getId())).thenReturn(solicitud);
        
        String ruta_vista = solicitudDocPreviaBean.visualizarSolicitud(solicitud);
        assertThat(solicitudDocPreviaBean.getSolicitudDocumentacionPrevia().getInspeccion().getNumero())
                .isEqualTo("1/2017");
        verify(solicitudDocumentacionService, times(1)).findByIdConDocumentos(1L);
        verify(tipoDocumentacionService, times(1)).findByIdSolicitud(1L);
        assertThat(ruta_vista).isEqualTo("/solicitudesPrevia/vistaSolicitud?faces-redirect=true");
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#visualizarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void visualizarSolicitud_Excepcion() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        when(solicitudDocumentacionService.findByIdConDocumentos(solicitud.getId())).thenThrow(SQLException.class);
        
        String ruta_vista = solicitudDocPreviaBean.visualizarSolicitud(solicitud);
        assertThat(ruta_vista).isEqualTo(null);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(SQLException.class));
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
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(alertaService, times(1)).crearAlertaJefeEquipo(eq(SeccionesEnum.DOCUMENTACION.name()), any(String.class),
                eq(inspeccion));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
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
        when(solicitudDocumentacionService.save(solicitudCaptor.capture())).thenThrow(SQLException.class);
        
        solicitudDocPreviaBean.validacionApoyo();
        
        verify(regActividadService, times(0)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(SQLException.class));
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
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(alertaService, times(1)).crearAlertaRol(eq(SeccionesEnum.DOCUMENTACION.name()), any(String.class),
                eq(RoleEnum.ROLE_JEFE_INSPECCIONES));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
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
        when(solicitudDocumentacionService.save(solicitudCaptor.capture())).thenThrow(SQLException.class);
        
        solicitudDocPreviaBean.validacionJefeEquipo();
        
        verify(regActividadService, times(0)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(SQLException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#getFormularioCrearSolicitud()}.
     */
    @Ignore
    @Test
    public void getFormularioCrearSolicitud() {
        
        // solicitudDocPreviaBean.getFormularioCrearSolicitud();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#init()}.
     */
    @Ignore
    @Test
    public void init() {
        
        // solicitudDocPreviaBean.init();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#descargarFichero(Long)}.
     * @throws SQLException error al recuperar el blob
     */
    @Test
    public void descargarFichero() throws SQLException {
        Long idDocumento = null;
        
        solicitudDocPreviaBean.descargarFichero(idDocumento);
        
        verify(documentoService).descargaDocumento(idDocumento);
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#descargarFichero(Long)}.
     * @throws SQLException error al recuperar el blob
     */
    @Test
    public void descargarFichero_Excepcion() throws SQLException {
        Long idDocumento = null;
        when(documentoService.descargaDocumento(idDocumento)).thenThrow(SQLException.class);
        
        solicitudDocPreviaBean.descargarFichero(idDocumento);
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(SQLException.class));
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
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "general", "documentacion");
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        verify(tipoDocumentacionService, times(1)).findByAmbito(ambito);
        assertThat(nombre_paso).isEqualTo("documentacion");
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
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "general", "documentacion");
        when(solicitudDocumentacionService.findNoFinalizadaPorInspeccion(inspeccion))
                .thenReturn(mock(SolicitudDocumentacionPrevia.class));
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("general");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoGeneralADocumentacionAmbitoOtros() {
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.OTROS;
        Inspeccion inspeccion = Inspeccion.builder().ambito(ambito).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().inspeccion(inspeccion).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "general", "documentacion");
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        verify(tipoDocumentacionService, times(1)).findAll();
        assertThat(nombre_paso).isEqualTo("documentacion");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void onFlowProcess_pasoDocumentacionAApoyo() {
        solicitudDocPreviaBean.setDocumentosSeleccionados(mock(List.class));
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "documentacion", "apoyo");
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("apoyo");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoDocumentacionAApoyo_validacionSaltarPaso() {
        solicitudDocPreviaBean.setSkip(Boolean.TRUE);
        solicitudDocPreviaBean.setDocumentosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "documentacion", "apoyo");
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("apoyo");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoDocumentacionAApoyo_validacionDocumentosNoSeleccionados() {
        solicitudDocPreviaBean.setSkip(Boolean.FALSE);
        solicitudDocPreviaBean.setDocumentosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "documentacion", "apoyo");
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(""));
        assertThat(nombre_paso).isEqualTo("documentacion");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoApoyoAConfirm() {
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "apoyo", "confirm");
        
        String nombre_paso = solicitudDocPreviaBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("confirm");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onToggle(ToggleEvent)}.
     */
    @Ignore
    @Test
    public void onToggle() {
        // ToggleEvent e = mock(ToggleEvent.class);
        //
        // solicitudDocPreviaBean.onToggle(e);
        
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
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#eliminarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void eliminarSolicitud_ConFechaEnvio_Logica() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .fechaEnvio(new Date()).correoDestinatario("correoDestinatario").build();
        when(authentication.getPrincipal()).thenReturn(User.builder().role(RoleEnum.ROLE_JEFE_INSPECCIONES).build());
        
        solicitudDocPreviaBean.eliminarSolicitud(solicitud);
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveElimUsuarioProv(solicitud, "correoDestinatario");
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
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
                .fechaEnvio(sdf.parse("1/1/2017")).fechaLimiteEnvio(sdf.parse("10/1/2017"))
                .correoDestinatario("correoDestinatario").build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        solicitudDocPreviaBean.setBackupFechaLimiteEnvio(sdf.parse("10/1/2017"));
        
        solicitudDocPreviaBean.modificarSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).save(solicitud);
        verify(correoElectronico, times(0)).envioCorreo(eq("correoDestinatario"), any(String.class), any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
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
                .fechaEnvio(sdf.parse("1/1/2017")).fechaLimiteEnvio(sdf.parse("11/1/2017"))
                .correoDestinatario("correoDestinatario").build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        solicitudDocPreviaBean.setBackupFechaLimiteEnvio(sdf.parse("10/1/2017"));
        
        solicitudDocPreviaBean.modificarSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).save(solicitud);
        verify(correoElectronico, times(1)).envioCorreo(eq("correoDestinatario"), any(String.class), any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#enviarSolicitud()}.
     */
    @Test
    public void enviarSolicitud_validacionUsuarioProvExiste() {
        String correoDestinatario = "correoDestinatario";
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .correoDestinatario("correoDestinatario").build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        when(userService.exists(correoDestinatario)).thenReturn(Boolean.TRUE);
        
        solicitudDocPreviaBean.enviarSolicitud();
        
        verify(userService, times(1)).exists(correoDestinatario);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq("Envío abortado"),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#enviarSolicitud()}.
     */
    @Test
    public void enviarSolicitud() {
        String correoDestinatario = "correoDestinatario";
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.GC;
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).ambito(ambito).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .correoDestinatario(correoDestinatario).asunto("asunto").build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        when(userService.exists(correoDestinatario)).thenReturn(Boolean.FALSE);
        @SuppressWarnings("unchecked")
        Map<String, Map<String, String>> mapa = mock(Map.class);
        when(applicationBean.getMapaParametros()).thenReturn(mapa);
        @SuppressWarnings("unchecked")
        Map<String, String> submapa = mock(Map.class);
        when(mapa.get("URLPROGESIN")).thenReturn(submapa);
        when(submapa.get(ambito.name())).thenReturn("url");
        List<RoleEnum> listRoles = new ArrayList<>();
        listRoles.add(RoleEnum.ROLE_SERVICIO_APOYO);
        listRoles.add(RoleEnum.ROLE_EQUIPO_INSPECCIONES);
        
        solicitudDocPreviaBean.enviarSolicitud();
        
        verify(userService, times(1)).exists(correoDestinatario);
        verify(solicitudDocumentacionService, times(1)).transaccSaveCreaUsuarioProv(eq(solicitud), any(User.class));
        verify(correoElectronico, times(1)).envioCorreo(eq(correoDestinatario), any(String.class), contains("url"));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(notificacionService, times(1)).crearNotificacionRol(any(String.class),
                eq(SeccionesEnum.DOCUMENTACION.name()), eq(listRoles));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#finalizarSolicitud()}.
     */
    @Test
    public void finalizarSolicitud() {
        String correoDestinatario = "correoDestinatario";
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .correoDestinatario(correoDestinatario).asunto("asunto").build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitud);
        
        solicitudDocPreviaBean.finalizarSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveElimUsuarioProv(eq(solicitud),
                eq(correoDestinatario));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#noConformeSolicitud(String)}.
     */
    @Test
    public void noConformeSolicitud() {
        String correoDestinatario = "correoDestinatario";
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.GC;
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).ambito(ambito).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .correoDestinatario(correoDestinatario).asunto("asunto").build();
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
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveActivaUsuarioProv(eq(solicitud),
                eq(correoDestinatario));
        verify(correoElectronico, times(1)).envioCorreo(eq(correoDestinatario), any(String.class),
                contains(motivosNoConforme));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#getFormBusquedaSolicitudes()}.
     */
    @Ignore
    @Test
    public void getFormBusquedaSolicitudes() {
        solicitudDocPreviaBean.getFormBusquedaSolicitudes();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#limpiarBusqueda()}.
     */
    @Ignore
    @Test
    public void limpiarBusqueda() {
        solicitudDocPreviaBean.limpiarBusqueda();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#buscarSolicitudDocPrevia()}.
     */
    @Ignore
    @Test
    public void buscarSolicitudDocPrevia() {
        solicitudDocPreviaBean.buscarSolicitudDocPrevia();
        
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
        @SuppressWarnings("unchecked")
        List<DocumentacionPrevia> listDoc = mock(List.class);
        solicitudDocPreviaBean.setListadoDocumentosPrevios(listDoc);
        solicitudDocPreviaBean.imprimirPdf();
        
        verify(pdfGenerator, times(1)).imprimirSolicitudDocumentacionPrevia(eq(solicitud), eq(listDoc));
        
    }
    
}
