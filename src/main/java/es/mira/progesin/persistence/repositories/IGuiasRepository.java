package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.TipoInspeccion;

/**
 * Repositorio de operaciones de base de datos para la clase Guias.
 * 
 * @author EZENTIS
 * 
 **/
public interface IGuiasRepository extends CrudRepository<Guia, Long> {
    
    /**
     * Recupera todas las guías almacenadas en base de datos.
     * 
     * @return Listado de guías
     * 
     */
    
    @Override
    List<Guia> findAll();
    
    /**
     * Comprueba si existen modelos de gías asociadas a un determinado tipo de inspección.
     * 
     * @param tipo Tipo sobre el que se quiere consultar
     * @return boolean Respuesta de la consulta
     */
    boolean existsByTipoInspeccion(TipoInspeccion tipo);
    
    /**
     * Devuelve un listado de todas las guías ordenadas por nombre.
     * 
     * @return Listado de guías
     */
    List<Guia> findAllByOrderByNombre();
}
