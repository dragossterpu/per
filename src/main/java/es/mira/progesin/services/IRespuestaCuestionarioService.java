package es.mira.progesin.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.primefaces.model.UploadedFile;

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
     * @param listaDocumentos listado de documentos que ya tenía la respuesta
     * @throws SQLException posible excepción
     * @throws IOException posible excepción
     */
    public void saveConDocumento(RespuestaCuestionario respuestaCuestionario, UploadedFile file,
            List<Documento> listaDocumentos) throws SQLException, IOException;
    
    /**
     * Elimina de BBDD la respuesta y el documento pasados como parámetros.
     * 
     * @param respueta respuesta a eliminar
     * @param documento documento a eliminar
     */
    public void eliminarDocumentoRespuesta(RespuestaCuestionario respueta, Documento documento);
}
