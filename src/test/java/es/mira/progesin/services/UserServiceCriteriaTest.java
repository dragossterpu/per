package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

import es.mira.progesin.persistence.entities.User;
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
public class UserServiceCriteraTest {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Configuración inicial del test
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.UserService#buscarUsuarioCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.UserBusqueda)}
     */
    @Test
    public void buscarUsuarioCriteria() {
        int first = 1;
        int pageSize = 20;
        String sortField = "username";
        SortOrder sortOrder = SortOrder.ASCENDING;
        
        UserService userService = new UserService(sessionFactory);
        UserBusqueda userBusqueda = UserBusqueda.builder().role(RoleEnum.ROLE_EQUIPO_INSPECCIONES).build();
        
        List<User> listaUsuarios = userService.buscarUsuarioCriteria(first, pageSize, sortField, sortOrder,
                userBusqueda);
        assertThat(listaUsuarios).hasSize(20);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.UserService#contarRegistros(es.mira.progesin.web.beans.UserBusqueda)}.
     */
    @Test
    public void contarRegistros() {
        UserService userService = new UserService(sessionFactory);
        UserBusqueda userBusqueda = UserBusqueda.builder().username("EZEN").build();
        
        int numRegistros = userService.contarRegistros(userBusqueda);
        assertThat(numRegistros).isEqualTo(1);
    }
    
}
