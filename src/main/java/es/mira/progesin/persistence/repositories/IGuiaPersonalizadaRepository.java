package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;

/**
 * Repositorio de operaciones de base de datos para la entidad GuiaPersonalizada.
 * 
 * @author EZENTIS
 * 
 */
public interface IGuiaPersonalizadaRepository extends CrudRepository<GuiaPersonalizada, Long> {
    
    /**
     * Devuelve true si existe al menos una guía personalizada cuya guía modelo corresponda a la que se recibe como
     * parámetro.
     * 
     * @param guia Modelo de guía de la cual se buscan guías personalizadas
     * @return Valor booleano: true si existe al menos una guía personalizada, false en caso contrario
     */
    boolean existsByGuia(Guia guia);
    
}
