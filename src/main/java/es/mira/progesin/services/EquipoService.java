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
import es.mira.progesin.persistence.entities.Miembros;
import es.mira.progesin.persistence.repositories.IEquipoRepository;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;
import es.mira.progesin.web.beans.EquipoBusqueda;

@Service
public class EquipoService implements IEquipoService {

	@Autowired
	IEquipoRepository equipoRepository;

	@Autowired
	IMiembrosRepository miembrosRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Iterable<Equipo> findAll() {
		return equipoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Equipo> save(Iterable<Equipo> entities) {
		return equipoRepository.save(entities);
	}

	@Override

	public Equipo save(Equipo entity) {
		return equipoRepository.save(entity);
	}

	@Override
	public Miembros save(Miembros miembro) {
		return miembrosRepository.save(miembro);
	}

	@Override
	public List<Miembros> findByIdMiembros(Integer idMiembros) {
		return miembrosRepository.findByIdMiembros(idMiembros);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Equipo> buscarEquipoCriteria(EquipoBusqueda equipoBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Equipo.class);

		if (equipoBusqueda.getFechaDesde() != null) {
			criteria.add(Restrictions.ge("fechaAlta", equipoBusqueda.getFechaDesde()));
		}
		if (equipoBusqueda.getFechaHasta() != null) {
			criteria.add(Restrictions.lt("fechaAlta", equipoBusqueda.getFechaHasta()));
		}
		if (equipoBusqueda.getNombreJefe() != null && equipoBusqueda.getNombreJefe().isEmpty() == false) {
			criteria.add(Restrictions.ilike("nombreJefe", equipoBusqueda.getNombreJefe(), MatchMode.ANYWHERE));
		}

		if (equipoBusqueda.getNombreEquipo() != null && equipoBusqueda.getNombreEquipo().isEmpty() == false) {
			criteria.add(Restrictions.ilike("nombreEquipo", equipoBusqueda.getNombreEquipo(), MatchMode.ANYWHERE));
		}
		if (equipoBusqueda.getEstado() != null && equipoBusqueda.getEstado().equals("INACTIVO")) {
			criteria.add(Restrictions.isNotNull("fechaBaja"));
			criteria.addOrder(Order.desc("fechaBaja"));
		}
		if (equipoBusqueda.getEstado() != null && equipoBusqueda.getEstado().equals("ACTIVO")) {
			criteria.add(Restrictions.isNull("fechaBaja"));
		}
		if (equipoBusqueda.getEstado() != null && equipoBusqueda.getEstado().equals("INACTIVO")) {
			criteria.addOrder(Order.desc("fechaBaja"));

		}
		else {

			criteria.addOrder(Order.desc("fechaAlta"));
		}
		List<Equipo> listEquipos = criteria.list();
		session.close();

		return listEquipos;
	}

}
