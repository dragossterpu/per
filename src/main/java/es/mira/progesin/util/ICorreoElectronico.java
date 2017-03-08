package es.mira.progesin.util;

import java.io.File;
import java.util.List;

/*********************************
 * 
 * Clase para el envío de correos electrónicos
 * 
 * @author Ezentis
 * 
 ********************************/
public interface ICorreoElectronico {

	/**************************************************************
	 * 
	 * Envío de correos electrónicos a una lista de destinatarios pasada como parámetros. El asunto, cuerpo del mensaje
	 * y los documentos adjuntos se reciben como parámetros
	 * 
	 * @param List<String> Lista de destinatarios
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @param List<File> Lista de ficheros adjuntos
	 * @exception CorreoException
	 * 
	 ************************************************************/

	public void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto)
			throws CorreoException;

	/**************************************************************
	 * 
	 * Envío de correos electrónico. El destinatario, destinatario en copia, asunto, cuerpo del mensaje y los documentos
	 * adjuntos se reciben como parámetros.
	 * 
	 * @param String Destinatario
	 * @param String Destinatario en copia
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @param List<File> Lista de ficheros adjuntos
	 * @exception CorreoException
	 * 
	 ************************************************************/

	public void envioCorreo(String paramDestino, String paramCC, String paramAsunto, String paramCuerpo,
			List<File> paramAdjunto) throws CorreoException;

	/**************************************************************
	 * 
	 * Envío de correos electrónico. El destinatario, asunto, cuerpo del mensaje y los documentos adjuntos se reciben
	 * como parámetros
	 * 
	 * @param String Destinatario
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @param List<File> Lista de ficheros adjuntos
	 * @exception CorreoException
	 * 
	 ************************************************************/
	void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto)
			throws CorreoException;

	/**************************************************************
	 * 
	 * Envío de correos electrónico sin adjuntos. El destinatario, asunto y cuerpo del mensaje se reciben como
	 * parámetros
	 * 
	 * @param String Destinatario
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @exception CorreoException
	 * 
	 ************************************************************/

	void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo) throws CorreoException;

	/**************************************************************
	 * 
	 * Envío de correos electrónico sin adjuntos. La lista de destinatarios, asunto y cuerpo del mensaje se reciben como
	 * parámetros
	 * 
	 * @param List<String> Lista de destinatarios
	 * @param String Asunto del correo
	 * @param String Cuerpo del correo
	 * @exception CorreoException
	 * 
	 ************************************************************/

	void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo) throws CorreoException;;

}
