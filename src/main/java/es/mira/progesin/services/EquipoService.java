package es.mira.progesin.services;

import java.text.SimpleDateFormat;
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
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.repositories.IEquipoRepository;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;
import es.mira.progesin.web.beans.EquipoBusqueda;

@Service
public class EquipoService implements IEquipoService {

	private static final String FECHABAJA = "fechaBaja";

	@Autowired
	IEquipoRepository equipoRepository;

	@Autowired
	IMiembrosRepository miembrosRepository;

	@Autowired
	private SessionFactory sessionFactory;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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
	public List<Miembros> findByIdEquipo(Long idEquipo) {
		return miembrosRepository.findByIdEquipo(idEquipo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Equipo> buscarEquipoCriteria(EquipoBusqueda equipoBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Equipo.class);

		if (equipoBusqueda.getFechaDesde() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions
					.sqlRestriction("TRUNC(this_.fecha_alta) >= '" + sdf.format(equipoBusqueda.getFechaDesde()) + "'"));
		}
		if (equipoBusqueda.getFechaHasta() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions
					.sqlRestriction("TRUNC(this_.fecha_alta) <= '" + sdf.format(equipoBusqueda.getFechaHasta()) + "'"));
		}
		if (equipoBusqueda.getNombreJefe() != null && !equipoBusqueda.getNombreJefe().isEmpty()) {
			criteria.add(Restrictions.ilike("nombreJefe", equipoBusqueda.getNombreJefe(), MatchMode.ANYWHERE));
		}

		if (equipoBusqueda.getNombreEquipo() != null && !equipoBusqueda.getNombreEquipo().isEmpty()) {
			criteria.add(Restrictions.ilike("nombreEquipo", equipoBusqueda.getNombreEquipo(), MatchMode.ANYWHERE));
		}
		if (equipoBusqueda.getEstado() != null && equipoBusqueda.getEstado().equals(EstadoEnum.ACTIVO.name())) {
			criteria.add(Restrictions.isNull(FECHABAJA));
		}
		if (equipoBusqueda.getEstado() != null && equipoBusqueda.getEstado().equals(EstadoEnum.INACTIVO.name())) {
			criteria.add(Restrictions.isNotNull(FECHABAJA));
			criteria.addOrder(Order.desc(FECHABAJA));

		}
		else {

			criteria.addOrder(Order.desc("fechaAlta"));
		}
		List<Equipo> listEquipos = criteria.list();
		session.close();

		return listEquipos;
	}

}
