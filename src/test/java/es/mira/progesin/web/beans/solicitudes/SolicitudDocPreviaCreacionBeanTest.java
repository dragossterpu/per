package es.mira.progesin.web.beans.solicitudes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.FlowEvent;
import org.springframework.dao.TransientDataAccessResourceException;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * Test del bean solicitudDocPreviaCreacion.
 * 
 * @author EZENTIS
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FacesUtilities.class })
public class SolicitudDocPreviaCreacionBeanTest {
    
    /**
     * Mock del servicio del registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
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
     * Mock del bean de aplicación.
     */
    @Mock
    private ApplicationBean applicationBean;
    
    /**
     * Mock del servicio de cuestionarios enviados.
     */
    @Mock
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Instancia de prueba del bean de solicitudes de documentación.
     */
    @InjectMocks
    private SolicitudDocPreviaCreacionBean SolicitudDocPreviaCreacionBean;
    
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
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(SolicitudDocPreviaCreacionBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        SolicitudDocPreviaCreacionBean target = new SolicitudDocPreviaCreacionBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Prepara la inspección asociada a la documentación previa.
     * @return inspeccion
     */
    private Inspeccion cargarDatosInspeccion() {
        Inspeccion inspeccion = new Inspeccion();
        TipoInspeccion tipoInspeccion = TipoInspeccion.builder().descripcion("general periódica").build();
        TipoUnidad tipoUnidad = TipoUnidad.builder().descripcion("tipo unidad").build();
        inspeccion.setTipoInspeccion(tipoInspeccion);
        inspeccion.setTipoUnidad(tipoUnidad);
        inspeccion.setNombreUnidad("nombre Unidad");
        Municipio municipio = Municipio.builder().provincia(Provincia.builder().nombre("provincia").build()).build();
        inspeccion.setMunicipio(municipio);
        return inspeccion;
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud() {
        
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(cargarDatosInspeccion()).correoDestinatario(CORREO).build();
        
        SolicitudDocPreviaCreacionBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        SolicitudDocPreviaCreacionBean.setDocumentosSeleccionados(documentosSeleccionados);
        
        SolicitudDocPreviaCreacionBean.crearSolicitud();
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                documentosSeleccionados);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud_validacionInspeccionConTareasPendientes() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).correoDestinatario(CORREO).build();
        SolicitudDocPreviaCreacionBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        SolicitudDocPreviaCreacionBean.setDocumentosSeleccionados(documentosSeleccionados);
        when(solicitudDocumentacionService.findNoFinalizadaPorInspeccion(inspeccion))
                .thenReturn(mock(SolicitudDocumentacionPrevia.class));
        
        SolicitudDocPreviaCreacionBean.crearSolicitud();
        
        verify(solicitudDocumentacionService, times(0)).transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                documentosSeleccionados);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud_validacionUsuarioConTareasPendientes() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(inspeccion).correoDestinatario(CORREO).build();
        SolicitudDocPreviaCreacionBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        SolicitudDocPreviaCreacionBean.setDocumentosSeleccionados(documentosSeleccionados);
        Inspeccion inspeccion2 = mock(Inspeccion.class);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(CORREO))
                .thenReturn(CuestionarioEnvio.builder().inspeccion(inspeccion2).build());
        
        SolicitudDocPreviaCreacionBean.crearSolicitud();
        
        verify(solicitudDocumentacionService, times(0)).transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                documentosSeleccionados);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#crearSolicitud()}.
     */
    @Test
    public void crearSolicitud_Excepcion() {
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = SolicitudDocumentacionPrevia.builder().id(1L)
                .inspeccion(cargarDatosInspeccion()).correoDestinatario(CORREO).build();
        SolicitudDocPreviaCreacionBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        List<TipoDocumentacion> documentosSeleccionados = new ArrayList<>();
        SolicitudDocPreviaCreacionBean.setDocumentosSeleccionados(documentosSeleccionados);
        doThrow(TransientDataAccessResourceException.class).when(solicitudDocumentacionService)
                .transaccSaveAltaDocumentos(solicitudDocumentacionPrevia, documentosSeleccionados);
        
        SolicitudDocPreviaCreacionBean.crearSolicitud();
        
        verify(regActividadService, times(0)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#getFormularioCrearSolicitud()}.
     */
    
    @Test
    public void getFormularioCrearSolicitud() {
        Map<String, String> datosApoyo = new HashMap<>();
        datosApoyo.put("ApoyoCorreo", "apoyoCorreo");
        datosApoyo.put("ApoyoPuesto", "apoyoPuesto");
        datosApoyo.put("ApoyoTelefono", "apoyoTelefono");
        SolicitudDocPreviaCreacionBean.setDatosApoyo(datosApoyo);
        
        SolicitudDocPreviaCreacionBean.getFormularioCrearSolicitud();
        assertThat(SolicitudDocPreviaCreacionBean.getSolicitudDocumentacionPrevia().getApoyoCorreo())
                .isEqualTo("apoyoCorreo");
        assertThat(SolicitudDocPreviaCreacionBean.getSolicitudDocumentacionPrevia().getApoyoPuesto())
                .isEqualTo("apoyoPuesto");
        assertThat(SolicitudDocPreviaCreacionBean.getSolicitudDocumentacionPrevia().getApoyoTelefono())
                .isEqualTo("apoyoTelefono");
        assertThat(SolicitudDocPreviaCreacionBean.getDocumentosSeleccionados()).isNotNull();
        assertThat(SolicitudDocPreviaCreacionBean.isSkip()).isFalse();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#init()}.
     */
    @Test
    public void init() {
        Map<String, String> datosApoyo = new HashMap<>();
        Map<String, Map<String, String>> mapaParametros = new HashMap<>();
        mapaParametros.put("datosApoyo", datosApoyo);
        when(applicationBean.getMapaParametros()).thenReturn(mapaParametros);
        
        SolicitudDocPreviaCreacionBean.init();
        
        assertThat(SolicitudDocPreviaCreacionBean.getDatosApoyo()).isNotNull();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoGeneralADocumentacion() {
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.PN;
        Inspeccion inspeccion = Inspeccion.builder().ambito(ambito).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().inspeccion(inspeccion).build();
        SolicitudDocPreviaCreacionBean.setSolicitudDocumentacionPrevia(solicitud);
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "general", DOCUMENTACION);
        
        String nombre_paso = SolicitudDocPreviaCreacionBean.onFlowProcess(event);
        
        verify(tipoDocumentacionService, times(1)).findByAmbito(ambito);
        assertThat(nombre_paso).isEqualTo(DOCUMENTACION);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoGeneralADocumentacion_TareasPendientes() {
        AmbitoInspeccionEnum ambito = AmbitoInspeccionEnum.PN;
        Inspeccion inspeccion = Inspeccion.builder().ambito(ambito).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().inspeccion(inspeccion).build();
        SolicitudDocPreviaCreacionBean.setSolicitudDocumentacionPrevia(solicitud);
        FlowEvent event = new FlowEvent(mock(UIComponent.class), "general", DOCUMENTACION);
        when(solicitudDocumentacionService.findNoFinalizadaPorInspeccion(inspeccion))
                .thenReturn(mock(SolicitudDocumentacionPrevia.class));
        
        String nombre_paso = SolicitudDocPreviaCreacionBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("general");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoDocumentacionAApoyo() {
        SolicitudDocPreviaCreacionBean.setDocumentosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), DOCUMENTACION, APOYO);
        
        String nombre_paso = SolicitudDocPreviaCreacionBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo(DOCUMENTACION);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoDocumentacionAApoyo_validacionSaltarPaso() {
        SolicitudDocPreviaCreacionBean.setSkip(Boolean.TRUE);
        SolicitudDocPreviaCreacionBean.setDocumentosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), DOCUMENTACION, APOYO);
        
        String nombre_paso = SolicitudDocPreviaCreacionBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo(APOYO);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoDocumentacionAApoyo_validacionDocumentosNoSeleccionados() {
        SolicitudDocPreviaCreacionBean.setSkip(Boolean.FALSE);
        SolicitudDocPreviaCreacionBean.setDocumentosSeleccionados(new ArrayList<>());
        FlowEvent event = new FlowEvent(mock(UIComponent.class), DOCUMENTACION, APOYO);
        
        String nombre_paso = SolicitudDocPreviaCreacionBean.onFlowProcess(event);
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(""));
        assertThat(nombre_paso).isEqualTo(DOCUMENTACION);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaCreacionBean#onFlowProcess(FlowEvent)}.
     */
    @Test
    public void onFlowProcess_pasoApoyoAConfirm() {
        FlowEvent event = new FlowEvent(mock(UIComponent.class), APOYO, "confirm");
        
        String nombre_paso = SolicitudDocPreviaCreacionBean.onFlowProcess(event);
        
        assertThat(nombre_paso).isEqualTo("confirm");
    }
    
}
