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

import es.mira.progesin.persistence.entities.gd.Documento;

/**
 * @author EZENTIS
 * 
 * Test del repositorio IDocumentoRepository
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IDocumentoRepositoryTest {
    
    /**
     * Repositorio de documentos.
     */
    @Autowired
    private IDocumentoRepository documentoRepository;
    
    /**
     * Test method for {@link es.mira.progesin.persistence.repositories.IDocumentoRepository#findByFechaBajaIsNull()}.
     */
    @Test
    public void testFindByFechaBajaIsNull() {
        List<Documento> listDoc = documentoRepository.findByFechaBajaIsNull();
        assertThat(listDoc).hasSize(3);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IDocumentoRepository#findByFechaBajaIsNotNull()}.
     */
    @Test
    public void testFindByFechaBajaIsNotNull() {
        List<Documento> listDoc = documentoRepository.findByFechaBajaIsNotNull();
        assertThat(listDoc).hasSize(1);
    }
    
    /**
     * Test method for {@link es.mira.progesin.persistence.repositories.IDocumentoRepository#findById(Long)}.
     */
    @Test
    public void testFindById() {
        Documento doc = documentoRepository.findById(1L);
        assertThat(doc.getId()).isEqualTo(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IDocumentoRepository#perteneceACuestionario(Long)}.
     */
    @Test
    public void testPerteneceACuestionario() {
        Long resp = documentoRepository.perteneceACuestionario(1L);
        assertThat(resp).isEqualTo(1L);
    }
    
    /**
     * Test method for {@link es.mira.progesin.persistence.repositories.IDocumentoRepository#perteneceASolicitud(Long)}.
     */
    @Test
    public void testPerteneceASolicitud() {
        Long resp = documentoRepository.perteneceASolicitud(4L);
        assertThat(resp).isEqualTo(6L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IDocumentoRepository#deleteByFechaBajaIsNotNull()}.
     */
    @Test
    public void testDeleteByFechaBajaIsNotNull() {
        documentoRepository.deleteByFechaBajaIsNotNull();
        List<Documento> listDoc = documentoRepository.findByFechaBajaIsNotNull();
        assertThat(listDoc).hasSize(0);
    }
    
}
