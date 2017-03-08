package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.GuiaPersonalizada;

/************************
 * Repositorio de operaciones de base de datos para la entidad GuiaPersonalizada
 * 
 * @author Ezentis
 * 
 ******************************/
public interface IGuiaPersonalizadaRepository extends CrudRepository<GuiaPersonalizada, Long> {

}
