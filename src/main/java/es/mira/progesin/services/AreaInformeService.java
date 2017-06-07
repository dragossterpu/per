package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.repositories.IAreaInformeRepository;

/**
 * Servicio de areas de informe.
 * 
 * @author EZENTIS
 */
@Service
public class AreaInformeService implements IAreaInformeService {
    
    /**
     * Repositorio de áreas de informe.
     */
    @Autowired
    private IAreaInformeRepository areaInformeRepository;
    
}
