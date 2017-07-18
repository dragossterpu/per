package es.mira.progesin.persistence.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;

/**
 * Repositorio de modelos de informe personalizados.
 * 
 * @author EZENTIS
 */
public interface IModeloInformePersonalizadoRepository extends CrudRepository<ModeloInformePersonalizado, Long> {
    
    /**
     * Busca un modelo de informe personalizado con las subáreas cargadas.
     * 
     * @param id id del modelo de informe personalizado
     * @return modelo de informe personalizado
     */
    @EntityGraph(value = "InformePersonalizado.subareas", type = EntityGraphType.LOAD)
    ModeloInformePersonalizado findById(Long id);
    
    /**
     * Determina si existen modelos personalizados del tipo pasado como referencia.
     * 
     * @param modelo Modelo del que se desea saber si existen personalizados.
     * @return Booleano con la respuesta.
     */
    boolean existsByModeloInforme(ModeloInforme modelo);
}
