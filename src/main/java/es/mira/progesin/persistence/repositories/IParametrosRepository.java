package es.mira.progesin.persistence.repositories;

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
	 * Devuelve el valor de una clave localizada 
	 * en la tabla de Parámetros de BDD.
	 * 
	 * @author 	Ezentis
	 * 
	 * @return	String 	valor
	 * @param	String	clave
	 *
	 *************************************/
	
	@Query("select param.valor from Parametro c where c.param.clave = :clave)")
	String findValueForKey(@Param("clave") String clave);

}
