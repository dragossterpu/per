package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import es.mira.progesin.persistence.entities.Mensaje;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;

public interface IMensajeRepository extends PagingAndSortingRepository<Mensaje, Long> {
	
	List<Mensaje> findByUsuario(String usuario);
	
	Mensaje findByUsuarioAndTipoAndIdMensaje(String usuario, TipoMensajeEnum tipo, Long id);

	List<Mensaje> findByUsuarioAndTipo(String usuario, TipoMensajeEnum tipo);
	
	

}
