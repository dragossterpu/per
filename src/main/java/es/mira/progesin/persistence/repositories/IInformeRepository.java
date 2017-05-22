package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Informe;

public interface IInformeRepository extends CrudRepository<Informe, Long> {
    
}
