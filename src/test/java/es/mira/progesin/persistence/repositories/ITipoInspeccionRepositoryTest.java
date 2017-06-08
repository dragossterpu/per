package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test del repositorio de tipo de inspección.
 * 
 * @author EZENTIS
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class ITipoInspeccionRepositoryTest {
    
    /**
     * Repositorio de tipos de inspección.
     */
    @Autowired
    ITipoInspeccionRepository repository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ITipoInspeccionRepository#existsByCodigoIgnoreCase(java.lang.String)}.
     */
    @Test
    public final void existsByCodigoIgnoreCase() {
        boolean respuesta = this.repository.existsByCodigoIgnoreCase("I.G.P.");
        assertThat(respuesta).isTrue();
        
    }
}
