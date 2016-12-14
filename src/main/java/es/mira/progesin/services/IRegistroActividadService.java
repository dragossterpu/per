package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.web.beans.RegActividadBusqueda;

public interface IRegistroActividadService {
	void delete(Integer id);

	void deleteAll();

	boolean exists(Integer id);

	Iterable<RegistroActividad> findAll();

	RegistroActividad findOne(Integer id);

	RegistroActividad save(RegistroActividad entity);

	List<RegistroActividad> buscarRegActividadCriteria(RegActividadBusqueda regActividadBusqueda);

	void altaRegActividadError(String nombreSeccion, Exception e);

	void altaRegActividad(String descripcion, String tipoReg, String seccion);
	
	public List<String> buscarPorNombreSeccion(String infoSeccion);
	
	public List<String> buscarPorUsuarioRegistro(String infoUsuario);
}
