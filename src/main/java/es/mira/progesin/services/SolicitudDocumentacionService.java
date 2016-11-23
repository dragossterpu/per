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

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;

@Service
public class SolicitudDocumentacionService implements ISolicitudDocumentacionService {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ISolicitudDocumentacionPreviaRepository solicitudDocumentacionPreviaRepository;

	@Autowired
	IUserService userService;

	@Override
	public SolicitudDocumentacionPrevia save(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia) {
		return solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
	}

	@Override
	public List<SolicitudDocumentacionPrevia> findAll() {
		return (List<SolicitudDocumentacionPrevia>) solicitudDocumentacionPreviaRepository.findAll();
	}

	@Override
	public SolicitudDocumentacionPrevia findByCorreoDestiantario(String correo) {
		return solicitudDocumentacionPreviaRepository.findByCorreoDestiantario(correo);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		solicitudDocumentacionPreviaRepository.delete(id);
	}

	@Override
	public List<SolicitudDocumentacionPrevia> buscarSolicitudDocPreviaCriteria(
			SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SolicitudDocumentacionPrevia.class);
		String campoFecha = "this_.fecha_alta";
		if (solicitudDocPreviaBusqueda.getEstado() != null) {
			switch (solicitudDocPreviaBusqueda.getEstado()) {
			case VALIDADA_APOYO:
				campoFecha = "this_.fecha_valid_apoyo";
				criteria.add(Restrictions.isNotNull("fechaValidApoyo"));
				criteria.add(Restrictions.isNull("fechaEnvio"));
				break;
			case ENVIADA:
				campoFecha = "this_.fecha_envio";
				criteria.add(Restrictions.isNotNull("fechaEnvio"));
				criteria.add(Restrictions.isNull("fechaCumplimentacion"));
				break;
			case CUMPLIMENTADA:
				campoFecha = "this_.fecha_cumplimentacion";
				criteria.add(Restrictions.isNotNull("fechaCumplimentacion"));
				criteria.add(Restrictions.isNull("fechaFinalizacion"));
				break;
			case FINALIZADA:
				campoFecha = "this_.fecha_finalizacion";
				criteria.add(Restrictions.isNotNull("fechaFinalizacion"));
				break;
			// case CREADA:
			default:
				criteria.add(Restrictions.isNull("fechaValidApoyo"));
				break;
			}
		}
		if (solicitudDocPreviaBusqueda.getFechaDesde() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			// PostgreSQL usa DATE_TRUNC, Oracle usa ROUND/TRUNC habrá que cambiarlo
			criteria.add(Restrictions.sqlRestriction(
					"DATE_TRUNC('day'," + campoFecha + ") >= '" + solicitudDocPreviaBusqueda.getFechaDesde() + "'"));
		}
		if (solicitudDocPreviaBusqueda.getFechaHasta() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			// PostgreSQL usa DATE_TRUNC, Oracle usa ROUND/TRUNC habrá que cambiarlo
			criteria.add(Restrictions.sqlRestriction(
					"DATE_TRUNC('day'," + campoFecha + ") <= '" + solicitudDocPreviaBusqueda.getFechaHasta() + "'"));
		}
		if (solicitudDocPreviaBusqueda.getUsuarioCreacion() != null
				&& !solicitudDocPreviaBusqueda.getUsuarioCreacion().getUsername().isEmpty()) {
			criteria.add(Restrictions.ilike("usernameAlta",
					solicitudDocPreviaBusqueda.getUsuarioCreacion().getUsername(), MatchMode.ANYWHERE));
		}
		criteria.addOrder(Order.desc("fechaAlta"));
		@SuppressWarnings("unchecked")
		List<SolicitudDocumentacionPrevia> listaSolicitudesDocPrevia = criteria.list();
		session.close();

		return listaSolicitudesDocPrevia;

	}

	@Override
	@Transactional(readOnly = false)
	public boolean transaccSaveCreaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
			String password) {
		solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
		User user = new User(solicitudDocumentacionPrevia.getCorreoDestiantario(), password, RoleEnum.PROV_SOLICITUD);
		userService.save(user);
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean transaccSaveElimUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
			String usuarioProv) {
		solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
		userService.delete(usuarioProv);
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean transaccSaveInactivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
			String usuarioProv) {
		solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
		userService.cambiarEstado(usuarioProv, EstadoEnum.INACTIVO);
		return true;
	}

	@Override
	public List<SolicitudDocumentacionPrevia> findSolicitudDocumentacionFinalizadaPorInspeccion(Inspeccion inspeccion) {
		return solicitudDocumentacionPreviaRepository
				.findByFechaFinalizacionIsNotNullAndInspeccionOrderByFechaFinalizacionDesc(inspeccion);
	}

}
