package es.mira.progesin.util;

/*********************************************
 * 
 * Se crea una nueva excepción personalizada para los errores de la clase CorreoElectronico
 * 
 * @author Ezentis
 *
 *********************************************/

public class CorreoException extends Exception {

	private static final long serialVersionUID = 1L;

	private Throwable e;

	public CorreoException(Throwable e) {
		this.e = e;
	};

	public String excError() {
		return "Se ha producido un error en el envío de correo electrónico ";
	}
}
