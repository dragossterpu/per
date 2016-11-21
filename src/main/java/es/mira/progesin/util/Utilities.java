package es.mira.progesin.util;

import java.util.Date;

public class Utilities {

	public static Date getCurrentDate() {
		return new Date();
	}

	// ************* Generating new password Progesin ********************//
	/**
	 * @author EZENTIS STAD
	 * @param getPassword
	 * @return
	 * @comment getPassword
	 */
	public static String getPassword() {
		String pswd = getPinLetters() + getPinNumber();
		return pswd;
	}

	public static String NUMBARS = "0123456789";

	public static String LETTERS = "ABCDEFGHIJKLMNOPRSTUVXYZWQ";

	public static String getPinNumber() {
		return getRandomNumber(NUMBARS, 4);
	}

	public static String getPinLetters() {
		return getRandomLetters(LETTERS, 4);
	}

	public static String getRandomNumber(String key, int length) {
		String pswd = "";

		for (int i = 0; i < length; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}

		return pswd;
	}

	public static String getRandomLetters(String key, int length) {
		String pswd = "";

		for (int i = 0; i < length; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}

		return pswd;
	}

	// ************* Generating new password Progesin END ********************//

	// ************* Descripcion message error Progesin ********************
	/**
	 * @param e
	 * @return
	 */
	public static String messageError(Exception e) {
		String message = null;
		if (e.getMessage().length() > 4000) {
			message = e.getMessage().substring(4000);
		}
		else {
			message = e.getMessage();
		}
		return message;
	}

	// ************* Descripcion message error Progesin END********************

	/**
	 * Obtener la extensión a partir del contentType
	 * 
	 * @author EZENTIS
	 * @param contentType
	 * @return extension
	 * @see es.mira.progesin.web.beans.ProvisionalSolicitudBean.cargaDocumento()
	 * @see es.mira.progesin.web.beans.SolicitudDocPreviaBean.cargaDocumento()
	 */
	public static String getExtensionTipoContenido(String tipoContenido) {
		String extension;
		switch (tipoContenido) {
		case "application/msword":
			extension = "DOC";
			break;
		case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
			extension = "DOCX";
			break;
		case "application/vnd.ms-powerpoint":
			extension = "PPT";
			break;
		case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
			extension = "PPTX";
			break;
		case "application/vnd.ms-excel":
			extension = "XLS";
			break;
		case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
			extension = "XLSX";
			break;
		case "image/jpeg":
			extension = "JPEG";
			break;
		case "image/png":
			extension = "PNG";
			break;
		case "image/bmp":
			extension = "BMP";
			break;
		case "application/x-mspublisher":
			extension = "PUB";
			break;
		// case "application/pdf":
		default:
			extension = "PDF";
			break;
		}
		return extension;
	}
}