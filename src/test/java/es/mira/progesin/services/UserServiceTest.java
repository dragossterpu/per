package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IUserRepository;

/**
 * 
 * Test para la clase UserService.
 *
 * @author EZENTIS
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    
    /**
     * Simulación de un repositorio de usuarios.
     */
    @Mock
    private IUserRepository userRepositoryMock;
    
    /**
     * Simulación del encriptador de claves.
     */
    @Mock
    private PasswordEncoder passwordEncoderMock;
    
    /**
     * Inyección del servicio de usuarios.
     */
    @InjectMocks
    private IUserService userService = new UserService();
    
    /**
     * Comprueba que existe la clase.
     */
    @Test
    public void type() {
        assertThat(UserService.class).isNotNull();
    }
    
    /**
     * Comprueba que no es una clase abstracta.
     */
    @Test
    public void instantiation() {
        UserService target = new UserService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#delete(String)}.
     */
    @Test
    public void deleteById() {
        userService.delete("ezentis");
        verify(userRepositoryMock, times(1)).delete("ezentis");
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#exists(String)}.
     */
    @Test
    public void exists() {
        userService.exists("ezentis");
        verify(userRepositoryMock, times(1)).exists("ezentis");
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#findOne(String)}.
     */
    @Test
    public void findOne() {
        User user = User.builder().username("ezentis").build();
        when(userRepositoryMock.findOne("ezentis")).thenReturn(user);
        User userFind = userService.findOne("ezentis");
        assertThat(userFind.getUsername()).isEqualTo(user.getUsername());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#findByUsernameIgnoreCase(String)}.
     */
    @Test
    public void findByUsernameIgnoreCase() {
        userService.findByUsernameIgnoreCase("ezentis");
        verify(userRepositoryMock, times(1)).findByUsernameIgnoreCase("ezentis");
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#save(User)}.
     */
    @Test
    public void save() {
        User user = User.builder().username("ezentis").build();
        userService.save(user);
        verify(userRepositoryMock, times(1)).save(user);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.UserService#findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(String, String)}.
     */
    @Test
    public void findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase() {
        userService.findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase("ezentis@ezentis.com", "");
        verify(userRepositoryMock, times(1)).findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase("ezentis@ezentis.com", "");
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#findByCuerpoEstado(CuerpoEstado)}.
     */
    @Test
    public void findByCuerpoEstado() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).build();
        userService.findByCuerpoEstado(cuerpo);
        verify(userRepositoryMock, times(1)).findByCuerpoEstado(cuerpo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#findByfechaBajaIsNullAndRoleNotIn(java.util.List)}.
     */
    @Test
    public void findByfechaBajaIsNullAndRoleNotIn() {
        userService.findByfechaBajaIsNullAndRoleNotIn(Arrays.asList(RoleEnum.ROLE_ADMIN));
        verify(userRepositoryMock, times(1)).findByfechaBajaIsNullAndRoleNotIn(Arrays.asList(RoleEnum.ROLE_ADMIN));
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#cambiarEstado(String, EstadoEnum)}.
     */
    @Test
    public void cambiarEstado() {
        User user = User.builder().username("provisional").estado(EstadoEnum.ACTIVO).build();
        when(userRepositoryMock.findOne("provisional")).thenReturn(user);
        userService.cambiarEstado("provisional", EstadoEnum.INACTIVO);
        verify(userRepositoryMock, times(1)).save(user);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#findByfechaBajaIsNullAndRole(RoleEnum)}.
     */
    
    @Test
    public void findByfechaBajaIsNullAndRole() {
        userService.findByfechaBajaIsNullAndRole(RoleEnum.ROLE_ADMIN);
        verify(userRepositoryMock, times(1)).findByfechaBajaIsNullAndRole(RoleEnum.ROLE_ADMIN);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#buscarNoJefeNoMiembroEquipo(Equipo)}.
     */
    @Test
    public void buscarNoJefeNoMiembroEquipo() {
        Equipo equipo = Equipo.builder().id(2L).build();
        userService.buscarNoJefeNoMiembroEquipo(equipo);
        verify(userRepositoryMock, times(1)).buscarNoJefeNoMiembroEquipo(equipo);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.UserService#crearUsuariosProvisionalesCuestionario(String, String)}.
     */
    @Test
    public void crearUsuariosProvisionalesCuestionario() {
        when(passwordEncoderMock.encode("123")).thenReturn("sadafsdfsdf");
        assertThat(userService.crearUsuariosProvisionalesCuestionario("provisional@ezentis.com", "123").size())
                .isEqualTo(10);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#findByPuestoTrabajo(PuestoTrabajo)}.
     */
    @Test
    public void findByPuestoTrabajo() {
        PuestoTrabajo puesto = PuestoTrabajo.builder().id(1L).build();
        userService.findByPuestoTrabajo(puesto);
        verify(userRepositoryMock, times(1)).findByPuestoTrabajo(puesto);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#findByDepartamento(Departamento)}.
     */
    @Test
    public void findByDepartamento() {
        Departamento departamento = Departamento.builder().id(1L).build();
        userService.findByDepartamento(departamento);
        verify(userRepositoryMock, times(1)).findByDepartamento(departamento);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#existByCuerpoEstado(CuerpoEstado)}.
     */
    @Test
    public void existByCuerpoEstado() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).build();
        userService.existByCuerpoEstado(cuerpo);
        verify(userRepositoryMock, times(1)).existsByCuerpoEstado(cuerpo);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#save(java.util.List)}.
     */
    @Test
    public void save_listaUsers() {
        List<User> listaUsuarios = new ArrayList<>();
        User user = User.builder().username("ezentis").build();
        listaUsuarios.add(user);
        userService.save(listaUsuarios);
        
        verify(userRepositoryMock, times(1)).save(listaUsuarios);
    }
}
