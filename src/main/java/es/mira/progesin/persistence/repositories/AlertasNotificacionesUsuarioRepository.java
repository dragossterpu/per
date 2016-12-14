package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;

public interface AlertasNotificacionesUsuarioRepository extends CrudRepository<AlertasNotificacionesUsuario, Long> {
	
	List<AlertasNotificacionesUsuario> findByUsuario(String usuario);
	
	AlertasNotificacionesUsuario findByUsuarioAndTipoAndIdMensaje(String usuario, TipoMensajeEnum tipo, Long id);

	List<AlertasNotificacionesUsuario> findByUsuarioAndTipo(String usuario, TipoMensajeEnum tipo);
	
	

}
