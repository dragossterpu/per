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

/**
 * @author EZENTIS
 * 
 * Test del repositorio IRegActividadRepository.
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IRegActividadRepositoryTest {
    
    @Autowired
    IRegActividadRepository regActividadRepository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IRegActividadRepository#buscarPorNombreSeccion(java.lang.String)}.
     */
    @Test
    public final void testBuscarPorNombreSeccion() {
        List<String> listReg = regActividadRepository.buscarPorNombreSeccion("INSPECCION");
        assertThat(listReg).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IRegActividadRepository#buscarPorUsuarioRegistro(java.lang.String)}.
     */
    @Test
    public final void testBuscarPorUsuarioRegistro() {
        List<String> listReg = regActividadRepository.buscarPorUsuarioRegistro("rahshss");
        assertThat(listReg).hasSize(0);
    }
    
}
