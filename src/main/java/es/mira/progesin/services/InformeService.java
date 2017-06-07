package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Informe;
import es.mira.progesin.persistence.repositories.IInformeRepository;

/**
 * Servicio de informes de inspección.
 * 
 * @author EZENTIS
 */
@Service
public class InformeService implements IInformeService {
    
    /**
     * Repositorio de informes.
     */
    @Autowired
    private IInformeRepository informeRepository;
    
    /**
     * Guarda la información de un informe en la bdd.
     * 
     * @param informe informe creado o modificado
     * @return informe sincronizado
     */
    @Override
    public Informe save(Informe informe) {
        return informeRepository.save(informe);
    }
    
}
