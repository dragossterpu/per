package es.mira.progesin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

public interface ICorreoElectronico { 
	
	public void envioCorreo () throws  MailException, MessagingException, FileNotFoundException;
	public void setDatos(List<String> paramDestino,  List<String> paramCC, String paramAsunto,String paramCuerpo, List<File> paramAdjunto);
	public void setDatos(String paramDestino,  String paramCC, String paramAsunto,String paramCuerpo, List<File> paramAdjunto);
	public void setDatos(List<String> paramDestino, List<String> paramCC, String paramAsunto,String paramCuerpo);
	public void setDatos(List<String> paramDestino,  String paramAsunto,String paramCuerpo);
	public void setDatos(List<String> paramDestino,  String paramAsunto,String paramCuerpo, List<File> paramAdjunto);
	public void setDatos(String paramDestino,  String paramAsunto,String paramCuerpo, List<File> paramAdjunto);
	public void setDatos(String paramDestino,  String paramAsunto,String paramCuerpo);
	
}
