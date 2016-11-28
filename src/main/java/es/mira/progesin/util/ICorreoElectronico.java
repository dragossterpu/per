package es.mira.progesin.util;

import java.io.File;

public interface ICorreoElectronico { 
	
	public void envioCorreo (String destino, String asunto, String cuerpo) throws Exception;
	public void envioCorreo (String[] destino, String asunto, String cuerpo) throws Exception;
	
	public void envioCorreoAdjuntos (String destino, String asunto, String cuerpo, File adjunto) throws Exception;
	public void envioCorreoAdjuntos (String[] destino, String asunto, String cuerpo, File adjunto) throws Exception;
	
}
