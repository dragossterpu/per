package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.TipoEquipo;

/**
 * Repositorio de funciones de acceso a la base de datos para la entidad TipoEquipo.
 * 
 * @author EZENTIS
 *
 */
public interface ITipoEquiposRepository extends CrudRepository<TipoEquipo, Long> {
    
    /**
     * Recupera de base de datos un objeto cuyo código se recibe como parámetro.
     * 
     * @param codigo del objeto a recuperar
     * @return objeto recuperado
     */
    TipoEquipo findByCodigoIgnoreCase(String codigo);
    
    /**
     * Recupera todos los objetos ordenados ascendentemente por id.
     * 
     * @return lista de objetos
     */
    List<TipoEquipo> findAllByOrderByIdAsc();
    
    /**
     * Recupera todos los objetos ordenados ascendentemente por su descripción.
     * 
     * @return lista de objetos
     */
    List<TipoEquipo> findAllByOrderByDescripcionAsc();
}
