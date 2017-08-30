/**
 * 
 */
package es.mira.progesin.web.beans.cuestionarios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.primefaces.model.SortOrder;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelCuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;

/**
 * 
 * Test del bean CuestionarioPersonalizadoBean.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class CuestionarioPersonalizadoBeanTest {
    
    /**
     * Constante user.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
    /**
     * Constante nombre cuestionario.
     */
    private static final String NOMBRECUESTIONARIO = "cuestionario_test";
    
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
     * Simulación del servicio de cuestionarios personalizados.
     */
    @Mock
    private ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;
    
    /**
     * Simulación del bean de cuestionarios personalizados.
     */
    @InjectMocks
    private CuestionarioPersonalizadoBean cuestionarioPersonalizadoBean;
    
    /**
     * Simulación del servicio de cuestionarios envio.
     */
    @Mock
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Simulación del bean de cuestionarios envio.
     */
    private EnvioCuestionarioBean envioCuestionarioBean;
    
    /**
     * Lazy Model para la consulta paginada de guías personalizadas en base de datos.
     */
    private LazyModelCuestionarioPersonalizado model;
    
    /**
     * Simulación del servicio de documentos.
     */
    @Mock
    private IDocumentoService documentoService;
    
    /**
     * Mock del Servicio de registro de actividad.
     */
    @Mock
    transient IRegistroActividadService regActividadService;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(CuestionarioPersonalizadoBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        CuestionarioPersonalizadoBean target = new CuestionarioPersonalizadoBean();
        assertThat(target).isNotNull();
    }
    
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
        model = new LazyModelCuestionarioPersonalizado(cuestionarioPersonalizadoService);
        envioCuestionarioBean = new EnvioCuestionarioBean();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBean#buscarCuestionario()}.
     */
    @Test
    public final void testBuscarCuestionario() {
        List<CuestionarioPersonalizado> cuestionariosPersonalizados = new ArrayList<>();
        cuestionariosPersonalizados.add(mock(CuestionarioPersonalizado.class));
        CuestionarioPersonalizadoBusqueda busqueda = new CuestionarioPersonalizadoBusqueda();
        cuestionarioPersonalizadoBean.setModel(model);
        cuestionarioPersonalizadoBean.setCuestionarioBusqueda(busqueda);
        when(cuestionarioPersonalizadoService.getCountCuestionarioCriteria(busqueda)).thenReturn(1);
        when(cuestionarioPersonalizadoService.buscarCuestionarioPersonalizadoCriteria(0, Constantes.TAMPAGINA,
                "fechaCreacion", SortOrder.DESCENDING, busqueda)).thenReturn(cuestionariosPersonalizados);
        
        cuestionarioPersonalizadoBean.buscarCuestionario();
        verify(cuestionarioPersonalizadoService, times(1)).getCountCuestionarioCriteria(busqueda);
        verify(cuestionarioPersonalizadoService, times(1)).buscarCuestionarioPersonalizadoCriteria(0,
                Constantes.TAMPAGINA, "fechaCreacion", SortOrder.DESCENDING, busqueda);
        assertThat(cuestionarioPersonalizadoBean.getModel().getDatasource()).hasSize(1);
        assertThat(cuestionarioPersonalizadoBean.getModel().getRowCount()).isEqualTo(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBean#getFormBusquedaModelosCuestionario()}.
     */
    @Test
    public final void testGetFormBusquedaModelosCuestionario() {
        model.setRowCount(2);
        cuestionarioPersonalizadoBean.setModel(model);
        String redireccion = cuestionarioPersonalizadoBean.getFormBusquedaModelosCuestionario();
        assertThat(cuestionarioPersonalizadoBean.getCuestionarioBusqueda()).isNotNull();
        assertThat(cuestionarioPersonalizadoBean.getModel().getRowCount()).isEqualTo(0);
        assertThat(redireccion).isEqualTo("/cuestionarios/busquedaModelosCuestionarios?faces-redirect=true");
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBean#limpiar()}.
     */
    @Test
    public final void testLimpiar() {
        model.setRowCount(2);
        cuestionarioPersonalizadoBean.setModel(model);
        cuestionarioPersonalizadoBean.limpiar();
        assertThat(cuestionarioPersonalizadoBean.getCuestionarioBusqueda()).isNotNull();
        assertThat(cuestionarioPersonalizadoBean.getModel().getRowCount()).isEqualTo(0);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBean#eliminarCuestionario(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     */
    @Test
    public final void testEliminarCuestionarioBajaLogica() {
        CuestionarioPersonalizado cuestionario = new CuestionarioPersonalizado();
        cuestionario.setId(1L);
        cuestionario.setNombreCuestionario(NOMBRECUESTIONARIO);
        User user = new User();
        user.setUsername(USUARIOLOGUEADO);
        user.setRole(RoleEnum.ROLE_ADMIN);
        when(authentication.getPrincipal()).thenReturn(user);
        when(cuestionarioEnvioService.existsByCuestionarioPersonalizado(cuestionario)).thenReturn(true);
        
        cuestionarioPersonalizadoBean.eliminarCuestionario(cuestionario);
        verify(cuestionarioPersonalizadoService, times(1)).save(cuestionario);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_INFO), any(String.class), any(String.class),
                eq(null));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBean#eliminarCuestionario(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     */
    @Test
    public final void testEliminarCuestionarioBajaFisica() {
        CuestionarioPersonalizado cuestionario = new CuestionarioPersonalizado();
        cuestionario.setId(1L);
        cuestionario.setNombreCuestionario(NOMBRECUESTIONARIO);
        User user = new User();
        user.setUsername(USUARIOLOGUEADO);
        user.setRole(RoleEnum.ROLE_ADMIN);
        when(authentication.getPrincipal()).thenReturn(user);
        when(cuestionarioEnvioService.existsByCuestionarioPersonalizado(cuestionario)).thenReturn(false);
        
        cuestionarioPersonalizadoBean.eliminarCuestionario(cuestionario);
        verify(cuestionarioPersonalizadoService, times(1)).delete(cuestionario);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_INFO), any(String.class), any(String.class),
                eq(null));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBean#eliminarCuestionario(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     */
    @Test
    public final void testEliminarCuestionarioRolNoPermitido() {
        CuestionarioPersonalizado cuestionario = new CuestionarioPersonalizado();
        cuestionario.setId(1L);
        cuestionario.setNombreCuestionario(NOMBRECUESTIONARIO);
        User user = new User();
        user.setUsername(USUARIOLOGUEADO);
        user.setRole(RoleEnum.ROLE_EQUIPO_INSPECCIONES);
        when(authentication.getPrincipal()).thenReturn(user);
        
        cuestionarioPersonalizadoBean.eliminarCuestionario(cuestionario);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_WARN), any(String.class), any(String.class),
                eq(null));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBean#eliminarCuestionario(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     */
    @Test
    public final void testEliminarCuestionarioException() {
        CuestionarioPersonalizado cuestionario = new CuestionarioPersonalizado();
        cuestionario.setId(1L);
        cuestionario.setNombreCuestionario(NOMBRECUESTIONARIO);
        User user = new User();
        user.setUsername(USUARIOLOGUEADO);
        user.setRole(RoleEnum.ROLE_ADMIN);
        when(authentication.getPrincipal()).thenReturn(user);
        when(cuestionarioEnvioService.existsByCuestionarioPersonalizado(cuestionario)).thenReturn(false);
        doThrow(TransientDataAccessResourceException.class).when(cuestionarioPersonalizadoService).delete(cuestionario);
        
        cuestionarioPersonalizadoBean.eliminarCuestionario(cuestionario);
        verify(cuestionarioPersonalizadoService, times(1)).delete(cuestionario);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class), eq(null));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBean#mostrarFormularioEnvio(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     */
    @Test
    public final void testMostrarFormularioEnvio() {
        CuestionarioPersonalizado cuestionario = new CuestionarioPersonalizado();
        cuestionario.setId(1L);
        List<Documento> documentos = new ArrayList<>();
        documentos.add(mock(Documento.class));
        cuestionarioPersonalizadoBean.setEnvioCuestionarioBean(envioCuestionarioBean);
        
        when(documentoService.buscaNombreTipoDocumento("PLANTILLA CUESTIONARIO")).thenReturn(documentos);
        String redireccion = cuestionarioPersonalizadoBean.mostrarFormularioEnvio(cuestionario);
        
        assertThat(cuestionarioPersonalizadoBean.getEnvioCuestionarioBean().getListaPlantillas()).isEqualTo(documentos);
        assertThat(cuestionarioPersonalizadoBean.getEnvioCuestionarioBean().getCuestionarioEnvio()
                .getCuestionarioPersonalizado()).isEqualTo(cuestionario);
        
        assertThat(redireccion).isEqualTo("/cuestionarios/enviarCuestionario?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBean#mostrarFormularioEnvio(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     */
    @Test
    public final void testMostrarFormularioEnvioFechaBajaNotNull() {
        CuestionarioPersonalizado cuestionario = new CuestionarioPersonalizado();
        cuestionario.setId(1L);
        cuestionario.setFechaBaja(new Date());
        String redireccion = cuestionarioPersonalizadoBean.mostrarFormularioEnvio(cuestionario);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_WARN), any(String.class), any(String.class),
                eq(null));
        assertThat(redireccion).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBean#init()}.
     */
    @Test
    public final void testInit() {
        cuestionarioPersonalizadoBean.init();
        assertThat(cuestionarioPersonalizadoBean.getCuestionarioBusqueda()).isNotNull();
        assertThat(cuestionarioPersonalizadoBean.getModel()).isNotNull();
        
    }
    
}
