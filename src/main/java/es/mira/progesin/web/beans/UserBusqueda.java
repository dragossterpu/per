package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
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
    
    /**
     * Fecha alta de usuarios desde la que busca.
     */
    private Date fechaDesde;
    
    /**
     * Fecha alta de usuarios hasta la que busca.
     */
    private Date fechaHasta;
    
    /**
     * Filtro username.
     */
    private String username;
    
    /**
     * Filtro nombre user.
     */
    private String nombre;
    
    /**
     * Filtro primer apellido user.
     */
    private String apellido1;
    
    /**
     * Filtro segundo apellido user.
     */
    private String apellido2;
    
    /**
     * Filtro rol user.
     */
    private RoleEnum role;
    
    /**
     * Filtro estado user.
     */
    private EstadoEnum estado;
    
    /**
     * Filtro cuerpo al que pertenece el user.
     */
    private CuerpoEstado cuerpoEstado;
    
    /**
     * Filtro puesto user.
     */
    private PuestoTrabajo puestoTrabajo;
    
}
