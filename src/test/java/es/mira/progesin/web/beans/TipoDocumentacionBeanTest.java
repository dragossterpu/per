/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.RowEditEvent;

import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.IParametroService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;

/**
 * @author EZENTIS
 * 
 * Test del bean TipoDocumentacionBean
 *
 */

@PrepareForTest(FacesUtilities.class)
@RunWith(PowerMockRunner.class)
public class TipoDocumentacionBeanTest {
    
    @Mock
    private ITipoDocumentacionService tipoDocumentacionServiceMock;
    
    @InjectMocks
    private TipoDocumentacionBean tipoDocumentacionBeanMock;
    
    @Mock
    private IRegistroActividadService regActividadServiceMock;
    
    @Mock
    private ApplicationBean applicationBeanMock;
    
    @Mock
    private IParametroService parametroServiceMock;
    
    private List<String> exts;
    
    /**
     * @throws java.lang.Exception
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
     * {@link es.mira.progesin.web.beans.TipoDocumentacionBean#eliminarDocumentacion(es.mira.progesin.persistence.entities.gd.TipoDocumentacion)}.
     */
    @Test
    public void testEliminarDocumentacion() {
        
        TipoDocumentacion tipoDoc = TipoDocumentacion.builder().id(1L).nombre("tipo1").descripcion("tipo test")
                .ambito(AmbitoInspeccionEnum.PN).extensiones(exts).build();
        
        List<TipoDocumentacion> listTipoDoc = new ArrayList<>();
        listTipoDoc.add(tipoDoc);
        
        tipoDocumentacionBeanMock.setListaTipoDocumentacion(listTipoDoc);
        
        tipoDocumentacionBeanMock.eliminarDocumentacion(tipoDoc);
        
        verify(tipoDocumentacionServiceMock, times(1)).delete(tipoDoc.getId());
        assertThat(tipoDocumentacionBeanMock.getListaTipoDocumentacion().size()).isEqualTo(0);
        
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
        
        when(applicationBeanMock.getMapaParametros()).thenReturn(mapaParametros);
        
        tipoDocumentacionBeanMock.init();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoDocumentacionBean#altaTipo()}.
     */
    @Test
    public void testAltaTipo() {
        
        tipoDocumentacionBeanMock.setNombreNuevo("tipo1");
        tipoDocumentacionBeanMock.setDescripcionNuevo("tipo test");
        tipoDocumentacionBeanMock.setExtensionesNuevo(exts);
        tipoDocumentacionBeanMock.setAmbitoNuevo(AmbitoInspeccionEnum.PN);
        
        TipoDocumentacion tipoDoc = TipoDocumentacion.builder().nombre(tipoDocumentacionBeanMock.getNombreNuevo())
                .descripcion(tipoDocumentacionBeanMock.getDescripcionNuevo())
                .ambito(tipoDocumentacionBeanMock.getAmbitoNuevo())
                .extensiones(tipoDocumentacionBeanMock.getExtensionesNuevo()).build();
        
        when(tipoDocumentacionServiceMock.save(tipoDoc)).thenReturn(tipoDoc);
        
        tipoDocumentacionBeanMock.altaTipo();
        
        verify(tipoDocumentacionServiceMock, times(1)).save(tipoDoc);
        verify(regActividadServiceMock, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.DOCUMENTACION.name()));
        verify(regActividadServiceMock, times(0)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.name()),
                any(Exception.class));
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
        when(tipoDocumentacionServiceMock.save(tipoDoc)).thenReturn(tipoDoc);
        
        tipoDocumentacionBeanMock.onRowEdit(event);
        
        verify(tipoDocumentacionServiceMock, times(1)).save(tipoDoc);
        verify(regActividadServiceMock, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.name()));
    }
    
}
