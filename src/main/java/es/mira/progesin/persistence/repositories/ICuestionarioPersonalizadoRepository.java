package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;

/**
 * 
 * Interfaz del repositorio de modelos de cuestionario personalizados
 * 
 * @author Ezentis
 *
 */
public interface ICuestionarioPersonalizadoRepository extends CrudRepository<CuestionarioPersonalizado, Long> {
    
}
