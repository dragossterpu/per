/**
 * 
 */
package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;

/**
 * Repositorio de operaciones de base de datos el repositorio de pasos de guía.
 * 
 * @author EZENTIS
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IGuiasPasosRepositoryTest {
    
    /**
     * Repositorio de pasos de guia.
     */
    @Autowired
    private IGuiasPasosRepository guiasPasosRepository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IGuiasPasosRepository#findByIdGuiaAndFechaBajaIsNullOrderByOrdenAsc(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testFindByIdGuiaAndFechaBajaIsNullOrderByOrdenAsc() {
        Guia guia = new Guia();
        guia.setId(3L);
        List<GuiaPasos> pasosGuia = guiasPasosRepository.findByIdGuiaAndFechaBajaIsNullOrderByOrdenAsc(guia);
        assertThat(pasosGuia).hasSize(3);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IGuiasPasosRepository#findByIdGuiaOrderByOrdenAsc(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testFindByIdGuiaOrderByOrdenAsc() {
        Guia guia = new Guia();
        guia.setId(1L);
        List<GuiaPasos> pasosGuia = guiasPasosRepository.findByIdGuiaOrderByOrdenAsc(guia);
        assertThat(pasosGuia).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IGuiasPasosRepository#findPasosElegidosGuiaPersonalizada(java.lang.Long)}.
     */
    @Test
    public final void testFindPasosElegidosGuiaPersonalizada() {
        List<GuiaPasos> pasosGuiaPersonalizada = guiasPasosRepository.findPasosElegidosGuiaPersonalizada(2L);
        assertThat(pasosGuiaPersonalizada).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IGuiasPasosRepository#findPasoExistenteEnGuiasPersonalizadas(java.lang.Long)}.
     */
    @Test
    public final void testFindPasoExistenteEnGuiasPersonalizadas() {
        GuiaPasos guiaPaso = guiasPasosRepository.findPasoExistenteEnGuiasPersonalizadas(6L);
        assertThat(guiaPaso).isNotNull();
        assertThat(guiaPaso.getIdGuia().getId()).isEqualTo(3);
    }
    
}
