package es.mira.progesin.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * 
 * Servicio para la programación de tareas automáticas.
 * 
 * @author EZENTIS
 * 
 */

@Service("tareasService")

public class TareasService implements ITareasService {
    /**
     * Servicio de cuestionarios enviados.
     */
    @Autowired
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Servicio de solicitud de documentación.
     */
    @Autowired
    private ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Servicio de documentos.
     */
    @Autowired
    private IDocumentoService documentoService;
    
    /**
     * Envío de correos electrónicos.
     */
    @Autowired
    private ICorreoElectronico correoElectronico;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividad;
    
    /**
     * Bean de configuración de la aplciación.
     */
    @Autowired
    private ApplicationBean applicationBean;
    
    /**
     * Propiedades de las tareas.
     */
    private Properties tareasProperties = new Properties();
    
    /**
     * Constante con literal para el final de mensaje.
     */
    private static final String FINAL = "\n\nEste es un recordatorio automático.\nNo responda a este correo.";
    
    /**
     * Constante con literal para el inicio de mensaje.
     */
    private static final String INICIO = "Se envía este correo como redordatorio\n";
    
    /**
     * Inicializa el servicio cargando los parámetros relativos a las tareas.
     */
    @PostConstruct
    private void init() {
        Map<String, String> parametrosTareas = applicationBean.getMapaParametros().get("tareas");
        
        Iterator<Entry<String, String>> it = parametrosTareas.entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry<String, String> param = it.next();
            tareasProperties.put(param.getKey(), param.getValue());
        }
        
    }
    
    /**
     * Recordatorio de la necesidad de enviar un cuestionario.
     */
    @Override
    @Scheduled(cron = "0 0 8 * * MON-FRI")
    
    public void recordatorioEnvioCuestionario() {
        Date hoy;
        try {
            hoy = new Date();
            List<CuestionarioEnvio> lista = cuestionarioEnvioService.findNoCumplimentados();
            
            for (int i = 0; i < lista.size(); i++) {
                CuestionarioEnvio cuestionario = lista.get(i);
                long milis = cuestionario.getFechaLimiteCuestionario().getTime() - hoy.getTime();
                int dias = (int) (milis / 86400000);
                
                if (dias == Integer.parseInt(tareasProperties.getProperty("plazoDiasCuestionario"))) {
                    StringBuilder cuerpo = new StringBuilder().append("Faltan ").append(dias)
                            .append(" dia/s para la fecha límite de envío del cuestionario de la inspección ")
                            .append(cuestionario.getInspeccion().getNumero()).append(FINAL);
                    
                    correoElectronico.envioCorreo(cuestionario.getCorreoEnvio(), "Recordatorio envío cuestionario",
                            cuerpo.toString());
                }
                
                if (dias == 0) {
                    StringBuilder cuerpo = new StringBuilder().append(INICIO)
                            .append("Hoy finaliza el plazo para el envío del cuestionario de la inspección número ")
                            .append(cuestionario.getInspeccion().getNumero()).append(FINAL);
                    
                    List<String> listaDestinos = new ArrayList<>();
                    listaDestinos.add(cuestionario.getCorreoEnvio());
                    listaDestinos.add(tareasProperties.getProperty("correoApoyo"));
                    
                    correoElectronico.envioCorreo(listaDestinos,
                            "Recordatorio Fin de plazo para el envío del cuestionario", cuerpo.toString());
                }
            }
        } catch (Exception e) {
            registroActividad.altaRegActividadError("Batch envio recordatorio", e);
        }
    }
    
    /**
     * Recordatorio de la necesidad de enviar la documentación solicitada.
     */
    @Override
    @Scheduled(cron = "0 0 8 * * MON-FRI")
    
    public void recordatorioEnvioDocumentacion() {
        Date hoy;
        try {
            hoy = new Date();
            List<SolicitudDocumentacionPrevia> lista = solicitudDocumentacionService.findEnviadasNoCumplimentadas();
            
            for (int i = 0; i < lista.size(); i++) {
                
                SolicitudDocumentacionPrevia solicitud = lista.get(i);
                long milis = solicitud.getFechaLimiteCumplimentar().getTime() - hoy.getTime();
                int dias = (int) (milis / 86400000);
                if (dias == Integer.parseInt(tareasProperties.getProperty("plazoDiasDocumentacion"))) {
                    
                    StringBuilder cuerpo = new StringBuilder().append("INICIO").append("Faltan ").append(dias)
                            .append(" dia/s para la fecha límite de envío de la documentación para la inspección número ")
                            .append(solicitud.getInspeccion().getNumero()).append(FINAL);
                    
                    correoElectronico.envioCorreo(solicitud.getCorreoDestinatario(),
                            "Recordatorio envío de documentación previa", cuerpo.toString());
                }
                
                if (dias == 0) {
                    StringBuilder cuerpo = new StringBuilder().append("INICIO")
                            .append("Hoy finaliza el plazo para el envío de la documentación para la inspección número ")
                            .append(solicitud.getInspeccion().getNumero()).append(FINAL);
                    
                    List<String> listaDestinos = new ArrayList<>();
                    listaDestinos.add(solicitud.getCorreoDestinatario());
                    listaDestinos.add(tareasProperties.getProperty("correoApoyo"));
                    
                    correoElectronico.envioCorreo(listaDestinos,
                            "Recordatorio Fin de plazo para el envío de documentación previa", cuerpo.toString());
                }
            }
        } catch (Exception e) {
            registroActividad.altaRegActividadError("Batch envio documentación previa", e);
        }
    }
    
    /**
     * Limpia la papelera de documentos.
     */
    @Override
    @Scheduled(cron = "0 0 8 * * MON-FRI")
    
    public void limpiarPapelera() {
        Date hoy = new Date();
        List<Documento> listadoDocumentosPapelera = documentoService.findByFechaBajaIsNotNull();
        
        for (Documento documento : listadoDocumentosPapelera) {
            Long milis = hoy.getTime() - documento.getFechaBaja().getTime();
            int dias = (int) (milis / 86400000);
            if (dias >= 90) {
                documentoService.delete(documento);
            }
        }
    }
}
