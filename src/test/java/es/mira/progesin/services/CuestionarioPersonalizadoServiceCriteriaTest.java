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

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda;

/**
 * Test del servicio de cuestionarios personalizados donde se usa criteria de hibernate.
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
public class CuestionarioPersonalizadoServiceCriteriaTest {
    
    /**
     * Constante usuario.
     */
    private static final String USER = "admin";
    
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
     * {@link es.mira.progesin.services.CuestionarioPersonalizadoService#buscarCuestionarioPersonalizadoCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda)}.
     */
    @Test
    public final void testBuscarCuestionarioPersonalizadoCriteriaFechas() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        CuestionarioPersonalizadoService cuestionarioPersonalizadoService = new CuestionarioPersonalizadoService(
                sessionFactory, criteriaService);
        
        LocalDate localDate = LocalDate.of(2017, 4, 28);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        CuestionarioPersonalizadoBusqueda busqueda = new CuestionarioPersonalizadoBusqueda();
        busqueda.setFechaDesde(fechaDesde);
        busqueda.setFechaHasta(fechaHasta);
        
        List<CuestionarioPersonalizado> listaCuestionariosPersonalizados = cuestionarioPersonalizadoService
                .buscarCuestionarioPersonalizadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaCuestionariosPersonalizados).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioPersonalizadoService#buscarCuestionarioPersonalizadoCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda)}.
     */
    @Test
    public final void testBuscarCuestionarioPersonalizadoCriteriaModeloUsername() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        CuestionarioPersonalizadoService cuestionarioPersonalizadoService = new CuestionarioPersonalizadoService(
                sessionFactory, criteriaService);
        
        ModeloCuestionario modeloCuestionario = new ModeloCuestionario();
        modeloCuestionario.setId(1);
        CuestionarioPersonalizadoBusqueda busqueda = new CuestionarioPersonalizadoBusqueda();
        busqueda.setModeloCuestionarioSeleccionado(modeloCuestionario);
        busqueda.setUsername("system");
        
        List<CuestionarioPersonalizado> listaCuestionariosPersonalizados = cuestionarioPersonalizadoService
                .buscarCuestionarioPersonalizadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaCuestionariosPersonalizados).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioPersonalizadoService#buscarCuestionarioPersonalizadoCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda)}.
     */
    @Test
    public final void testBuscarCuestionarioPersonalizadoCriteriaNombreEstado() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        CuestionarioPersonalizadoService cuestionarioPersonalizadoService = new CuestionarioPersonalizadoService(
                sessionFactory, criteriaService);
        
        ModeloCuestionario modeloCuestionario = new ModeloCuestionario();
        modeloCuestionario.setId(1);
        CuestionarioPersonalizadoBusqueda busqueda = new CuestionarioPersonalizadoBusqueda();
        busqueda.setNombreCuestionario("ST2");
        busqueda.setEstado(EstadoEnum.INACTIVO);
        
        List<CuestionarioPersonalizado> listaCuestionariosPersonalizados = cuestionarioPersonalizadoService
                .buscarCuestionarioPersonalizadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaCuestionariosPersonalizados).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioPersonalizadoService#getCountCuestionarioCriteria(es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda)}.
     */
    @Test
    public final void testGetCountCuestionarioCriteria() {
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        CriteriaService criteriaService = new CriteriaService();
        CuestionarioPersonalizadoService cuestionarioPersonalizadoService = new CuestionarioPersonalizadoService(
                sessionFactory, criteriaService);
        CuestionarioPersonalizadoBusqueda busqueda = new CuestionarioPersonalizadoBusqueda();
        
        int numRegistros = cuestionarioPersonalizadoService.getCountCuestionarioCriteria(busqueda);
        assertThat(numRegistros).isEqualTo(1);
    }
    
}
