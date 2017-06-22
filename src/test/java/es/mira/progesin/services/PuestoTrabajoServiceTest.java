package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.repositories.IPuestoTrabajoRepository;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * 
 * Test para el servicio de puestos de trabajo.
 *
 * @author EZENTIS
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class PuestoTrabajoServiceTest {
    
    /**
     * Mock del repositorio de puestos de trabajo.
     */
    @Mock
    private IPuestoTrabajoRepository puestroTrabajoRepository;
    
    /**
     * Mock del ApplicationBean.
     */
    @Mock
    private ApplicationBean applicationBean;
    
    /**
     * Inyección del servicio de usuarios.
     */
    @InjectMocks
    private IPuestoTrabajoService puestoTrabajoService = new PuestoTrabajoService();
    
    /**
     * Comprueba que existe la clase.
     */
    @Test
    public void type() {
        assertThat(PuestoTrabajoService.class).isNotNull();
    }
    
    /**
     * Comprueba que no es una clase abstracta.
     */
    @Test
    public void instantiation() {
        PuestoTrabajoService target = new PuestoTrabajoService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.PuestoTrabajoService#findAll()}.
     */
    @Test
    public void findAll() {
        puestoTrabajoService.findAll();
        verify(puestroTrabajoRepository, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.PuestoTrabajoService#save(PuestoTrabajo)}.
     */
    @Test
    public void save_alta() {
        PuestoTrabajo puesto = new PuestoTrabajo();
        puesto.setId(100L);
        puesto.setDescripcion("puesto");
        puesto.setFechaAlta(new Date());
        puesto.setUsernameAlta("ezentis");
        puestoTrabajoService.save(puesto);
        verify(puestroTrabajoRepository, times(1)).save(puesto);
        verify(applicationBean, times(1)).setListaPuestosTrabajo(ArgumentMatchers.anyList());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.PuestoTrabajoService#save(PuestoTrabajo)}.
     */
    @Test
    public void save_modificacion() {
        PuestoTrabajo puesto = new PuestoTrabajo();
        puesto.setId(1L);
        puesto.setDescripcion("puesto");
        puesto.setFechaModif(new Date());
        puesto.setUsernameModif("ezentis");
        puestoTrabajoService.save(puesto);
        verify(puestroTrabajoRepository, times(1)).save(puesto);
        verify(applicationBean, times(1)).setListaPuestosTrabajo(ArgumentMatchers.anyList());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.PuestoTrabajoService#delete(PuestoTrabajo)}.
     */
    @Test
    public void delete() {
        PuestoTrabajo puestoMock = mock(PuestoTrabajo.class);
        puestoTrabajoService.delete(puestoMock);
        verify(puestroTrabajoRepository, times(1)).delete(puestoMock);
        verify(applicationBean, times(1)).setListaPuestosTrabajo(ArgumentMatchers.anyList());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.PuestoTrabajoService#findAllByOrderByDescripcionAsc()}.
     */
    @Test
    public void findAllByOrderByDescripcionAsc() {
        puestoTrabajoService.findAllByOrderByDescripcionAsc();
        verify(puestroTrabajoRepository, times(1)).findAllByOrderByDescripcionAsc();
    }
    
}
