package es.mira.progesin.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;

/**
 * Servicio de login.
 * 
 * @author EZENTIS
 *
 */
@Service
public class LoginService implements UserDetailsService {
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private IUserService userService;
    
    /**
     * Devuelve los datos de un usuario cuyo username se ha recibido como parámetro.
     * 
     * @param username Username del usuario a buscar.
     * @return Detalles del usuario
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByUsernameIgnoreCase(username);
        if (null == user) {
            throw new UsernameNotFoundException("El usuario " + username + " no existe.");
        } else {
            return new UserRepositoryUserDetails(user);
        }
    }
    
    private static final class UserRepositoryUserDetails extends User implements UserDetails {
        
        private static final long serialVersionUID = 1L;
        
        /**
         * Constructor que recibe como parámetro un usuario.
         * 
         * @param user Usuario del que se desean los detalles.
         */
        public UserRepositoryUserDetails(User user) {
            super();
            username = user.getUsername();
            password = user.getPassword();
            nombre = user.getNombre();
            apellido1 = user.getApellido1();
            apellido2 = user.getApellido2();
            role = user.getRole();
            correo = user.getCorreo();
            docIdentidad = user.getDocIdentidad();
            estado = user.getEstado();
            telefono = user.getTelefono();
            fechaAlta = user.getFechaAlta();
            fechaBaja = user.getFechaBaja();
        }
        
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(role.name()));
            return authorities;
        }
        
        @Override
        public boolean isAccountNonExpired() {
            return fechaBaja == null;
        }
        
        @Override
        public boolean isAccountNonLocked() {
            return EstadoEnum.ACTIVO.equals(estado);
        }
        
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
    }
    
}
