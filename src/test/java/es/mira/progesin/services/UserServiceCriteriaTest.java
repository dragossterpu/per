package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.web.beans.UserBusqueda;

/**
 * Test del servicio usuarios donde se usa critera de hibernate
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest(SecurityContextHolder.class)
@PowerMockIgnore("javax.management.*")
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserServiceCriteriaTest {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    private SecurityContext securityContext;
    
    private Authentication authentication;
    
    /**
     * Configuración inicial del test
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
        
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.UserService#buscarUsuarioCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.UserBusqueda)}
     */
    @Test
    public void buscarUsuarioCriteria_fechaAlta() {
        int first = 0;
        int pageSize = 20;
        String sortField = "username";
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        UserService userService = new UserService(sessionFactory);
        
        LocalDate localDate = LocalDate.of(2017, 1, 15);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        UserBusqueda userBusqueda = UserBusqueda.builder().fechaDesde(fechaDesde).fechaHasta(fechaHasta).build();
        
        List<User> listaUsuarios = userService.buscarUsuarioCriteria(first, pageSize, sortField, sortOrder,
                userBusqueda);
        assertThat(listaUsuarios).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.UserService#buscarUsuarioCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.UserBusqueda)}
     */
    @Test
    public void buscarUsuarioCriteria_NombreApellidosCuerpo() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        UserService userService = new UserService(sessionFactory);
        UserBusqueda userBusqueda = new UserBusqueda();
        userBusqueda.setNombre("AN");
        userBusqueda.setApellido1("LO");
        userBusqueda.setApellido2("A");
        userBusqueda.setCuerpoEstado(CuerpoEstado.builder().id(2).build());
        
        List<User> listaUsuarios = userService.buscarUsuarioCriteria(first, pageSize, sortField, sortOrder,
                userBusqueda);
        assertThat(listaUsuarios).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.UserService#buscarUsuarioCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.UserBusqueda)}
     */
    @Test
    public void buscarUsuarioCriteria_Combos() {
        int first = 0;
        int pageSize = 20;
        String sortField = "nombre";
        SortOrder sortOrder = SortOrder.ASCENDING;
        
        User user = User.builder().username("pepe").role(RoleEnum.ROLE_SERVICIO_APOYO).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        UserService userService = new UserService(sessionFactory);
        UserBusqueda userBusqueda = new UserBusqueda();
        userBusqueda.setUsername("");
        userBusqueda.setNombre("");
        userBusqueda.setApellido1("");
        userBusqueda.setApellido2("");
        userBusqueda.setPuestoTrabajo(PuestoTrabajo.builder().id(7L).build());
        userBusqueda.setRole(RoleEnum.ROLE_EQUIPO_INSPECCIONES);
        userBusqueda.setEstado(EstadoEnum.ACTIVO);
        
        List<User> listaUsuarios = userService.buscarUsuarioCriteria(first, pageSize, sortField, sortOrder,
                userBusqueda);
        assertThat(listaUsuarios).hasSize(14);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.UserService#contarRegistros(es.mira.progesin.web.beans.UserBusqueda)}.
     */
    @Test
    public void contarRegistros() {
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        UserService userService = new UserService(sessionFactory);
        UserBusqueda userBusqueda = UserBusqueda.builder().username("EZEN").build();
        
        int numRegistros = userService.contarRegistros(userBusqueda);
        assertThat(numRegistros).isEqualTo(1);
    }
    
}
