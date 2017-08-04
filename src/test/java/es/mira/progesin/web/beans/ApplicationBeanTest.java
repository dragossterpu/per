/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.repositories.IClaseUsuarioRepository;
import es.mira.progesin.persistence.repositories.IEmpleoRepository;
import es.mira.progesin.persistence.repositories.IProvinciaRepository;
import es.mira.progesin.persistence.repositories.ITipoUnidadRepository;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.services.IDepartamentoService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IParametroService;
import es.mira.progesin.services.IPuestoTrabajoService;
import es.mira.progesin.services.ITipoEquipoService;
import es.mira.progesin.services.ITipoInspeccionService;

/**
 * Test para el servicio de ApplicationBean.
 * @author EZENTIS
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationBeanTest {
    /**
     * Constante dominiosCorreo.
     */
    private static final String DOMINIOCORREO = "dominiosCorreo";
    
    /**
     * Mock Servicio de parámetros.
     */
    @Mock
    private transient IParametroService parametroService;
    
    /**
     * Mock Servicio de documentos.
     */
    @Mock
    private transient IDocumentoService documentoService;
    
    /**
     * Mock Repositorio de tipo de unidad.
     */
    @Mock
    private transient ITipoUnidadRepository tipoUnidadRepository;
    
    /**
     * Mock Repositorio de provincia.
     */
    @Mock
    private transient IProvinciaRepository provinciaRepository;
    
    /**
     * Mock Servicio de cuerpos de estado.
     */
    @Mock
    private transient ICuerpoEstadoService cuerposService;
    
    /**
     * Mock Servicio de puestos de trabajo.
     */
    @Mock
    private transient IPuestoTrabajoService puestosTrabajoService;
    
    /**
     * Mock Servicio de departamentos.
     */
    @Mock
    private transient IDepartamentoService departamentoService;
    
    /**
     * Mock Repositorio de clases de usuario.
     */
    @Mock
    private transient IClaseUsuarioRepository claseUsuarioRepository;
    
    /**
     * Mock Repositorio de empleos.
     */
    @Mock
    private transient IEmpleoRepository empleoRepository;
    
    /**
     * Mock Servicio de modelos/tipos de inspección.
     */
    @Mock
    private transient ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Mock Servicio de tipos de equipo.
     */
    @Mock
    private transient ITipoEquipoService tipoEquipoService;
    
    /**
     * Simulación ApplicationBean.
     */
    @InjectMocks
    private ApplicationBean applicationBean;
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.ApplicationBean#init()}.
     */
    @Test
    public final void testInit() {
        Map<String, Map<String, String>> mapaParametros = new HashMap<>();
        Map<String, String> parametrosDominios = new HashMap<>();
        parametrosDominios.put(DOMINIOCORREO, "aaa");
        mapaParametros.put(DOMINIOCORREO, parametrosDominios);
        when(parametroService.getMapaParametros()).thenReturn(mapaParametros);
        
        applicationBean.init();
        
        verify(parametroService, times(1)).getMapaParametros();
        assertThat(applicationBean.getDominiosValidos()).isEqualTo("aaa");
        verify(documentoService, times(1)).listaTiposDocumento();
        verify(provinciaRepository, times(1)).findAllByOrderByNombreAsc();
        verify(tipoUnidadRepository, times(1)).findAllByOrderByDescripcionAsc();
        verify(cuerposService, times(1)).findAllByOrderByDescripcionAsc();
        verify(puestosTrabajoService, times(1)).findAllByOrderByDescripcionAsc();
        verify(departamentoService, times(1)).findAllByOrderByDescripcionAsc();
        verify(claseUsuarioRepository, times(1)).findAllByOrderByClaseAsc();
        verify(empleoRepository, times(1)).findAll();
        verify(tipoInspeccionService, times(1)).buscaTodos();
        verify(tipoEquipoService, times(1)).findAllByOrderByDescripcionAsc();
        
    }
    
}
