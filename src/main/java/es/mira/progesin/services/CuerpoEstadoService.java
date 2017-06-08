package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository;

/**
 * Implementación de los métodos definidos en la interfaz ICuerpoEstadoService.
 * 
 * @author EZENTIS
 *
 */
@Service
public class CuerpoEstadoService implements ICuerpoEstadoService {
    
    /**
     * Variable utilizada para inyectar el repositorio de cuerpos de estado.
     * 
     */
    @Autowired
    private ICuerpoEstadoRepository cuerpoEstadoRepository;
    
    /**
     * 
     * Busca todos los cuerpos del estado existentes en la BBDD.
     * 
     * @return lista con todos los cuerpos
     */
    @Override
    public List<CuerpoEstado> findAll() {
        return (List<CuerpoEstado>) cuerpoEstadoRepository.findAll();
    }
    
    /**
     * Guarada o actualiza un Cuerpo.
     * 
     * @param cuerpo a guardar
     * @return CuerpoEstado actualizado
     */
    @Override
    @Transactional(readOnly = false)
    public CuerpoEstado save(CuerpoEstado cuerpo) {
        return cuerpoEstadoRepository.save(cuerpo);
    }
    
    /**
     * Existe un Cuerpo que no coincide con un id pero coincide con un nombre corto empleado para la modificación en
     * caliente.
     * @param nombreCorto del cuerpo
     * @param id identificador del cuerpo
     * 
     * @return valor booleano
     */
    @Override
    public boolean existeByNombreCortoIgnoreCaseAndIdNotIn(String nombreCorto, int id) {
        return cuerpoEstadoRepository.existsByNombreCortoIgnoreCaseAndIdNotIn(nombreCorto, id);
    }
    
    /**
     * Elimina un cuerpo de estado.
     * 
     * @param id clave del cuerpo
     */
    @Override
    public void delete(Integer id) {
        cuerpoEstadoRepository.delete(id);
    }
    
}
