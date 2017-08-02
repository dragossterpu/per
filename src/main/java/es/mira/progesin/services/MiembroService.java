package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.User;
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
    
    /**
     * Variable utilizada para inyectar el repositorio de miembros.
     * 
     */
    @Autowired
    IMiembrosRepository miembrosRepository;
    
    /**
     * Devuelve un miembro con un login especifico.
     * 
     * @param user Usuario
     * @return devuelve el miembro encontrado
     */
    @Override
    public Miembro buscaMiembroByUserAndEquipo(User user, Equipo equipo) {
        return miembrosRepository.findByUsuarioAndEquipo(user, equipo);
    }
    
    /**
     * Guarda la información de un miembro de equipo en la bdd.
     * 
     * @param miembro a guardar
     * @return miembro con id
     */
    @Override
    public Miembro save(Miembro miembro) {
        return miembrosRepository.save(miembro);
    }
    
    /**
     * Recupera los miembros que pertenecen a un equipo determinado.
     * 
     * @param equipo a consultar
     * @return lista de miembros del equipo
     */
    @Override
    public List<Miembro> findByEquipo(Equipo equipo) {
        return miembrosRepository.findByEquipo(equipo);
    }
    
    /**
     * Comprueba si un usuario es jefe de algún equipo de inspecciones.
     * 
     * @param username login del usuario
     * @return valor booleano
     */
    @Override
    public boolean esJefeEquipo(String username) {
        return miembrosRepository.existsByUsuarioUsernameAndPosicionAndEquipoFechaBajaIsNull(username, RolEquipoEnum.JEFE_EQUIPO);
    }
    
}
