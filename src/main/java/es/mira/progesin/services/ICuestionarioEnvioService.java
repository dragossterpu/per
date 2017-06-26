package es.mira.progesin.services;

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
 * Interfaz del servicio de cuestionarios enviados.
 * 
 * @author EZENTIS
 *
 */
public interface ICuestionarioEnvioService {
    
    /**
     * Crea y envía un cuestionario a partir de un modelo personalizado, genera los usuarios provisionales que lo
     * responderán y envía un correo al destinatario.
     * 
     * @param user remitentes del cuestionario
     * @param cuestionarioEnvio enviado
     * @param cuerpoCorreo correo electrónico de los remitentes
     */
    void crearYEnviarCuestionario(List<User> user, CuestionarioEnvio cuestionarioEnvio, String cuerpoCorreo);
    
    /**
     * Recupera el cuestionario enviado no finalizado perteneciente a un destinatario (no puede haber más de uno).
     * 
     * @param correo electrónico del remitente
     * @return cuestionario a enviar
     */
    CuestionarioEnvio findNoFinalizadoPorCorreoEnvio(String correo);
    
    /**
     * Guarda la información de un cuestionario enviado en la bdd.
     * 
     * @param cuestionario a enviar
     */
    void save(CuestionarioEnvio cuestionario);
    
    /**
     * Transacción que guarda los datos de las respuestas de un cuestionario enviado.
     * 
     * @param listaRespuestas para un cuestionario
     * @return lista de respuestas guardadas con id
     */
    List<RespuestaCuestionario> transaccSaveConRespuestas(List<RespuestaCuestionario> listaRespuestas);
    
    /**
     * Guarda los datos de un cuestionario enviado y elimina los usuarios provisionales que lo han cumplimentado una vez
     * finalizado o anulado.
     * 
     * @param cuestionario enviado
     */
    void transaccSaveElimUsuariosProv(CuestionarioEnvio cuestionario);
    
    /**
     * Guarda los datos de un cuestionario enviado y sus respuestas, e inactiva los usuarios provisionales que lo han
     * cumplimentado una vez finalizado o anulado.
     * 
     * @param cuestionario enviado
     * @param listaRespuestas de un cuestionario
     */
    void transaccSaveConRespuestasInactivaUsuariosProv(CuestionarioEnvio cuestionario,
            List<RespuestaCuestionario> listaRespuestas);
    
    /**
     * Guarda los datos de un cuestionario enviado y activa los usuarios provisionales que debe cumplimentarlo de nuevo
     * en caso de no conformidad.
     * 
     * @param cuestionario enviado
     */
    void transaccSaveActivaUsuariosProv(CuestionarioEnvio cuestionario);
    
    /**
     * Recupera un cuestionario enviado a partir de su identificador.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario
     * @return cuestionario enviado objeto
     */
    CuestionarioEnvio findById(Long idCuestionarioEnviado);
    
    /**
     * Recupera el cuestionario enviado no finalizado y no anulado perteneciente a una inspección (no puede haber más de
     * uno).
     * 
     * @param inspeccion inspección a la que pertenece el cuestionario
     * @return cuestionario enviado
     */
    CuestionarioEnvio findNoFinalizadoPorInspeccion(Inspeccion inspeccion);
    
    /**
     * Comprueba si existe algún cuestionario enviado asociado a un modelo de cuestionario personalizado.
     * 
     * @param cuestionario personalizado
     * @return boolean valor booleano
     */
    boolean existsByCuestionarioPersonalizado(CuestionarioPersonalizado cuestionario);
    
    /**
     * Recupera los cuestionarios enviados que aún no han sido cumplimentados.
     * 
     * @return lista de cuestionarios enviados no cumplimentados
     */
    List<CuestionarioEnvio> findNoCumplimentados();
    
    /**
     * Método que devuelve la lista de cuestionarios enviados en una consulta basada en criteria.
     * 
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
     * Método que devuelve el número de cuestionarios enviados en una consulta basada en criteria.
     * 
     * @param cuestionarioEnviadoBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de la consulta
     */
    int getCountCuestionarioCriteria(CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda);
    
}
