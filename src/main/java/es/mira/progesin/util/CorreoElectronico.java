package es.mira.progesin.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.mitchellbosecke.pebble.error.PebbleException;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.web.beans.ApplicationBean;
import lombok.Getter;

/**
 * Implementación de la clase ICorreoElectronico.
 * 
 * @author EZENTIS
 * 
 */
@Component("correoElectronico")
@Getter
public class CorreoElectronico implements ICorreoElectronico {
    
    /**
     * Bean de configuración de la aplicación.
     */
    @Autowired
    private ApplicationBean applicationBean;
    
    /**
     * Servicio de envío de correo.
     */
    @Autowired
    private JavaMailSenderImpl mailSender;
    
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividad;
    
    /**
     * Configura la conexión con el servidor de correo.
     */
    @PostConstruct
    public void conexionServidor() {
        
        Map<String, String> parametrosMail = applicationBean.getMapaParametros().get("mail");
        String pass = parametrosMail.get("UserPwd");
        
        Properties mailProperties = new Properties();
        
        Iterator<Entry<String, String>> it = parametrosMail.entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry<String, String> param = it.next();
            mailProperties.put(param.getKey(), param.getValue());
        }
        
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setPassword(pass);
        
    }
    
    /**
     * 
     * Envío de correos electrónico. Destinatarios, destinatarios en copia, asunto, cuerpo del mensaje y los documentos
     * adjuntos se reciben como parámetros.
     * 
     * @param paramDestino Destinatarios separados por ','
     * @param paramCC Destinatario en copia
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * @param paramAdjunto Lista de ficheros adjuntos
     * 
     */
    @Override
    public void envioCorreo(String paramDestino, String paramCC, String paramAsunto, String paramCuerpo,
            List<File> paramAdjunto) {
        
        enviarCorreo(paramDestino, paramCC, paramAsunto, paramCuerpo, paramAdjunto, Constantes.TEMPLATECORREOBASE,
                null);
    }
    
    /**
     * 
     * Envío de correos electrónico. Destinatarios, asunto, cuerpo del mensaje y los documentos adjuntos se reciben como
     * parámetros
     * 
     * @param paramDestino Destinatarios separados por ','
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * @param paramAdjunto Lista de ficheros adjuntos
     * 
     */
    @Override
    public void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto) {
        
        enviarCorreo(paramDestino, null, paramAsunto, paramCuerpo, paramAdjunto, Constantes.TEMPLATECORREOBASE, null);
    }
    
    /**
     * 
     * Envío de correos electrónico sin adjuntos. Destinatarios, asunto y cuerpo del mensaje se reciben como parámetros
     * 
     * @param paramDestino Destinatarios separados por ','
     * @param paramAsunto del correo
     * @param paramCuerpo Cuerpo del correo
     * 
     */
    @Override
    public void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo) {
        
        enviarCorreo(paramDestino, null, paramAsunto, paramCuerpo, null, Constantes.TEMPLATECORREOBASE, null);
        
    }
    
    /**
     * 
     * Envío de correos electrónico sin adjuntos con plantilla personalizada. Destinatarios, asunto, datos del cuerpo
     * del mensaje y ruta de la plantilla se reciben como parámetros
     * 
     * @param paramDestino Destinatarios separados por ','
     * @param paramAsunto del correo
     * @param plantilla ruta del archivo de la plantilla pebble
     * @param paramPlantilla parametros del cuerpo del correo que se usan en la plantilla
     * 
     */
    @Override
    public void envioCorreo(String paramDestino, String paramAsunto, String plantilla,
            Map<String, String> paramPlantilla) {
        
        enviarCorreo(paramDestino, null, paramAsunto, null, null, plantilla, paramPlantilla);
        
    }
    
    /**
     * Envío de correos a uno o varios destinatarios en formato html con o sin archivos adjuntos.
     * 
     * @param destino Lista de destinatarios destinatario
     * @param conCopia Lista de destinatario en copia
     * @param asunto Asunto del correo
     * @param cuerpo Cuerpo del correo
     * @param adjuntos Lista de ficheros adjuntos
     * @param plantilla ruta del archivo de la plantilla pebble
     * @param parametrosExtra parametros que se usan en la plantilla
     * @throws CorreoException excepción al enviar el correo
     */
    private void enviarCorreo(String destino, String conCopia, String asunto, String cuerpo, List<File> adjuntos,
            String plantilla, Map<String, String> parametrosExtra) {
        try {
            
            // Prepare message using a Spring helper
            final MimeMessage message = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(destino);
            if (conCopia != null) {
                helper.setCc(conCopia);
            }
            helper.setSubject(asunto);
            // helper.setFrom("no-reply@interior.es");
            
            Map<String, String> datosApoyo = applicationBean.getMapaParametros().get("datosApoyo");
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cuerpo", cuerpo);
            parametros.putAll(datosApoyo);
            if (parametrosExtra != null) {
                parametros.putAll(parametrosExtra);
            }
            
            final String htmlContent = Utilities.generarTextoConPlantilla(plantilla, parametros);
            helper.setText(htmlContent, true);
            
            ClassPathResource imagen = new ClassPathResource(Constantes.ESCUDOIPSS);
            helper.addInline("imagenfirma", imagen.getFile());
            
            if (adjuntos != null) {
                for (File adj : adjuntos) {
                    helper.addAttachment(adj.getName(), adj);
                }
            }
            
            mailSender.send(message);
            
        } catch (MailException | MessagingException | IOException | PebbleException e) {
            registroActividad.altaRegActividadError("Envio correo", e);
            throw new CorreoException(e);
        }
        
    }
    
}
