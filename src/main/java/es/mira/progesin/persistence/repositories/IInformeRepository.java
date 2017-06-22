package es.mira.progesin.persistence.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.informes.Informe;

/**
 * Repositorio de informes de inspecciones.
 * 
 * @author EZENTIS
 */
public interface IInformeRepository extends CrudRepository<Informe, Long> {
    
    /**
     * Recupera un informe con sus respuestas a partir de su id.
     * 
     * @param id id del informe
     * @return informe completo
     */
    @Override
    @EntityGraph(value = "Informe.respuestas", type = EntityGraphType.LOAD)
    Informe findOne(Long id);
    
}
