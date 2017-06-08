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
 * 
 * Test del repositorio User.
 * 
 * @author EZENTIS
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IUserRepositoryTest {
    /**
     * Repositorio de usuarios.
     */
    @Autowired
    private IUserRepository repository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IUserRepository#findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(String, String)}
     * .
     */
    @Test
    public final void testFindByCorreoIgnoreCaseOrDocIdentidadIgnoreCase() {
        User user = this.repository.findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase("EZENTIS@EZENTIS.COM", "");
        assertThat(user.getUsername()).isEqualTo("ezentis");
    }
    
    /**
     * Test method for {@link es.mira.progesin.persistence.repositories.IUserRepository#findByCorreo(String)}.
     */
    @Test
    public final void testFindByCorreo() {
        User user = this.repository.findByCorreo("ezentis@ezentis.com");
        assertThat(user.getUsername()).isEqualTo("ezentis");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IUserRepository#findByCorreoOrDocIdentidad(String, String)}.
     */
    @Test
    public final void testFindByCorreoOrDocIdentidad() {
        User user = this.repository.findByCorreoOrDocIdentidad("", "12345678S");
        assertThat(user.getUsername()).isEqualTo("ezentis");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IUserRepository#findByfechaBajaIsNullAndRoleNotIn(List)}.
     */
    @Test
    public final void testFindByfechaBajaIsNullAndRoleNotIn() {
        List<User> userList = this.repository.findByfechaBajaIsNullAndRoleNotIn(Arrays.asList(RoleEnum.ROLE_ADMIN));
        assertThat(userList.size()).isEqualTo(44);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IUserRepository#findByfechaBajaIsNullAndRole(RoleEnum)}.
     */
    @Test
    public final void testFindByfechaBajaIsNullAndRole() {
        List<User> userList = this.repository.findByfechaBajaIsNullAndRole(RoleEnum.ROLE_ADMIN);
        assertThat(userList.size()).isEqualTo(5);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IUserRepository#buscarNoJefeNoMiembroEquipo(Equipo)}.
     */
    @Test
    public final void testBuscarNoJefeNoMiembroEquipo() {
        Equipo equipo = Equipo.builder().id(2L).jefeEquipo("cgonzalez").build();
        List<User> userList = this.repository.buscarNoJefeNoMiembroEquipo(equipo);
        assertThat(userList.size()).isEqualTo(33);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IUserRepository#findByUsernameIgnoreCase(String)}.
     */
    @Test
    public final void testFindByUsernameIgnoreCase() {
        User user = this.repository.findByUsernameIgnoreCase("ezentis");
        assertThat(user).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IUserRepository#existsByPuestoTrabajo(PuestoTrabajo)}.
     */
    @Test
    public final void testExistsByPuestoTrabajo() {
        PuestoTrabajo puesto = PuestoTrabajo.builder().id(1L).build();
        assertThat(this.repository.existsByPuestoTrabajo(puesto)).isTrue();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IUserRepository#existsByDepartamento(Departamento)}.
     */
    @Test
    public final void testExistsByDepartamento() {
        Departamento departamento = Departamento.builder().id(8L).build();
        assertThat(this.repository.existsByDepartamento(departamento)).isTrue();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IUserRepository#existsByCuerpoEstado(CuerpoEstado)}.
     */
    @Test
    public final void existsByCuerpoEstado() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).build();
        assertThat(this.repository.existsByCuerpoEstado(cuerpo)).isTrue();
    }
}
