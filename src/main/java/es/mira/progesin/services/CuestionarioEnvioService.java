package es.mira.progesin.services;

import java.text.Normalizer;
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

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBusqueda;

@Service
public class CuestionarioEnvioService implements ICuestionarioEnvioService {

	private static final long serialVersionUID = 1L;

	private static final String ACENTOS = "\\p{InCombiningDiacriticalMarks}+";

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private transient ICuestionarioEnvioRepository cuestionarioEnvioRepository;

	@Autowired
	private transient IUserRepository userRepository;

	@Autowired
	private transient IRegistroActividadService regActividadService;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public CuestionarioEnvio findByInspeccion(Inspeccion inspeccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void enviarCuestionarioService(List<User> listadoUsuariosProvisionales,
			CuestionarioEnvio cuestionarioEnvio) {
		userRepository.save(listadoUsuariosProvisionales);
		cuestionarioEnvioRepository.save(cuestionarioEnvio);
		regActividadService.altaRegActividad(
				"Cuestionario para la inspección " + cuestionarioEnvio.getInspeccion().getNumero() + " enviado",
				EstadoRegActividadEnum.ALTA.name(), "CUESTIONARIOS");
	}

	@Override
	public CuestionarioEnvio findByCorreoEnvioAndFechaFinalizacionIsNull(String correo) {
		return cuestionarioEnvioRepository.findByCorreoEnvioAndFechaFinalizacionIsNull(correo);
	}

	@Override
	// NECESITO LO INVERSO O CAMBIAR MAPPING EN LA ENTIDAD A LAZY Y AÑADIR LOAD AL RESTO DE MÉTODOS QUE GESTIONEN
	// PREGUNTAS.
	// @EntityGraph(value = "CuestionarioPersonalizado.preguntasElegidas", type = EntityGraph.EntityGraphType.LOAD)
	// AHORA MUESTRA UN RESULTADO POR RESPUESTA DEL CUESTIONARIO PERSONALIZADO DE CADA CUESTIONARIO ENVIADO
	// APAÑADO CON UN DISTINCT
	public List<CuestionarioEnvio> buscarCuestionarioEnviadoCriteria(
			CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(CuestionarioEnvio.class, "cuestionario");
		String campoFecha = "this_.fecha_envio";
		if (cuestionarioEnviadoBusqueda.getEstado() != null) {
			switch (cuestionarioEnviadoBusqueda.getEstado()) {
			case CUMPLIMENTADO:
				// campoFecha = "this_.fecha_cumplimentacion";
				criteria.add(Restrictions.isNotNull("fechaCumplimentacion"));
				criteria.add(Restrictions.isNull("fechaFinalizacion"));
				break;
			// Aparecen como no conformes tanto si están sólo reenviadas como si están recumplimentadas
			case NO_CONFORME:
				// campoFecha = "this_.fecha_no_conforme";
				criteria.add(Restrictions.isNotNull("fechaNoConforme"));
				criteria.add(Restrictions.isNull("fechaFinalizacion"));
				break;
			case FINALIZADO:
				// campoFecha = "this_.fecha_finalizacion";
				criteria.add(Restrictions.isNotNull("fechaFinalizacion"));
				criteria.add(Restrictions.isNull("fechaAnulacion"));
				break;
			case ANULADO:
				// campoFecha = "this_.fecha_anulacion";
				criteria.add(Restrictions.isNotNull("fechaAnulacion"));
				break;
			// case ENVIADO:
			default:
				criteria.add(Restrictions.isNull("fechaCumplimentacion"));
				criteria.add(Restrictions.isNull("fechaAnulacion"));
				break;
			}
		}
		if (cuestionarioEnviadoBusqueda.getFechaDesde() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions.sqlRestriction(
					"TRUNC(" + campoFecha + ") >= '" + sdf.format(cuestionarioEnviadoBusqueda.getFechaDesde()) + "'"));
		}
		if (cuestionarioEnviadoBusqueda.getFechaHasta() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions.sqlRestriction(
					"TRUNC(" + campoFecha + ") <= '" + sdf.format(cuestionarioEnviadoBusqueda.getFechaHasta()) + "'"));
		}
		if (cuestionarioEnviadoBusqueda.getFechaLimiteRespuesta() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions.sqlRestriction("TRUNC(FECHA_LIMITE_CUESTIONARIO) <= '"
					+ sdf.format(cuestionarioEnviadoBusqueda.getFechaLimiteRespuesta()) + "'"));
		}

		String parametro;
		if (cuestionarioEnviadoBusqueda.getNombreUsuarioEnvio() != null
				&& !cuestionarioEnviadoBusqueda.getNombreUsuarioEnvio().isEmpty()) {
			// TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
			parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getNombreUsuarioEnvio(), Normalizer.Form.NFKD)
					.replaceAll(ACENTOS, "");
			criteria.add(Restrictions.ilike("nombreUsuarioEnvio", parametro, MatchMode.ANYWHERE));
		}

		criteria.createAlias("cuestionario.inspeccion", "inspeccion"); // inner join
		if (cuestionarioEnviadoBusqueda.getNombreUnidad() != null
				&& !cuestionarioEnviadoBusqueda.getNombreUnidad().isEmpty()) {
			// TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
			parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getNombreUnidad(), Normalizer.Form.NFKD)
					.replaceAll(ACENTOS, "");
			criteria.add(Restrictions.ilike("inspeccion.nombreUnidad", parametro, MatchMode.ANYWHERE));
		}
		if (cuestionarioEnviadoBusqueda.getNumeroInspeccion() != null
				&& !cuestionarioEnviadoBusqueda.getNumeroInspeccion().isEmpty()) {
			// TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
			parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getNumeroInspeccion(), Normalizer.Form.NFKD)
					.replaceAll(ACENTOS, "");
			criteria.add(Restrictions.ilike("inspeccion.numero", parametro, MatchMode.ANYWHERE));
		}
		if (cuestionarioEnviadoBusqueda.getAmbitoInspeccion() != null) {
			criteria.add(Restrictions.eq("inspeccion.ambito", cuestionarioEnviadoBusqueda.getAmbitoInspeccion()));
		}

		criteria.createAlias("inspeccion.tipoInspeccion", "tipoInspeccion"); // inner join
		if (cuestionarioEnviadoBusqueda.getTipoInspeccion() != null) {
			criteria.add(Restrictions.eq("tipoInspeccion.codigo",
					cuestionarioEnviadoBusqueda.getTipoInspeccion().getCodigo()));
		}

		criteria.createAlias("inspeccion.equipo", "equipo"); // inner join
		if (cuestionarioEnviadoBusqueda.getNombreEquipo() != null
				&& !cuestionarioEnviadoBusqueda.getNombreEquipo().isEmpty()) {
			// TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
			parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getNombreEquipo(), Normalizer.Form.NFKD)
					.replaceAll(ACENTOS, "");
			criteria.add(Restrictions.ilike("equipo.nombreEquipo", parametro, MatchMode.ANYWHERE));
		}

		criteria.createAlias("cuestionario.cuestionarioPersonalizado", "cuestionarioPersonalizado"); // inner join
		if (cuestionarioEnviadoBusqueda.getNombreCuestionario() != null
				&& !cuestionarioEnviadoBusqueda.getNombreCuestionario().isEmpty()) {
			// TODO: Cambiar esta condición para que busque sin tildes/espacios por la parte de BDD
			parametro = Normalizer.normalize(cuestionarioEnviadoBusqueda.getNombreCuestionario(), Normalizer.Form.NFKD)
					.replaceAll(ACENTOS, "");
			criteria.add(
					Restrictions.ilike("cuestionarioPersonalizado.nombreCuestionario", parametro, MatchMode.ANYWHERE));
		}

		criteria.createAlias("cuestionarioPersonalizado.modeloCuestionario", "modeloCuestionario"); // inner join
		if (cuestionarioEnviadoBusqueda.getModeloCuestionarioSeleccionado() != null) {
			criteria.add(Restrictions.eq("modeloCuestionario.id",
					cuestionarioEnviadoBusqueda.getModeloCuestionarioSeleccionado().getId()));
		}

		criteria.addOrder(Order.desc("fechaEnvio"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<CuestionarioEnvio> listaCuestionarioEnvio = criteria.list();
		session.close();

		return listaCuestionarioEnvio;
	}

	@Override
	@Transactional(readOnly = false)
	public void save(CuestionarioEnvio cuestionario) {
		cuestionarioEnvioRepository.save(cuestionario);
	}

}
