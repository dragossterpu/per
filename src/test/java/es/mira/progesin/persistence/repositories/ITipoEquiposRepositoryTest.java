package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.TipoEquipo;

/**
 * Test del repositorio TipoEquipos.
 * 
 * @author EZENTIS
 * 
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")

public class ITipoEquiposRepositoryTest {
    /**
     * Repositorio de tipos de equipo.
     */
    @Autowired
    private ITipoEquiposRepository repository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ITipoEquiposRepository#findByCodigoIgnoreCase(java.lang.String)}.
     */
    @Test
    public final void findByCodigoIgnoreCase() {
        TipoEquipo tipoEquipo = this.repository.findByCodigoIgnoreCase("iAPrL");
        assertThat(tipoEquipo.getDescripcion()).isEqualTo("Inspecciones Área Prevención de Riesgos Laborales");
    }
    
    /**
     * Test method for {@link es.mira.progesin.persistence.repositories.ITipoEquiposRepository#findAllByOrderByIdAsc()}.
     */
    @Test
    public final void findAllByOrderByIdAsc() {
        List<TipoEquipo> tiposEquipo = this.repository.findAllByOrderByIdAsc();
        assertThat(tiposEquipo).hasSize(8);
    }
}
