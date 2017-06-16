package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * POJO con los parámetros de búsqueda de equipos.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
public class EquipoBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Fecha desde la que se desea hacer la búsqueda.
     */
    private Date fechaDesde;
    
    /**
     * Fecha hasta la que se desea hacer la búsqueda.
     */
    private Date fechaHasta;
    
    /**
     * Nombre del equipo que se desea buscar.
     */
    private String nombreEquipo;
    
    /**
     * Tipo de equipo que se desea buscar.
     */
    private TipoEquipo tipoEquipo;
    
    /**
     * Nombre del jefe del equipo que se desea buscar.
     */
    
    private String nombreJefe;
    
    /**
     * Nombre de un miembro del equipo que se desea buscar.
     */
    
    private String nombreMiembro;
    
    /**
     * Usuario jefe del equipo que se desea buscar.
     */
    
    private User jefeEquipo;
    
    /**
     * Listado de miembros del equipo.
     */
    
    private List<User> miembros;
    
    /**
     * Estado del equipo que se desea buscar.
     */
    private String estado;
    
    /**
     * Rol dentro del equipo.
     */
    private RolEquipoEnum role;
    
}
