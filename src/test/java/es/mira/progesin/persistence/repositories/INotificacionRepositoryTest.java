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

import es.mira.progesin.persistence.entities.Notificacion;

/**
 * @author EZENTIS
 * 
 * Test del repositorio INotificacionRepository
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class INotificacionRepositoryTest {
    
    /**
     * Repositorio de alertas.
     */
    @Autowired
    private INotificacionRepository notificacionRepository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.INotificacionRepository#findByFechaBajaIsNull()}.
     */
    @Test
    public final void testFindByFechaBajaIsNull() {
        List<Notificacion> listNotificaciones = notificacionRepository.findByFechaBajaIsNull();
        assertThat(listNotificaciones).hasSize(1);
    }
    
}
