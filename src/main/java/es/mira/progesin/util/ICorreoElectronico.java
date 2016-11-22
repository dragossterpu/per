package es.mira.progesin.util;

import java.io.File;

public interface ICorreoElectronico { 
	
	public void envioCorreo (String destino, String asunto, String cuerpo);
	public void envioCorreo (String[] destino, String asunto, String cuerpo);
	
	public void envioCorreoAdjuntos (String destino, String asunto, String cuerpo, File adjunto);
	public void envioCorreoAdjuntos (String[] destino, String asunto, String cuerpo, File adjunto);
	
}
