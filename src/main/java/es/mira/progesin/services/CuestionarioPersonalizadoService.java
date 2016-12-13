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

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.repositories.ICuestionarioPersonalizadoRepository;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda;

@Service
public class CuestionarioPersonalizadoService implements ICuestionarioPersonalizadoService {

	@Autowired
	ICuestionarioPersonalizadoRepository cuestionarioPersRep;

	@Autowired
	private SessionFactory sessionFactory;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static final String FECHA_CREACION = "fechaCreacion";

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		cuestionarioPersRep.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Iterable<CuestionarioPersonalizado> entities) {
		cuestionarioPersRep.delete(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(CuestionarioPersonalizado entity) {
		cuestionarioPersRep.delete(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		cuestionarioPersRep.deleteAll();
	}

	@Override
	public boolean exists(Long id) {
		return cuestionarioPersRep.exists(id);
	}

	@Override
	public Iterable<CuestionarioPersonalizado> findAll() {
		return cuestionarioPersRep.findAll();
	}

	@Override
	public Iterable<CuestionarioPersonalizado> findAll(Iterable<Long> ids) {
		return cuestionarioPersRep.findAll(ids);
	}

	@Override
	public CuestionarioPersonalizado findOne(Long id) {
		return cuestionarioPersRep.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Iterable<CuestionarioPersonalizado> save(Iterable<CuestionarioPersonalizado> entities) {
		return cuestionarioPersRep.save(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public CuestionarioPersonalizado save(CuestionarioPersonalizado entity) {
		return cuestionarioPersRep.save(entity);
	}

	@Override
	public List<CuestionarioPersonalizado> buscarCuestionarioPersonalizadoCriteria(
			CuestionarioPersonalizadoBusqueda cuestionarioBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(CuestionarioPersonalizado.class);

		if (cuestionarioBusqueda.getFechaDesde() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions.sqlRestriction(
					"TRUNC(this_.fecha_creacion) >= '" + sdf.format(cuestionarioBusqueda.getFechaDesde())));
		}
		if (cuestionarioBusqueda.getFechaHasta() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions.sqlRestriction(
					"TRUNC(this_.fecha_creacion) <= '" + sdf.format(cuestionarioBusqueda.getFechaHasta())));
		}
		if (cuestionarioBusqueda.getUsername() != null && !cuestionarioBusqueda.getUsername().isEmpty()) {
			criteria.add(
					Restrictions.ilike("usernameCreacion", cuestionarioBusqueda.getUsername(), MatchMode.ANYWHERE));
		}
		if (cuestionarioBusqueda.getModeloCuestionarioSeleccionado() != null) {
			criteria.add(
					Restrictions.eq("modeloCuestionario", cuestionarioBusqueda.getModeloCuestionarioSeleccionado()));
		}
		if (cuestionarioBusqueda.getNombreCuestionario() != null
				&& !cuestionarioBusqueda.getNombreCuestionario().isEmpty()) {
			criteria.add(Restrictions.ilike("nombreCuestionario", cuestionarioBusqueda.getNombreCuestionario(),
					MatchMode.ANYWHERE));
		}

		criteria.addOrder(Order.desc(FECHA_CREACION));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<CuestionarioPersonalizado> listaCuestionarios = criteria.list();
		session.close();

		return listaCuestionarios;
	}

}
