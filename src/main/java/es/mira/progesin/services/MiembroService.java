package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Equipo;
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
    
    @Override
    public Miembro save(Miembro miembro) {
        return miembrosRepository.save(miembro);
    }
    
    @Override
    public List<Miembro> findByEquipo(Equipo equipo) {
        return miembrosRepository.findByEquipo(equipo);
    }
}
