package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;

public interface IAlertaService {
	void delete(Long id);

	void deleteAll();

	boolean exists(Long id);

	Alerta findOne(Long id);

	List<Alerta> findByFechaBajaIsNull();

	void crearAlertaUsuario(String seccion, String descripcion, String usuario);
	
	void crearAlertaRol(String seccion, String descripcion, RoleEnum rol);
	
	void crearAlertaEquipo(String seccion, String descripcion, Long idEquipo);
	
	void crearAlertaJefeEquipo(String seccion, String descripcion, Long idEquipo);
}
