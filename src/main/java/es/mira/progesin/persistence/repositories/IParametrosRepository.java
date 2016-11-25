package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Parametro;
import es.mira.progesin.persistence.entities.ParametroId;

public interface IParametrosRepository extends CrudRepository<Parametro, ParametroId> {

	/***************************************
	 * 
	 * findValueForKey
	 * 
	 * Devuelve el valor de una clave localizada en la tabla de Parámetros de BDD.
	 * 
	 * @author Ezentis
	 * 
	 * @return String valor
	 * @param String clave
	 * @param String seccion
	 *
	 *************************************/

	@Query("select param.valor from Parametro c where c.param.clave = :clave and c.param.seccion= :seccion)")
	String findValueForKey(@Param("clave") String clave, @Param("seccion") String seccion);

	/***************************************
	 * 
	 * findValuesForSeccion
	 * 
	 * Devuelve los valores de una seccion localizada en la tabla de Parámetros de BDD.
	 * 
	 * @author Ezentis
	 * 
	 * @return List<String> valor
	 * @param String seccion
	 *
	 *************************************/

	@Query("select param.valor from Parametro c where c.param.seccion= :seccion)")
	List<String> findValuesForSeccion(@Param("seccion") String seccion);

	/***************************************
	 * 
	 * findParametrosForSeccion
	 * 
	 * Devuelve una lista de objetos Parametro de una seccion localizada en la tabla de Parámetros de BDD.
	 * 
	 * @author Ezentis
	 * 
	 * @return List<Parametro> Lista parámetros
	 * @param String seccion
	 *
	 *************************************/

	@Query("select param from Parametro c where c.param.seccion= :seccion)")
	List<Parametro> findParametrosForSeccion(@Param("seccion") String seccion);

	List<Parametro> findParamByParamSeccion(String seccion);
}
