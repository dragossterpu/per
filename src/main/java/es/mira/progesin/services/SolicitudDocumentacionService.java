package es.mira.progesin.services;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IDocumentacionPreviaRepository;
import es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;

@Service
public class SolicitudDocumentacionService implements ISolicitudDocumentacionService {

	private static final String ACENTOS = "\\p{InCombiningDiacriticalMarks}+";

	private static final String FECHAFINALIZACION = "fechaFinalizacion";

	private static final String FECHABAJA = "fechaBaja";

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ISolicitudDocumentacionPreviaRepository solicitudDocumentacionPreviaRepository;

	@Autowired
	IUserService userService;

	@Autowired
	IDocumentacionPreviaRepository documentacionPreviaRepository;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	@Transactional(readOnly = false)
	public SolicitudDocumentacionPrevia save(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia) {
		return solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
	}

	@Override
	public List<SolicitudDocumentacionPrevia> findAll() {
		return (List<SolicitudDocumentacionPrevia>) solicitudDocumentacionPreviaRepository.findAll();
	}

	@Override
	public SolicitudDocumentacionPrevia findByFechaFinalizacionIsNullAndCorreoDestinatario(String correo) {
		return solicitudDocumentacionPreviaRepository.findByFechaFinalizacionIsNullAndCorreoDestinatario(correo);
	}

	@Override
	public SolicitudDocumentacionPrevia findByFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndCorreoDestinatario(
			String correo) {
		return solicitudDocumentacionPreviaRepository
				.findByFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndCorreoDestinatario(correo);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		solicitudDocumentacionPreviaRepository.delete(id);
	}

	@Override
	public List<SolicitudDocumentacionPrevia> buscarSolicitudDocPreviaCriteria(
			SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SolicitudDocumentacionPrevia.class, "solicitud");
		String campoFecha = "this_.fecha_alta";
		if (solicitudDocPreviaBusqueda.getEstado() != null) {
			switch (solicitudDocPreviaBusqueda.getEstado()) {
			case VALIDADA_APOYO:
				// campoFecha = "this_.fecha_valid_apoyo";
				criteria.add(Restrictions.isNotNull("fechaValidApoyo"));
				criteria.add(Restrictions.isNull("fechaValidJefeEquipo"));
				break;
			case VALIDADA_JEFE_EQUIPO:
				// campoFecha = "this_.fecha_valid_jefe_equipo";
				criteria.add(Restrictions.isNotNull("fechaValidJefeEquipo"));
				criteria.add(Restrictions.isNull("fechaEnvio"));
				break;
			case ENVIADA:
				// campoFecha = "this_.fecha_envio";
				criteria.add(Restrictions.isNotNull("fechaEnvio"));
				criteria.add(Restrictions.isNull("fechaCumplimentacion"));
				criteria.add(Restrictions.isNull(FECHABAJA));
				break;
			case CUMPLIMENTADA:
				// campoFecha = "this_.fecha_cumplimentacion";
				criteria.add(Restrictions.isNotNull("fechaCumplimentacion"));
				criteria.add(Restrictions.isNull(FECHAFINALIZACION));
				criteria.add(Restrictions.isNull(FECHABAJA));
				break;
			// Aparecen como no conformes tanto si están sólo reenviadas como si están recumplimentadas
			case NO_CONFORME:
				// campoFecha = "this_.fecha_no_conforme";
				criteria.add(Restrictions.isNotNull("fechaNoConforme"));
				criteria.add(Restrictions.isNull(FECHAFINALIZACION));
				criteria.add(Restrictions.isNull(FECHABAJA));
				break;
			case FINALIZADA:
				// campoFecha = "this_.fecha_finalizacion";
				criteria.add(Restrictions.isNotNull(FECHAFINALIZACION));
				criteria.add(Restrictions.isNull(FECHABAJA));
				break;
			case ANULADA:
				// campoFecha = "this_.fecha_baja";
				criteria.add(Restrictions.isNotNull(FECHABAJA));
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
			criteria.add(Restrictions.sqlRestriction(
					"TRUNC(" + campoFecha + ") >= '" + sdf.format(solicitudDocPreviaBusqueda.getFechaDesde()) + "'"));
		}
		if (solicitudDocPreviaBusqueda.getFechaHasta() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions.sqlRestriction(
					"TRUNC(" + campoFecha + ") <= '" + sdf.format(solicitudDocPreviaBusqueda.getFechaHasta()) + "'"));
		}
		if (solicitudDocPreviaBusqueda.getUsuarioCreacion() != null) {
			criteria.add(
					Restrictions.eq("usernameAlta", solicitudDocPreviaBusqueda.getUsuarioCreacion().getUsername()));
		}
		criteria.createAlias("solicitud.inspeccion", "inspeccion"); // inner join
		criteria.createAlias("inspeccion.tipoInspeccion", "tipoInspeccion"); // inner join
		String parametro;
		if (solicitudDocPreviaBusqueda.getNombreUnidad() != null
				&& !solicitudDocPreviaBusqueda.getNombreUnidad().isEmpty()) {
			// TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
			parametro = Normalizer.normalize(solicitudDocPreviaBusqueda.getNombreUnidad(), Normalizer.Form.NFKD)
					.replaceAll(ACENTOS, "");
			criteria.add(Restrictions.ilike("inspeccion.nombreUnidad", parametro, MatchMode.ANYWHERE));
		}
		if (solicitudDocPreviaBusqueda.getNumeroInspeccion() != null
				&& !solicitudDocPreviaBusqueda.getNumeroInspeccion().isEmpty()) {
			// TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
			parametro = Normalizer.normalize(solicitudDocPreviaBusqueda.getNumeroInspeccion(), Normalizer.Form.NFKD)
					.replaceAll(ACENTOS, "");
			criteria.add(Restrictions.ilike("inspeccion.numero", parametro, MatchMode.ANYWHERE));
		}
		if (solicitudDocPreviaBusqueda.getAmbitoInspeccion() != null) {
			criteria.add(Restrictions.eq("inspeccion.ambito", solicitudDocPreviaBusqueda.getAmbitoInspeccion()));
		}
		if (solicitudDocPreviaBusqueda.getTipoInspeccion() != null) {
			criteria.add(Restrictions.eq("tipoInspeccion.codigo",
					solicitudDocPreviaBusqueda.getTipoInspeccion().getCodigo()));
		}

		criteria.createAlias("inspeccion.equipo", "equipo"); // inner join
		User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (RoleEnum.EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
			DetachedCriteria subquery = DetachedCriteria.forClass(Miembro.class, "miembro");
			subquery.add(Restrictions.eq("miembro.username", usuarioActual.getUsername()));
			subquery.add(Restrictions.eqProperty("equipo.id", "miembro.equipo"));
			subquery.setProjection(Projections.property("miembro.equipo"));
			criteria.add(Property.forName("equipo.id").in(subquery));
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
			User usuarioProv) {
		solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
		userService.save(usuarioProv);
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
	@Transactional(readOnly = false)
	public boolean transaccSaveActivaUsuarioProv(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia,
			String usuarioProv) {
		solicitudDocumentacionPreviaRepository.save(solicitudDocumentacionPrevia);
		userService.cambiarEstado(usuarioProv, EstadoEnum.ACTIVO);
		return true;
	}

	@Override
	public List<SolicitudDocumentacionPrevia> findSolicitudDocumentacionFinalizadaPorInspeccion(Inspeccion inspeccion) {
		return solicitudDocumentacionPreviaRepository
				.findByFechaBajaIsNullAndFechaFinalizacionIsNotNullAndInspeccionOrderByFechaFinalizacionDesc(
						inspeccion);
	}

	@Override
	@Transactional(readOnly = false)
	public void transaccDeleteElimDocPrevia(Long idSolicitud) {
		documentacionPreviaRepository.deleteByIdSolicitud(idSolicitud);
		solicitudDocumentacionPreviaRepository.delete(idSolicitud);

	}

	@Override
	public List<SolicitudDocumentacionPrevia> findByFechaFinalizacionIsNullAndInspeccion(Inspeccion inspeccion) {
		return solicitudDocumentacionPreviaRepository.findByFechaFinalizacionIsNullAndInspeccion(inspeccion);
	}

}
