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

import es.mira.progesin.persistence.entities.Guia;

/**
 * Repositorio de operaciones de base de datos para la entidad Guía.
 * 
 * @author EZENTIS
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IGuiaPersonalizadaRepositoryTest {
    
    /**
     * Mock de prueba del repositorio de la guía personalizada.
     */
    @Autowired
    private IGuiaPersonalizadaRepository guiaPersonalizadaRepository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IGuiaPersonalizadaRepository#existsByGuia(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testExistsByGuia() {
        Guia guia = new Guia();
        guia.setId(2L);
        boolean existePersonalizada = guiaPersonalizadaRepository.existsByGuia(guia);
        assertThat(existePersonalizada).isTrue();
        
    }
    
}
