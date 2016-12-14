package es.mira.progesin.services;

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

@Service("registroActividadService")
public class RegistroActividadService implements IRegistroActividadService {

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
			criteria.add(Restrictions.ge("fechaAlta", regActividadBusqueda.getFechaDesde()));
		}
		if (regActividadBusqueda.getFechaHasta() != null) {
			criteria.add(Restrictions.lt("fechaAlta", regActividadBusqueda.getFechaHasta()));
		}
		if (regActividadBusqueda.getNombreSeccion() != null && !regActividadBusqueda.getNombreSeccion().isEmpty()) {
			criteria.add(
					Restrictions.ilike("nombreSeccion", regActividadBusqueda.getNombreSeccion(), MatchMode.ANYWHERE));
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
		RegistroActividad registroActividad = new RegistroActividad();
		String message = Utilities.messageError(e);
		registroActividad.setTipoRegActividad(EstadoRegActividadEnum.ERROR.name());
		registroActividad.setFechaAlta(new Date());
		registroActividad.setNombreSeccion(nombreSeccion);
		registroActividad.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
		registroActividad.setDescripcion(message);
		regActividadRepository.save(registroActividad);
	}

	@Override
	public void altaRegActividad(String descripcion, String tipoReg, String seccion) {
		RegistroActividad registroActividad = new RegistroActividad();
		registroActividad.setTipoRegActividad(tipoReg);
		registroActividad.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
		registroActividad.setFechaAlta(new Date());
		registroActividad.setNombreSeccion(seccion);
		registroActividad.setDescripcion(descripcion);
		regActividadRepository.save(registroActividad);

	}
	
	@Override
	public List<String> buscarPorNombreSeccion(String infoSeccion) {
		return regActividadRepository.buscarPorNombreSeccion("%"+infoSeccion+"%");
	}
	
	@Override
	public List<String> buscarPorUsuarioRegistro(String infoUsuario){
		return regActividadRepository.buscarPorUsuarioRegistro("%"+infoUsuario+"%");
	}

}
