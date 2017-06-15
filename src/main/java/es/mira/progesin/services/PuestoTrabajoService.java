package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.enums.AdministracionAccionEnum;
import es.mira.progesin.persistence.repositories.IPuestoTrabajoRepository;
import es.mira.progesin.web.beans.ApplicationBean;

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
     * Variable usada para actualizar la lista cargada en el contexto de la aplicación.
     */
    @Autowired
    private ApplicationBean applicationBean;
    
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
     * @param accion alta/baja/modificación
     * @return PuestoTrabajo actualizado
     */
    @Override
    @Transactional(readOnly = false)
    public PuestoTrabajo save(PuestoTrabajo puesto, AdministracionAccionEnum accion) {
        PuestoTrabajo puestoTrabajoActualizado = puestoTrabajoRepository.save(puesto);
        applicationBean.setListaPuestosTrabajo(findAllByOrderByDescripcionAsc());
        return puestoTrabajoActualizado;
    }
    
    /**
     * Elimina un puesto de trabajo.
     * 
     * @param puesto puesto a eliminar.
     */
    @Override
    public void delete(PuestoTrabajo puesto) {
        puestoTrabajoRepository.delete(puesto);
        applicationBean.setListaPuestosTrabajo(findAllByOrderByDescripcionAsc());
    }
    
    /**
     * Busca todos los puestos de trabajo dados de alta en la BBDD ordenados por descripción.
     * 
     * @return lista de puestos existentes
     */
    @Override
    public List<PuestoTrabajo> findAllByOrderByDescripcionAsc() {
        return puestoTrabajoRepository.findAllByOrderByDescripcionAsc();
    }
    
}
