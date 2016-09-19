package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Alerta;


public interface IAlertaRepository extends CrudRepository<Alerta, Integer>{
}
