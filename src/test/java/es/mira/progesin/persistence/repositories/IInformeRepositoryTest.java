/**
 * 
 */
package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;

/**
 * Repositorio de operaciones de base de datos para la entidad Informe.
 * 
 * @author EZENTIS
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IInformeRepositoryTest {
    
    /**
     * Repositorio de informes.
     */
    @Autowired
    private IInformeRepository informeRepository;
    
    /**
     * Test method for {@link es.mira.progesin.persistence.repositories.IInformeRepository#findById(java.lang.Long)}.
     */
    @Test
    public final void testFindById() {
        Informe informe = informeRepository.findById(1L);
        assertThat(informe).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInformeRepository#existsByModeloPersonalizado(es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado)}.
     */
    @Test
    public final void testExistsByModeloPersonalizado() {
        ModeloInformePersonalizado modelo = new ModeloInformePersonalizado();
        modelo.setId(1L);
        boolean existe = informeRepository.existsByModeloPersonalizado(modelo);
        assertThat(existe).isTrue();
    }
    
}
