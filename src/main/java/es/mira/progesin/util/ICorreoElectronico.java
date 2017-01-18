package es.mira.progesin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

public interface ICorreoElectronico { 
	
	
	public void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto) throws MailException, FileNotFoundException, MessagingException;
	public void envioCorreo(String paramDestino, String paramCC, String paramAsunto, String paramCuerpo, List<File> paramAdjunto) throws MailException, FileNotFoundException, MessagingException;
	void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto) throws MailException, FileNotFoundException, MessagingException;
	void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo) throws MailException, FileNotFoundException, MessagingException;
	void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo);	
	
}
