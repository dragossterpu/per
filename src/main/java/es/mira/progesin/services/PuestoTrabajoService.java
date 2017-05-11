package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.repositories.IPuestoTrabajoRepository;

/**
 * Servicio para la administración de puestos de trabajo
 * 
 * @author EZENTIS
 *
 */
@Service
public class PuestoTrabajoService implements IPuestoTrabajoService {
    
    @Autowired
    IPuestoTrabajoRepository puestoTrabajoRepository;
    
    @Override
    public Iterable<PuestoTrabajo> findAll() {
        return puestoTrabajoRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = false)
    public PuestoTrabajo save(PuestoTrabajo puesto) {
        return puestoTrabajoRepository.save(puesto);
    }
    
    @Override
    public List<PuestoTrabajo> findByFechaBajaIsNull() {
        return puestoTrabajoRepository.findByFechaBajaIsNullOrderByIdAsc();
    }
    
}
