package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;

public interface ISolicitudDocumentacionPreviaRepository extends CrudRepository<SolicitudDocumentacionPrevia, Integer> {

	SolicitudDocumentacionPrevia findByCorreoDestiantario(String correo);

	List<SolicitudDocumentacionPrevia> findByFechaFinalizacionIsNotNullAndInspeccionOrderByFechaFinalizacionDesc(
			Inspeccion inspeccion);

}
