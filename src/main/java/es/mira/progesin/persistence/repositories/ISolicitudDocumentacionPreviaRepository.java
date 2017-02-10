package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;

public interface ISolicitudDocumentacionPreviaRepository extends CrudRepository<SolicitudDocumentacionPrevia, Long> {

	SolicitudDocumentacionPrevia findByFechaFinalizacionIsNullAndCorreoDestinatarioIgnoreCase(String correo);

	SolicitudDocumentacionPrevia findByFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndCorreoDestinatarioIgnoreCase(
			String correo);

	List<SolicitudDocumentacionPrevia> findByFechaBajaIsNullAndFechaFinalizacionIsNotNullAndInspeccionOrderByFechaFinalizacionDesc(
			Inspeccion inspeccion);

	List<SolicitudDocumentacionPrevia> findByFechaFinalizacionIsNullAndInspeccion(Inspeccion inspeccion);

	List<SolicitudDocumentacionPrevia> findByFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndFechaBajaIsNullAndFechaCumplimentacionIsNull();

}
