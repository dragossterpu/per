package es.mira.progesin.services;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.repositories.IGuiasPreguntasRepository;
import es.mira.progesin.persistence.repositories.IGuiasRepository;
import es.mira.progesin.web.beans.GuiaBusqueda;

@Service
public class GuiaService implements IGuiaService {

	private static final String COMPARADORSINACENTOS = "upper(convert(replace(%1$s, \' \', \'\'), \'US7ASCII\')) LIKE upper(convert(\'%%\' || replace(\'%2$s\', \' \', \'\') || \'%%\', \'US7ASCII\'))";

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	IGuiasPreguntasRepository preguntasRepository;

	@Autowired
	IGuiasRepository guiaRepository;

	@Override
	public List<Guia> buscarGuiaPorCriteria(GuiaBusqueda busqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Guia.class);

		if (busqueda.getFechaDesde() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions
					.sqlRestriction("TRUNC(this_.fecha_creacion) >= '" + sdf.format(busqueda.getFechaDesde()) + "'"));
		}
		if (busqueda.getFechaHasta() != null) {
			/**
			 * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
			 * compara con 0:00:00
			 */
			criteria.add(Restrictions
					.sqlRestriction("TRUNC(this_.fecha_creacion) <= '" + sdf.format(busqueda.getFechaHasta()) + "'"));
		}

		if (busqueda.getNombre() != null && !busqueda.getNombre().isEmpty()) {
			criteria.add(Restrictions
					.sqlRestriction(String.format(COMPARADORSINACENTOS, "nombre_guia", busqueda.getNombre())));
		}

		if (busqueda.getUsuarioCreacion() != null && !busqueda.getUsuarioCreacion().isEmpty()) {
			criteria.add(Restrictions.sqlRestriction(
					String.format(COMPARADORSINACENTOS, "usuario_creacion", busqueda.getUsuarioCreacion())));
		}

		if (busqueda.getTipoInspeccion() != null) {
			criteria.add(Restrictions.eq("tipoInspeccion", busqueda.getTipoInspeccion()));
		}

		criteria.addOrder(Order.desc("fechaCreacion"));

		@SuppressWarnings("unchecked")
		List<Guia> listaGuias = criteria.list();
		session.close();

		return listaGuias;
	}

	@Override
	public List<GuiaPasos> listaPasos(Guia guia) {
		return preguntasRepository.findByIdGuiaOrderByOrdenAsc(guia);

	}

	@Override
	@Transactional(readOnly = false)
	public Guia guardaGuia(Guia guia) {
		return guiaRepository.save(guia);

	}

	@Override
	public List<Guia> findAll() {
		return guiaRepository.findAll();
	}
}
