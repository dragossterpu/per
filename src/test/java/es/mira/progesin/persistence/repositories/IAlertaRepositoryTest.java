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

import es.mira.progesin.persistence.entities.Alerta;

/**
 * @author EZENTIS
 * 
 * Test del repositorio IAlertaRepository
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IAlertaRepositoryTest {
    
    /**
     * Repositorio de alertas.
     */
    @Autowired
    private IAlertaRepository alertaRepository;
    
    /**
     * Test method for {@link es.mira.progesin.persistence.repositories.IAlertaRepository#findByFechaBajaIsNull()}.
     */
    @Test
    public final void testFindByFechaBajaIsNull() {
        List<Alerta> listAlertas = alertaRepository.findByFechaBajaIsNull();
        assertThat(listAlertas).hasSize(2);
    }
    
}
