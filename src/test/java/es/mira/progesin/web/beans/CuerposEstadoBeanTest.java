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
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.enums.AdministracionAccionEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IEmpleoRepository;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;

/**
 * 
 * Test del bean Cuerpos Estado.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })

public class CuerposEstadoBeanTest {
    /**
     * Lista de cuerpos de estado.
     */
    private List<CuerpoEstado> listaCuerposEstado;
    
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
     * Simulación del servicio de cuerpo de estado.
     */
    @Mock
    private transient ICuerpoEstadoService cuerposEstadoService;
    
    /**
     * Simulación del servicio de usuarios.
     */
    @Mock
    private transient IUserService userService;
    
    /**
     * Simulación del servicio de registro de actividad.
     */
    @Mock
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Simulación del repositorio de empleos.
     */
    @Mock
    private transient IEmpleoRepository empleoRepository;
    
    /**
     * Bean de cuerpos de estado.
     */
    @InjectMocks
    private CuerposEstadoBean cuerposEstadoBean;
    
    /**
     * Captor de tipo CuerpoEstado.
     */
    @Captor
    private ArgumentCaptor<CuerpoEstado> cuerpoCaptor;
    
    /**
     * Captor de excepciones.
     */
    @Captor
    private ArgumentCaptor<Exception> exceptionCaptor;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(CuerposEstadoBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        CuerposEstadoBean target = new CuerposEstadoBean();
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
        when(authentication.getName()).thenReturn("usuarioLogueado");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#eliminarCuerpo(CuerpoEstado)}.
     */
    @Test
    public void eliminarCuerpo_conUsuariosYEmpleos() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).descripcion("Cuerpo Test").build();
        when(userService.existsByCuerpoEstado(cuerpo)).thenReturn(true);
        when(empleoRepository.existsByCuerpo(cuerpo)).thenReturn(true);
        
        cuerposEstadoBean.eliminarCuerpo(cuerpo);
        
        verify(userService, times(1)).existsByCuerpoEstado(cuerpo);
        verify(empleoRepository, times(1)).existsByCuerpo(cuerpo);
        verify(cuerposEstadoService, times(0)).delete(cuerpo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#eliminarCuerpo(CuerpoEstado)}.
     */
    @Test
    public void eliminarCuerpo_sinUsuariosNiEmpleos() {
        listaCuerposEstado = new ArrayList<>();
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).descripcion("Cuerpo Test").build();
        listaCuerposEstado.add(cuerpo);
        cuerposEstadoBean.setListaCuerposEstado(listaCuerposEstado);
        when(userService.existsByCuerpoEstado(cuerpo)).thenReturn(false);
        when(empleoRepository.existsByCuerpo(cuerpo)).thenReturn(false);
        
        cuerposEstadoBean.eliminarCuerpo(cuerpo);
        
        verify(userService, times(1)).existsByCuerpoEstado(cuerpo);
        verify(empleoRepository, times(1)).existsByCuerpo(cuerpo);
        verify(cuerposEstadoService, times(1)).delete(cuerpo);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.ADMINISTRACION.name()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#eliminarCuerpo(CuerpoEstado)}.
     */
    @Test
    public void eliminarCuerpo_excepcion() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).descripcion("Cuerpo Test").build();
        when(userService.existsByCuerpoEstado(cuerpo)).thenThrow(TransientDataAccessResourceException.class);
        
        cuerposEstadoBean.eliminarCuerpo(cuerpo);
        
        verify(userService, times(1)).existsByCuerpoEstado(cuerpo);
        verify(cuerposEstadoService, times(0)).delete(cuerpo);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.name()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#altaCuerpo(String, String)}.
     */
    @Test
    public void altaCuerpo() {
        
        cuerposEstadoBean.altaCuerpo("TEST", "Cuerpo Test");
        
        verify(cuerposEstadoService, times(1)).save(cuerpoCaptor.capture(), eq(AdministracionAccionEnum.ALTA));
        assertThat(cuerpoCaptor.getValue().getDescripcion()).isEqualTo("Cuerpo Test");
        
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.ADMINISTRACION.name()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#altaCuerpo(String, String)}.
     */
    @Test
    public void altaCuerpo_excepcion() {
        when(cuerposEstadoService.save(cuerpoCaptor.capture(), eq(AdministracionAccionEnum.ALTA)))
                .thenThrow(TransientDataAccessResourceException.class);
        
        cuerposEstadoBean.altaCuerpo("TEST", "Cuerpo Test");
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.name()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#onRowEdit(RowEditEvent)}.
     */
    @Test
    public void onRowEdit() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(7).descripcion("Cuerpo Test").build();
        RowEditEvent event = mock(RowEditEvent.class);
        when(event.getObject()).thenReturn(cuerpo);
        
        cuerposEstadoBean.onRowEdit(event);
        
        verify(cuerposEstadoService, times(1)).save(cuerpo, AdministracionAccionEnum.MODIFICACION);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.ADMINISTRACION.name()));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#onRowEdit(RowEditEvent)}.
     */
    @Test
    public void onRowEdit_excepcion() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(7).descripcion("Cuerpo Test").build();
        RowEditEvent event = mock(RowEditEvent.class);
        when(event.getObject()).thenReturn(cuerpo);
        when(cuerposEstadoService.save(cuerpo, AdministracionAccionEnum.MODIFICACION))
                .thenThrow(TransientDataAccessResourceException.class);
        
        cuerposEstadoBean.onRowEdit(event);
        
        verify(cuerposEstadoService, times(1)).save(cuerpo, AdministracionAccionEnum.MODIFICACION);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.name()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#init()}.
     */
    @Test
    public void init() {
        cuerposEstadoBean.init();
        
        verify(cuerposEstadoService, times(1)).findAll();
    }
    
}
