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
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;

@Service
public class LoginService implements UserDetailsService {
	@Autowired
	private IUserService userService;

	@Autowired
	private IRegistroActividadService registroActividadService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsernameIgnoreCase(username);
		if (null == user) {
			throw new UsernameNotFoundException("El usuario " + username + " no existe.");
		}
		else {
			String textoReg = "El usuario " + user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2()
					+ " ha iniciado sesión en la aplicación";
			registroActividadService.altaRegActividad(textoReg, EstadoRegActividadEnum.AUDITORIA.name(), "LOGIN");
			return new UserRepositoryUserDetails(user);
		}
	}

	private static final class UserRepositoryUserDetails extends User implements UserDetails {

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
			if (fechaBaja != null) {
				return false;
			}
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			if (EstadoEnum.ACTIVO.equals(estado)) {
				return true;
			}
			return false;
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
