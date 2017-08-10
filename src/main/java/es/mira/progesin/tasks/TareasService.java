package es.mira.progesin.tasks;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
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
     * Nombre del parámetro que define número de días para responder cuestionario.
     */
    private static final String PLAZODIASCUESTIONARIO = "plazoDiasCuestionario";
    
    /**
     * Nombre del parámetro que define número de días para responder una solicitud de documentación previa.
     */
    private static final String PLAZODIASSOLICITUD = "plazoDiasDocumentacion";
    
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
    private static final String INICIO = "Se envía este correo como recordatorio.\n";
    
    /**
     * Inicializa el servicio cargando los parámetros relativos a las tareas.
     */
    @PostConstruct
    public void init() {
        Map<String, String> parametrosTareas = applicationBean.getMapaParametros().get("tareas");
        
        if (parametrosTareas != null) {
            Iterator<Entry<String, String>> it = parametrosTareas.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> param = it.next();
                tareasProperties.put(param.getKey(), param.getValue());
            }
        }
        
    }
    
    /**
     * Recordatorio de la necesidad de enviar un cuestionario.
     * El proceso se ejecutará de lunes a viernes a las 8 de la mañana.
     */
    @Override
    @Scheduled(cron = "0 0 8 * * MON-FRI")
    public void recordatorioEnvioCuestionario() {
        LocalDate hoy = LocalDate.now();  
        List<CuestionarioEnvio> lista = cuestionarioEnvioService.findNoCumplimentados();
        try {
            for (int i = 0; i < lista.size(); i++) {
                CuestionarioEnvio cuestionario = lista.get(i);
                LocalDate fechaCuestionario = cuestionario.getFechaLimiteCuestionario().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long dias = ChronoUnit.DAYS.between(hoy, fechaCuestionario);
                int plazoDiasCuestionario = 0;
                if (tareasProperties.getProperty(PLAZODIASCUESTIONARIO) != null) {
                    plazoDiasCuestionario = Integer.parseInt(tareasProperties.getProperty(PLAZODIASCUESTIONARIO));
                }
                if (dias == plazoDiasCuestionario) {
                    StringBuilder cuerpo = new StringBuilder(INICIO).append("Faltan ").append(dias)
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
                            "Recordatorio fin de plazo para el envío del cuestionario", cuerpo.toString());
                }
            }
        } catch (CorreoException ce) {
            registroActividad.altaRegActividadError(SeccionesEnum.ALERTAS.getDescripcion(), ce);
        }
    }
    
    /**
     * Recordatorio de la necesidad de enviar la documentación solicitada.
     * El proceso se ejecutará de lunes a viernes a las 8 de la mañana.
     */
    @Override
    @Scheduled(cron = "0 0 8 * * MON-FRI")
    public void recordatorioEnvioDocumentacion() {
        LocalDate hoy = LocalDate.now();  
        List<SolicitudDocumentacionPrevia> lista = solicitudDocumentacionService.findEnviadasNoCumplimentadas();
        try {
            for (int i = 0; i < lista.size(); i++) {
                
                SolicitudDocumentacionPrevia solicitud = lista.get(i);
                LocalDate fechaSolicitud = solicitud.getFechaLimiteCumplimentar().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long dias = ChronoUnit.DAYS.between(hoy, fechaSolicitud);
                int plazoDiasSolcitud = 0;
                if (tareasProperties.getProperty(PLAZODIASSOLICITUD) != null) {
                    plazoDiasSolcitud = Integer.parseInt(tareasProperties.getProperty(PLAZODIASSOLICITUD));
                }
                if (dias == plazoDiasSolcitud) {
                    
                    StringBuilder cuerpo = new StringBuilder(INICIO).append("Faltan ").append(dias)
                            .append(" dia/s para la fecha límite de envío de la documentación para la inspección número ")
                            .append(solicitud.getInspeccion().getNumero()).append(FINAL);
                    
                    correoElectronico.envioCorreo(solicitud.getCorreoDestinatario(),
                            "Recordatorio envío de documentación previa", cuerpo.toString());
                }
                
                if (dias == 0) {
                    StringBuilder cuerpo = new StringBuilder(INICIO).append("Hoy finaliza el plazo para el envío de la documentación para la inspección número ")
                            .append(solicitud.getInspeccion().getNumero()).append(FINAL);
                    
                    List<String> listaDestinos = new ArrayList<>();
                    listaDestinos.add(solicitud.getCorreoDestinatario());
                    listaDestinos.add(tareasProperties.getProperty("correoApoyo"));
                    
                    correoElectronico.envioCorreo(listaDestinos,
                            "Recordatorio fin de plazo para el envío de documentación previa", cuerpo.toString());
                }
            }
        } catch (CorreoException ce) {
            registroActividad.altaRegActividadError(SeccionesEnum.ALERTAS.getDescripcion(), ce);
        }
    }
    
    /**
     * Borrra definitivamente los documentos que lleven más de 90 días o más dados de baja.
     * El proceso se ejecutará de lunes a viernes a la 1 de la mañana.
     */
    @Override
    @Scheduled(cron = "0 0 1 * * MON-FRI")
    public void limpiarPapelera() {
        LocalDate hoy = LocalDate.now(); 
        List<Documento> listadoDocumentosPapelera = documentoService.findByFechaBajaIsNotNull();
        
        for (Documento documento : listadoDocumentosPapelera) {
            LocalDate fechaBajaDocumento = documento.getFechaBaja().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long dias = ChronoUnit.DAYS.between(hoy, fechaBajaDocumento);
            if (dias >= 90) {
                try {
                    documentoService.delete(documento);
                } catch (DataAccessException e) {
                    registroActividad.altaRegActividadError(SeccionesEnum.ALERTAS.getDescripcion(), e);
                }
            }
        }
    }
}
