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
     * Envío de correos electrónico sin adjuntos. La lista de destinatarios, asunto y cuerpo del mensaje se reciben como
     * parámetros
     * 
     * @param paramDestino Lista de destinatarios
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * 
     */
    @Override
    public void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo) {
        String destino = String.join(", ", paramDestino);
        enviarCorreo(destino, null, paramAsunto, paramCuerpo, null);
    }
    
    /**
     * 
     * Envío de correos electrónicos a una lista de destinatarios pasada como parámetros. El asunto, cuerpo del mensaje
     * y los documentos adjuntos se reciben como parámetros
     * 
     * @param paramDestino Lista de destinatarios
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * @param paramAdjunto Lista de ficheros adjuntos
     * 
     */
    @Override
    public void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo,
            List<File> paramAdjunto) {
        String destino = String.join(", ", paramDestino);
        enviarCorreo(destino, null, paramAsunto, paramCuerpo, paramAdjunto);
    }
    
    /**
     * 
     * Envío de correos electrónico. El destinatario, destinatario en copia, asunto, cuerpo del mensaje y los documentos
     * adjuntos se reciben como parámetros.
     * 
     * @param paramDestino Destinatario
     * @param paramCC Destinatario en copia
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * @param paramAdjunto Lista de ficheros adjuntos
     * 
     */
    @Override
    public void envioCorreo(String paramDestino, String paramCC, String paramAsunto, String paramCuerpo,
            List<File> paramAdjunto) {
        
        enviarCorreo(paramDestino, paramCC, paramAsunto, paramCuerpo, paramAdjunto);
    }
    
    /**
     * 
     * Envío de correos electrónico. El destinatario, asunto, cuerpo del mensaje y los documentos adjuntos se reciben
     * como parámetros
     * 
     * @param paramDestino Destinatario
     * @param paramAsunto Asunto del correo
     * @param paramCuerpo Cuerpo del correo
     * @param paramAdjunto Lista de ficheros adjuntos
     * 
     */
    @Override
    public void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto) {
        
        enviarCorreo(paramDestino, null, paramAsunto, paramCuerpo, paramAdjunto);
    }
    
    /**
     * 
     * Envío de correos electrónico sin adjuntos. El destinatario, asunto y cuerpo del mensaje se reciben como
     * parámetros
     * 
     * @param paramDestino Destinatario
     * @param paramAsunto del correo
     * @param paramCuerpo Cuerpo del correo
     * 
     */
    @Override
    public void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo) {
        
        enviarCorreo(paramDestino, null, paramAsunto, paramCuerpo, null);
        
    }
    
    /**
     * Envío de correos sin adjuntos.
     * 
     * @param destino Lista de destinatarios destinatario
     * @param conCopia Lista de destinatario en copia
     * @param asunto Asunto del correo
     * @param cuerpo Cuerpo del correo
     * @param adjuntos Lista de ficheros adjuntos
     * @throws CorreoException excepción al enviar el correo
     */
    private void enviarCorreo(String destino, String conCopia, String asunto, String cuerpo, List<File> adjuntos) {
        try {
            
            // Prepare message using a Spring helper
            final MimeMessage message = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(destino);
            if (conCopia != null) {
                helper.setCc(conCopia);
            }
            helper.setSubject(asunto);
            // msg.setFrom("no-reply@interior.es");
            
            Map<String, String> datosApoyo = applicationBean.getMapaParametros().get("datosApoyo");
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cuerpo", cuerpo);
            parametros.putAll(datosApoyo);
            
            final String htmlContent = Utilities.generarTextoConPlantilla(Constantes.TEMPLATECORREO, parametros);
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
