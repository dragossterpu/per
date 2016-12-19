package es.mira.progesin.persistence.repositories;

import java.util.List;
/******************
 * Repositorio de alertas y notificaciones
 * 
 * @author Ezentis
 * 
 * *****************/

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;

public interface IAlertasNotificacionesUsuarioRepository extends PagingAndSortingRepository<AlertasNotificacionesUsuario, Long> {
	
	List<AlertasNotificacionesUsuario> findByUsuario(String usuario);
	
	AlertasNotificacionesUsuario findByUsuarioAndTipoAndIdMensaje(String usuario, TipoMensajeEnum tipo, Long id);

	Page<AlertasNotificacionesUsuario>  findByUsuarioAndTipo(String usuario, TipoMensajeEnum tipo, Pageable page);
	
	List<AlertasNotificacionesUsuario> findByUsuarioAndTipo(String usuario, TipoMensajeEnum tipo);

}
