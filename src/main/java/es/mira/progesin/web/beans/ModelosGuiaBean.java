package es.mira.progesin.web.beans;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.ModeloGuia;
import es.mira.progesin.services.IModeloGuiaService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("modelosGuiaBean")
@SessionScoped
public class ModelosGuiaBean {

	List<ModeloGuia> listadoGuias;

	Map<String, String> mapaDatosGuia;

	private StreamedContent file;

	@Autowired
	IModeloGuiaService modeloGuiaService;

	@Autowired
	private SessionFactory sessionFactory;

	@PostConstruct
	public void init() {
	//insertar();
		listadoGuias = (List<ModeloGuia>) modeloGuiaService.findAll();
	}

	public void descargarFichero(ModeloGuia guia) {
		try {
			InputStream stream = guia.getFichero().getBinaryStream();
			String contentType = "application/msword";
			if ("pdf".equals(guia.getExtension())) {
				contentType = "application/pdf";
			}
			else if (guia.getExtension().startsWith("xls")) {
				contentType = "application/x-msexcel";
			}
			file = new DefaultStreamedContent(stream, contentType, guia.getNombreFichero());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String editarGuia(ModeloGuia modeloGuia) {
		String page = null;

		return page;
	}

	public void insertar() {
		try {

			File directory = new File("E:\\SES\\Documentación facilitada por IPSS\\guias");

			File[] listaFicheros = directory.listFiles();
			for (int i = 0; i < listaFicheros.length; i++) {
				File fichero = listaFicheros[i];

				// Obtiene el contenido del fichero en []bytes
				byte[] data = Files.readAllBytes(fichero.toPath());
				ModeloGuia guia = new ModeloGuia();
				guia.setCodigo("codigo");
				guia.setDescripcion(
						fichero.getName().substring(0, fichero.getName().lastIndexOf('.')).toUpperCase());
				guia.setNombreFichero(fichero.getName());
				System.out.println(fichero.getName().substring(fichero.getName().lastIndexOf('.') + 1));
				guia.setExtension(
						fichero.getName().substring(fichero.getName().lastIndexOf('.') + 1).toLowerCase());
				Blob fichero2 = Hibernate.getLobCreator(sessionFactory.openSession()).createBlob(data);
				//guia.setFichero(data);
				guia.setFichero(fichero2);
				modeloGuiaService.save(guia);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
