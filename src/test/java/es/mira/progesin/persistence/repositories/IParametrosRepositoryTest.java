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

import es.mira.progesin.persistence.entities.Parametro;

/**
 * Test del repositorio Parametros.
 * 
 * @author EZENTIS
 * 
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")

public class IParametrosRepositoryTest {
    /**
     * Repositorio de parámetros.
     */
    @Autowired
    private IParametrosRepository repository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IParametrosRepository#findValueForKey(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testFindValueForKey() {
        String parametro = this.repository.findValueForKey("BMP", "extensiones");
        assertThat(parametro).isEqualTo("image/bmp");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IParametrosRepository#findValuesForSeccion(java.lang.String)}.
     */
    @Test
    public final void testFindValuesForSeccion() {
        List<String> valList = this.repository.findValuesForSeccion("extensiones");
        assertThat(valList.size()).isEqualTo(11);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IParametrosRepository#findParamByParamSeccion(java.lang.String)}.
     */
    @Test
    public final void testFindParamByParamSeccion() {
        List<Parametro> paramList = this.repository.findParamByParamSeccion("extensiones");
        assertThat(paramList.size()).isEqualTo(11);
    }
    
    /**
     * Test method for {@link es.mira.progesin.persistence.repositories.IParametrosRepository#findSecciones()}.
     */
    @Test
    public final void testFindSecciones() {
        List<String> seccList = this.repository.findSecciones();
        assertThat(seccList.size()).isEqualTo(7);
    }
    
}
