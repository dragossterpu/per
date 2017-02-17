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

import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.web.beans.ApplicationBean;
import lombok.Getter;

@Component("correoElectronico")
@Getter
public class CorreoElectronico implements ICorreoElectronico {

	@Autowired
	ApplicationBean applicationBean;

	@Autowired
	IRegistroActividadService registroActividad;

	@Override
	public void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo) throws CorreoException {
		envioCorreoSinAdjuntos(paramDestino, null, paramAsunto, paramCuerpo);
	}

	@Override
	public void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto)
			throws CorreoException {
		envioCorreoAdjuntos(paramDestino, null, paramAsunto, paramCuerpo, paramAdjunto);
	}

	@Override
	public void envioCorreo(String paramDestino, String paramCC, String paramAsunto, String paramCuerpo,
			List<File> paramAdjunto) throws CorreoException {
		List<String> lista = new ArrayList<String>();
		lista.add(paramDestino);

		List<String> cc = new ArrayList<String>();
		lista.add(paramCC);

		envioCorreoAdjuntos(lista, cc, paramAsunto, paramCuerpo, paramAdjunto);
	}

	@Override
	public void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto)
			throws CorreoException {
		List<String> lista = new ArrayList<String>();
		lista.add(paramDestino);

		envioCorreoAdjuntos(lista, null, paramAsunto, paramCuerpo, paramAdjunto);
	}

	@Override
	public void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo) throws CorreoException {
		List<String> lista = new ArrayList<String>();
		lista.add(paramDestino);

		envioCorreoSinAdjuntos(lista, null, paramAsunto, paramCuerpo);

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

	public JavaMailSenderImpl conexionServidor() {
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
	 * @throws CorreoException
	 *
	 *************************************/

	private void envioCorreoSinAdjuntos(List<String> destino, List<String> conCopia, String asunto, String cuerpo)
			throws CorreoException {
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
		}
		catch (Exception e) {
			registroActividad.altaRegActividadError("Envio correo", e);
			throw new CorreoException(e);

		}

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
	 * @throws CorreoException
	 *
	 *
	 *************************************/

	private void envioCorreoAdjuntos(List<String> destino, List<String> conCopia, String asunto, String cuerpo,
			List<File> adjunto) throws CorreoException {
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
		}
		catch (MessagingException e) {
			registroActividad.altaRegActividadError("Envio correo", e);
			throw new CorreoException(e);

		}

	}

}
