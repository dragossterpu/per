package es.mira.progesin.util;

import java.io.File;
import java.util.List;

public interface ICorreoElectronico {

	public void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto)
			throws CorreoException;

	public void envioCorreo(String paramDestino, String paramCC, String paramAsunto, String paramCuerpo,
			List<File> paramAdjunto) throws CorreoException;

	void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo, List<File> paramAdjunto)
			throws CorreoException;

	void envioCorreo(String paramDestino, String paramAsunto, String paramCuerpo) throws CorreoException;

	void envioCorreo(List<String> paramDestino, String paramAsunto, String paramCuerpo) throws CorreoException;;

}
