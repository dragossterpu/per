package es.mira.progesin.util;

import java.io.File;
import java.io.FileNotFoundException;
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
	
	List<String> destino;
	List<String> conCopia;
	String asunto;
	String cuerpo;
	List<File> adjunto;
	
	SimpleMailMessage msg;
	
	/***************************************
	 * 
	 * setDatos
	 * 
	 * Crea un mensaje.
	 * 
	 * @author Ezentis
	 * @param List<String> Destinatarios de correo
	 * @param List<String> Destinatarios en copia
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 *
	 *************************************/
	
	public void setDatos(List<String> paramDestino,  List<String> paramCC, String paramAsunto,String paramCuerpo){
		this.destino=paramDestino;
		this.conCopia=paramCC;
		this.asunto=paramAsunto;
		this.cuerpo=paramCuerpo;
	}
	
	
	/***************************************
	 * 
	 * setDatos
	 * 
	 * Crea un mensaje.
	 * 
	 * @author Ezentis
	 * @param List<String> Destinatarios de correo
	 * @param List<String> Destinatarios en copia
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @param List<File> Ficheros adjunto
	 *
	 *************************************/
	
	public void setDatos(List<String> paramDestino, List<String> paramCC, String paramAsunto,String paramCuerpo, List<File> paramAdjunto){
		this.destino=paramDestino;
		this.conCopia=paramCC;
		this.asunto=paramAsunto;
		this.cuerpo=paramCuerpo;
		this.adjunto=paramAdjunto;
	}

	/***************************************
	 * 
	 * setDatos
	 * 
	 * Crea un mensaje.
	 * 
	 * @author Ezentis
	 * @param List<String> Destinatarios de correo
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 *
	 *************************************/
	
	@Override
	public void setDatos(List<String> paramDestino, String paramAsunto, String paramCuerpo) {
		this.destino=paramDestino;
		this.asunto=paramAsunto;
		this.cuerpo=paramCuerpo;
		
	}


	/***************************************
	 * 
	 * setDatos
	 * 
	 * Crea un mensaje.
	 * 
	 * @author Ezentis
	 * @param List<String> Destinatarios de correo
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @param List<File> Fichero adjunto
	 *
	 *************************************/
	@Override
	public void setDatos(List<String> paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjuntos) {
		this.destino=paramDestino;
		this.asunto=paramAsunto;
		this.cuerpo=paramCuerpo;
		this.adjunto=paramAdjuntos;
		
	}

	/***************************************
	 * 
	 * conexionServidor
	 * 
	 * Realiza la conexión con el servidor de correo. Emplea los parámetros de conexión guardados en BDD
	 * 
	 * @author Ezentis
	 *
	 *************************************/

	public JavaMailSenderImpl conexionServidor() throws MailException{
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
	 * Realiza el envío de correo electrónico.
	 * 
	 * @author Ezentis
	 * @throws FileNotFoundException 
	 *
	 *************************************/
	
	public void envioCorreo() throws MailException, MessagingException, FileNotFoundException{
		if (this.adjunto!=null){
			envioCorreoAdjuntos();
			}
		else {
			envioCorreoSinAdjuntos();
			}
	}

	/***************************************
	 * 
	 * envioCorreoSinAdjuntos
	 * 
	 * Realiza el envío de correo electrónico asin adjuntos.
	 * 
	 * @author Ezentis
	 * @param List<String> Destinatarios de correo
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 *
	 *************************************/

	
	private void envioCorreoSinAdjuntos() throws MailException {

		
			JavaMailSenderImpl mailSender = conexionServidor();
			msg = new SimpleMailMessage();

			String[] arrDestino = this.destino.toArray(new String[this.destino.size()]);
			msg.setTo(arrDestino);
			
			if(this.conCopia!=null){
				String[] arrCopia=this.conCopia.toArray(new String[this.conCopia.size()]);
				msg.setCc(arrCopia);
			}	
			msg.setSubject(this.asunto);
			msg.setText(this.cuerpo);

			mailSender.send(msg);
		

	}
	
	

	/***************************************
	 * 
	 * envioCorreoAdjuntos
	 * 
	 * Realiza el envío de correo electrónico con fichero adjunto.
	 * 
	 * @author Ezentis
	 * @param List<String> Destinatarios de correo
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @param List<File> Adjunto
	 * @throws MessagingException 
	 * @throws Exception 
	 *
	 *************************************/

	
	private void envioCorreoAdjuntos() throws MailException, MessagingException, FileNotFoundException {
		JavaMailSenderImpl mailSender = conexionServidor();
		MimeMessage message = mailSender.createMimeMessage();

		
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			String[] arrDestino = this.destino.toArray(new String[this.destino.size()]);
			
			helper.setTo(arrDestino);
			
			
			if(this.conCopia!=null){
				String[] arrCopia=this.conCopia.toArray(new String[this.conCopia.size()]);
				helper.setCc(arrCopia);
			}
			
			
			helper.setSubject(this.asunto);
			helper.setText(this.cuerpo);
			
			for (File adj: this.adjunto){
				helper.addAttachment(adj.getName(), adj);
			} 
			

		mailSender.send(message);
	}


	


}
