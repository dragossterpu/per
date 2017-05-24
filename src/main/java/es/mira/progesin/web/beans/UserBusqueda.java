package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Controlador de las operaciones relacionadas con la búsqueda de usuarios. Reseteo de valores de búsqueda.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private String username;
    
    private String nombre;
    
    private String apellido1;
    
    private String apellido2;
    
    private RoleEnum role;
    
    private EstadoEnum estado;
    
    private CuerpoEstado cuerpoEstado;
    
    private PuestoTrabajo puestoTrabajo;
    
    private List<User> listaUsuarios;
    
    /**
     * Resetea los valores del formulario de búsqueda de usuarios
     */
    public void resetValues() {
        setFechaDesde(null);
        setFechaHasta(null);
        setUsername(null);
        setNombre(null);
        setApellido1(null);
        setApellido2(null);
        setRole(null);
        setCuerpoEstado(null);
        setPuestoTrabajo(null);
        setListaUsuarios(null);
        setEstado(null);
    }
    
}
