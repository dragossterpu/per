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

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.TipoInspeccion;

/**
 * Repositorio de operaciones de base de datos para la entidad Guía.
 * 
 * @author EZENTIS
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IGuiasRepositoryTest {
    
    /**
     * Repositorio de guías.
     */
    @Autowired
    private IGuiasRepository guiasRepository;
    
    /**
     * Test method for {@link es.mira.progesin.persistence.repositories.IGuiasRepository#findAll()}.
     */
    @Test
    public final void testFindAll() {
        List<Guia> guias = guiasRepository.findAll();
        assertThat(guias).hasSize(3);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IGuiasRepository#existsByTipoInspeccion(es.mira.progesin.persistence.entities.TipoInspeccion)}.
     */
    @Test
    public final void testExistsByTipoInspeccion() {
        TipoInspeccion tipo = new TipoInspeccion();
        tipo.setCodigo("I.T_PRL");
        boolean existe = guiasRepository.existsByTipoInspeccion(tipo);
        assertThat(existe).isTrue();
    }
    
}
