package es.mira.progesin.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.RegActividad;
import es.mira.progesin.persistence.repositories.IRegActividadRepository;
import es.mira.progesin.web.beans.RegActividadBusqueda;


@Service
public class RegActividadService implements IRegActividadService {
	
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
	public Iterable<RegActividad> findAll() {
		return regActividadRepository.findAll();
	}

	public Iterable<RegActividad> findAll(Iterable<Integer> ids) {
		return regActividadRepository.findAll(ids);
	}

	@Override
	public RegActividad findOne(Integer id) {
		return regActividadRepository.findOne(id);
	}


	@Override
	@Transactional(readOnly = false)
	public RegActividad save(RegActividad entity) {
		return regActividadRepository.save(entity);
	}
	
	@Override
	public List<RegActividad> buscarRegActividadCriteria(RegActividadBusqueda regActividadBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(RegActividad.class);

		if (regActividadBusqueda.getFechaDesde() != null) {
			criteria.add(Restrictions.ge("fechaAlta", regActividadBusqueda.getFechaDesde()));
		}
		if (regActividadBusqueda.getFechaHasta() != null) {
			criteria.add(Restrictions.lt("fechaAlta", regActividadBusqueda.getFechaHasta()));
		}
		if (regActividadBusqueda.getNombreSeccion() != null && !regActividadBusqueda.getNombreSeccion().isEmpty()) {
			criteria.add(Restrictions.ilike("nombreSeccion", regActividadBusqueda.getNombreSeccion(), MatchMode.ANYWHERE));
		}
		if (regActividadBusqueda.getTipoRegActividad() != null && !regActividadBusqueda.getTipoRegActividad().isEmpty()) {
			criteria.add(Restrictions.ilike("tipoRegActividad", regActividadBusqueda.getTipoRegActividad(), MatchMode.ANYWHERE));
		}
		if (regActividadBusqueda.getUsernameRegActividad() != null && !regActividadBusqueda.getUsernameRegActividad().isEmpty()) {
			criteria.add(Restrictions.ilike("usernameRegActividad", regActividadBusqueda.getUsernameRegActividad(), MatchMode.ANYWHERE));
		}
		
		criteria.add(Restrictions.isNull("fechaBaja"));
		criteria.addOrder(Order.desc("fechaAlta"));

		@SuppressWarnings("unchecked")
		List<RegActividad> listaRegActividad = criteria.list();
		session.close();

		return listaRegActividad;
	}

}
