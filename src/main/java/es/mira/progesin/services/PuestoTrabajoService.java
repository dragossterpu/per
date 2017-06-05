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
     * 
     */
    @Autowired
    IPuestoTrabajoRepository puestoTrabajoRepository;
    
    /**
     * Busca todos los puestos de trabajo dados de alta en la BBDD.
     * @return Iterable<PuestoTrabajo> lista de puestos existentes
     */
    @Override
    public Iterable<PuestoTrabajo> findAll() {
        return puestoTrabajoRepository.findAll();
    }
    
    /**
     * @param puesto a guardar.
     * @return PuestoTrabajo actualizado
     */
    @Override
    @Transactional(readOnly = false)
    public PuestoTrabajo save(PuestoTrabajo puesto) {
        return puestoTrabajoRepository.save(puesto);
    }
    
    /**
     * puestos de trabajo sin fecha de baja, es decir activos.
     * @return List<PuestoTrabajo> lista de puestos no dados de baja
     */
    @Override
    public List<PuestoTrabajo> findByFechaBajaIsNull() {
        return puestoTrabajoRepository.findByFechaBajaIsNullOrderByIdAsc();
    }
    
}
