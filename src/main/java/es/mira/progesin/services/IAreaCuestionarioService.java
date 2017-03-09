package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;

public interface IAreaCuestionarioService {
	void delete(Long id);

	void delete(Iterable<AreasCuestionario> entities);

	void delete(AreasCuestionario entity);

	void deleteAll();

	boolean exists(Long id);

	Iterable<AreasCuestionario> findAll();

	Iterable<AreasCuestionario> findAll(Iterable<Long> ids);

	AreasCuestionario findOne(Long id);

	Iterable<AreasCuestionario> save(Iterable<AreasCuestionario> entities);

	AreasCuestionario save(AreasCuestionario entity);

	/**
	 * Busca las áreas de un cuestionario ordenadas de manera ascendente por el
	 * campo orden
	 * 
	 * @param idCuestionario
	 *            Id del cuestionario del que se desea obtener las áreas
	 * @return Lista de áreas
	 */
	List<AreasCuestionario> findAreasByIdCuestionarioByOrder(Integer idCuestionario);

}
