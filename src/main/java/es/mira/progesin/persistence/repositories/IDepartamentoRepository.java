package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Departamento;

/**
 * Repositorio de operaciones de base de datos para la entidad Departamento.
 * 
 * @author EZENTIS
 *
 */
public interface IDepartamentoRepository extends CrudRepository<Departamento, Long> {
    
    /**
     * Busca todos los departamentos de trabajo dados de alta en la BBDD ordenados por su descripción.
     * 
     * @return lista con todos los departamentos en BBDD
     */
    List<Departamento> findAllByOrderByDescripcionAsc();
}
