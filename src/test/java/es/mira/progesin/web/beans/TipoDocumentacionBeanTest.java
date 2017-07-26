/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test del bean TipoDocumentacionBean.
 * 
 * @author EZENTIS
 * 
 */

@PrepareForTest({ FacesUtilities.class })
// @PowerMockIgnore("javax.security.*")
@RunWith(PowerMockRunner.class)
public class TipoDocumentacionBeanTest {
    /**
     * Simulación del servicio de tipo de documentación.
     */
    @Mock
    private ITipoDocumentacionService tipoDocumentacionServiceMock;
    
    /**
     * Bean de tipo de documentación.
     */
    @InjectMocks
    private TipoDocumentacionBean tipoDocumentacionBeanMock;
    
    /**
     * Simulación del servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadServiceMock;
    
    /**
     * Simulación del bean de configuración de la aplicación.
     */
    @Mock
    private ApplicationBean applicationBeanMock;
    
    /**
     * Listado de extensiones.
     */
    private List<String> exts;
    
    /**
     * Captor de tipo CuerpoEstado.
     */
    @Captor
    private ArgumentCaptor<TipoDocumentacion> tipoDocumentacionCaptor;
    
    /**
     * @throws java.lang.Exception Excepción
     */
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(FacesUtilities.class);
        exts = new ArrayList<>();
        exts.add(".pdf");
        exts.add(".docx");
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(TipoDocumentacionBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        TipoDocumentacionBean target = new TipoDocumentacionBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoDocumentacionBean#tipoDocumentacionListado()}.
     */
    @Test
    public void testTipoDocumentacionListado() {
        String ruta_vista = tipoDocumentacionBeanMock.tipoDocumentacionListado();
        verify(tipoDocumentacionServiceMock, times(1)).findAll();
        assertThat(ruta_vista).isEqualTo("/documentacionPrevia/documentacionPrevia");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.TipoDocumentacionBean#eliminarDocumentacion(es.mira.progesin.persistence.entities.gd.TipoDocumentacion)}
     * .
     */
    @Test
    public void testEliminarDocumentacion() {
        
        TipoDocumentacion tipoDoc = TipoDocumentacion.builder().id(1L).build();
        
        List<TipoDocumentacion> listTipoDoc = new ArrayList<>();
        listTipoDoc.add(tipoDoc);
        
        tipoDocumentacionBeanMock.setListaTipoDocumentacion(listTipoDoc);
        
        tipoDocumentacionBeanMock.eliminarDocumentacion(tipoDoc);
        
        verify(tipoDocumentacionServiceMock, times(1)).delete(tipoDoc.getId());
        verify(regActividadServiceMock, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
        assertThat(tipoDocumentacionBeanMock.getListaTipoDocumentacion().size()).isEqualTo(0);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.TipoDocumentacionBean#eliminarDocumentacion(es.mira.progesin.persistence.entities.gd.TipoDocumentacion)}
     * .
     */
    @Test
    public void testEliminarDocumentacionDataException() {
        
        TipoDocumentacion tipoDoc = TipoDocumentacion.builder().id(1L).build();
        
        List<TipoDocumentacion> listTipoDoc = new ArrayList<>();
        listTipoDoc.add(tipoDoc);
        tipoDocumentacionBeanMock.setListaTipoDocumentacion(listTipoDoc);
        doThrow(TransientDataAccessResourceException.class).when(tipoDocumentacionServiceMock).delete(tipoDoc.getId());
        
        tipoDocumentacionBeanMock.eliminarDocumentacion(tipoDoc);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class));
        verify(regActividadServiceMock, times(1)).altaRegActividadError(
                eq(SeccionesEnum.ADMINISTRACION.getDescripcion()), any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoDocumentacionBean#init()}.
     */
    @Test
    public void testInit() {
        Map<String, String> mapaExtensiones = new HashMap<>();
        mapaExtensiones.put("pdf", ".pdf");
        mapaExtensiones.put("docx", ".docx");
        
        Map<String, Map<String, String>> mapaParametros = new HashMap<>();
        mapaParametros.put("extensiones", mapaExtensiones);
        
        TipoDocumentacion tipoDoc = TipoDocumentacion.builder().id(1L).build();
        
        List<TipoDocumentacion> listTipoDoc = new ArrayList<>();
        listTipoDoc.add(tipoDoc);
        
        when(applicationBeanMock.getMapaParametros()).thenReturn(mapaParametros);
        when(tipoDocumentacionServiceMock.findAll()).thenReturn(listTipoDoc);
        
        tipoDocumentacionBeanMock.init();
        assertThat(tipoDocumentacionBeanMock.getListaTipoDocumentacion()).isEqualTo(listTipoDoc);
        assertThat(tipoDocumentacionBeanMock.getListaExtensionesPosibles())
                .isEqualTo(new ArrayList<>(mapaExtensiones.keySet()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoDocumentacionBean#altaTipo()}.
     */
    @Test
    public void testAltaTipo() {
        
        tipoDocumentacionBeanMock.setNombreNuevo("nombre1");
        tipoDocumentacionBeanMock.setDescripcionNuevo("tipoNuevo");
        tipoDocumentacionBeanMock.setExtensionesNuevo(exts);
        tipoDocumentacionBeanMock.setAmbitoNuevo(AmbitoInspeccionEnum.PN);
        
        List<TipoDocumentacion> listTipoDoc = new ArrayList<>();
        listTipoDoc.add(mock(TipoDocumentacion.class));
        
        when(tipoDocumentacionServiceMock.findAll()).thenReturn(listTipoDoc);
        
        tipoDocumentacionBeanMock.altaTipo();
        
        verify(tipoDocumentacionServiceMock, times(1)).save(tipoDocumentacionCaptor.capture());
        verify(regActividadServiceMock, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
        verify(regActividadServiceMock, times(0))
                .altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()), any(Exception.class));
        assertThat(tipoDocumentacionBeanMock.getListaTipoDocumentacion()).isEqualTo(listTipoDoc);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoDocumentacionBean#altaTipo()}.
     */
    @Test
    public void testAltaTipoDataException() {
        
        tipoDocumentacionBeanMock.setNombreNuevo("tipo1");
        tipoDocumentacionBeanMock.setDescripcionNuevo("tipo test");
        tipoDocumentacionBeanMock.setExtensionesNuevo(exts);
        tipoDocumentacionBeanMock.setAmbitoNuevo(AmbitoInspeccionEnum.PN);
        
        List<TipoDocumentacion> listTipoDoc = new ArrayList<>();
        listTipoDoc.add(mock(TipoDocumentacion.class));
        
        when(tipoDocumentacionServiceMock.findAll()).thenReturn(listTipoDoc);
        
        doThrow(TransientDataAccessResourceException.class).when(tipoDocumentacionServiceMock)
                .save(tipoDocumentacionCaptor.capture());
        
        tipoDocumentacionBeanMock.altaTipo();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        verify(regActividadServiceMock, times(1)).altaRegActividadError(
                eq(SeccionesEnum.DOCUMENTACION.getDescripcion()), any(TransientDataAccessResourceException.class));
        assertThat(tipoDocumentacionBeanMock.getListaTipoDocumentacion()).isEqualTo(listTipoDoc);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.TipoDocumentacionBean#onRowEdit(org.primefaces.event.RowEditEvent)}.
     */
    @Test
    public void testOnRowEdit() {
        
        TipoDocumentacion tipoDoc = TipoDocumentacion.builder().nombre("tipo1").descripcion("tipo test")
                .ambito(AmbitoInspeccionEnum.PN).extensiones(exts).build();
        
        RowEditEvent event = mock(RowEditEvent.class);
        when(event.getObject()).thenReturn(tipoDoc);
        
        tipoDocumentacionBeanMock.onRowEdit(event);
        
        verify(tipoDocumentacionServiceMock, times(1)).save(tipoDoc);
        verify(regActividadServiceMock, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.TipoDocumentacionBean#onRowEdit(org.primefaces.event.RowEditEvent)}.
     */
    @Test
    public void testOnRowEditException() {
        
        TipoDocumentacion tipoDoc = TipoDocumentacion.builder().nombre("tipo1").descripcion("tipo test")
                .ambito(AmbitoInspeccionEnum.PN).extensiones(exts).build();
        
        RowEditEvent event = mock(RowEditEvent.class);
        when(event.getObject()).thenReturn(tipoDoc);
        doThrow(TransientDataAccessResourceException.class).when(tipoDocumentacionServiceMock).save(tipoDoc);
        
        tipoDocumentacionBeanMock.onRowEdit(event);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class));
    }
    
}
