package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Guia;

/***************************************
 * Repositorio de operaciones de base de datos para la clase Guias
 * 
 * @author Ezentis
 * 
 *************************************/
public interface IGuiasRepository extends CrudRepository<Guia, Long> {

	/***************************
	 * Recupera todas las guías almacenadas en base de datos
	 * 
	 * @return List<Guia>
	 * 
	 ************************************/

	@Override
	List<Guia> findAll();

}
