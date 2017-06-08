package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.Departamento;
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
     * Test method for {@link es.mira.progesin.services.DepartamentoService#delete(Long)}.
     */
    @Test
    public void delete() {
        service.delete(1L);
        verify(repository, times(1)).delete(1L);
    }
    
}
