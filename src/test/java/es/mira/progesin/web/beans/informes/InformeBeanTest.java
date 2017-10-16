/**
 * 
 */
package es.mira.progesin.web.beans.informes;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.exceptions.ExcepcionRollback;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.AsignSubareaInformeUser;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.RespuestaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.IAsignSubareaInformeUserService;
import es.mira.progesin.services.IInformeService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IModeloInformePersonalizadoService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.RegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.HtmlDocxGenerator;
import es.mira.progesin.util.HtmlPdfGenerator;
import es.mira.progesin.util.Utilities;

/**
 * Test del bean de informes.
 * 
 * @author EZENTIS
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ Utilities.class, FacesUtilities.class, SecurityContextHolder.class })
public class InformeBeanTest {
    
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
     * Servicio de informes.
     */
    @Mock
    private transient IInformeService informeService;
    
    /**
     * Servicio de modelos personalizados de informe.
     */
    @Mock
    private transient IModeloInformePersonalizadoService modeloInformePersonalizadoService;
    
    /**
     * Generador de PDFs a partir de código html.
     */
    @Mock
    private transient HtmlPdfGenerator htmlPdfGenerator;
    
    /**
     * Generador de DOCXs a partir de código html.
     */
    @Mock
    private transient HtmlDocxGenerator htmlDocxGenerator;
    
    /**
     * Servicio de inspecciones.
     */
    @Mock
    private transient IInspeccionesService inspeccionService;
    
    /**
     * Servicio de asignaciones de subareas a inspectores.
     */
    @Mock
    private transient IAsignSubareaInformeUserService asignSubareaInformeUserService;
    
    /**
     * Servicio del registro de actividad.
     */
    @Mock
    private transient RegistroActividadService regActividadService;
    
    /**
     * Servicio de notificaciones.
     */
    @Mock
    private transient INotificacionService notificacionesService;
    
    /**
     * Bean de informes.
     */
    @InjectMocks
    private InformeBean informeBean;
    
    /**
     * Literal user_login.
     */
    private static final String USERLOGIN = "user_login";
    
    /**
     * Simulación del usuario de la sesión.
     */
    private User userLogin;
    
    /**
     * Literal areaTest.
     */
    private static final String AREATEST = "areaTest";
    
    /**
     * Literal respuestaTest.
     */
    private static final String RESPUESTATEST = "respuestaTest";
    
    /**
     * Literal conclusionesTest.
     */
    private static final String CONCLUSIONESTEST = "conclusionesTest";
    
    /**
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Utilities.class);
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
        when(Utilities.getFechaFormateada(any(Date.class), eq("dd/MM/yyyy"))).thenCallRealMethod();
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USERLOGIN);
        userLogin = new User();
        userLogin.setUsername(USERLOGIN);
        when(authentication.getPrincipal()).thenReturn(userLogin);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBean#getFormCrearInforme(java.lang.Long)}.
     */
    @Test
    public void testGetFormCrearInforme() {
        Long idModeloPersonalizado = 1L;
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(SubareaInforme.builder().area(mock(AreaInforme.class)).build());
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        when(modeloInformePersonalizadoService.findModeloPersonalizadoCompleto(idModeloPersonalizado))
                .thenReturn(modeloPersonalizado);
        
        String ruta_form = informeBean.getFormCrearInforme(idModeloPersonalizado);
        
        assertThat(ruta_form).isEqualTo("/informes/crearInforme?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBean#getFormEditarInforme(java.lang.Long)}.
     */
    @Test
    public void testGetFormEditarInforme() {
        Long idInforme = 1L;
        Long idModeloPersonalizado = 1L;
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(SubareaInforme.builder().area(mock(AreaInforme.class)).build());
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().subarea(mock(SubareaInforme.class)).build());
        Informe informe = Informe.builder().id(idInforme).modeloPersonalizado(modeloPersonalizado)
                .respuestas(respuestas).build();
        when(informeService.findConRespuestas(idInforme)).thenReturn(informe);
        when(modeloInformePersonalizadoService.findModeloPersonalizadoCompleto(idModeloPersonalizado))
                .thenReturn(modeloPersonalizado);
        
        String ruta_form = informeBean.getFormEditarInforme(idModeloPersonalizado);
        
        assertThat(ruta_form).isEqualTo("/informes/editarInforme?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBean#getFormVisualizarInforme(java.lang.Long)}.
     */
    @Test
    public void testGetFormVisualizarInforme() {
        Long idInforme = 1L;
        Long idModeloPersonalizado = 1L;
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(SubareaInforme.builder().area(mock(AreaInforme.class)).build());
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().subarea(mock(SubareaInforme.class)).build());
        Informe informe = Informe.builder().id(idInforme).modeloPersonalizado(modeloPersonalizado)
                .respuestas(respuestas).build();
        when(informeService.findConRespuestas(idInforme)).thenReturn(informe);
        when(modeloInformePersonalizadoService.findModeloPersonalizadoCompleto(idModeloPersonalizado))
                .thenReturn(modeloPersonalizado);
        
        String ruta_form = informeBean.getFormVisualizarInforme(idModeloPersonalizado);
        
        assertThat(ruta_form).isEqualTo("/informes/visualizarInforme?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.InformeBean#crearInforme(es.mira.progesin.persistence.entities.Inspeccion)}.
     * @throws ExcepcionRollback error
     */
    @Test
    public void testCrearInforme() throws ExcepcionRollback {
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(SubareaInforme.builder().area(mock(AreaInforme.class)).build());
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(1L).subareas(subareas)
                .build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).equipo(mock(Equipo.class)).build();
        informeBean.crearInforme(inspeccion);
        verify(informeService, times(1)).crearInforme(inspeccion, modeloPersonalizado);
        verify(notificacionesService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INFORMES.getDescripcion()), eq(inspeccion.getEquipo()));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.InformeBean#crearInforme(es.mira.progesin.persistence.entities.Inspeccion)}.
     * @throws ExcepcionRollback error
     */
    @Test
    public void testCrearInformeExcepcion() throws ExcepcionRollback {
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(SubareaInforme.builder().area(mock(AreaInforme.class)).build());
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(1L).subareas(subareas)
                .build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).equipo(mock(Equipo.class)).build();
        doThrow(ExcepcionRollback.class).when(informeService).crearInforme(inspeccion, modeloPersonalizado);
        informeBean.crearInforme(inspeccion);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.INFORMES.getDescripcion()),
                any(ExcepcionRollback.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBean#guardarInforme()}.
     */
    @Test
    public void testGuardarInforme() {
        Long idModeloPersonalizado = 1L;
        AreaInforme area = AreaInforme.builder().id(1L).descripcion(AREATEST).build();
        SubareaInforme subarea = SubareaInforme.builder().area(area).build();
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(subarea);
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().subarea(subarea).texto(RESPUESTATEST.getBytes())
                .conclusiones(CONCLUSIONESTEST.getBytes()).build());
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).equipo(mock(Equipo.class)).build();
        Informe informe = Informe.builder().id(1L).inspeccion(inspeccion).modeloPersonalizado(modeloPersonalizado)
                .respuestas(respuestas).build();
        informeBean.setInforme(informe);
        
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuesta = new String[2];
        respuesta[0] = RESPUESTATEST;
        respuesta[1] = CONCLUSIONESTEST;
        mapaRespuestas.put(subarea, respuesta);
        mapaAsignaciones.put(subarea, USERLOGIN);
        informeBean.setMapaRespuestas(mapaRespuestas);
        informeBean.setMapaAsignaciones(mapaAsignaciones);
        when(informeService.guardarInforme(informe, mapaRespuestas, mapaAsignaciones)).thenReturn(informe);
        
        informeBean.guardarInforme();
        
        verify(informeService, times(1)).guardarInforme(informe, mapaRespuestas, mapaAsignaciones);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), eq("Modificación"),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBean#desasignarInforme()}.
     */
    @Test
    public void testDesasignarInforme() {
        Long idModeloPersonalizado = 1L;
        SubareaInforme subarea = SubareaInforme.builder().area(mock(AreaInforme.class)).build();
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(subarea);
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().subarea(subarea).texto(RESPUESTATEST.getBytes())
                .conclusiones(CONCLUSIONESTEST.getBytes()).build());
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).equipo(mock(Equipo.class)).build();
        Informe informe = Informe.builder().id(1L).inspeccion(inspeccion).modeloPersonalizado(modeloPersonalizado)
                .respuestas(respuestas).build();
        informeBean.setInforme(informe);
        
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuesta = new String[2];
        respuesta[0] = RESPUESTATEST;
        respuesta[1] = CONCLUSIONESTEST;
        mapaRespuestas.put(subarea, respuesta);
        mapaAsignaciones.put(subarea, USERLOGIN);
        informeBean.setMapaRespuestas(mapaRespuestas);
        informeBean.setMapaAsignaciones(mapaAsignaciones);
        when(informeService.desasignarInforme(informe, mapaRespuestas, mapaAsignaciones)).thenReturn(informe);
        
        informeBean.desasignarInforme();
        
        verify(informeService, times(1)).desasignarInforme(informe, mapaRespuestas, mapaAsignaciones);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), eq("Modificación"),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBean#finalizarInforme()}.
     */
    @Test
    public void testFinalizarInforme() {
        Long idModeloPersonalizado = 1L;
        SubareaInforme subarea = SubareaInforme.builder().area(mock(AreaInforme.class)).build();
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(subarea);
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().subarea(subarea).texto(RESPUESTATEST.getBytes())
                .conclusiones(CONCLUSIONESTEST.getBytes()).build());
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).equipo(mock(Equipo.class)).build();
        Informe informe = Informe.builder().id(1L).inspeccion(inspeccion).modeloPersonalizado(modeloPersonalizado)
                .respuestas(respuestas).build();
        informeBean.setInforme(informe);
        
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuesta = new String[2];
        respuesta[0] = RESPUESTATEST;
        respuesta[1] = CONCLUSIONESTEST;
        mapaRespuestas.put(subarea, respuesta);
        informeBean.setMapaRespuestas(mapaRespuestas);
        informeBean.setMapaAsignaciones(mapaAsignaciones);
        when(informeService.finalizarInforme(informe, mapaRespuestas, mapaAsignaciones)).thenReturn(informe);
        
        informeBean.finalizarInforme();
        
        verify(informeService, times(1)).finalizarInforme(informe, mapaRespuestas, mapaAsignaciones);
        List<RoleEnum> rolesNotif = new ArrayList<>();
        rolesNotif.add(RoleEnum.ROLE_JEFE_INSPECCIONES);
        rolesNotif.add(RoleEnum.ROLE_SERVICIO_APOYO);
        verify(notificacionesService, times(1)).crearNotificacionRol(any(String.class),
                eq(SeccionesEnum.INFORMES.getDescripcion()), eq(rolesNotif));
        verify(notificacionesService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INFORMES.getDescripcion()), eq(informe.getInspeccion().getEquipo()));
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), eq("Modificación"),
                any(String.class), any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.InformeBean#anularInforme(es.mira.progesin.persistence.entities.informes.Informe, java.lang.String)}.
     */
    @Test
    public void testAnularInforme() {
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(SubareaInforme.builder().area(mock(AreaInforme.class)).build());
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(1L).subareas(subareas)
                .build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).equipo(mock(Equipo.class)).build();
        Informe informe = Informe.builder().id(1L).inspeccion(inspeccion).modeloPersonalizado(modeloPersonalizado)
                .build();
        String motivoAnulacion = "motivo_test";
        ArgumentCaptor<Informe> informeCaptor = ArgumentCaptor.forClass(Informe.class);
        informeBean.anularInforme(informe, motivoAnulacion);
        
        verify(informeService, times(1)).save(informeCaptor.capture());
        List<RoleEnum> rolesNotif = new ArrayList<>();
        assertThat(informeCaptor.getValue().getFechaBaja()).isNotNull();
        assertThat(informeCaptor.getValue().getUsernameBaja()).isNotNull();
        assertThat(informeCaptor.getValue().getMotivoAnulacion()).isEqualTo(motivoAnulacion);
        rolesNotif.add(RoleEnum.ROLE_JEFE_INSPECCIONES);
        rolesNotif.add(RoleEnum.ROLE_SERVICIO_APOYO);
        verify(notificacionesService, times(1)).crearNotificacionRol(any(String.class),
                eq(SeccionesEnum.INFORMES.getDescripcion()), eq(rolesNotif));
        verify(notificacionesService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.INFORMES.getDescripcion()), eq(informe.getInspeccion().getEquipo()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBean#exportarInforme(java.lang.String)}.
     * @throws ProgesinException error
     */
    @Test
    public void testExportarInformePdf() throws ProgesinException {
        Long idModeloPersonalizado = 1L;
        AreaInforme area = AreaInforme.builder().id(1L).descripcion(AREATEST).build();
        SubareaInforme subarea = SubareaInforme.builder().descripcion("subareaTest").area(area).build();
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(subarea);
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().subarea(subarea).texto(RESPUESTATEST.getBytes())
                .conclusiones(CONCLUSIONESTEST.getBytes()).build());
        TipoUnidad tipoUnidad = TipoUnidad.builder().id(1L).descripcion("tipoUnidadTest").build();
        Municipio municipio = Municipio.builder().id(1L).name("municipioTest").build();
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).tipoUnidad(tipoUnidad)
                .ambito(AmbitoInspeccionEnum.GC).municipio(municipio).equipo(mock(Equipo.class)).build();
        Informe informe = Informe.builder().id(1L).inspeccion(inspeccion).modeloPersonalizado(modeloPersonalizado)
                .respuestas(respuestas).fechaFinalizacion(new Date()).usernameFinalizacion("usuario_test").build();
        informeBean.setInforme(informe);
        
        List<AreaInforme> listaAreas = new ArrayList<>();
        listaAreas.add(area);
        informeBean.setListaAreas(listaAreas);
        
        Map<AreaInforme, List<SubareaInforme>> mapaAreasSubareas = new HashMap<>();
        mapaAreasSubareas.put(area, subareas);
        informeBean.setMapaAreasSubareas(mapaAreasSubareas);
        
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuesta = new String[2];
        respuesta[0] = RESPUESTATEST;
        respuesta[1] = CONCLUSIONESTEST;
        mapaRespuestas.put(subarea, respuesta);
        informeBean.setMapaRespuestas(mapaRespuestas);
        informeBean.setMapaAsignaciones(mapaAsignaciones);
        
        when(Utilities.limpiarHtml(any(String.class))).thenCallRealMethod();
        when(htmlPdfGenerator.generarInformePdf(any(String.class), any(String.class), any(String.class),
                any(String.class), any(String.class))).thenReturn(mock(DefaultStreamedContent.class));
        
        informeBean.exportarInforme("PDF", "completo");
        verifyStatic(Utilities.class, times(1));
        Utilities.limpiarHtml(any(String.class));
        verify(htmlPdfGenerator, times(1)).generarInformePdf(any(String.class), any(String.class), any(String.class),
                any(String.class), any(String.class));
        assertThat(informeBean.getFile()).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBean#exportarInforme(java.lang.String)}.
     * @throws ProgesinException error
     */
    @Test
    public void testExportarInformeDocx() throws ProgesinException {
        Long idModeloPersonalizado = 1L;
        AreaInforme area = AreaInforme.builder().id(1L).descripcion(AREATEST).build();
        SubareaInforme subarea = SubareaInforme.builder().descripcion("subareaTest").area(area).build();
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(subarea);
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().subarea(subarea).texto(RESPUESTATEST.getBytes())
                .conclusiones(CONCLUSIONESTEST.getBytes()).build());
        TipoUnidad tipoUnidad = TipoUnidad.builder().id(1L).descripcion("tipoUnidadTest").build();
        Municipio municipio = Municipio.builder().id(1L).name("municipioTest").build();
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).tipoUnidad(tipoUnidad)
                .ambito(AmbitoInspeccionEnum.GC).municipio(municipio).equipo(mock(Equipo.class)).build();
        Informe informe = Informe.builder().id(1L).inspeccion(inspeccion).modeloPersonalizado(modeloPersonalizado)
                .respuestas(respuestas).fechaFinalizacion(new Date()).usernameFinalizacion("usuario_test").build();
        informeBean.setInforme(informe);
        
        List<AreaInforme> listaAreas = new ArrayList<>();
        listaAreas.add(area);
        informeBean.setListaAreas(listaAreas);
        
        Map<AreaInforme, List<SubareaInforme>> mapaAreasSubareas = new HashMap<>();
        mapaAreasSubareas.put(area, subareas);
        informeBean.setMapaAreasSubareas(mapaAreasSubareas);
        
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuesta = new String[2];
        respuesta[0] = RESPUESTATEST;
        respuesta[1] = CONCLUSIONESTEST;
        mapaRespuestas.put(subarea, respuesta);
        informeBean.setMapaRespuestas(mapaRespuestas);
        informeBean.setMapaAsignaciones(mapaAsignaciones);
        
        when(Utilities.limpiarHtml(any(String.class))).thenCallRealMethod();
        when(htmlDocxGenerator.generarInformeDocx(any(String.class), any(String.class), any(String.class),
                any(String.class), any(String.class))).thenReturn(mock(DefaultStreamedContent.class));
        
        informeBean.exportarInforme("DOCX", "conclusiones");
        verifyStatic(Utilities.class, times(1));
        Utilities.limpiarHtml(any(String.class));
        verify(htmlDocxGenerator, times(1)).generarInformeDocx(any(String.class), any(String.class), any(String.class),
                any(String.class), any(String.class));
        assertThat(informeBean.getFile()).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.InformeBean#asignarSubarea(es.mira.progesin.persistence.entities.informes.SubareaInforme)}.
     */
    @Test
    public void testAsignarSubarea() {
        Long idModeloPersonalizado = 1L;
        AreaInforme area = AreaInforme.builder().id(1L).descripcion(AREATEST).build();
        SubareaInforme subarea = SubareaInforme.builder().area(area).build();
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(subarea);
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).equipo(mock(Equipo.class)).build();
        Informe informe = Informe.builder().id(1L).inspeccion(inspeccion).modeloPersonalizado(modeloPersonalizado)
                .build();
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().informe(informe).subarea(subarea).texto(RESPUESTATEST.getBytes())
                .conclusiones(CONCLUSIONESTEST.getBytes()).build());
        informe.setRespuestas(respuestas);
        informeBean.setInforme(informe);
        
        List<AreaInforme> listaAreas = new ArrayList<>();
        listaAreas.add(area);
        informeBean.setListaAreas(listaAreas);
        
        Map<AreaInforme, List<SubareaInforme>> mapaAreasSubareas = new HashMap<>();
        mapaAreasSubareas.put(area, subareas);
        informeBean.setMapaAreasSubareas(mapaAreasSubareas);
        
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuesta = new String[2];
        respuesta[0] = RESPUESTATEST;
        respuesta[1] = CONCLUSIONESTEST;
        mapaRespuestas.put(subarea, respuesta);
        informeBean.setMapaRespuestas(mapaRespuestas);
        informeBean.setMapaAsignaciones(mapaAsignaciones);
        when(informeService.guardarInforme(informe, mapaRespuestas, mapaAsignaciones)).thenReturn(informe);
        
        List<AsignSubareaInformeUser> listaAsignaciones = new ArrayList<>();
        AsignSubareaInformeUser asignacion = AsignSubareaInformeUser.builder().informe(informe).subarea(subarea)
                .user(userLogin).build();
        listaAsignaciones.add(asignacion);
        
        when(asignSubareaInformeUserService.findByInforme(informe)).thenReturn(listaAsignaciones);
        
        informeBean.asignarSubarea(subarea);
        
        verify(informeService, times(1)).guardarInforme(informe, mapaRespuestas, mapaAsignaciones);
        verify(informeService, times(1)).asignarSubarea(subarea, informe);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.InformeBean#desasignarSubarea(es.mira.progesin.persistence.entities.informes.SubareaInforme)}.
     */
    @Test
    public void testDesasignarSubarea() {
        Long idModeloPersonalizado = 1L;
        AreaInforme area = AreaInforme.builder().id(1L).descripcion(AREATEST).build();
        SubareaInforme subarea = SubareaInforme.builder().area(area).build();
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(subarea);
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().subarea(subarea).texto(RESPUESTATEST.getBytes())
                .conclusiones(CONCLUSIONESTEST.getBytes()).build());
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).equipo(mock(Equipo.class)).build();
        Informe informe = Informe.builder().id(1L).inspeccion(inspeccion).modeloPersonalizado(modeloPersonalizado)
                .respuestas(respuestas).build();
        informeBean.setInforme(informe);
        
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuesta = new String[2];
        respuesta[0] = RESPUESTATEST;
        respuesta[1] = CONCLUSIONESTEST;
        mapaRespuestas.put(subarea, respuesta);
        mapaAsignaciones.put(subarea, USERLOGIN);
        informeBean.setMapaRespuestas(mapaRespuestas);
        informeBean.setMapaAsignaciones(mapaAsignaciones);
        when(informeService.guardarInforme(informe, mapaRespuestas, mapaAsignaciones)).thenReturn(informe);
        
        List<AsignSubareaInformeUser> listaAsignaciones = new ArrayList<>();
        
        when(asignSubareaInformeUserService.findByInforme(informe)).thenReturn(listaAsignaciones);
        
        informeBean.desasignarSubarea(subarea);
        
        verify(informeService, times(1)).guardarInforme(informe, mapaRespuestas, mapaAsignaciones);
        verify(asignSubareaInformeUserService, times(1)).deleteBySubareaAndInforme(subarea, informe);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.InformeBean#autocompletarInspeccion(java.lang.String)}.
     */
    @Test
    public void testAutocompletarInspeccion() {
        String infoInspeccion = "1/2017";
        
        informeBean.autocompletarInspeccion(infoInspeccion);
        
        verify(inspeccionService, times(1)).buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.InformeBean#autocompletarInspeccion(java.lang.String)}.
     */
    @Test
    public void testAutocompletarInspeccionInspector() {
        userLogin.setRole(RoleEnum.ROLE_EQUIPO_INSPECCIONES);
        String infoInspeccion = "1/2017";
        
        informeBean.autocompletarInspeccion(infoInspeccion);
        
        verify(inspeccionService, times(1)).buscarNoFinalizadaPorNombreUnidadONumeroYMiembroEquipo(infoInspeccion,
                userLogin.getUsername());
    }
    
}
