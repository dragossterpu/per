package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.IAreaUsuarioCuestEnvRepository;
import es.mira.progesin.persistence.repositories.IDatosTablaGenericaRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.util.VerificadorExtensiones;

/**
 * Implementación del servicio de respuestas de cuestionario.
 * @author EZENTIS
 *
 */
@Service
public class RespuestaCuestionarioService implements IRespuestaCuestionarioService {
    
    /**
     * Repositorio de respuestas de cuestionario.
     */
    @Autowired
    private IRespuestaCuestionarioRepository respuestaRepository;
    
    /**
     * Repositorio de respuestas tipo tabla/matriz.
     */
    @Autowired
    private IDatosTablaGenericaRepository datosTablaRepository;
    
    /**
     * Servicio de documentos.
     */
    @Autowired
    private IDocumentoService documentoService;
    
    /**
     * Verificador de extensiones.
     */
    @Autowired
    private VerificadorExtensiones verificadorExtensiones;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private IUserService userService;
    
    /**
     * Servicio de cuestionarios enviados.
     */
    @Autowired
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Repositorio de áreas/usuario de cuestionarios enviados.
     */
    @Autowired
    private IAreaUsuarioCuestEnvRepository areaUsuarioCuestEnvRepository;
    
    /**
     * Guarda la respuesta de un cuestionario de tipo ADJUNTO y la lista de documentos en BBDD.
     * 
     * @param respuestaCuestionario respuesta que se quiere grabar
     * @param archivoSubido fichero que se quiere cargar
     * @return respuesta actualizada
     * @throws ProgesinException posible excepción
     */
    
    @Override
    @Transactional(readOnly = false)
    public RespuestaCuestionario saveConDocumento(RespuestaCuestionario respuestaCuestionario,
            UploadedFile archivoSubido) throws ProgesinException {
        TipoDocumento tipo = TipoDocumento.builder().id(6L).build();
        Documento documentoSubido = documentoService.cargaDocumento(archivoSubido, tipo,
                respuestaCuestionario.getRespuestaId().getCuestionarioEnviado().getInspeccion());
        respuestaCuestionario.getDocumentos().add(documentoSubido);
        return respuestaRepository.save(respuestaCuestionario);
    }
    
    /**
     * Elimina de BBDD la respuesta y el documento pasados como parámetros.
     * 
     * @param respueta respuesta a eliminar
     * @param documento documento a eliminar
     * @return respuesta actualizada
     */
    @Override
    @Transactional(readOnly = false)
    public RespuestaCuestionario eliminarDocumentoRespuesta(RespuestaCuestionario respueta, Documento documento) {
        RespuestaCuestionario respuestaActualizada = respuestaRepository.save(respueta);
        documentoService.delete(documento);
        return respuestaActualizada;
    }
    
    /**
     * Recibe un objeto de tipo UploadedFile y comprueba que el content-type dado por JSF (basándose en su extensión) se
     * corresponde con el content-type real obtenido a través de Tika (basándose en el contenido de la cabecera).
     * 
     * 
     * @param ficheroSubido fichero para el cual se quiere comprobar la validez de la extensión
     * @return boolean
     *
     */
    @Override
    public boolean esExtensionCorrecta(UploadedFile ficheroSubido) {
        return verificadorExtensiones.extensionCorrecta(ficheroSubido);
    }
    
    /**
     * Recibe el id de un documento como parámetro y devuelve un stream para realizar la descarga.
     * 
     * 
     * @param idPlantilla id del documento a descargar
     * @return DefaultStreamedContent Flujo de descarga
     * @throws ProgesinException posible excepción
     */
    @Override
    public DefaultStreamedContent descargarPlantilla(Long idPlantilla) throws ProgesinException {
        return documentoService.descargaDocumento(idPlantilla);
    }
    
    /**
     * Transacción que guarda los datos de las respuestas de un cuestionario enviado.
     * 
     * @param listaRespuestas para un cuestionario
     * @return lista de respuestas guardadas con id
     */
    @Override
    @Transactional(readOnly = false)
    public List<RespuestaCuestionario> transaccSaveConRespuestas(List<RespuestaCuestionario> listaRespuestas) {
        List<RespuestaCuestionario> listaRespuestasGuardadas = respuestaRepository.save(listaRespuestas);
        respuestaRepository.flush();
        datosTablaRepository.deleteRespuestasTablaHuerfanas();
        return listaRespuestasGuardadas;
    }
    
    /**
     * Guarda los datos de un cuestionario enviado y sus respuestas, e inactiva los usuarios provisionales que lo han
     * cumplimentado una vez finalizado o anulado.
     * 
     * @param cuestionario enviado
     * @param listaRespuestas de un cuestionario
     */
    @Override
    @Transactional(readOnly = false)
    public void transaccSaveConRespuestasInactivaUsuariosProv(CuestionarioEnvio cuestionario,
            List<RespuestaCuestionario> listaRespuestas) {
        respuestaRepository.save(listaRespuestas);
        respuestaRepository.flush();
        datosTablaRepository.deleteRespuestasTablaHuerfanas();
        String correoPrincipal = cuestionario.getCorreoEnvio();
        userService.cambiarEstado(correoPrincipal, EstadoEnum.INACTIVO);
        cuestionarioEnvioService.save(cuestionario);
    }
    
    /**
     * Recupera el cuestionario enviado no finalizado y no anulado perteneciente a un destinatario (no puede haber más
     * de uno).
     * 
     * @param correo electrónico del remitente
     * @return cuestionario a enviar
     */
    @Override
    public CuestionarioEnvio buscaCuestionarioAResponder(String correo) {
        return cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correo);
    }
    
 /**
  * Guarda las respuestas de un usuario provisional y finaliza su parte, inactivándolo y reasignado sus áreas al usuario principal.
  * 
  * @param listaRespuestas lista de respuestas a guardar
  * @param usernameProvisional usuario provisional que está respondiendo
  * @param usernamePrincipal usuario principal del cuestionario
  * @param listaAreasUsuarioCuestEnv lista de áreas a reasignar
  * @return lista de respuestas actualizadas
  */
    @Override
    @Transactional(readOnly = false)
    public List<RespuestaCuestionario> guardarRespuestasYAsignarAreasPrincipal(List<RespuestaCuestionario> listaRespuestas, String usernameProvisional, String usernamePrincipal, List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv) {
        userService.cambiarEstado(usernameProvisional, EstadoEnum.INACTIVO);
        listaAreasUsuarioCuestEnv.forEach(areaUsuario -> {
            if (areaUsuario.getUsernameProv().equals(usernameProvisional)) {
                areaUsuario.setUsernameProv(usernamePrincipal);
            }
        });
        areaUsuarioCuestEnvRepository.save(listaAreasUsuarioCuestEnv);
        return transaccSaveConRespuestas(listaRespuestas);
    }
    
}
