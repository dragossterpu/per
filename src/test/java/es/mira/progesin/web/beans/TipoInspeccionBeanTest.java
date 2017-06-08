package es.mira.progesin.web.beans;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
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
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IGuiaService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test del controlador de Tipos de Inspeccion.
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class TipoInspeccionBeanTest {
    /**
     * Lista de tipos de inspección.
     */
    private List<TipoInspeccion> listaTipoInspeccion;
    
    /**
     * Simulación del tipo de inspeccion.
     */
    @Mock
    private transient ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Simulación del servicio del registro de actividad.
     * 
     */
    @Mock
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Simulación del servicio del registro de actividad.
     * 
     */
    @Mock
    private transient IInspeccionesService inspeccionesService;
    
    /**
     * Simulación del servicio de guías.
     * 
     */
    @Mock
    private transient IGuiaService guiaService;
    
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
     * Bean de tipo de inspeccion.
     */
    @InjectMocks
    private TipoInspeccionBean tipoInspeccionBean;
    
    /**
     * Capturador del tipo de inspeccion creado.
     */
    @Captor
    private ArgumentCaptor<TipoInspeccion> tipoCaptor;
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioLogueado");
        listaTipoInspeccion = new ArrayList<>();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoInspeccionBean#eliminarTipo(TipoInspeccion)}.
     */
    @Test
    public void eliminarTipo_existeEnInspeccion() {
        TipoInspeccion tipo = TipoInspeccion.builder().codigo("test").build();
        when(inspeccionesService.existeByTipoInspeccion(tipo)).thenReturn(true);
        
        tipoInspeccionBean.eliminarTipo(tipo);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
    };
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoInspeccionBean#eliminarTipo(TipoInspeccion)}.
     */
    @Test
    public void eliminarTipo_existeEnGuia() {
        TipoInspeccion tipo = TipoInspeccion.builder().codigo("test").build();
        when(guiaService.existeByTipoInspeccion(tipo)).thenReturn(true);
        
        tipoInspeccionBean.eliminarTipo(tipo);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
    };
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoInspeccionBean#eliminarTipo(TipoInspeccion)}.
     */
    @Test
    public void eliminarTipo_noExisteEnInspeccion() {
        TipoInspeccion tipo = TipoInspeccion.builder().codigo("test").build();
        listaTipoInspeccion.add(tipo);
        tipoInspeccionBean.setListaTipoInspeccion(listaTipoInspeccion);
        when(inspeccionesService.existeByTipoInspeccion(tipo)).thenReturn(false);
        
        tipoInspeccionBean.eliminarTipo(tipo);
        
        verify(tipoInspeccionService, times(1)).borrarTipo(tipo);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_INFO), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoInspeccionBean#eliminarTipo(TipoInspeccion)}.
     */
    @Test
    public void eliminarTipo_noExisteEnGuia() {
        TipoInspeccion tipo = TipoInspeccion.builder().codigo("test").build();
        listaTipoInspeccion.add(tipo);
        tipoInspeccionBean.setListaTipoInspeccion(listaTipoInspeccion);
        when(guiaService.existeByTipoInspeccion(tipo)).thenReturn(false);
        
        tipoInspeccionBean.eliminarTipo(tipo);
        
        verify(tipoInspeccionService, times(1)).borrarTipo(tipo);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_INFO), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoInspeccionBean#eliminarTipo(TipoInspeccion)}.
     */
    @Test
    public void eliminarTipo_excepcion() {
        TipoInspeccion tipo = mock(TipoInspeccion.class);
        doThrow(TransientDataAccessResourceException.class).when(tipoInspeccionService).borrarTipo(tipo);
        
        tipoInspeccionBean.eliminarTipo(tipo);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.name()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoInspeccionBean#onRowEdit(RowEditEvent)}.
     */
    @Test
    public void onRowEdit() {
        TipoInspeccion tipo = mock(TipoInspeccion.class);
        RowEditEvent evento = mock(RowEditEvent.class);
        when(evento.getObject()).thenReturn(tipo);
        
        tipoInspeccionBean.onRowEdit(evento);
        
        verify(tipoInspeccionService, times(1)).guardarTipo(tipo);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.INSPECCION.name()));
    };
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoInspeccionBean#onRowEdit(RowEditEvent)}.
     */
    @Test
    public void onRowEdit_excepcion() {
        TipoInspeccion tipo = mock(TipoInspeccion.class);
        RowEditEvent evento = mock(RowEditEvent.class);
        when(evento.getObject()).thenReturn(tipo);
        when(tipoInspeccionService.guardarTipo(tipo)).thenThrow(TransientDataAccessResourceException.class);
        
        tipoInspeccionBean.onRowEdit(evento);
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.name()),
                any(TransientDataAccessResourceException.class));
    };
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoInspeccionBean#altaTipo()}.
     */
    @Test
    public void altaTipo() {
        tipoInspeccionBean.setListaTipoInspeccion(listaTipoInspeccion);
        tipoInspeccionBean.altaTipo("codigo", "descripcion");
        
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.INSPECCION.name()));
    };
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoInspeccionBean#altaTipo()}.
     */
    @Test
    public void altaTipo_excepcion() {
        when(tipoInspeccionService.guardarTipo(tipoCaptor.capture()))
                .thenThrow(TransientDataAccessResourceException.class);
        
        tipoInspeccionBean.altaTipo("codigo", "descripcion");
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.INSPECCION.name()),
                any(TransientDataAccessResourceException.class));
    };
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoInspeccionBean#init()}.
     */
    @Test
    public void init() {
        tipoInspeccionBean.init();
        verify(tipoInspeccionService, times(1)).buscaTodos();
    }
    
}
