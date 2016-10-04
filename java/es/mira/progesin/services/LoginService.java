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

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findOne(username);
        if (null == user) {
            throw new UsernameNotFoundException("El usuario " + username + " no existe.");
        } else {
            return new UserRepositoryUserDetails(user);
        }
    }

    private final static class UserRepositoryUserDetails extends User implements UserDetails {

        private static final long serialVersionUID = 1L;

        private UserRepositoryUserDetails(User user) {
            super();
            username = user.getUsername();
            password = user.getPassword();
            nombre = user.getNombre();
            apellido1 = user.getApellido1();
            apellido2 = user.getApellido2();
            role = user.getRole();
            correo = user.getCorreo();
            docIndentidad = user.getDocIndentidad();
            envioNotificacion = user.getEnvioNotificacion();
            estado = user.getEstado();
            telefono = user.getTelefono();
            fechaAlta = user.getFechaAlta();
            fechaBaja = user.getFechaBaja();
            numIdentificacion = user.getNumIdentificacion();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER" ));
            return authorities;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
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
