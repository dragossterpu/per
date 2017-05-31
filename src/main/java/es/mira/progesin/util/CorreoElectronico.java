package es.mira.progesin.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

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
     * Servicio del registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividad;
    
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
        envioCorreoSinAdjuntos(paramDestino, null, paramAsunto, paramCuerpo);
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
        envioCorreoAdjuntos(paramDestino, null, paramAsunto, paramCuerpo, paramAdjunto);
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
        List<String> lista = new ArrayList<>();
        lista.add(paramDestino);
        
        List<String> cc = new ArrayList<>();
        lista.add(paramCC);
        
        envioCorreoAdjuntos(lista, cc, paramAsunto, paramCuerpo, paramAdjunto);
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
        List<String> lista = new ArrayList<>();
        lista.add(paramDestino);
        
        envioCorreoAdjuntos(lista, null, paramAsunto, paramCuerpo, paramAdjunto);
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
        List<String> lista = new ArrayList<>();
        lista.add(paramDestino);
        
        envioCorreoSinAdjuntos(lista, null, paramAsunto, paramCuerpo);
        
    }
    
    /**
     * Realiza la conexión con el servidor de correo.
     * 
     * @return Objeto sender para realizar operaciones con la conexión
     */
    private JavaMailSenderImpl conexionServidor() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
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
        
        return mailSender;
    }
    
    /**
     * Envío de correos sin adjuntos.
     * 
     * @param destino Lista de destinatarios destinatario
     * @param conCopia Lista de destinatario en copia
     * @param asunto Asunto del correo
     * @param cuerpo Cuerpo del correo
     */
    private void envioCorreoSinAdjuntos(List<String> destino, List<String> conCopia, String asunto, String cuerpo) {
        try {
            JavaMailSenderImpl mailSender = conexionServidor();
            SimpleMailMessage msg = new SimpleMailMessage();
            
            String[] arrDestino = destino.toArray(new String[destino.size()]);
            msg.setTo(arrDestino);
            
            if (conCopia != null) {
                String[] arrCopia = conCopia.toArray(new String[conCopia.size()]);
                msg.setCc(arrCopia);
            }
            msg.setSubject(asunto);
            msg.setText(cuerpo);
            
            mailSender.send(msg);
        } catch (Exception e) {
            registroActividad.altaRegActividadError("Envio correo", e);
            throw new CorreoException(e);
            
        }
        
    }
    
    /**
     * Envío de correos con adjuntos.
     * 
     * @param destino Lista de destinatarios destinatario
     * @param conCopia Lista de destinatario en copia
     * @param asunto Asunto del correo
     * @param cuerpo Cuerpo del correo
     * @param adjunto Lista de ficheros adjuntos
     */
    private void envioCorreoAdjuntos(List<String> destino, List<String> conCopia, String asunto, String cuerpo,
            List<File> adjunto) {
        JavaMailSenderImpl mailSender = conexionServidor();
        MimeMessage message = mailSender.createMimeMessage();
        
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            String[] arrDestino = destino.toArray(new String[destino.size()]);
            
            helper.setTo(arrDestino);
            
            if (conCopia != null) {
                String[] arrCopia = conCopia.toArray(new String[conCopia.size()]);
                helper.setCc(arrCopia);
            }
            
            helper.setSubject(asunto);
            helper.setText(cuerpo);
            
            for (File adj : adjunto) {
                helper.addAttachment(adj.getName(), adj);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            registroActividad.altaRegActividadError("Envio correo", e);
            throw new CorreoException(e);
            
        }
        
    }
    
}
