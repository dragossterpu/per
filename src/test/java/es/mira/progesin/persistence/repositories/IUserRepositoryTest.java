package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
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
    private IUserRepository repository;
    
    /**
     * 
     */
    @Test
    public final void testFindByCorreoIgnoreCaseOrDocIdentidadIgnoreCase() {
        User user = this.repository.findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase("EZENTIS@EZENTIS.COM", "");
        assertThat(user.getUsername()).isEqualTo("ezentis");
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByCorreo() {
        User user = this.repository.findByCorreo("ezentis@ezentis.com");
        assertThat(user.getUsername()).isEqualTo("ezentis");
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByCorreoOrDocIdentidad() {
        User user = this.repository.findByCorreoOrDocIdentidad("", "12345678S");
        assertThat(user.getUsername()).isEqualTo("ezentis");
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByCuerpoEstado() {
        CuerpoEstado cuerpo = new CuerpoEstado();
        cuerpo.setId(1);
        List<User> userList = this.repository.findByCuerpoEstado(cuerpo);
        assertThat(userList.size()).isEqualTo(15);
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByfechaBajaIsNullAndRoleNotIn() {
        List<User> userList = this.repository.findByfechaBajaIsNullAndRoleNotIn(Arrays.asList(RoleEnum.ROLE_ADMIN));
        assertThat(userList.size()).isEqualTo(44);
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByfechaBajaIsNullAndRole() {
        List<User> userList = this.repository.findByfechaBajaIsNullAndRole(RoleEnum.ROLE_ADMIN);
        assertThat(userList.size()).isEqualTo(5);
    }
    
    /**
     * 
     */
    @Test
    public final void testBuscarNoJefeNoMiembroEquipo() {
        Equipo equipo = Equipo.builder().id(2L).jefeEquipo("cgonzalez").build();
        List<User> userList = this.repository.buscarNoJefeNoMiembroEquipo(equipo);
        assertThat(userList.size()).isEqualTo(35);
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByUsernameIgnoreCase() {
        User user = this.repository.findByUsernameIgnoreCase("ezentis");
        assertThat(user).isNotNull();
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByPuestoTrabajo() {
        PuestoTrabajo puesto = PuestoTrabajo.builder().id(1L).build();
        List<User> userList = this.repository.findByPuestoTrabajo(puesto);
        assertThat(userList.size()).isEqualTo(2);
    }
    
    /**
     * 
     */
    @Test
    public final void testFindByDepartamento() {
        Departamento departamento = Departamento.builder().id(1L).build();
        List<User> userList = this.repository.findByDepartamento(departamento);
        assertThat(userList).isNullOrEmpty();
    }
    
}
