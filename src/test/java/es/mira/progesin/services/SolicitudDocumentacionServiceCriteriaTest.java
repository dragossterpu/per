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

import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SolicitudDocPreviaEnum;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;

/**
 * Test del servicio de solicitud de documentación previa donde se usa criteria de hibernate.
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
public class SolicitudDocumentacionServiceCriteriaTest {
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
     * {@link es.mira.progesin.services.SolicitudDocumentacionService#buscarSolicitudDocPreviaCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda)}.
     */
    @Test
    public final void testBuscarSolicitudDocPreviaCriteriaSinFiltros() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        ISolicitudDocumentacionService solicitudDocumentacionService = new SolicitudDocumentacionService(sessionFactory,
                criteriaService);
        
        SolicitudDocPreviaBusqueda busqueda = new SolicitudDocPreviaBusqueda();
        
        List<SolicitudDocumentacionPrevia> listaGuiasPersonalizadas = solicitudDocumentacionService
                .buscarSolicitudDocPreviaCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaGuiasPersonalizadas).hasSize(7);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.SolicitudDocumentacionService#buscarSolicitudDocPreviaCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda)}.
     */
    @Test
    public final void testBuscarSolicitudDocPreviaCriteria() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        CriteriaService criteriaService = new CriteriaService();
        
        ISolicitudDocumentacionService solicitudDocumentacionService = new SolicitudDocumentacionService(sessionFactory,
                criteriaService);
        TipoInspeccion tipoInspeccion = new TipoInspeccion();
        tipoInspeccion.setCodigo("I.G.P.");
        LocalDate localDate = LocalDate.of(2017, 5, 3);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SolicitudDocPreviaBusqueda busqueda = new SolicitudDocPreviaBusqueda();
        busqueda.setFechaDesde(fechaDesde);
        busqueda.setFechaHasta(fechaHasta);
        busqueda.setAmbitoInspeccion(AmbitoInspeccionEnum.PN);
        busqueda.setAnioInspeccion("2017");
        busqueda.setEstado(SolicitudDocPreviaEnum.CUMPLIMENTADA);
        busqueda.setIdInspeccion("1");
        busqueda.setNombreUnidad("a");
        busqueda.setTipoInspeccion(tipoInspeccion);
        busqueda.setUsuarioCreacion("ystem");
        
        List<SolicitudDocumentacionPrevia> listaGuiasPersonalizadas = solicitudDocumentacionService
                .buscarSolicitudDocPreviaCriteria(first, pageSize, sortField, sortOrder, busqueda);
        assertThat(listaGuiasPersonalizadas).hasSize(0);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.SolicitudDocumentacionService#getCountSolicitudDocPreviaCriteria(es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda)}.
     */
    @Test
    public final void testGetCountSolicitudDocPreviaCriteria() {
        User user = User.builder().username(USER).role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        CriteriaService criteriaService = new CriteriaService();
        ISolicitudDocumentacionService solicitudDocumentacionService = new SolicitudDocumentacionService(sessionFactory,
                criteriaService);
        SolicitudDocPreviaBusqueda busqueda = new SolicitudDocPreviaBusqueda();
        
        int numRegistros = solicitudDocumentacionService.getCountSolicitudDocPreviaCriteria(busqueda);
        assertThat(numRegistros).isEqualTo(7);
    }
    
}
