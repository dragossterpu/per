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

import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentacionRepository;

/**
 * Test del repositorio de tipo de documentación.
 * 
 * @author EZENTIS
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class ITipoDocumentacionRepositoryTest {
    /**
     * Repositorio de tipo de documentación.
     */
    @Autowired
    private ITipoDocumentacionRepository repository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.gd.ITipoDocumentacionRepository#findByAmbito(es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum)}.
     */
    @Test
    public void testFindByAmbito() {
        List<TipoDocumentacion> listTipoDoc = repository.findByAmbito(AmbitoInspeccionEnum.PN);
        assertThat(listTipoDoc.size()).isEqualTo(11);
    }
    
}
