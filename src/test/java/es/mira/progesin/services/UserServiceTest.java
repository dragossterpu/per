package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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
     * Username de prueba.
     */
    private static final String EZENTIS = "ezentis";
    
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
        userService.delete(EZENTIS);
        verify(userRepositoryMock, times(1)).delete(EZENTIS);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#exists(String)}.
     */
    @Test
    public void exists() {
        userService.exists(EZENTIS);
        verify(userRepositoryMock, times(1)).exists(EZENTIS);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#findOne(String)}.
     */
    @Test
    public void findOne() {
        User user = User.builder().username(EZENTIS).build();
        when(userRepositoryMock.findOne(EZENTIS)).thenReturn(user);
        User userFind = userService.findOne(EZENTIS);
        assertThat(userFind.getUsername()).isEqualTo(user.getUsername());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#findByUsernameIgnoreCase(String)}.
     */
    @Test
    public void findByUsernameIgnoreCase() {
        userService.findByUsernameIgnoreCase(EZENTIS);
        verify(userRepositoryMock, times(1)).findByUsernameIgnoreCase(EZENTIS);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#save(User)}.
     */
    @Test
    public void save() {
        User user = User.builder().username(EZENTIS).build();
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
     * Test method for {@link es.mira.progesin.services.UserService#buscarNoMiembroEquipoNoJefe(Long)}.
     */
    @Test
    public void buscarNoJefeNoMiembroEquipo() {
        Equipo equipo = Equipo.builder().id(2L).build();
        userService.buscarNoMiembroEquipoNoJefe(equipo.getId());
        verify(userRepositoryMock, times(1)).buscarPosibleMiembroEquipoNoJefe(equipo.getId());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#buscarUserSinEquipo()}.
     */
    @Test
    public void buscarNoMiembro() {
        userService.buscarUserSinEquipo();
        verify(userRepositoryMock, times(1)).buscarNoMiembro();
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
     * Test method for {@link es.mira.progesin.services.UserService#existsByPuestoTrabajo(PuestoTrabajo)}.
     */
    @Test
    public void existsByPuestoTrabajo() {
        PuestoTrabajo puesto = PuestoTrabajo.builder().id(1L).build();
        userService.existsByPuestoTrabajo(puesto);
        verify(userRepositoryMock, times(1)).existsByPuestoTrabajo(puesto);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#existsByDepartamento(Departamento)}.
     */
    @Test
    public void existsByDepartamento() {
        Departamento departamento = Departamento.builder().id(1L).build();
        userService.existsByDepartamento(departamento);
        verify(userRepositoryMock, times(1)).existsByDepartamento(departamento);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#existsByCuerpoEstado(CuerpoEstado)}.
     */
    @Test
    public void existsByCuerpoEstado() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).build();
        userService.existsByCuerpoEstado(cuerpo);
        verify(userRepositoryMock, times(1)).existsByCuerpoEstado(cuerpo);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#save(java.util.List)}.
     */
    @Test
    public void save_listaUsers() {
        List<User> listaUsuarios = new ArrayList<>();
        User user = User.builder().username(EZENTIS).build();
        listaUsuarios.add(user);
        userService.save(listaUsuarios);
        
        verify(userRepositoryMock, times(1)).save(listaUsuarios);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.UserService#usuariosEquipo(Equipo)}.
     */
    @Test
    public void usuariosEquipo() {
        Equipo equipo = mock(Equipo.class);
        userService.usuariosEquipo(equipo);
        verify(userRepositoryMock, times(1)).usuariosEnEquipo(equipo);
    }
}
