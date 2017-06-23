package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;

/**
 * 
 * Test del repositorio Solicitud Documentación Previa.
 *
 * @author EZENTIS
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")

public class ISolicitudDocumentacionPreviaRepositoryTest {
    /**
     * Repositorio de solicitud de documentación previa.
     */
    @Autowired
    private ISolicitudDocumentacionPreviaRepository solicitudDocPreviaRepository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository#findByFechaBajaIsNullAndFechaFinalizacionIsNullAndCorreoDestinatarioIgnoreCase(String)}.
     */
    @Test
    public final void findByFechaBajaIsNullAndFechaFinalizacionIsNullAndCorreoDestinatarioIgnoreCase() {
        SolicitudDocumentacionPrevia solicitud = solicitudDocPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndCorreoDestinatarioIgnoreCase(
                        "correoDestinatario1@ezentis.com");
        assertThat(solicitud).isNotNull();
        // Creada
        assertThat(solicitud.getId()).isEqualTo(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository#findByFechaBajaIsNullAndFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndCorreoDestinatarioIgnoreCase(String)}.
     */
    @Test
    public final void findByFechaBajaIsNullAndFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndCorreoDestinatarioIgnoreCase() {
        SolicitudDocumentacionPrevia solicitud = solicitudDocPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndCorreoDestinatarioIgnoreCase(
                        "correoDestinatario4@ezentis.com");
        assertThat(solicitud).isNotNull();
        // Enviada
        assertThat(solicitud.getId()).isEqualTo(4L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository#findByFechaBajaIsNullAndFechaFinalizacionIsNotNullAndInspeccionOrderByFechaFinalizacionDesc(Inspeccion)}.
     */
    @Test
    public final void findByFechaBajaIsNullAndFechaFinalizacionIsNotNullAndInspeccionOrderByFechaFinalizacionDesc() {
        Inspeccion inspeccion = Inspeccion.builder().id(7L).build();
        List<SolicitudDocumentacionPrevia> solicitudes = solicitudDocPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNotNullAndInspeccionOrderByFechaFinalizacionDesc(
                        inspeccion);
        assertThat(solicitudes).hasSize(1);
        // Finalizada
        assertThat(solicitudes.get(0).getId()).isEqualTo(7L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository#findByFechaBajaIsNullAndFechaFinalizacionIsNullAndInspeccion(Inspeccion)}.
     */
    @Test
    public final void findByFechaBajaIsNullAndFechaFinalizacionIsNullAndInspeccion() {
        Inspeccion inspeccion = Inspeccion.builder().id(2L).build();
        SolicitudDocumentacionPrevia solicitud = solicitudDocPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndInspeccion(inspeccion);
        assertThat(solicitud).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository#findByFechaBajaIsNullAndFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndFechaCumplimentacionIsNull()}.
     */
    @Test
    public final void findByFechaBajaIsNullAndFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndFechaCumplimentacionIsNull() {
        List<SolicitudDocumentacionPrevia> solicitudes = solicitudDocPreviaRepository
                .findByFechaBajaIsNullAndFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndFechaCumplimentacionIsNull();
        assertThat(solicitudes).hasSize(2);
        // Enviada
        assertThat(solicitudes.get(0).getId()).isEqualTo(4L);
        // No conforme
        assertThat(solicitudes.get(1).getId()).isEqualTo(6L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ISolicitudDocumentacionPreviaRepository#findById(Long)}.
     */
    @Test
    public final void findById() {
        SolicitudDocumentacionPrevia sol = solicitudDocPreviaRepository.findById(2L);
        assertThat(sol).isNotNull();
    }
}
