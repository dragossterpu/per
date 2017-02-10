package es.mira.progesin.tasks;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.web.beans.ApplicationBean;

@Service("tareasService")

public class TareasService implements ITareasService {

	@Autowired
	ICuestionarioEnvioService cuestionarioEnvioService;

	@Autowired
	ISolicitudDocumentacionService solicitudDocumentacionService;

	@Autowired
	ICuestionarioEnvioRepository repositorio;

	@Autowired
	ICorreoElectronico correoElectronico;

	@Autowired
	IRegistroActividadService registroActividad;

	@Autowired
	ApplicationBean applicationBean;

	Map<String, String> parametrosTareas;

	Properties tareasProperties = new Properties();

	Date hoy;

	@PostConstruct
	private void init() {
		parametrosTareas = applicationBean.getMapaParametros().get("tareas");

		Iterator<Entry<String, String>> it = parametrosTareas.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, String> param = it.next();
			tareasProperties.put(param.getKey(), param.getValue());
		}

		hoy = new Date();

	}

	@Override
	@Scheduled(cron = "0 0 8 * * MON-FRI")

	public void recordatorioEnvioCuestionario() {
		try {
			List<CuestionarioEnvio> lista = cuestionarioEnvioService
					.findNoCumplimentados();

			for (int i = 0; i < lista.size(); i++) {
				CuestionarioEnvio cuestionario = lista.get(i);
				long milis = cuestionario.getFechaLimiteCuestionario().getTime() - hoy.getTime();
				int dias = (int) (milis / 86400000);

				if (dias < Integer.parseInt(tareasProperties.getProperty("plazoDiasCuestionario"))) {
					StringBuilder cuerpo = new StringBuilder().append("Faltan ").append(dias)
							.append(" dia/s para la fecha límite de envío del cuestionario de la inspección ")
							.append(cuestionario.getInspeccion().getNumero())
							.append("\n\nEste es un recordatorio automático.\nNo responda a este correo.");

					correoElectronico.envioCorreo(cuestionario.getCorreoEnvio(), "Recordatorio envío cuestionario",
							cuerpo.toString());
				}
			}
		}
		catch (Exception e) {
			registroActividad.altaRegActividadError("Batch envio recordatorio", e);
		}
	}

	@Override
	@Scheduled(cron = "0 0 8 * * MON-FRI")

	public void recordatorioEnvioDocumentacion() {
		try {
			List<SolicitudDocumentacionPrevia> lista = solicitudDocumentacionService
					.findEnviadasNoCumplimentadas();

			for (int i = 0; i < lista.size(); i++) {

				SolicitudDocumentacionPrevia solicitud = lista.get(i);
				long milis = solicitud.getFechaLimiteCumplimentar().getTime() - hoy.getTime();
				int dias = (int) (milis / 86400000);
				if (dias < Integer.parseInt(tareasProperties.getProperty("plazoDiasDocumentacion"))) {

					StringBuilder cuerpo = new StringBuilder().append("Se envía este correo como redordatorio\n")
							.append("Faltan ").append(dias)
							.append(" dia/s para la fecha límite de envío de la documentación para la inspección número ")
							.append(solicitud.getInspeccion().getNumero())
							.append("\n\nEste es un recordatorio automático.\nNo responda a este correo.");

					correoElectronico.envioCorreo(solicitud.getCorreoDestinatario(),
							"Recordatorio envío de documentación previa", cuerpo.toString());
				}
			}
		}
		catch (Exception e) {
			registroActividad.altaRegActividadError("Batch envio documentación previa", e);
		}
	}

}
