package es.mira.progesin.services;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
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
     * Crea y envía un cuestionario a partir de un modelo personalizado, genera los usuarios provisionales que lo
     * responderán y envía un correo al destinatario
     * 
     * @author Ezentis
     * @param user
     * @param cuestionarioEnvio
     * @param cuerpoCorreo
     */
    void crearYEnviarCuestionario(List<User> user, CuestionarioEnvio cuestionarioEnvio, String cuerpoCorreo);
    
    /**
     * Recupera el cuestionario enviado no finalizado perteneciente a un destinatario (no puede haber más de una)
     * 
     * @author Ezentis
     * @param correo
     * @return cuestionario enviado
     */
    CuestionarioEnvio findNoFinalizadoPorCorreoEnvio(String correo);
    
    /**
     * Guarda la información de un cuestionario enviado en la bdd.
     * 
     * @author Ezentis
     * @param cuestionario
     */
    void save(CuestionarioEnvio cuestionario);
    
    /**
     * Guarda los datos de las respuestas de un cuestionario enviado
     * 
     * @author Ezentis
     * @param listaRespuestas
     * @return lista de respuestas guardadas con id
     */
    List<RespuestaCuestionario> transaccSaveConRespuestas(List<RespuestaCuestionario> listaRespuestas);
    
    /**
     * Guarda los datos de un cuestionario enviado y elimina los usuarios provisionales que lo han cumplimentado una vez
     * finalizado o anulado
     * 
     * @author Ezentis
     * @param cuestionario enviado
     */
    void transaccSaveElimUsuariosProv(CuestionarioEnvio cuestionario);
    
    /**
     * Guarda los datos de un cuestionario enviado y sus respuestas, e inactiva los usuarios provisionales que lo han
     * cumplimentado una vez finalizado o anulado
     * 
     * @author Ezentis
     * @param cuestionario enviado
     * @param listaRespuestas
     */
    void transaccSaveConRespuestasInactivaUsuariosProv(CuestionarioEnvio cuestionario,
            List<RespuestaCuestionario> listaRespuestas);
    
    /**
     * Guarda los datos de un cuestionario enviado y activa los usuarios provisionales que debe cumplimentarlo de nuevo
     * en caso de no conformidad
     * 
     * @author Ezentis
     * @param cuestionario enviado
     */
    void transaccSaveActivaUsuariosProv(CuestionarioEnvio cuestionario);
    
    /**
     * Recupera un cuestionario enviado a partir de su id
     * 
     * @author Ezentis
     * @param idCuestionarioEnviado
     * @return cuestionario enviado
     */
    CuestionarioEnvio findById(Long idCuestionarioEnviado);
    
    /**
     * Recupera el cuestionario no finalizado perteneciente a un destinatario (no puede haber más de uno)
     * 
     * @author Ezentis
     * @param inspeccion
     * @return cuestionario enviado
     */
    CuestionarioEnvio findNoFinalizadoPorInspeccion(Inspeccion inspeccion);
    
    /**
     * Comprueba si existe algún cuestionario enviado asociado a un modelo de cuestionario personalizado
     * 
     * @author Ezentis
     * @param cuestionario
     * @return boolean si o no
     */
    boolean existsByCuestionarioPersonalizado(CuestionarioPersonalizado cuestionario);
    
    /**
     * Recupera los cuestionarios enviados que aún no han sido cumplimentados
     * 
     * @author Ezentis
     * @return lista de cuestionarios enviados
     */
    List<CuestionarioEnvio> findNoCumplimentados();
    
    /**
     * Método que devuelve la lista de cuestionarios enviados en una consulta basada en criteria.
     * 
     * @author EZENTIS
     * @param cuestionarioEnviadoBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de cuestionarios enviados.
     */
    List<CuestionarioEnvio> buscarCuestionarioEnviadoCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda);
    
    /**
     * Método que devuelve el número de cuestionarios enviados en una consulta basada en criteria
     * 
     * @param cuestionarioEnviadoBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de una consulta criteria.
     * @author EZENTIS
     */
    int getCountCuestionarioCriteria(CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda);
    
}
