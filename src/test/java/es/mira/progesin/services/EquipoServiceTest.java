package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IEquipoRepository;
import es.mira.progesin.web.beans.EquipoBusqueda;

/**
 * Test del servicio Equipo.
 * 
 * @author EZENTIS
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest(SecurityContextHolder.class)
// Evita conflictos con clases del sistema al enlazar los mocks por tipo
@PowerMockIgnore({ "javax.management.*", "javax.security.*" })
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
// @RunWith(MockitoJUnitRunner.class)
public class EquipoServiceTest {
    
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
     * Mock del repositorio de equipos.
     */
    @Mock
    private IEquipoRepository equipoRepository;
    
    /**
     * Instancia de prueba del servicio de equipos.
     */
    @InjectMocks
    private IEquipoService equipoService = new EquipoService();
    
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
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(EquipoService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        EquipoService target = new EquipoService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#findAll()}.
     */
    @Test
    public void findAll() {
        equipoService.findAll();
        verify(equipoRepository, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#save(Equipo)}.
     */
    @Test
    public void save_Equipo() {
        Equipo equipo = mock(Equipo.class);
        equipoService.save(equipo);
        verify(equipoRepository, times(1)).save(equipo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#existsByTipoEquipo(TipoEquipo)}.
     */
    @Test
    public void existsByTipoEquipo() {
        TipoEquipo tEquipo = mock(TipoEquipo.class);
        equipoService.existsByTipoEquipo(tEquipo);
        verify(equipoRepository, times(1)).existsByTipoEquipo(tEquipo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#findByFechaBajaIsNull()}.
     */
    @Test
    public void findByFechaBajaIsNull() {
        equipoService.findByFechaBajaIsNull();
        verify(equipoRepository, times(1)).findByFechaBajaIsNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.EquipoService#buscarEquipoCriteria(int, int, String, org.primefaces.model.SortOrder, EquipoBusqueda)}
     * .
     */
    @Test
    public void buscarEquipoCriteria() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        EquipoService equipoServiceCriteria = new EquipoService(sessionFactory, criteriaService);
        
        LocalDate localDate = LocalDate.of(2017, 5, 10);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        EquipoBusqueda busqueda = new EquipoBusqueda();
        busqueda.setFechaDesde(fechaDesde);
        busqueda.setFechaHasta(fechaHasta);
        
        List<Equipo> listaEquipos = equipoServiceCriteria.buscarEquipoCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(listaEquipos).hasSize(4);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#getCounCriteria(EquipoBusqueda)}.
     */
    @Test
    public void getCounCriteria() {
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        CriteriaService criteriaService = new CriteriaService();
        EquipoService equipoServiceCriteria = new EquipoService(sessionFactory, criteriaService);
        
        EquipoBusqueda busqueda = new EquipoBusqueda();
        
        int numRegistros = equipoServiceCriteria.getCounCriteria(busqueda);
        assertThat(numRegistros).isEqualTo(4);
    }
}
