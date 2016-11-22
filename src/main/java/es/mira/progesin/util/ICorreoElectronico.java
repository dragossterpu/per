package es.mira.progesin.util;

public interface ICorreoElectronico { 
	
	public void envioCorreo (String destino, String asunto, String cuerpo);
	
	public void envioCorreo (String[] destino, String asunto, String cuerpo);
	
}
