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

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.CuestionarioEnviadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBusqueda;

/**
 * Test del servicio de cuestionarios envio donde se usa criteria de hibernate.
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
public class CuestionarioEnvioServiceCriteriaTests {
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
     * {@link es.mira.progesin.services.CuestionarioEnvioService#buscarCuestionarioEnviadoCriteria(int, int, String, SortOrder, CuestionarioEnviadoBusqueda)}.
     */
    @Test
    public void buscarCuestionarioEnviadoCriteriaFechas() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        CuestionarioEnvioService cuestionarioEnvioService = new CuestionarioEnvioService(sessionFactory,
                criteriaService);
        
        LocalDate localDate = LocalDate.of(2017, 5, 10);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        CuestionarioEnviadoBusqueda busqueda = new CuestionarioEnviadoBusqueda();
        busqueda.setFechaDesde(fechaDesde);
        busqueda.setFechaHasta(fechaHasta);
        
        List<CuestionarioEnvio> listaCuestionariosEnviados = cuestionarioEnvioService
                .buscarCuestionarioEnviadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaCuestionariosEnviados).hasSize(4);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#buscarCuestionarioEnviadoCriteria(int, int, String, SortOrder, CuestionarioEnviadoBusqueda)}.
     */
    @Test
    public void buscarCuestionarioEnviadoCriteriaFechaLimiteEstado() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        CuestionarioEnvioService cuestionarioEnvioService = new CuestionarioEnvioService(sessionFactory,
                criteriaService);
        
        LocalDate localDate = LocalDate.of(2017, 7, 10);
        Date fechaLimite = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        CuestionarioEnviadoBusqueda busqueda = new CuestionarioEnviadoBusqueda();
        busqueda.setFechaLimiteRespuesta(fechaLimite);
        busqueda.setEstado(CuestionarioEnviadoEnum.CUMPLIMENTADO);
        
        List<CuestionarioEnvio> listaCuestionariosEnviados = cuestionarioEnvioService
                .buscarCuestionarioEnviadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaCuestionariosEnviados).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#buscarCuestionarioEnviadoCriteria(int, int, String, SortOrder, CuestionarioEnviadoBusqueda)}.
     */
    @Test
    public void buscarCuestionarioEnviadoCriteriaUsernameEnvioInspeccion() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        CuestionarioEnvioService cuestionarioEnvioService = new CuestionarioEnvioService(sessionFactory,
                criteriaService);
        
        CuestionarioEnviadoBusqueda busqueda = new CuestionarioEnviadoBusqueda();
        busqueda.setUsernameEnvio("te");
        busqueda.setIdInspeccion("1");
        List<CuestionarioEnvio> listaCuestionariosEnviados = cuestionarioEnvioService
                .buscarCuestionarioEnviadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaCuestionariosEnviados).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#buscarCuestionarioEnviadoCriteria(int, int, String, SortOrder, CuestionarioEnviadoBusqueda)}.
     */
    @Test
    public void buscarCuestionarioEnviadoCriteriaAnioTipoInspeccion() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        CuestionarioEnvioService cuestionarioEnvioService = new CuestionarioEnvioService(sessionFactory,
                criteriaService);
        
        TipoInspeccion tipo = new TipoInspeccion();
        tipo.setCodigo("I.G.P.");
        CuestionarioEnviadoBusqueda busqueda = new CuestionarioEnviadoBusqueda();
        busqueda.setAnioInspeccion("2017");
        busqueda.setTipoInspeccion(tipo);
        List<CuestionarioEnvio> listaCuestionariosEnviados = cuestionarioEnvioService
                .buscarCuestionarioEnviadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaCuestionariosEnviados).hasSize(4);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#buscarCuestionarioEnviadoCriteria(int, int, String, SortOrder, CuestionarioEnviadoBusqueda)}.
     */
    @Test
    public void buscarCuestionarioEnviadoCriteriaAmbitoNombreEquipo() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        CuestionarioEnvioService cuestionarioEnvioService = new CuestionarioEnvioService(sessionFactory,
                criteriaService);
        
        CuestionarioEnviadoBusqueda busqueda = new CuestionarioEnviadoBusqueda();
        busqueda.setAmbitoInspeccion(AmbitoInspeccionEnum.GC);
        busqueda.setNombreEquipo("A");
        List<CuestionarioEnvio> listaCuestionariosEnviados = cuestionarioEnvioService
                .buscarCuestionarioEnviadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaCuestionariosEnviados).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#buscarCuestionarioEnviadoCriteria(int, int, String, SortOrder, CuestionarioEnviadoBusqueda)}.
     */
    @Test
    public void buscarCuestionarioEnviadoCriteriaNombreUnidadNombreCuestionarioModeloCuestionario() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        CuestionarioEnvioService cuestionarioEnvioService = new CuestionarioEnvioService(sessionFactory,
                criteriaService);
        
        ModeloCuestionario modeloCuestionario = new ModeloCuestionario();
        modeloCuestionario.setId(1);
        CuestionarioEnviadoBusqueda busqueda = new CuestionarioEnviadoBusqueda();
        busqueda.setNombreUnidad("ri");
        busqueda.setNombreCuestionario("TIONARIO_TEST");
        busqueda.setModeloCuestionarioSeleccionado(modeloCuestionario);
        List<CuestionarioEnvio> listaCuestionariosEnviados = cuestionarioEnvioService
                .buscarCuestionarioEnviadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaCuestionariosEnviados).hasSize(3);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#getCountCuestionarioCriteria(CuestionarioEnviadoBusqueda)}.
     */
    @Test
    public void getCountCuestionarioCriteria() {
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        CriteriaService criteriaService = new CriteriaService();
        CuestionarioEnvioService cuestionarioEnvioService = new CuestionarioEnvioService(sessionFactory,
                criteriaService);
        CuestionarioEnviadoBusqueda busqueda = new CuestionarioEnviadoBusqueda();
        
        int numRegistros = cuestionarioEnvioService.getCountCuestionarioCriteria(busqueda);
        assertThat(numRegistros).isEqualTo(4);
    }
}
