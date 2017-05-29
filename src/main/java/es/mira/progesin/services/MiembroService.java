package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;

/**
 * Implementación del servicio de gestión de miembros.
 * 
 * @author EZENTIS
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
    
    @Override
    public boolean esJefeEquipo(String username) {
        return miembrosRepository.existsByUsernameAndPosicion(username, RolEquipoEnum.JEFE_EQUIPO);
    }
    
}
