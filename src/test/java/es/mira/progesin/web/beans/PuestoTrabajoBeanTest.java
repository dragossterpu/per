package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.services.IPuestoTrabajoService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;

/**
 * 
 * Test para el bean de puestos de trabajo.
 *
 * @author EZENTIS
 * 
 */
@PrepareForTest(FacesUtilities.class)
@RunWith(PowerMockRunner.class)
public class PuestoTrabajoBeanTest {
    
    /**
     * Variable utilizada para inyectar el servicio de puestos de trabajo.
     * 
     */
    @Mock
    private IPuestoTrabajoService puestoTrabajoService;
    
    /**
     * Variable utilizada para inyectar el servicio de usuarios.
     * 
     */
    @Mock
    private IUserService userService;
    
    /**
     * Bean puestos de trabajo.
     */
    @InjectMocks
    private PuestoTrabajoBean puestoTrabajoBean;
    
    /**
     * Comprueba que existe la clase.
     */
    @Test
    public void type() {
        assertThat(PuestoTrabajoBean.class).isNotNull();
    }
    
    /**
     * Comprueba que no es una clase abstracta.
     */
    @Test
    public void instantiation() {
        PuestoTrabajoBean target = new PuestoTrabajoBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Método que se ejecuta antes de realizar cualquier método del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        List<PuestoTrabajo> listaPuestosTrabajo = new ArrayList<>();
        listaPuestosTrabajo.add(PuestoTrabajo.builder().id(1L).build());
        listaPuestosTrabajo.add(PuestoTrabajo.builder().id(2L).build());
        listaPuestosTrabajo.add(PuestoTrabajo.builder().id(3L).build());
        List<PuestoTrabajo> listaPuestosSpy = spy(listaPuestosTrabajo);
        puestoTrabajoBean.setListaPuestosTrabajo(listaPuestosSpy);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.PuestoTrabajoBean#eliminarPuesto(es.mira.progesin.persistence.entities.PuestoTrabajo)}
     * .
     */
    @Test
    public void eliminarPuesto_conUsuarios() {
        PuestoTrabajo puesto = mock(PuestoTrabajo.class);
        when(userService.existsByPuestoTrabajo(puesto)).thenReturn(true);
        
        puestoTrabajoBean.eliminarPuesto(puesto);
        
        // Comprobamos que se muestra el mensaje en pantalla
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.PuestoTrabajoBean#eliminarPuesto(es.mira.progesin.persistence.entities.PuestoTrabajo)}
     * .
     */
    @Test
    public void eliminarPuesto_sinUsuarios() {
        PuestoTrabajo puesto = PuestoTrabajo.builder().id(1L).build();
        when(userService.existsByPuestoTrabajo(puesto)).thenReturn(false);
        
        puestoTrabajoBean.eliminarPuesto(puesto);
        
        verify(puestoTrabajoService, times(1)).delete(puesto);
        assertThat(puestoTrabajoBean.getListaPuestosTrabajo()).hasSize(2);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.PuestoTrabajoBean#altaPuesto(String)}.
     */
    @Ignore
    @Test
    public void altaPuesto() {
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.PuestoTrabajoBean#onRowEdit(org.primefaces.event.RowEditEvent)}
     * .
     */
    @Ignore
    @Test
    public void onRowEdit() {
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.PuestoTrabajoBean#init()} .
     */
    @Ignore
    @Test
    public void init() {
        
    }
    
}
