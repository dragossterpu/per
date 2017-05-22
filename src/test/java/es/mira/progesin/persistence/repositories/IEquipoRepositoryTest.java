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
 * Test del repositorio Equipos
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
// @AutoConfigureTestDatabase(replace = Replace.NONE)
public class IEquipoRepositoryTest {
    
    @Autowired
    private IEquipoRepository repository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IEquipoRepository#existsByTipoEquipo(TipoEquipo)}.
     */
    @Test
    public final void existsByTipoEquipo() {
        TipoEquipo tEquipo = TipoEquipo.builder().id(1L).codigo("IAPRL")
                .descripcion("Inspecciones Área Prevención de Riesgos Laborales").build();
        boolean existeEquipo = this.repository.existsByTipoEquipo(tEquipo);
        assertThat(existeEquipo).isTrue();
    }
    
}
