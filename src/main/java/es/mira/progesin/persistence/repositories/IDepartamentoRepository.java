package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Departamento;

/**
 * Repositorio de operaciones de base de datos para la entidad Departamento.
 * 
 * @author EZENTIS
 *
 */
public interface IDepartamentoRepository extends CrudRepository<Departamento, Long> {
    
}
