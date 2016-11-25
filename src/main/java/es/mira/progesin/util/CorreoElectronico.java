package es.mira.progesin.util;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Parametro;
import es.mira.progesin.persistence.repositories.IParametrosRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("correoElectronico")



public class CorreoElectronico implements ICorreoElectronico {

	@Autowired	
	IParametrosRepository parametro;
	
	
	
	
	/***************************************
	 * 
	 * conexionServidor
	 * 
	 * Realiza la conexión con el servidor de correo.
	 * Emplea los parámetros de conexión guardados en BDD
	 * 
	 * @author Ezentis
	 *
	 *************************************/
	
	public JavaMailSenderImpl conexionServidor(){
		JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
		
		
		try {
			
			List<Parametro> parametrosMail=parametro.findParametrosForSeccion("mail");
		
			String host= parametro.findValueForKey("Host","mail");
			String puerto= parametro.findValueForKey("HostPort","mail");
			String auth= parametro.findValueForKey("Auth","mail");
			String tls= parametro.findValueForKey("TLS","mail");
			String usuario= parametro.findValueForKey("User","mail");
			String pass= parametro.findValueForKey("UserPwd","mail");
			
			
			Properties mailProperties = new Properties();
			mailProperties.put("mail.smtp.auth", auth);
			mailProperties.put("mail.smtp.ssl.trust", host);
			mailProperties.put("mail.smtp.starttls.enable", tls);
			mailProperties.put("mail.smtp.host", host);
			mailProperties.put("mail.smtp.port", puerto);

			mailSender.setJavaMailProperties(mailProperties);
			
			mailSender.setUsername(usuario);
			mailSender.setPassword(pass);
			
		} catch (Exception e) {
			log.error("Error en la conexión con el servidor: ", e);
		}
		return mailSender;
	}
	
	/***************************************
	 * 
	 * envioCorreo
	 * 
	 * Realiza el envío de correo electrónico
	 * a varios destinatarios.
	 * 
	 * @author 	Ezentis
	 * @param	String[] Destinatarios de correo
	 * @param	String	Asunto del correo
	 * @param	String	Cuerpo del correo
	 *
	 *************************************/
	
	@Override
	public void envioCorreo(String[] destino, String asunto, String cuerpo) {
	
		try {
			JavaMailSenderImpl mailSender=conexionServidor();
			SimpleMailMessage msg=new SimpleMailMessage();
			
			msg.setTo(destino);
			msg.setSubject(asunto);
			msg.setText(cuerpo);
			
			mailSender.send(msg);
		} catch (MailException e) {
			log.error("Error en el envío de correo: ", e);
		}

	}
	
	/***************************************
	 * 
	 * envioCorreo
	 * 
	 * Realiza el envío de correo electrónico
	 * a un único destinatario.
	 * 
	 * @author 	Ezentis
	 * @param	String Destinatario de correo
	 * @param	String	Asunto del correo
	 * @param	String	Cuerpo del correo
	 *
	 *************************************/

	@Override
	public void envioCorreo(String destino, String asunto, String cuerpo) {
		
		String[] destinos = new String[1];
		destinos[0]=destino;
		envioCorreo(destinos, asunto, cuerpo);
		
	}
	
	/***************************************
	 * 
	 * envioCorreoAdjuntos
	 * 
	 * Realiza el envío de correo electrónico
	 * a varios destinatarios con fichero adjunto.
	 * 
	 * @author 	Ezentis
	 * @param	String[] Destinatarios de correo
	 * @param	String	Asunto del correo
	 * @param	String	Cuerpo del correo
	 * @param	File  Adjunto
	 *
	 *************************************/

	@Override
	public void envioCorreoAdjuntos( String[] destino, String asunto, String cuerpo, File adjunto) {
		JavaMailSenderImpl mailSender=conexionServidor();
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(destino);
			helper.setSubject(asunto);
			helper.setText(cuerpo);			
			helper.addAttachment(adjunto.getName(), adjunto);
			
		} catch (MessagingException e) {
			log.error("Error en el envío de correo: ", e);
		}
		
		mailSender.send(message);
	}
	
	/***************************************
	 * 
	 * envioCorreoAdjuntos
	 * 
	 * Realiza el envío de correo electrónico
	 * a un único destinatario con fichero adjunto.
	 * 
	 * @author 	Ezentis
	 * @param	String Destinatario de correo
	 * @param	String	Asunto del correo
	 * @param	String	Cuerpo del correo
	 * @param	File  Adjunto
	 *
	 *************************************/

	@Override
	public void envioCorreoAdjuntos(String destino, String asunto, String cuerpo, File adjunto) {
		String[] destinos = new String[1];
		destinos[0]=destino;
		envioCorreoAdjuntos(destinos, asunto, cuerpo, adjunto);
		
	}

	

	
}
