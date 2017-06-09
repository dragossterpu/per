package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.enums.AdministracionAccionEnum;
import es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository;
import es.mira.progesin.web.beans.ApplicationBean;

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
     * Variable usada para actualizar la lista cargada en el contexto de la aplicación.
     */
    @Autowired
    private ApplicationBean applicationBean;
    
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
     * Guarda o actualiza un Cuerpo.
     * 
     * @param cuerpo a guardar
     * @param accion alta/baja/modificación
     * @return CuerpoEstado actualizado
     */
    @Override
    @Transactional(readOnly = false)
    public CuerpoEstado save(CuerpoEstado cuerpo, AdministracionAccionEnum accion) {
        CuerpoEstado cuerpoActualizado = cuerpoEstadoRepository.save(cuerpo);
        applicationBean.actualizarApplicationBean(cuerpoActualizado, applicationBean.getListaCuerpos(), accion);
        return cuerpo;
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
     * @param cuerpo a eliminar
     */
    @Override
    public void delete(CuerpoEstado cuerpo) {
        cuerpoEstadoRepository.delete(cuerpo);
        applicationBean.actualizarApplicationBean(cuerpo, applicationBean.getListaCuerpos(),
                AdministracionAccionEnum.BAJA);
    }
    
}
