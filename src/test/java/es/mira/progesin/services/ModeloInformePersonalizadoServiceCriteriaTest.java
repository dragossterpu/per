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
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.web.beans.informes.InformePersonalizadoBusqueda;

/**
 * Test del servicio de modelos de informe personalizados donde se usa criteria de hibernate.
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
public class ModeloInformePersonalizadoServiceCriteriaTest {
    /**
     * Constatnte usuario.
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
     * {@link es.mira.progesin.services.ModeloInformePersonalizadoServiceTest#testBuscarInformePersonalizadoCriteria()}.
     */
    @Test
    public final void testTestBuscarInformePersonalizadoCriteriaFechas() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        ModeloInformePersonalizadoService modeloInformePersonalizadoService = new ModeloInformePersonalizadoService(
                sessionFactory, criteriaService);
        
        LocalDate localDate = LocalDate.of(2017, 6, 10);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        InformePersonalizadoBusqueda busqueda = new InformePersonalizadoBusqueda();
        busqueda.setFechaDesde(fechaDesde);
        busqueda.setFechaHasta(fechaHasta);
        
        List<ModeloInformePersonalizado> listaModelosInformesPersonalizadas = modeloInformePersonalizadoService
                .buscarInformePersonalizadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaModelosInformesPersonalizadas).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformePersonalizadoServiceTest#testBuscarInformePersonalizadoCriteria()}.
     */
    @Test
    public final void testTestBuscarInformePersonalizadoCriteriaUsuarioNombreModelo() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        ModeloInformePersonalizadoService modeloInformePersonalizadoService = new ModeloInformePersonalizadoService(
                sessionFactory, criteriaService);
        
        InformePersonalizadoBusqueda busqueda = new InformePersonalizadoBusqueda();
        busqueda.setUsernameAlta("system");
        busqueda.setNombreInforme("Estándar (Guardia Civil)");
        ModeloInforme modeloPersonalizado = new ModeloInforme();
        modeloPersonalizado.setId(1L);
        busqueda.setModeloInformeSeleccionado(modeloPersonalizado);
        
        List<ModeloInformePersonalizado> listaModelosInformesPersonalizadas = modeloInformePersonalizadoService
                .buscarInformePersonalizadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaModelosInformesPersonalizadas).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformePersonalizadoServiceTest#testGetCountInformePersonalizadoCriteria()}.
     */
    @Test
    public final void testTestGetCountInformePersonalizadoCriteria() {
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        ModeloInformePersonalizadoService modeloInformePersonalizadoService = new ModeloInformePersonalizadoService(
                sessionFactory, criteriaService);
        
        InformePersonalizadoBusqueda busqueda = new InformePersonalizadoBusqueda();
        busqueda.setUsernameAlta("system");
        busqueda.setNombreInforme("Estándar (Guardia Civil)");
        ModeloInforme modeloPersonalizado = new ModeloInforme();
        modeloPersonalizado.setId(1L);
        busqueda.setModeloInformeSeleccionado(modeloPersonalizado);
        
        int numRegistros = modeloInformePersonalizadoService.getCountInformePersonalizadoCriteria(busqueda);
        assertThat(numRegistros).isEqualTo(1);
    }
    
}
