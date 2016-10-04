package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.CuerpoEstado;

public interface ICuerpoEstadoRepository extends CrudRepository<CuerpoEstado, Integer> {
	/**
	 * 
	 * @return Cuerpos del estado sin fecha de baja, es decir activos
	 */
	List<CuerpoEstado> findByFechaBajaIsNull();
}
