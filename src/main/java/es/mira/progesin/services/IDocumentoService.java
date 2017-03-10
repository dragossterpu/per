package es.mira.progesin.services;

/**
 *
 * Intefaz para el servicio de documentos
 * 
 * @author Ezentis
 * 
 */

import java.io.IOException;
import java.sql.SQLException;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import es.mira.progesin.persistence.entities.Documento;

public interface IDocumentoService {
	void delete(Long id);

	void delete(Iterable<Documento> entities);

	void delete(Documento entity);

	void deleteAll();

	boolean exists(Long id);

	Iterable<Documento> findAll();

	Iterable<Documento> findAll(Iterable<Long> ids);

	Documento findOne(Long id);

	Iterable<Documento> save(Iterable<Documento> entities);

	Documento save(Documento entity);

	DefaultStreamedContent descargaDocumento(Documento entity) throws Exception;

	DefaultStreamedContent descargaDocumento(Long id) throws Exception;

	Documento cargaDocumento(UploadedFile file) throws SQLException, IOException;

	Documento crearDocumento(UploadedFile file) throws SQLException, IOException;

	Iterable<Documento> findByFechaBajaIsNull();

	boolean extensionCorrecta(UploadedFile file);

}
