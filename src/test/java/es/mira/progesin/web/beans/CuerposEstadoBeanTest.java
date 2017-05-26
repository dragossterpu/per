package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;

/**
 * @author EZENTIS
 * 
 * Test del bean Cuerpos Estado
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })

public class CuerposEstadoBeanTest {
    
    private List<CuerpoEstado> listaCuerposEstado;
    
    @Mock
    private SecurityContext securityContext;
    
    @Mock
    private Authentication authentication;
    
    @Mock
    private transient ICuerpoEstadoService cuerposEstadoService;
    
    @Mock
    private transient IUserService userService;
    
    @Mock
    private transient IRegistroActividadService regActividadService;
    
    @InjectMocks
    private CuerposEstadoBean cuerposEstadoBean;
    
    @Captor
    private ArgumentCaptor<CuerpoEstado> cuerpoCaptor;
    
    @Captor
    private ArgumentCaptor<Exception> exceptionCaptor;
    
    /**
     * Comprueba que la clase existe
     * @throws Exception
     */
    @Test
    public void type() throws Exception {
        assertThat(CuerposEstadoBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar
     * @throws Exception
     */
    @Test
    public void instantiation() throws Exception {
        CuerposEstadoBean target = new CuerposEstadoBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Inicializa el test
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
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#eliminarCuerpo}.
     */
    @Test
    public void eliminarCuerpo_conUsuarios() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).descripcion("Cuerpo Test").build();
        when(userService.existByCuerpoEstado(cuerpo)).thenReturn(true);
        
        cuerposEstadoBean.eliminarCuerpo(cuerpo);
        
        verify(userService, times(1)).existByCuerpoEstado(cuerpo);
        verify(cuerposEstadoService, times(0)).save(cuerpo);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#eliminarCuerpo}.
     */
    @Test
    public void eliminarCuerpo_sinUsuarios() {
        listaCuerposEstado = new ArrayList<>();
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).descripcion("Cuerpo Test").build();
        listaCuerposEstado.add(cuerpo);
        cuerposEstadoBean.setListaCuerposEstado(listaCuerposEstado);
        when(userService.existByCuerpoEstado(cuerpo)).thenReturn(false);
        
        cuerposEstadoBean.eliminarCuerpo(cuerpo);
        listaCuerposEstado.remove(cuerpo);
        
        regActividadService.altaRegActividad(any(String.class), TipoRegistroEnum.ALTA.name(),
                SeccionesEnum.ADMINISTRACION.name());
        
        verify(userService, times(1)).existByCuerpoEstado(cuerpo);
        verify(cuerposEstadoService, times(1)).save(cuerpo);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.ADMINISTRACION.name()));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#eliminarCuerpo}.
     */
    @Test
    public void eliminarCuerpo_excepcion() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).descripcion("Cuerpo Test").build();
        when(userService.existByCuerpoEstado(cuerpo)).thenThrow(Exception.class);
        
        cuerposEstadoBean.eliminarCuerpo(cuerpo);
        
        verify(userService, times(1)).existByCuerpoEstado(cuerpo);
        verify(cuerposEstadoService, times(0)).save(cuerpo);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.name()),
                exceptionCaptor.capture());
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#altaCuerpo}.
     */
    @Test
    public void altaCuerpo() {
        
        cuerposEstadoBean.altaCuerpo("TEST", "Cuerpo Test");
        
        verify(cuerposEstadoService, times(1)).save(cuerpoCaptor.capture());
        assertThat(cuerpoCaptor.getValue().getDescripcion()).isEqualTo("Cuerpo Test");
        
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.ADMINISTRACION.name()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#altaCuerpo}.
     */
    @Test
    public void altaCuerpo_excepcion() {
        when(cuerposEstadoService.save(cuerpoCaptor.capture())).thenThrow(SQLException.class);
        cuerposEstadoBean.altaCuerpo("TEST", "Cuerpo Test");
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.name()),
                any(SQLException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#onRowEdit}.
     */
    @Test
    public void onRowEdit() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(7).descripcion("Cuerpo Test").build();
        RowEditEvent event = mock(RowEditEvent.class);
        when(event.getObject()).thenReturn(cuerpo);
        
        cuerposEstadoBean.onRowEdit(event);
        regActividadService.altaRegActividad(any(String.class), TipoRegistroEnum.MODIFICACION.name(),
                SeccionesEnum.ADMINISTRACION.name());
        
        verify(cuerposEstadoService, times(1)).save(cuerpoCaptor.capture());
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.ADMINISTRACION.name()));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#onRowEdit}.
     */
    @Test
    public void onRowEdit_excepcion() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(7).descripcion("Cuerpo Test").build();
        RowEditEvent event = mock(RowEditEvent.class);
        
        cuerposEstadoBean.onRowEdit(event);
        when(cuerposEstadoService.save(cuerpo)).thenThrow(Exception.class);
        
        verify(cuerposEstadoService, times(0)).save(cuerpoCaptor.capture());
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.name()),
                exceptionCaptor.capture());
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.CuerposEstadoBean#init}.
     */
    @Test
    public void init() {
        cuerposEstadoBean.init();
        verify(cuerposEstadoService, times(1)).findByFechaBajaIsNull();
        
    }
    
}
