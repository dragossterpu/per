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

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.repositories.IRegActividadRepository;
import es.mira.progesin.util.Utilities;
import es.mira.progesin.web.beans.RegActividadBusqueda;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("registroActividadService")
public class RegistroActividadService implements IRegistroActividadService {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	IRegActividadRepository regActividadRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		regActividadRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		regActividadRepository.deleteAll();
	}

	@Override
	public boolean exists(Integer id) {
		return regActividadRepository.exists(id);
	}

	@Override
	public Iterable<RegistroActividad> findAll() {
		return regActividadRepository.findAll();
	}

	public Iterable<RegistroActividad> findAll(Iterable<Integer> ids) {
		return regActividadRepository.findAll(ids);
	}

	@Override
	public RegistroActividad findOne(Integer id) {
		return regActividadRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public RegistroActividad save(RegistroActividad entity) {
		return regActividadRepository.save(entity);
	}

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
					.sqlRestriction("TRUNC(fecha_alta) <= '" + sdf.format(regActividadBusqueda.getFechaDesde()) + "'"));
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
					"upper(convert(replace(nombreSeccion, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('"
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

	@Override
	public void altaRegActividadError(String nombreSeccion, Exception e) {
		try {
			RegistroActividad registroActividad = new RegistroActividad();
			String message = Utilities.messageError(e);
			registroActividad.setTipoRegActividad(EstadoRegActividadEnum.ERROR.name());
			registroActividad.setFechaAlta(new Date());
			registroActividad.setNombreSeccion(nombreSeccion);
			registroActividad.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
			registroActividad.setDescripcion(message);
			regActividadRepository.save(registroActividad);
		}
		catch (Exception e1) {
			log.error(e1.getMessage());
		}
	}

	@Override
	public void altaRegActividad(String descripcion, String tipoReg, String seccion) {
		try {
			RegistroActividad registroActividad = new RegistroActividad();
			registroActividad.setTipoRegActividad(tipoReg);
			registroActividad.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
			registroActividad.setFechaAlta(new Date());
			registroActividad.setNombreSeccion(seccion);
			registroActividad.setDescripcion(descripcion);
			regActividadRepository.save(registroActividad);
		}
		catch (Exception e) {
			altaRegActividadError(seccion, e);
		}

	}

	@Override
	public List<String> buscarPorNombreSeccion(String infoSeccion) {
		return regActividadRepository.buscarPorNombreSeccion("%" + infoSeccion + "%");
	}

	@Override
	public List<String> buscarPorUsuarioRegistro(String infoUsuario) {
		return regActividadRepository.buscarPorUsuarioRegistro("%" + infoUsuario + "%");
	}

}
