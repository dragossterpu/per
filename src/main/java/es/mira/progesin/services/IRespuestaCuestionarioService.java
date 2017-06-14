package es.mira.progesin.services;

import org.primefaces.model.UploadedFile;

import es.mira.progesin.exceptions.ProgesinException;
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
    public RespuestaCuestionario saveConDocumento(RespuestaCuestionario respuestaCuestionario, UploadedFile file)
            throws ProgesinException;
    
    /**
     * Elimina de BBDD la respuesta y el documento pasados como parámetros.
     * 
     * @param respueta respuesta a eliminar
     * @param documento documento a eliminar
     * @return respuesta actualizada
     */
    public RespuestaCuestionario eliminarDocumentoRespuesta(RespuestaCuestionario respueta, Documento documento);
}
