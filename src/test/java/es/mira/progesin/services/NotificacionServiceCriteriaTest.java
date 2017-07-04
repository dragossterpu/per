/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.User;

/**
 * Test del servicio de notificaciones donde se usa criteria de hibernate.
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest(SecurityContextHolder.class)
// Evita conflictos con clases del sistema al enlazar los mocks por tipo
@PowerMockIgnore({ "javax.management.*", "javax.security.*" })
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class NotificacionServiceCriteriaTest {
    /**
     * Factoría de sesiones.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
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
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.NotificacionService#buscarPorCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, java.lang.String)}.
     */
    @Test
    public final void testBuscarPorCriteria() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username("fvilchews").build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        CriteriaService criteriaService = new CriteriaService();
        NotificacionService notificacionService = new NotificacionService(sessionFactory, criteriaService);
        
        List<Notificacion> listaAlertas = notificacionService.buscarPorCriteria(first, pageSize, sortField, sortOrder,
                user.getUsername());
        assertThat(listaAlertas).hasSize(1);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.NotificacionService#getCounCriteria(java.lang.String)}.
     */
    @Test
    public final void testGetCounCriteria() {
        User user = User.builder().username("fvilchews").build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        CriteriaService criteriaService = new CriteriaService();
        NotificacionService notificacionService = new NotificacionService(sessionFactory, criteriaService);
        
        int numNotificaciones = notificacionService.getCounCriteria(user.getUsername());
        assertThat(numNotificaciones).isEqualTo(1);
    }
    
}
