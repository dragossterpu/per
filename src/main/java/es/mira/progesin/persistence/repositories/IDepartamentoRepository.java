package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Departamento;

public interface IDepartamentoRepository extends CrudRepository<Departamento, Long> {
    
    /**
     * 
     * @return Departamentos sin fecha de baja, es decir activos
     */
    List<Departamento> findByFechaBajaIsNull();
    
}