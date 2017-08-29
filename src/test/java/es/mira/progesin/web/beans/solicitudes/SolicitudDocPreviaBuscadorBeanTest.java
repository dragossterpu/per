package es.mira.progesin.web.beans.solicitudes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelSolicitudes;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test del bean solicitudDocPreviaBuscador.
 * 
 * @author EZENTIS
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FacesUtilities.class })
public class SolicitudDocPreviaBuscadorBeanTest {
    
    /**
     * Mock del servicio de solicitudes de documentación.
     */
    @Mock
    private ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Mock del servicio de tipos de documentación.
     */
    @Mock
    private ITipoDocumentacionService tipoDocumentacionService;
    
    /**
     * Mock del bean de operaciones sobre solicitudes.
     */
    @Mock
    private SolicitudDocPreviaBean solicitudDocPreviaBean;
    
    /**
     * Instancia de prueba del bean de solicitudes de documentación.
     */
    @InjectMocks
    private SolicitudDocPreviaBuscadorBean solicitudDocPreviaBuscadorBean;
    
    /**
     * Mock LazyModel para la visualización de datos paginados en la vista.
     */
    @Mock
    private LazyModelSolicitudes model;
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(SolicitudDocPreviaBuscadorBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        SolicitudDocPreviaBuscadorBean target = new SolicitudDocPreviaBuscadorBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.solicitudDocPreviaBuscadorBean#init()}.
     */
    @Test
    public void init() {
        
        solicitudDocPreviaBuscadorBean.init();
        
        assertThat(solicitudDocPreviaBuscadorBean.getSolicitudDocPreviaBusqueda()).isNotNull();
        assertThat(solicitudDocPreviaBuscadorBean.getModel()).isNotNull();
        assertThat(solicitudDocPreviaBuscadorBean.getListaColumnToggler()).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.solicitudDocPreviaBuscadorBean#onToggle(ToggleEvent)}.
     */
    @Test
    public void onToggle() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        solicitudDocPreviaBuscadorBean.setListaColumnToggler(listaToogle);
        
        solicitudDocPreviaBuscadorBean.onToggle(eventMock);
        
        assertThat(listaToogle.get(0)).isFalse();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.solicitudDocPreviaBuscadorBean#getFormBusquedaSolicitudes()}.
     */
    @Test
    public void getFormBusquedaSolicitudes() {
        String ruta = solicitudDocPreviaBuscadorBean.getFormBusquedaSolicitudes();
        verify(model, times(1)).setRowCount(0);
        assertThat(solicitudDocPreviaBuscadorBean.getSolicitudDocPreviaBusqueda()).isNotNull();
        assertThat(ruta).isEqualTo("/solicitudesPrevia/busquedaSolicitudesDocPrevia?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBuscadorBean#getFormModificarSolicitud(SolicitudDocumentacionPrevia)}
     * .
     */
    @Test
    public void getFormModificarSolicitud_existe() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        when(solicitudDocumentacionService.findOne(1L)).thenReturn(solicitud);
        
        String ruta_vista = solicitudDocPreviaBuscadorBean.getFormModificarSolicitud(solicitud);
        
        verify(solicitudDocPreviaBean, times(1)).setSolicitudDocumentacionPrevia(solicitud);
        assertThat(ruta_vista).isEqualTo("/solicitudesPrevia/modificarSolicitud?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBuscadorBean#getFormModificarSolicitud(SolicitudDocumentacionPrevia)}
     * .
     */
    @Test
    public void getFormModificarSolicitud_noExiste() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        when(solicitudDocumentacionService.findOne(1L)).thenReturn(null);
        
        String ruta_vista = solicitudDocPreviaBuscadorBean.getFormModificarSolicitud(solicitud);
        
        assertThat(ruta_vista).isNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBuscadorBean#visualizarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void visualizarSolicitud_existe() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        when(solicitudDocumentacionService.findByIdConDocumentos(solicitud.getId())).thenReturn(solicitud);
        when(solicitudDocumentacionService.findOne(1L)).thenReturn(solicitud);
        
        String ruta_vista = solicitudDocPreviaBuscadorBean.visualizarSolicitud(solicitud);
        
        verify(solicitudDocumentacionService, times(1)).findByIdConDocumentos(1L);
        verify(tipoDocumentacionService, times(1)).findByIdSolicitud(1L);
        verify(solicitudDocPreviaBean, times(1)).setSolicitudDocumentacionPrevia(solicitud);
        assertThat(ruta_vista).isEqualTo("/solicitudesPrevia/vistaSolicitud?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.SolicitudDocPreviaBuscadorBean#visualizarSolicitud(SolicitudDocumentacionPrevia)}.
     */
    @Test
    public void visualizarSolicitud_noExiste() {
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).build();
        SolicitudDocumentacionPrevia solicitud = SolicitudDocumentacionPrevia.builder().id(1L).inspeccion(inspeccion)
                .build();
        when(solicitudDocumentacionService.findByIdConDocumentos(solicitud.getId())).thenReturn(solicitud);
        when(solicitudDocumentacionService.findOne(1L)).thenReturn(null);
        
        String ruta_vista = solicitudDocPreviaBuscadorBean.visualizarSolicitud(solicitud);
        
        assertThat(ruta_vista).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.solicitudDocPreviaBuscadorBean#limpiarBusqueda()}.
     */
    @Test
    public void limpiarBusqueda() {
        solicitudDocPreviaBuscadorBean.limpiarBusqueda();
        verify(model, times(1)).setRowCount(0);
        assertThat(solicitudDocPreviaBuscadorBean.getSolicitudDocPreviaBusqueda()).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.solicitudDocPreviaBuscadorBean#buscarSolicitudDocPrevia()}.
     */
    @Test
    public void buscarSolicitudDocPrevia() {
        solicitudDocPreviaBuscadorBean.buscarSolicitudDocPrevia();
        verify(model, times(1)).setBusqueda(solicitudDocPreviaBuscadorBean.getSolicitudDocPreviaBusqueda());
        verify(model, times(1)).load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
    }
    
}
