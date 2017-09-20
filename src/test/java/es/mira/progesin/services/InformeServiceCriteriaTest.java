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
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.InformeEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.web.beans.informes.InformeBusqueda;

/**
 * Test del servicio de Informes donde se usa criteria de hibernate.
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
public class InformeServiceCriteriaTest {
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
     * Constatnte usuario.
     */
    private static final String USER = "admin";
    
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
     * {@link es.mira.progesin.services.InformeService#buscarInformeCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, InformeBusqueda)}.
     */
    @Test
    public final void testBuscarInformeCriteriaFechas() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        IInformeService informeService = new InformeService(sessionFactory, criteriaService);
        
        LocalDate localDate = LocalDate.of(2017, 6, 16);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        InformeBusqueda busqueda = new InformeBusqueda();
        busqueda.setFechaDesde(fechaDesde);
        busqueda.setFechaHasta(fechaHasta);
        
        List<Informe> listaInformes = informeService.buscarInformeCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(listaInformes).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InformeService#buscarInformeCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, InformeBusqueda)}.
     */
    @Test
    public final void testBuscarInformeCriteriaAnoIdTipoInspeccion() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        IInformeService informeService = new InformeService(sessionFactory, criteriaService);
        
        TipoInspeccion tipoInspeccion = new TipoInspeccion();
        tipoInspeccion.setCodigo("I.G.S.");
        InformeBusqueda busqueda = new InformeBusqueda();
        busqueda.setAnioInspeccion("2017");
        busqueda.setIdInspeccion("5");
        busqueda.setTipoInspeccion(tipoInspeccion);
        
        List<Informe> listaInformes = informeService.buscarInformeCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(listaInformes).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InformeService#buscarInformeCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, InformeBusqueda)}.
     */
    @Test
    public final void testBuscarInformeCriteriaAmbitoEstadoUsuarioCreacion() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        IInformeService informeService = new InformeService(sessionFactory, criteriaService);
        
        InformeBusqueda busqueda = new InformeBusqueda();
        busqueda.setAmbitoInspeccion(AmbitoInspeccionEnum.PN);
        busqueda.setEstado(InformeEnum.INICIADO);
        busqueda.setUsuarioCreacion("system");
        
        List<Informe> listaInformes = informeService.buscarInformeCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(listaInformes).hasSize(1);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.InformeService#getCountInformeCriteria(InformeBusqueda)}.
     */
    @Test
    public final void testGetCountInformeCriteria() {
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        IInformeService informeService = new InformeService(sessionFactory, criteriaService);
        
        int numRegistros = informeService.getCountInformeCriteria(new InformeBusqueda());
        assertThat(numRegistros).isEqualTo(1);
    }
    
}
