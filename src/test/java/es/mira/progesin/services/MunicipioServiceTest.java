/**
 * 
 */
package es.mira.progesin.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IMunicipioRepository;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test para el servicio de alertas.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class MunicipioServiceTest {
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
     * Mock del repositorio de municipios.
     * 
     */
    @Mock
    private IMunicipioRepository municipioRepository;
    
    /**
     * Mock del registro de actividad.
     * 
     */
    @Mock
    private IRegistroActividadService registroActividadService;
    
    /**
     * Simulación del servicio de municipios.
     * 
     */
    @InjectMocks
    private IMunicipioService municipioService = new MunicipioService();
    
    /**
     * Captor de tipo Municipio.
     * 
     */
    @Captor
    private ArgumentCaptor<Municipio> municipioCaptor;
    
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
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.MunicipioService#existeByNameIgnoreCaseAndProvincia(java.lang.String, es.mira.progesin.persistence.entities.Provincia)}.
     */
    @Test
    public final void testExisteByNameIgnoreCaseAndProvincia() {
        Provincia provincia = mock(Provincia.class);
        municipioService.existeByNameIgnoreCaseAndProvincia("a", provincia);
        verify(municipioRepository, times(1)).existsByNameIgnoreCaseAndProvincia("a", provincia);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.MunicipioService#crearMunicipio(java.lang.String, es.mira.progesin.persistence.entities.Provincia)}.
     */
    @Test
    public final void testCrearMunicipio() {
        Provincia provincia = mock(Provincia.class);
        municipioService.crearMunicipio("aa", provincia);
        verify(municipioRepository, times(1)).save(municipioCaptor.capture());
        verify(registroActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.INSPECCION.getDescripcion()));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.MunicipioService#findByProvincia(es.mira.progesin.persistence.entities.Provincia)}.
     */
    @Test
    public final void testFindByProvincia() {
        Provincia provincia = mock(Provincia.class);
        municipioService.findByProvincia(provincia);
        verify(municipioRepository, times(1)).findByProvincia(provincia);
    }
    
}
