package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.TipoInspeccion;

/**
 * Repositorio de operaciones de base de datos para la clase Guias
 * 
 * @author Ezentis
 * 
 **/
public interface IGuiasRepository extends CrudRepository<Guia, Long> {
    
    /**
     * Recupera todas las guías almacenadas en base de datos
     * 
     * @return List<Guia>
     * 
     */
    
    @Override
    List<Guia> findAll();
    
    /**
     * Comprueba si existen modelos de gías asociadas a un determinado tipo de inspección
     * 
     * @param tipo
     * @return boolean
     */
    boolean existsByTipoInspeccion(TipoInspeccion tipo);
}
