package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;

/**
 * @author EZENTIS
 * 
 * Test del repositorio User
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
// @AutoConfigureTestDatabase(replace = Replace.NONE)
public class IUserRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private IUserRepository repository;
    
    /**
     * 
     */
    @Test
    public final void testFindByCorreoIgnoreCaseOrDocIdentidadIgnoreCase() {
        this.entityManager.persist(createUser());
        User user = this.repository.findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase("CORREOTEST@ezentis.com", "");
        assertThat(user.getUsername()).isEqualTo("pepetest");
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByCorreo() {
        this.entityManager.persist(createUser());
        User user = this.repository.findByCorreo("correotest@ezentis.com");
        assertThat(user.getUsername()).isEqualTo("pepetest");
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByCorreoOrDocIdentidad() {
        this.entityManager.persist(createUser());
        User user = this.repository.findByCorreoOrDocIdentidad("", "1111111111");
        assertThat(user.getUsername()).isEqualTo("pepetest");
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByCuerpoEstado() {
        User user = this.entityManager.persist(createUser());
        CuerpoEstado cuerpo = new CuerpoEstado();
        cuerpo.setId(1);
        List<User> userList = this.repository.findByCuerpoEstado(cuerpo);
        assertThat(userList).contains(user);
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByfechaBajaIsNullAndRoleNotIn() {
        User user = this.entityManager.persist(createUser());
        List<User> userList = this.repository.findByfechaBajaIsNullAndRoleNotIn(Arrays.asList(RoleEnum.ROLE_ADMIN));
        assertThat(userList).doesNotContain(user);
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByfechaBajaIsNullAndRole() {
        User user = this.entityManager.persist(createUser());
        List<User> userList = this.repository.findByfechaBajaIsNullAndRole(RoleEnum.ROLE_ADMIN);
        assertThat(userList).contains(user);
    }
    
    /**
     * 
     */
    @Test
    public final void testBuscarNoJefeNoMiembroEquipo() {
        Equipo equipo = Equipo.builder().id(99L).jefeEquipo("pepetest").build();
        List<User> userList = this.repository.buscarNoJefeNoMiembroEquipo(equipo);
        assertThat(userList).doesNotContain(createUser());
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByUsernameIgnoreCase() {
        this.entityManager.persist(createUser());
        User user = this.repository.findByUsernameIgnoreCase("pepetest");
        assertThat(user).isNotNull();
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByPuestoTrabajo() {
        User user = this.entityManager.persist(createUser());
        PuestoTrabajo puesto = PuestoTrabajo.builder().id(1L).build();
        List<User> userList = this.repository.findByPuestoTrabajo(puesto);
        assertThat(userList).contains(user);
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByDepartamento() {
        User user = this.entityManager.persist(createUser());
        Departamento departamento = Departamento.builder().id(1L).build();
        List<User> userList = this.repository.findByDepartamento(departamento);
        assertThat(userList).contains(user);
    }
    
    private User createUser() {
        User user = new User();
        user.setUsername("pepetest");
        user.setFechaAlta(new Date());
        user.setUsernameAlta("system");
        user.setApellido1("apellido1");
        user.setCorreo("correotest@ezentis.com");
        user.setDocIdentidad("1111111111");
        user.setEstado(EstadoEnum.ACTIVO);
        user.setNombre("pepe");
        user.setPassword("123");
        user.setRole(RoleEnum.ROLE_ADMIN);
        user.setCuerpoEstado(new CuerpoEstado(1, "", ""));
        user.setPuestoTrabajo(PuestoTrabajo.builder().id(1L).build());
        user.setDepartamento(Departamento.builder().id(1L).build());
        return user;
    }
}
