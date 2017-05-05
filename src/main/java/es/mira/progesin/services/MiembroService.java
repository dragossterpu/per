package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;

/**
 * @author Ezentis
 *
 */
@Service
public class MiembroService implements IMiembroService {
    
    @Autowired
    IMiembrosRepository miembrosRepository;
    
    @Override
    public Miembro buscaMiembroByUsername(String username) {
        return miembrosRepository.findByUsername(username);
    }
    
}
