package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.CuestionarioEnviadoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CuestionarioEnviadoBusqueda implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private Date fechaLimiteRespuesta;
    
    private CuestionarioEnviadoEnum estado;
    
    private String usernameEnvio;
    
    private String numeroInspeccion;
    
    private TipoInspeccion tipoInspeccion;
    
    private AmbitoInspeccionEnum ambitoInspeccion;
    
    private String nombreEquipo;
    
    private String nombreUnidad;
    
    private String nombreCuestionario;
    
    private ModeloCuestionario modeloCuestionarioSeleccionado;
    
    public void limpiar() {
        setFechaDesde(null);
        setFechaHasta(null);
        setFechaLimiteRespuesta(null);
        setEstado(null);
        setUsernameEnvio(null);
        setNumeroInspeccion(null);
        setTipoInspeccion(null);
        setAmbitoInspeccion(null);
        setNombreEquipo(null);
        setNombreUnidad(null);
        setNombreCuestionario(null);
        setModeloCuestionarioSeleccionado(null);
    }
    
}
