package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.repositories.IPuestoTrabajoRepository;

/**
 * Implemenatación del servicio para la administración de puestos de trabajo.
 * 
 * @author EZENTIS
 *
 */
@Service
public class PuestoTrabajoService implements IPuestoTrabajoService {
    
    /**
     * Variable utilizada para inyectar el repositorio de puestos de trabajo.
     */
    @Autowired
    private IPuestoTrabajoRepository puestoTrabajoRepository;
    
    /**
     * Busca todos los puestos de trabajo dados de alta en la BBDD.
     * 
     * @return lista de puestos existentes
     */
    @Override
    public List<PuestoTrabajo> findAll() {
        return (List<PuestoTrabajo>) puestoTrabajoRepository.findAll();
    }
    
    /**
     * Guarda la información de un puesto de trabajo en la bbdd.
     * 
     * @param puesto a guardar.
     * @return PuestoTrabajo actualizado
     */
    @Override
    @Transactional(readOnly = false)
    public PuestoTrabajo save(PuestoTrabajo puesto) {
        return puestoTrabajoRepository.save(puesto);
    }
    
    /**
     * Elimina un puesto de trabajo.
     * 
     * @param idPuesto clave del puesto a eliminar.
     */
    @Override
    public void delete(Long idPuesto) {
        puestoTrabajoRepository.delete(idPuesto);
    }
    
}
