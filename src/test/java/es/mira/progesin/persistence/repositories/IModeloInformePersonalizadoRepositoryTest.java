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

import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;

/**
 * Repositorio de operaciones de base de datos para la entidad Informe personalizado.
 * 
 * @author EZENTIS
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IModeloInformePersonalizadoRepositoryTest {
    /**
     * Repositorio de informes personalizados.
     */
    @Autowired
    private IModeloInformePersonalizadoRepository modeloInformePersonalizadoRepository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IModeloInformePersonalizadoRepository#findById(java.lang.Long)}.
     */
    @Test
    public final void testFindById() {
        ModeloInformePersonalizado modelo = modeloInformePersonalizadoRepository.findById(1L);
        assertThat(modelo).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IModeloInformePersonalizadoRepository#existsByModeloInforme(es.mira.progesin.persistence.entities.informes.ModeloInforme)}.
     */
    @Test
    public final void testExistsByModeloInforme() {
        ModeloInforme modelo = new ModeloInforme();
        modelo.setId(1L);
        boolean existe = modeloInformePersonalizadoRepository.existsByModeloInforme(modelo);
        assertThat(existe).isTrue();
    }
    
}
