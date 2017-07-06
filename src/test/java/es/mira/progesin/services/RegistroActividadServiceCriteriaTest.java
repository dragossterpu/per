/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.web.beans.RegActividadBusqueda;

/**
 * Test para los métodos criteria del servicio RegistroActividadService.
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
public class RegistroActividadServiceCriteriaTest {
    
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
     * {@link es.mira.progesin.services.RegistroActividadService#getCounCriteria(RegActividadBusqueda regActividadBusqueda)}.
     */
    @Test
    public final void testGetCounCriteria() {
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        RegActividadBusqueda regActividadBusqueda = new RegActividadBusqueda();
        regActividadBusqueda.setUsernameRegActividad("raul");
        CriteriaService criteriaService = new CriteriaService();
        RegistroActividadService registroActividadService = new RegistroActividadService(sessionFactory,
                criteriaService);
        
        int numRegistros = registroActividadService.getCounCriteria(regActividadBusqueda);
        assertThat(numRegistros).isEqualTo(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RegistroActividadService#buscarRegActividadCriteria(int first, int pageSize, String sortField, SortOrder sortOrder, RegActividadBusqueda regActividadBusqueda)}.
     */
    @Test
    public final void testBuscarRegActividadCriteriaUserSeccion() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        RegActividadBusqueda regActividadBusqueda = new RegActividadBusqueda();
        regActividadBusqueda.setUsernameRegActividad("raul");
        regActividadBusqueda.setNombreSeccion(SeccionesEnum.CUESTIONARIO.getDescripcion());
        CriteriaService criteriaService = new CriteriaService();
        RegistroActividadService registroActividadService = new RegistroActividadService(sessionFactory,
                criteriaService);
        
        List<RegistroActividad> listaRegistros = registroActividadService.buscarRegActividadCriteria(first, pageSize,
                sortField, sortOrder, regActividadBusqueda);
        assertThat(listaRegistros).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RegistroActividadService#buscarRegActividadCriteria(int first, int pageSize, String sortField, SortOrder sortOrder, RegActividadBusqueda regActividadBusqueda)}.
     */
    @Test
    public final void testBuscarRegActividadFechas() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        RegActividadBusqueda regActividadBusqueda = new RegActividadBusqueda();
        
        LocalDate localDate = LocalDate.of(2017, 6, 19);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        regActividadBusqueda.setFechaDesde(fechaDesde);
        regActividadBusqueda.setFechaHasta(fechaHasta);
        CriteriaService criteriaService = new CriteriaService();
        RegistroActividadService registroActividadService = new RegistroActividadService(sessionFactory,
                criteriaService);
        
        List<RegistroActividad> listaRegistros = registroActividadService.buscarRegActividadCriteria(first, pageSize,
                sortField, sortOrder, regActividadBusqueda);
        assertThat(listaRegistros).hasSize(2);
    }
    
}
