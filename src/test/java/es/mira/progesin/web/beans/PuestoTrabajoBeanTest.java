package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.TransientDataAccessResourceException;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IPuestoTrabajoService;
import es.mira.progesin.services.IRegistroActividadService;
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
    private static final String DESCRIPCIONPUESTO = "descripcionPuestoTest";
    
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
     * Variable utilizada para inyectar el servicio de registro de actividad.
     * 
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Captor de tipo PuestoTrabajo.
     */
    @Captor
    private ArgumentCaptor<PuestoTrabajo> puestoTrabajoCaptor;
    
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
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.ADMINISTRACION.getDescripcion()));
        assertThat(puestoTrabajoBean.getListaPuestosTrabajo()).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.PuestoTrabajoBean#eliminarPuesto(es.mira.progesin.persistence.entities.PuestoTrabajo)}
     * .
     */
    @Test
    public void eliminarPuestoExceptionData() {
        PuestoTrabajo puesto = PuestoTrabajo.builder().id(1L).build();
        when(userService.existsByPuestoTrabajo(puesto)).thenReturn(false);
        doThrow(TransientDataAccessResourceException.class).when(puestoTrabajoService).delete(puesto);
        puestoTrabajoBean.eliminarPuesto(puesto);
        
        verify(puestoTrabajoService, times(1)).delete(puesto);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.PuestoTrabajoBean#altaPuesto(String)}.
     */
    @Test
    public void altaPuesto() {
        String descripcionPuesto = DESCRIPCIONPUESTO;
        puestoTrabajoBean.altaPuesto(descripcionPuesto);
        verify(puestoTrabajoService, times(1)).save(puestoTrabajoCaptor.capture());
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.ADMINISTRACION.getDescripcion()));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.PuestoTrabajoBean#altaPuesto(String)}.
     */
    @Test
    public void altaPuestoDataException() {
        String descripcionPuesto = DESCRIPCIONPUESTO;
        doThrow(TransientDataAccessResourceException.class).when(puestoTrabajoService)
                .save(puestoTrabajoCaptor.capture());
        puestoTrabajoBean.altaPuesto(descripcionPuesto);
        verify(puestoTrabajoService, times(1)).save(puestoTrabajoCaptor.capture());
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.PuestoTrabajoBean#onRowEdit(org.primefaces.event.RowEditEvent)}.
     * 
     */
    
    @Test
    public void onRowEdit() {
        PuestoTrabajo puesto = PuestoTrabajo.builder().id(1L).descripcion(DESCRIPCIONPUESTO).build();
        RowEditEvent event = mock(RowEditEvent.class);
        when(event.getObject()).thenReturn(puesto);
        puestoTrabajoBean.onRowEdit(event);
        verify(puestoTrabajoService, times(1)).save(puesto);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                eq(puesto.getDescripcion()), eq(null));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.ADMINISTRACION.getDescripcion()));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.PuestoTrabajoBean#onRowEdit(org.primefaces.event.RowEditEvent)}.
     * 
     */
    
    @Test
    public void onRowEditException() {
        PuestoTrabajo puesto = PuestoTrabajo.builder().id(1L).descripcion(DESCRIPCIONPUESTO).build();
        RowEditEvent event = mock(RowEditEvent.class);
        when(event.getObject()).thenReturn(puesto);
        doThrow(TransientDataAccessResourceException.class).when(puestoTrabajoService).save(puesto);
        puestoTrabajoBean.onRowEdit(event);
        verify(puestoTrabajoService, times(1)).save(puesto);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class), eq(null));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.ADMINISTRACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.PuestoTrabajoBean#init()}.
     */
    @Test
    public void init() {
        List<PuestoTrabajo> puestos = new ArrayList<>();
        puestos.add(mock(PuestoTrabajo.class));
        when(puestoTrabajoService.findAll()).thenReturn(puestos);
        puestoTrabajoBean.init();
        verify(puestoTrabajoService, times(1)).findAll();
        assertThat(puestoTrabajoBean.getListaPuestosTrabajo()).isEqualTo(puestos);
    }
    
}
