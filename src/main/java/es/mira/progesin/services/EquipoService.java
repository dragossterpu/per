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

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.repositories.IEquipoRepository;
import es.mira.progesin.web.beans.EquipoBusqueda;

@Service
public class EquipoService implements IEquipoService {

	@Autowired
	IEquipoRepository equipoRepository;

	@Autowired
	private SessionFactory sessionFactory;

	// @Override
	// @Transactional(readOnly = false)
	// public void delete(Integer id) {
	// equipoRepository.delete(id);
	// }
	//
	// @Override
	// @Transactional(readOnly = false)
	// public void delete(Equipo entity) {
	// equipoRepository.delete(entity);
	// }

	// @Override
	// public boolean exists(Integer id) {
	// return equipoRepository.exists(id);
	// }

	@Override
	public Iterable<Equipo> findAll() {
		return equipoRepository.findAll();
	}

	// @Override
	// public Equipo findOne(Integer id) {
	// return equipoRepository.findOne(id);
	// }

	@Override
	@Transactional(readOnly = false)
	public Iterable<Equipo> save(Iterable<Equipo> entities) {
		return equipoRepository.save(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public Equipo save(Equipo entity) {
		return equipoRepository.save(entity);
	}

	// @Override
	// public Equipo findByEquipoEspecial(String equipoEspecial) {
	// return equipoRepository.findByEquipoEspecial(equipoEspecial);
	// }

	@Override
	public List<Equipo> buscarEquipoCriteria(EquipoBusqueda equipoBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Equipo.class);

		if (equipoBusqueda.getFechaDesde() != null) {
			criteria.add(Restrictions.ge("fechaAlta", equipoBusqueda.getFechaDesde()));
		}
		if (equipoBusqueda.getFechaHasta() != null) {
			criteria.add(Restrictions.lt("fechaAlta", equipoBusqueda.getFechaHasta()));
		}
		if (equipoBusqueda.getJefeEquipo() != null && equipoBusqueda.getJefeEquipo().getUsername().isEmpty() == false) {
			criteria.add(
					Restrictions.ilike("jefeEquipo", equipoBusqueda.getJefeEquipo().getUsername(), MatchMode.ANYWHERE));
		}
		if (equipoBusqueda.getNombreEquipo() != null && equipoBusqueda.getNombreEquipo().isEmpty() == false) {
			criteria.add(Restrictions.ilike("username", equipoBusqueda.getNombreEquipo(), MatchMode.ANYWHERE));
		}

		criteria.add(Restrictions.isNull("fechaBaja"));
		criteria.addOrder(Order.desc("fechaAlta"));

		List<Equipo> listEquipos = criteria.list();
		session.close();

		return listEquipos;
	}

}
