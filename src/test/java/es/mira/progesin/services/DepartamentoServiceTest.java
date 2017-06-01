package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.repositories.IDepartamentoRepository;

/**
 * Test de servicio de departamentos.
 * 
 * @author EZENTIS
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DepartamentoServiceTest {
    /**
     * Simulación del repositorio de departamentos.
     */
    @Mock
    private IDepartamentoRepository repository;
    
    /**
     * Simulación del servicio de usuarios.
     */
    @Mock
    private IUserService userService;
    
    /**
     * Servicio de departamentos.
     */
    @InjectMocks
    private IDepartamentoService service = new DepartamentoService();
    
    /**
     * Comprueba que la clase existe.
     * 
     * @throws Exception Excepción
     */
    @Test
    public void type() throws Exception {
        assertThat(Departamento.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     * 
     * @throws Exception Excepción
     */
    @Test
    public void instantiation() throws Exception {
        DepartamentoService target = new DepartamentoService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DepartamentoService#save(Departamento)}.
     */
    @Test
    public void save() {
        Departamento departamentoMock = mock(Departamento.class);
        service.save(departamentoMock);
        verify(repository, times(1)).save(departamentoMock);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DepartamentoService#findAll()}.
     */
    @Test
    public void findAll() {
        service.findAll();
        verify(repository, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DepartamentoService#findByFechaBajaIsNull()}.
     */
    @Test
    public void findByFechaBajaIsNull() {
        service.findByFechaBajaIsNull();
        verify(repository, times(1)).findByFechaBajaIsNull();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DepartamentoService#existenUsuariosDepartamento(Departamento)}.
     */
    @Test
    public void existenUsuariosDepartamento() {
        Departamento departamento = Departamento.builder().id(1L).descripcion("DepartamentoTest").build();
        User usuario = User.builder().nombre("Test").build();
        List<User> listaUsuario = new ArrayList<>();
        listaUsuario.add(usuario);
        
        when(userService.findByDepartamento(departamento)).thenReturn(listaUsuario);
        
        service.existenUsuariosDepartamento(departamento);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DepartamentoService#existenUsuariosDepartamento(Departamento)}.
     */
    @Test
    public void existenUsuariosDepartamento_vacio() {
        Departamento departamento = Departamento.builder().id(1L).descripcion("DepartamentoTest").build();
        List<User> listaUsuario = new ArrayList<>();
        
        when(userService.findByDepartamento(departamento)).thenReturn(listaUsuario);
        
        service.existenUsuariosDepartamento(departamento);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DepartamentoService#existenUsuariosDepartamento(Departamento)}.
     */
    @Test
    public void existenUsuariosDepartamento_null() {
        Departamento departamento = Departamento.builder().id(1L).descripcion("DepartamentoTest").build();
        
        when(userService.findByDepartamento(departamento)).thenReturn(null);
        
        service.existenUsuariosDepartamento(departamento);
        
    }
}
