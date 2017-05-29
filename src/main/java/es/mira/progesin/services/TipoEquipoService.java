package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.repositories.ITipoEquiposRepository;

/**
 * 
 * Implementación del servicio de tipos de equipo.
 * 
 * @author EZENTIS
 *
 */
@Service
public class TipoEquipoService implements ITipoEquipoService {
    
    /**
     * Variable utilizada para inyectar el repositorio de tipos de equipo.
     * 
     */
    @Autowired
    ITipoEquiposRepository tipoEquiposRepository;
    
    /**
     * Devuelve un listado de todos los tipos de equipo definidos.
     * 
     * @return lista de tipos de equipo
     */
    @Override
    public List<TipoEquipo> findAll() {
        return tipoEquiposRepository.findAllByOrderByIdAsc();
    }
    
    /**
     * Elimina un tipo de equipo.
     * 
     * @param id identificador del tipo de equipo a eliminar
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        tipoEquiposRepository.delete(id);
    }
    
    /**
     * Guarda en base de datos un nuevo tipo de equipo.
     * 
     * @param entity tipo de equipo a guardar
     * @return tipo de equipo guardado
     */
    @Override
    @Transactional(readOnly = false)
    public TipoEquipo save(TipoEquipo entity) {
        return tipoEquiposRepository.save(entity);
    }
    
    /**
     * Busca un tipo de equipo a partir de su código.
     * 
     * @param codigo por el que se buscará en la base de datos. Es case-insensitive
     * @return el tipo de equipo que corresponde a la búsqueda
     */
    @Override
    public TipoEquipo findByCodigoIgnoreCase(String codigo) {
        return tipoEquiposRepository.findByCodigoIgnoreCase(codigo);
    }
    
}
