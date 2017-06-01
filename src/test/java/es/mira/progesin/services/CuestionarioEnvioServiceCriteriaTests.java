package es.mira.progesin.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.primefaces.model.SortOrder;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBusqueda;

/**
 * Test de buscadores con criteria.
 * 
 * @author EZENTIS
 *
 *
 */
@Ignore
@RunWith(PowerMockRunner.class)
// Evita conflictos con clases del sistema al enlazar los mocks por tipo
@PowerMockIgnore({ "javax.security.*", "javax.management.*" })
@PowerMockRunnerDelegate(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
@PrepareForTest(SecurityContextHolder.class)
// @ContextConfiguration // @SpringBootTest
// @TestExecutionListeners(listeners = { WithSecurityContextTestExecutionListener.class })
public class CuestionarioEnvioServiceCriteriaTests {
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
     * Servicio de cuestionarios enviados.
     */
    @InjectMocks
    private transient ICuestionarioEnvioService cuestionarioEnvioService = new CuestionarioEnvioService();
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioLogueado");
        when(authentication.getPrincipal())
                .thenReturn(User.builder().username("usuarioLogueado").role(RoleEnum.ROLE_ADMIN).build());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#buscarCuestionarioEnviadoCriteria(int, int, String, SortOrder, CuestionarioEnviadoBusqueda)}.
     */
    @Test
    public void buscarCuestionarioEnviadoCriteria() {
        int first = 0;
        int pageSize = 0;
        String sortField = null;
        SortOrder sortOrder = SortOrder.ASCENDING;
        CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda = mock(CuestionarioEnviadoBusqueda.class);
        
        cuestionarioEnvioService.buscarCuestionarioEnviadoCriteria(first, pageSize, sortField, sortOrder,
                cuestionarioEnviadoBusqueda);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#getCountCuestionarioCriteria(CuestionarioEnviadoBusqueda)}.
     */
    @Test
    public void getCountCuestionarioCriteria() {
        CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda = mock(CuestionarioEnviadoBusqueda.class);
        
        cuestionarioEnvioService.getCountCuestionarioCriteria(cuestionarioEnviadoBusqueda);
    }
}
