package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.TipoEquipo;

/**
 * @author EZENTIS
 * 
 * Test del repositorio TipoEquipos
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
// @AutoConfigureTestDatabase(replace = Replace.NONE)
public class ITipoEquiposRepositoryTest {
    
    @Autowired
    private ITipoEquiposRepository repository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ITipoEquiposRepository#findByCodigoIgnoreCase(java.lang.String)}.
     */
    @Test
    public final void testFindValueForKey() {
        TipoEquipo tEquipo = this.repository.findByCodigoIgnoreCase("iAPrL");
        assertThat(tEquipo.getDescripcion()).isEqualTo("Inspecciones Área Prevención de Riesgos Laborales");
    }
    
}
