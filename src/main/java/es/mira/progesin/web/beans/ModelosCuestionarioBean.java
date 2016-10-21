package es.mira.progesin.web.beans;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

import org.hibernate.SessionFactory;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.ModeloCuestionario;
import es.mira.progesin.services.IModeloCuestionarioService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("modelosCuestionarioBean")
// @ApplicationScoped
@SessionScoped
public class ModelosCuestionarioBean {

	List<ModeloCuestionario> listadoCuestionarios;

	private StreamedContent file;

	@Autowired
	IModeloCuestionarioService modeloCuestionarioService;

	@Autowired
	private SessionFactory sessionFactory;

	@PostConstruct
	public void init() {
		// insertar();
		listadoCuestionarios = (List<ModeloCuestionario>) modeloCuestionarioService.findAll();
	}

	public void descargarFichero(ModeloCuestionario cuestionario) {
		try {
			InputStream stream = new ByteArrayInputStream(cuestionario.getFichero());
			String contentType = "application/msword";
			if ("pdf".equals(cuestionario.getExtension())) {
				contentType = "application/pdf";
			}
			else if (cuestionario.getExtension().startsWith("xls")) {
				contentType = "application/x-msexcel";
			}
			file = new DefaultStreamedContent(stream, contentType, cuestionario.getNombreFichero());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertar() {
		try {

			File directory = new File("E:\\SES\\Documentación facilitada por IPSS\\cuestionarios");

			File[] listaFicheros = directory.listFiles();
			for (int i = 0; i < listaFicheros.length; i++) {
				File fichero = listaFicheros[i];

				// Obtiene el contenido del fichero en []bytes
				byte[] data = Files.readAllBytes(fichero.toPath());
				ModeloCuestionario cuestionario = new ModeloCuestionario();
				cuestionario.setCodigo("codigo");
				cuestionario.setDescripcion(
						fichero.getName().substring(0, fichero.getName().lastIndexOf('.')).toUpperCase());
				cuestionario.setNombreFichero(fichero.getName());
				System.out.println(fichero.getName().substring(fichero.getName().lastIndexOf('.') + 1));
				cuestionario.setExtension(
						fichero.getName().substring(fichero.getName().lastIndexOf('.') + 1).toLowerCase());
				// Blob fichero = Hibernate.getLobCreator(sessionFactory.openSession()).createBlob(data);
				cuestionario.setFichero(data);
				modeloCuestionarioService.save(cuestionario);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
