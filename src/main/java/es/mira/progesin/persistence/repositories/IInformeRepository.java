package es.mira.progesin.persistence.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;

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
    @EntityGraph(value = "Informe.respuestas", type = EntityGraphType.LOAD)
    Informe findById(Long id);
    
    /**
     * Comprobar si hay algún informe basado en éste modelo personalizado.
     * 
     * @param modeloPersonalizado modelo de informe personalizado
     * @return verdadero o falso
     */
    boolean existsByModeloPersonalizado(ModeloInformePersonalizado modeloPersonalizado);
    
    /**
     * Comprueba si no existen otros informes sin finalizar asociados a la inspeccion.
     * 
     * @param inspeccion inspeccion asociada al informe
     * @return boolean
     */
    boolean existsByInspeccionAndFechaBajaIsNull(Inspeccion inspeccion);
    
}
