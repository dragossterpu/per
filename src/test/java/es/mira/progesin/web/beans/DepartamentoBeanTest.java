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

import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.services.IDepartamentoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test para DepartamentoBean.
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)

@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class DepartamentoBeanTest {
    /**
     * Variable utilizada para almacenar los departamentos.
     * 
     */
    private List<Departamento> listaDepartamentos;
    
    /**
     * Simulación del servicio de departamento.
     */
    @Mock
    private IDepartamentoService departamentoService;
    
    /**
     * Simulación del servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
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
     * Bean de departamento.
     */
    @InjectMocks
    private DepartamentoBean departamentoBean;
    
    /**
     * Captor de departamento.
     */
    @Captor
    private ArgumentCaptor<Departamento> departamentoCaptor;
    
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
     * Test method for {@link es.mira.progesin.web.beans.DepartamentoBean#eliminarDepartamento(Departamento)}.
     */
    @Test
    public void eliminarDepartamento_conUsuarios() {
        Departamento departamento = Departamento.builder().id(1L).descripcion("DepartamentoTest").build();
        when(departamentoService.existenUsuariosDepartamento(departamento)).thenReturn(true);
        
        departamentoBean.eliminarDepartamento(departamento);
        
        verify(departamentoService, times(1)).existenUsuariosDepartamento(departamento);
        verify(departamentoService, times(0)).save(departamento);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.DepartamentoBean#eliminarDepartamento(Departamento)}.
     */
    @Test
    public void eliminarDepartamento_sinUsuarios() {
        listaDepartamentos = new ArrayList<>();
        Departamento departamento = Departamento.builder().id(1L).descripcion("DepartamentoTest").build();
        listaDepartamentos.add(departamento);
        departamentoBean.setListaDepartamentos(listaDepartamentos);
        when(departamentoService.existenUsuariosDepartamento(departamento)).thenReturn(false);
        
        departamentoBean.eliminarDepartamento(departamento);
        
        verify(departamentoService, times(1)).existenUsuariosDepartamento(departamento);
        verify(departamentoService, times(1)).save(departamento);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.DepartamentoBean#altaDepartamento(String)}.
     */
    @Test
    public void altaDepartamento() {
        
        departamentoBean.altaDepartamento("Departamento test");
        
        verify(departamentoService, times(1)).save(departamentoCaptor.capture());
        assertThat(departamentoCaptor.getValue().getDescripcion()).isEqualTo("Departamento test");
        
        verify(regActividadService, times(0)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.DepartamentoBean#altaDepartamento(String)}.
     */
    @Test
    public void altaDepartamento_excepcion() {
        when(departamentoService.save(departamentoCaptor.capture()))
                .thenThrow(TransientDataAccessResourceException.class);
        
        departamentoBean.altaDepartamento("Departamento test");
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.name()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.DepartamentoBean#onRowEdit(RowEditEvent)}.
     */
    @Test
    public void onRowEdit() {
        Departamento departamento = Departamento.builder().id(1L).descripcion("DepartamentoTest").build();
        RowEditEvent event = mock(RowEditEvent.class);
        when(event.getObject()).thenReturn(departamento);
        
        departamentoBean.onRowEdit(event);
        
        verify(departamentoService, times(1)).save(departamentoCaptor.capture());
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.DepartamentoBean#onRowEdit(RowEditEvent)}.
     */
    @Test
    public void onRowEdit_excepcion() {
        Departamento departamento = Departamento.builder().id(1L).descripcion("DepartamentoTest").build();
        RowEditEvent event = mock(RowEditEvent.class);
        when(event.getObject()).thenReturn(departamento);
        when(departamentoService.save(departamento)).thenThrow(TransientDataAccessResourceException.class);
        
        departamentoBean.onRowEdit(event);
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.name()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.DepartamentoBean#init()}.
     */
    @Test
    public void init() {
        departamentoBean.init();
        verify(departamentoService, times(1)).findByFechaBajaIsNull();
    }
}
