package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.repositories.ISubareaInformeRepository;

/**
 * Servicio de subareas de informe.
 * 
 * @author EZENTIS
 */
@Service
public class SubareaInformeService implements ISubareaInformeService {
    
    /**
     * Repositorio de subareas de informe.
     */
    @Autowired
    private ISubareaInformeRepository subareaInformeRepository;
    
}
