package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * POJO con los parámetros de búsqueda de equipos
 * 
 * @author EZENTIS
 */
@Setter
@Getter
public class EquipoBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private String nombreEquipo;
    
    private TipoEquipo tipoEquipo;
    
    private String nombreJefe;
    
    private String nombreMiembro;
    
    private User jefeEquipo;
    
    private List<User> miembros;
    
    private List<Equipo> listaEquipos;
    
    private String estado;
    
    private RolEquipoEnum role;
    
    /**
     * Limpia valores seleccionados en anteriores búsquedas
     * 
     * @author EZENTIS
     */
    public void resetValues() {
        setFechaDesde(null);
        setFechaHasta(null);
        setNombreEquipo(null);
        setTipoEquipo(null);
        setJefeEquipo(null);
        setMiembros(null);
        setEstado(null);
        setRole(null);
        setNombreJefe(null);
        setNombreMiembro(null);
        setListaEquipos(null);
    }
    
}
