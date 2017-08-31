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

import es.mira.progesin.persistence.entities.informes.ModeloInforme;

/**
 * Repositorio de operaciones de base de datos para la entidad Modelo de Informe.
 * 
 * @author EZENTIS
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IModeloInformeRepositoryTest {
    
    /**
     * Repositorio de modelos informes.
     */
    @Autowired
    private IModeloInformeRepository modeloInformeRepository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IModeloInformeRepository#findDistinctById(java.lang.Long)}.
     */
    @Test
    public final void testFindDistinctById() {
        ModeloInforme modelo = modeloInformeRepository.findDistinctById(1L);
        assertThat(modelo).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IModeloInformeRepository#findAllByFechaBajaIsNull()}.
     */
    @Test
    public final void testFindAllByFechaBajaIsNull() {
        List<ModeloInforme> listaModelos = modeloInformeRepository.findAllByFechaBajaIsNull();
        assertThat(listaModelos).hasSize(1);
    }
    
}
