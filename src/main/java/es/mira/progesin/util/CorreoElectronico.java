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

@Component("correoElectronico")

public class CorreoElectronico implements ICorreoElectronico {

	@Autowired
	IParametrosRepository parametro;

	/***************************************
	 * 
	 * conexionServidor
	 * 
	 * Realiza la conexión con el servidor de correo. Emplea los parámetros de conexión guardados en BDD
	 * 
	 * @author Ezentis
	 *
	 *************************************/

	public JavaMailSenderImpl conexionServidor() throws Exception{
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();


			List<Parametro> parametrosMail = parametro.findParamByParamSeccion("mail");
			String pass = parametro.findValueForKey("UserPwd", "mail");
			
			

			Properties mailProperties = new Properties();
			for (Parametro param:parametrosMail){
				mailProperties.put(param.getParam().getClave(),param.getParam().getValor());
			}


			mailSender.setJavaMailProperties(mailProperties);
			mailSender.setPassword(pass);

		
		return mailSender;
	}

	/***************************************
	 * 
	 * envioCorreo
	 * 
	 * Realiza el envío de correo electrónico a varios destinatarios.
	 * 
	 * @author Ezentis
	 * @param String[] Destinatarios de correo
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 *
	 *************************************/

	@Override
	public void envioCorreo(String[] destino, String asunto, String cuerpo) throws Exception {

		
			JavaMailSenderImpl mailSender = conexionServidor();
			SimpleMailMessage msg = new SimpleMailMessage();

			msg.setTo(destino);
			msg.setSubject(asunto);
			msg.setText(cuerpo);

			mailSender.send(msg);
		

	}

	/***************************************
	 * 
	 * envioCorreo
	 * 
	 * Realiza el envío de correo electrónico a un único destinatario.
	 * 
	 * @author Ezentis
	 * @param String Destinatario de correo
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @throws Exception 
	 *
	 *************************************/

	@Override
	public void envioCorreo(String destino, String asunto, String cuerpo) throws Exception {

		String[] destinos = new String[1];
		destinos[0] = destino;
		envioCorreo(destinos, asunto, cuerpo);

	}

	/***************************************
	 * 
	 * envioCorreoAdjuntos
	 * 
	 * Realiza el envío de correo electrónico a varios destinatarios con fichero adjunto.
	 * 
	 * @author Ezentis
	 * @param String[] Destinatarios de correo
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @param File Adjunto
	 * @throws Exception 
	 *
	 *************************************/

	@Override
	public void envioCorreoAdjuntos(String[] destino, String asunto, String cuerpo, File adjunto) throws Exception {
		JavaMailSenderImpl mailSender = conexionServidor();
		MimeMessage message = mailSender.createMimeMessage();

		
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(destino);
			helper.setSubject(asunto);
			helper.setText(cuerpo);
			helper.addAttachment(adjunto.getName(), adjunto);

		

		mailSender.send(message);
	}

	/***************************************
	 * 
	 * envioCorreoAdjuntos
	 * 
	 * Realiza el envío de correo electrónico a un único destinatario con fichero adjunto.
	 * 
	 * @author Ezentis
	 * @param String Destinatario de correo
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @param File Adjunto
	 * @throws Exception 
	 *
	 *************************************/

	@Override
	public void envioCorreoAdjuntos(String destino, String asunto, String cuerpo, File adjunto) throws Exception {
		String[] destinos = new String[1];
		destinos[0] = destino;
		envioCorreoAdjuntos(destinos, asunto, cuerpo, adjunto);

	}

}
