package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Sugerencia;
import es.mira.progesin.persistence.repositories.ISugerenciaRepository;

/**
 * Implementación del servicio para la gestión de sugerencias.
 * 
 * @author EZENTIS
 *
 */
@Service
public class SugerenciaService implements ISugerenciaService {
    /**
     * Variable utilizada para inyectar el repositorio de sugerencias.
     * 
     */
    @Autowired
    ISugerenciaRepository sugerenciaRepository;
    
    /**
     * Borra una sugerencia de BBDD conociendo su identificador.
     * 
     * @param id identificador de la sugerencia a eliminar
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Integer id) {
        sugerenciaRepository.delete(id);
    }
    
    /**
     * Busca todas las sugerencias en BBDD.
     * 
     * @return iterable con la lista de todas las sugerencias en BBDD
     */
    @Override
    public Iterable<Sugerencia> findAll() {
        return sugerenciaRepository.findAll();
    }
    
    /**
     * Busca una sugerencia conociendo su identificador.
     * 
     * @param id identificador
     * @return objeto sugerencia encontrado
     */
    @Override
    public Sugerencia findOne(Integer id) {
        return sugerenciaRepository.findOne(id);
    }
    
    /**
     * Guarda o actualiza una sugerencia.
     * 
     * @param entity sugerencia a guardar
     * @return sugerencia resultado booleano
     */
    @Override
    @Transactional(readOnly = false)
    public Sugerencia save(Sugerencia entity) {
        return sugerenciaRepository.save(entity);
    }
    
}
