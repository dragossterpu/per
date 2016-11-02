package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Documento;

public interface IDocumentoRepository extends CrudRepository<Documento, Long> {

}