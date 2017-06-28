package es.mira.progesin.persistence.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.DatosTablaGenerica;

/**
 * Repositorio donde se almacenan las respuestas de un cuestionario que son de tipo TABLA o MATRIZ.
 * 
 * @author EZENTIS
 *
 */
public interface IDatosTablaGenericaRepository extends CrudRepository<DatosTablaGenerica, Long> {
    
    /**
     * Borra las respuestas huérfanas.
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from respuesta_datos_tabla r where r.respuesta_id_cuest_enviado is null and r.respuesta_id_pregunta is null", nativeQuery = true)
    void deleteRespuestasTablaHuerfanas();
}
