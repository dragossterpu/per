package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.CuerpoEstado;

/**
 * @author EZENTIS
 * 
 * Test del repositorio Cuerpos estado
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")

public class ICuerpoEstadoRepositoryTest {
    
    @Autowired
    private ICuerpoEstadoRepository repository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository#findByFechaBajaIsNullOrderByIdAsc()}.
     */
    @Test
    public final void findByFechaBajaIsNullOrderByIdAsc() {
        List<CuerpoEstado> cuerpos = repository.findByFechaBajaIsNullOrderByIdAsc();
        assertThat(cuerpos).hasSize(6);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository#existsByNombreCortoIgnoreCaseAndIdNotIn(String, int)}.
     */
    @Test
    public final void existsByNombreCortoIgnoreCaseAndIdNotIn() {
        boolean existe = repository.existsByNombreCortoIgnoreCaseAndIdNotIn("PN", 2);
        assertThat(existe).isTrue();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository#findAllByOrderByIdAsc()}.
     */
    @Test
    public final void findAllByOrderByIdAsc() {
        List<CuerpoEstado> cuerpos = repository.findByFechaBajaIsNullOrderByIdAsc();
        assertThat(cuerpos).hasSize(6);
    }
    
}
