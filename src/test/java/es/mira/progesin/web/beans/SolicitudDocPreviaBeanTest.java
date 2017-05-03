package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.ToggleEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
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
import es.mira.progesin.services.gd.IGestDocSolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.PdfGenerator;

/**
 * @author EZENTIS
 * 
 * Test del bean solicitudDocPrevia
 */
@RunWith(PowerMockRunner.class)
// @PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
// ApplicationContext will be loaded from AppConfig and TestConfig
@ContextConfiguration // (classes = { AppConfig.class, TestConfig.class })
public class SolicitudDocPreviaBeanTest {
    
    @Mock
    private FacesUtilities facesUtilities;
    
    @Mock
    private SecurityContextHolder securityContextHolder;
    
    @Mock
    private SecurityContext securityContext;
    
    @Mock
    private Authentication authentication;
    
    @Mock
    private SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda;
    
    @Mock
    private transient IRegistroActividadService regActividadService;
    
    @Mock
    private transient INotificacionService notificacionService;
    
    @Mock
    private transient IAlertaService alertaService;
    
    @Mock
    private transient ISolicitudDocumentacionService solicitudDocumentacionService;
    
    @Mock
    private transient ITipoDocumentacionService tipoDocumentacionService;
    
    @Mock
    private transient IInspeccionesService inspeccionesService;
    
    @Mock
    private transient IGestDocSolicitudDocumentacionService gestDocumentacionService;
    
    @Mock
    private transient IUserService userService;
    
    @Mock
    private transient IDocumentoService documentoService;
    
    @Mock
    private transient PasswordEncoder passwordEncoder;
    
    @Mock
    private transient ICorreoElectronico correoElectronico;
    
    @Mock
    private transient ApplicationBean applicationBean;
    
    @Mock
    private transient PdfGenerator pdfGenerator;
    
    @Mock
    private transient ICuestionarioEnvioService cuestionarioEnvioService;
    
    @InjectMocks
    private SolicitudDocPreviaBean solicitudDocPreviaBean;
    
    /**
     * Configuración inicial del test
     */
    @SuppressWarnings("static-access")
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
        when(securityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
    
    /**
     * Comprobación clase existe
     */
    @Test
    public void type() {
        assertThat(SolicitudDocPreviaBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta
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
    public void crearSolicitud_SinDocumentacion() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        solicitudDocPreviaBean.setDocumentosSeleccionados(new ArrayList<>());
        String correoDestinatario = "correoDestinatario";
        when(solicitudDocumentacionService.findNoFinalizadaPorInspeccion(inspeccion)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correoDestinatario)).thenReturn(null);
        
        solicitudDocPreviaBean.crearSolicitud();
        verify(solicitudDocumentacionService, times(1)).save(solicitudDocumentacionPrevia);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.DOCUMENTACION.name()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud_ConDocumentacion() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).build();
        solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        documentosSeleccionados.add(TipoDocumentacion.builder().nombre("tipo1").build());
        documentosSeleccionados.add(TipoDocumentacion.builder().nombre("tipo2").build());
        solicitudDocPreviaBean.setDocumentosSeleccionados(documentosSeleccionados);
        String correoDestinatario = "correoDestinatario";
        when(solicitudDocumentacionService.findNoFinalizadaPorInspeccion(inspeccion)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correoDestinatario)).thenReturn(null);
        
        solicitudDocPreviaBean.crearSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).save(solicitudDocumentacionPrevia);
        verify(tipoDocumentacionService, times(2)).save(any(DocumentacionPrevia.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.DOCUMENTACION.name()));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#getFormModificarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Ignore
    @Test
    public void getFormModificarSolicitud() {
        SolicitudDocumentacionPrevia solicitud = mock(SolicitudDocumentacionPrevia.class);
        
        solicitudDocPreviaBean.getFormModificarSolicitud(solicitud);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#visualizarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Ignore
    @Test
    public void visualizarSolicitud() {
        SolicitudDocumentacionPrevia solicitud = mock(SolicitudDocumentacionPrevia.class);
        
        solicitudDocPreviaBean.visualizarSolicitud(solicitud);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#validacionApoyo()}.
     */
    @Ignore
    @Test
    public void validacionApoyo() {
        
        solicitudDocPreviaBean.validacionApoyo();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#validacionJefeEquipo()}.
     */
    @Ignore
    @Test
    public void validacionJefeEquipo() {
        
        solicitudDocPreviaBean.validacionJefeEquipo();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#getFormularioCrearSolicitud()}.
     */
    @Ignore
    @Test
    public void getFormularioCrearSolicitud() {
        
        solicitudDocPreviaBean.getFormularioCrearSolicitud();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#init()}.
     */
    @Ignore
    @Test
    public void init() {
        
        solicitudDocPreviaBean.init();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#descargarFichero(Long)}.
     */
    @Ignore
    @Test
    public void descargarFichero() {
        Long idDocumento = null;
        
        solicitudDocPreviaBean.descargarFichero(idDocumento);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onFlowProcess(FlowEvent)}.
     */
    @Ignore
    @Test
    public void onFlowProcess() {
        FlowEvent event = mock(FlowEvent.class);
        
        solicitudDocPreviaBean.onFlowProcess(event);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#onToggle(ToggleEvent)}.
     */
    @Ignore
    @Test
    public void onToggle() {
        ToggleEvent e = mock(ToggleEvent.class);
        
        solicitudDocPreviaBean.onToggle(e);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#eliminarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Ignore
    @Test
    public void eliminarSolicitud() {
        SolicitudDocumentacionPrevia solicitud = mock(SolicitudDocumentacionPrevia.class);
        
        solicitudDocPreviaBean.eliminarSolicitud(solicitud);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#modificarSolicitud()}.
     */
    @Ignore
    @Test
    public void modificarSolicitud() {
        solicitudDocPreviaBean.modificarSolicitud();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#enviarSolicitud()}.
     */
    @Ignore
    @Test
    public void enviarSolicitud() {
        solicitudDocPreviaBean.enviarSolicitud();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#finalizarSolicitud()}.
     */
    @Ignore
    @Test
    public void finalizarSolicitud() {
        solicitudDocPreviaBean.finalizarSolicitud();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#noConformeSolicitud(String)}.
     */
    @Ignore
    @Test
    public void noConformeSolicitud() {
        String motivosNoConforme = null;
        
        solicitudDocPreviaBean.noConformeSolicitud(motivosNoConforme);
        
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
    @Ignore
    @Test
    public void autocompletarInspeccion() {
        String infoInspeccion = null;
        
        solicitudDocPreviaBean.autocompletarInspeccion(infoInspeccion);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#imprimirPdf()}.
     */
    @Ignore
    @Test
    public void imprimirPdf() {
        solicitudDocPreviaBean.imprimirPdf();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#inspeccionSinTareasPendientes()}.
     */
    @Ignore
    @Test
    public void inspeccionSinTareasPendientes() {
        solicitudDocPreviaBean.inspeccionSinTareasPendientes();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#usuarioSinTareasPendientes()}.
     */
    @Ignore
    @Test
    public void usuarioSinTareasPendientes() {
        solicitudDocPreviaBean.usuarioSinTareasPendientes();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#getCountRegistrosSolicitud()}.
     */
    @Ignore
    @Test
    public void getCountRegistrosSolicitud() {
        
        solicitudDocPreviaBean.getCountRegistrosSolicitud();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.SolicitudDocPreviaBean#copiaSolicitudDocPreviaBusqueda(SolicitudDocPreviaBusqueda)}.
     */
    @Ignore
    @Test
    public void copiaSolicitudDocPreviaBusqueda() {
        SolicitudDocPreviaBusqueda solicitudBusqueda = mock(SolicitudDocPreviaBusqueda.class);
        
        solicitudDocPreviaBean.copiaSolicitudDocPreviaBusqueda(solicitudBusqueda);
        
    }
    
}
