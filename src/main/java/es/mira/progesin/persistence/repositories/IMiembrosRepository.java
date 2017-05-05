package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;

public interface IMiembrosRepository extends CrudRepository<Miembro, Long> {
    
    List<Miembro> findByEquipo(Equipo equipo);
    
    boolean existsByUsernameAndPosicion(String username, RolEquipoEnum jefeEquipo);
    
    Miembro findByUsername(String username);
    
}
