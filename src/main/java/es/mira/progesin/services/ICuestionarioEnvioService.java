package es.mira.progesin.services;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.util.CorreoException;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBusqueda;

/**
 * 
 * Interfaz del servicio de cuestionarios enviados
 * 
 * @author Ezentis
 *
 */
public interface ICuestionarioEnvioService extends Serializable {
    
    /**
     * Recupera cuestionario asociados a una inspección
     * 
     * @author Ezentis
     * @return cuestionario
     */
    CuestionarioEnvio findByInspeccion(Inspeccion inspeccion);
    
    void enviarCuestionarioService(List<User> user, CuestionarioEnvio cuestionarioEnvio, String cuerpoCorreo)
            throws CorreoException;
    
    CuestionarioEnvio findNoFinalizadoPorCorreoEnvio(String correo);
    
    /**
     * Guarda la información de un cuestionario enviado en la bdd.
     * 
     * @author Ezentis
     * @param cuestionario
     */
    void save(CuestionarioEnvio cuestionario);
    
    List<RespuestaCuestionario> transaccSaveConRespuestas(List<RespuestaCuestionario> listaRespuestas);
    
    boolean transaccSaveElimUsuariosProv(CuestionarioEnvio cuestionario);
    
    boolean transaccSaveConRespuestasInactivaUsuariosProv(CuestionarioEnvio cuestionario,
            List<RespuestaCuestionario> listaRespuestas);
    
    boolean transaccSaveActivaUsuariosProv(CuestionarioEnvio cuestionario);
    
    CuestionarioEnvio findById(Long idCuestionarioEnviado);
    
    CuestionarioEnvio findNoFinalizadoPorInspeccion(Inspeccion inspeccion);
    
    CuestionarioEnvio findByCuestionarioPersonalizado(CuestionarioPersonalizado cuestionario);
    
    List<CuestionarioEnvio> findNoCumplimentados();
    
    List<CuestionarioEnvio> buscarCuestionarioEnviadoCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda);
    
    int getCountCuestionarioCriteria(CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda);
    
}
