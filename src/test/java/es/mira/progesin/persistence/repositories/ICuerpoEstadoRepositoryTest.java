package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * Test del repositorio Cuerpos estado.
 * 
 * @author EZENTIS
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")

public class ICuerpoEstadoRepositoryTest {
    /**
     * Repositorio de cuerpos de estado.
     */
    @Autowired
    private ICuerpoEstadoRepository repository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository#existsByNombreCortoIgnoreCaseAndIdNotIn(String, int)}.
     */
    @Test
    public final void existsByNombreCortoIgnoreCaseAndIdNotIn() {
        boolean existe = repository.existsByNombreCortoIgnoreCaseAndIdNotIn("PN", 2);
        assertThat(existe).isTrue();
    }
    
}
