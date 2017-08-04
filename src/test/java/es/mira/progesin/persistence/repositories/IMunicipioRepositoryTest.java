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

import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;

/**
 * 
 * Test del repositorio de inspecciones.
 * 
 * @author EZENTIS
 * 
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IMunicipioRepositoryTest {
    /**
     * Repositorio de municipios.
     */
    @Autowired
    private IMunicipioRepository repositorio;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IMunicipioRepository#existsByNameIgnoreCaseAndProvincia(java.lang.String, es.mira.progesin.persistence.entities.Provincia)}.
     */
    @Test
    public final void testExistsByNameIgnoreCaseAndProvincia() {
        Provincia prov = new Provincia();
        prov.setCodigo("45");
        prov.setCodigoMN("TO");
        boolean existe = repositorio.existsByNameIgnoreCaseAndProvincia("CABAÑAS DE LA SAGRA", prov);
        assertThat(existe).isTrue();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IMunicipioRepository#findByProvincia(es.mira.progesin.persistence.entities.Provincia)}.
     */
    @Test
    public final void testFindByProvincia() {
        Provincia prov = new Provincia();
        prov.setCodigo("45");
        prov.setCodigoMN("TO");
        List<Municipio> municipios = repositorio.findByProvincia(prov);
        assertThat(municipios).hasSize(2);
    }
    
}
