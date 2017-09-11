package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

/**
 * 
 * Interfaz del repositorio de modelos de cuestionario personalizados.
 * 
 * @author EZENTIS
 *
 */
public interface ICuestionarioPersonalizadoRepository extends CrudRepository<CuestionarioPersonalizado, Long> {
    
    /**
     * Comprueba la existencia de cuestionarios personalizados que tengan como modelo el que se recibe como parámetro.
     * 
     * @param modelo Modelo del que se desea comprobar si existen personalizados.
     * @return Respuesta a la consulta.
     */
    boolean existsBymodeloCuestionario(ModeloCuestionario modelo);
    
}
