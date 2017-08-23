package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.gd.Documento;

/**
 * Interfaz del servicio de repuestas de cuestionario.
 * 
 * @author EZENTIS
 *
 */
public interface IRespuestaCuestionarioService {
    
    /**
     * Guarda la respuesta de un cuestionario de tipo ADJUNTO y la lista de documentos en BBDD.
     * 
     * @param respuestaCuestionario respuesta que se quiere grabar
     * @param file fichero que se quiere cargar
     * @return respuesta actualizada
     * @throws ProgesinException posible excepción
     */
    RespuestaCuestionario saveConDocumento(RespuestaCuestionario respuestaCuestionario, UploadedFile file)
            throws ProgesinException;
    
    /**
     * Elimina de BBDD la respuesta y el documento pasados como parámetros.
     * 
     * @param respueta respuesta a eliminar
     * @param documento documento a eliminar
     * @return respuesta actualizada
     */
    RespuestaCuestionario eliminarDocumentoRespuesta(RespuestaCuestionario respueta, Documento documento);
    
    /**
     * Recibe un objeto de tipo UploadedFile y comprueba que el content-type dado por JSF (basándose en su extensión) se
     * corresponde con el content-type real obtenido a través de Tika (basándose en el contenido de la cabecera).
     * 
     * @param ficheroSubido fichero para el cual se quiere comprobar la validez de la extensión
     * @return boolean
     *
     */
    boolean esExtensionCorrecta(UploadedFile ficheroSubido);

    /**
     * Recibe el id de un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * @param idPlantilla id del documento a descargar
     * @return DefaultStreamedContent Flujo de descarga
     * @throws ProgesinException posible excepción
     */
    DefaultStreamedContent descargarPlantilla(Long idPlantilla) throws ProgesinException;
    
    /**
     * Transacción que guarda los datos de las respuestas de un cuestionario enviado.
     * 
     * @param listaRespuestas para un cuestionario
     * @return lista de respuestas guardadas con id
     */
    List<RespuestaCuestionario> transaccSaveConRespuestas(List<RespuestaCuestionario> listaRespuestas);
    
    
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
     * Recupera el cuestionario enviado no finalizado y no anulado perteneciente a un destinatario (no puede haber más de uno).
     * 
     * @param correo electrónico del remitente
     * @return cuestionario a enviar
     */
    CuestionarioEnvio buscaCuestionarioAResponder(String correo);

    /**
     * Guarda las respuestas de un usuario provisional y finaliza su parte, inactivándolo y reasignado sus áreas al usuario principal.
     * 
     * @param listaRespuestas lista de respuestas a guardar
     * @param usernameProvisional usuario provisional que está respondiendo
     * @param usernamePrincipal usuario principal del cuestionario
     * @param listaAreasUsuarioCuestEnv lista de áreas a reasignar
     * @return lista de respuestas actualizadas
     */
    List<RespuestaCuestionario> guardarRespuestasYAsignarAreasPrincipal(List<RespuestaCuestionario> listaRespuestas,
            String usernameProvisional, String usernamePrincipal, List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv);
}
