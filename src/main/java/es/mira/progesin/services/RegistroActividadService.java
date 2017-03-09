package es.mira.progesin.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IRegActividadRepository;
import es.mira.progesin.web.beans.RegActividadBusqueda;
import lombok.extern.slf4j.Slf4j;

/****************************************************
 * 
 * Implementación del servicio de Registro de Actividad
 * 
 * @author Ezentis
 *
 ****************************************************/

@Slf4j
@Service("registroActividadService")
public class RegistroActividadService implements IRegistroActividadService {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private IRegActividadRepository regActividadRepository;

	@Autowired
	private SessionFactory sessionFactory;

	/*********************************************
	 * 
	 * Guarda en base de datos el registro de actividad recibido como parámetro
	 * 
	 * @param RegistroActividad
	 * 
	 *********************************************/

	@Override
	@Transactional(readOnly = false)
	public RegistroActividad save(RegistroActividad entity) {
		return regActividadRepository.save(entity);
	}

	/*********************************************
	 * 
	 * Busca en base de datos según los criterios indicados en un objeto RegActividadBusqueda recibido como parámetro
	 * 
	 * @param RegActividadBusqueda
	 * @return List<RegistroActividad>
	 * 
	 *********************************************/

	@Override
	public List<RegistroActividad> buscarRegActividadCriteria(RegActividadBusqueda regActividadBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(RegistroActividad.class);

		if (regActividadBusqueda.getFechaDesde() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions
					.sqlRestriction("TRUNC(fecha_alta) >= '" + sdf.format(regActividadBusqueda.getFechaDesde()) + "'"));
		}
		if (regActividadBusqueda.getFechaHasta() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions
					.sqlRestriction("TRUNC(fecha_alta) <= '" + sdf.format(regActividadBusqueda.getFechaHasta()) + "'"));
		}
		if (regActividadBusqueda.getNombreSeccion() != null && !regActividadBusqueda.getNombreSeccion().isEmpty()) {
			criteria.add(Restrictions.sqlRestriction(
					"upper(convert(replace(nombre_seccion, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('"
							+ regActividadBusqueda.getNombreSeccion() + "', ' ', '') || '%', 'US7ASCII'))"));
		}
		if (regActividadBusqueda.getTipoRegActividad() != null
				&& !regActividadBusqueda.getTipoRegActividad().isEmpty()) {
			criteria.add(Restrictions.ilike("tipoRegActividad", regActividadBusqueda.getTipoRegActividad(),
					MatchMode.ANYWHERE));
		}
		if (regActividadBusqueda.getUsernameRegActividad() != null
				&& !regActividadBusqueda.getUsernameRegActividad().isEmpty()) {
			criteria.add(Restrictions.ilike("usernameRegActividad", regActividadBusqueda.getUsernameRegActividad(),
					MatchMode.ANYWHERE));
		}

		criteria.addOrder(Order.desc("fechaAlta"));

		@SuppressWarnings("unchecked")
		List<RegistroActividad> listaRegActividad = criteria.list();
		session.close();

		return listaRegActividad;
	}

	/*********************************************
	 * 
	 * Graba en el registro de actividad un error del que se reciben como parámetro la sección y la excepción lanzada.
	 * De la excepción posteriormente se grabará el stacktrace
	 * 
	 * @param String
	 * @param Exception
	 * 
	 *********************************************/

	@Override
	public void altaRegActividadError(String nombreSeccion, Exception e) {
		try {

			RegistroActividad registroActividad = new RegistroActividad();
			registroActividad.setTipoRegActividad(TipoRegistroEnum.ERROR.name());
			registroActividad.setFechaAlta(new Date());
			registroActividad.setNombreSeccion(nombreSeccion);
			registroActividad.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
			registroActividad.setDescripcion(Throwables.getStackTraceAsString(e));
			regActividadRepository.save(registroActividad);
		}
		catch (Exception e1) {
			log.error(e1.getMessage());

		}
	}

	/*********************************************
	 * 
	 * Graba en el registro de actividad una acción de la que quiere guardarse traza. Se reciben como parámetro la
	 * sección, el tipo de registro y la descripción.
	 * 
	 * @param String descripcion
	 * @param String tipoReg
	 * @param String seccion
	 * 
	 *********************************************/
	@Override
	public void altaRegActividad(String descripcion, String tipoReg, String seccion) {
		try {
			RegistroActividad registroActividad = new RegistroActividad();
			registroActividad.setTipoRegActividad(tipoReg);
			if (SecurityContextHolder.getContext().getAuthentication() != null) {
				registroActividad
						.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
			}
			else {
				registroActividad.setUsernameRegActividad("system");
			}
			registroActividad.setFechaAlta(new Date());
			registroActividad.setNombreSeccion(seccion);
			registroActividad.setDescripcion(descripcion);
			regActividadRepository.save(registroActividad);
		}
		catch (Exception e) {
			altaRegActividadError(seccion, e);
		}

	}

	/****************************************************
	 * 
	 * Devuelve una lista de nombre de seccion cuyo nombreSeccion incluya la cadena recibida como parámetro
	 * 
	 * @param info
	 * @return List<String>
	 * 
	 ****************************************************/

	@Override
	public List<String> buscarPorNombreSeccion(String infoSeccion) {
		return regActividadRepository.buscarPorNombreSeccion("%" + infoSeccion + "%");
	}

	/****************************************************
	 * 
	 * Devuelve una lista de nombres de usuario cuyo nombre incluya la cadena recibida como parámetro
	 * 
	 * @param info
	 * @return List<String>
	 * 
	 ****************************************************/

	@Override
	public List<String> buscarPorUsuarioRegistro(String infoUsuario) {
		return regActividadRepository.buscarPorUsuarioRegistro("%" + infoUsuario + "%");
	}

}
