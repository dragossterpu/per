package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.web.beans.RegActividadBusqueda;

/******************************************************
 * 
 * Interfaz para el servicio de Registro de Actividad
 * 
 * @author Ezentis
 *
 ******************************************************/

public interface IRegistroActividadService {

	RegistroActividad save(RegistroActividad entity);

	List<RegistroActividad> buscarRegActividadCriteria(RegActividadBusqueda regActividadBusqueda);

	void altaRegActividadError(String nombreSeccion, Exception e);

	void altaRegActividad(String descripcion, String tipoReg, String seccion);

	public List<String> buscarPorNombreSeccion(String infoSeccion);

	public List<String> buscarPorUsuarioRegistro(String infoUsuario);
}
