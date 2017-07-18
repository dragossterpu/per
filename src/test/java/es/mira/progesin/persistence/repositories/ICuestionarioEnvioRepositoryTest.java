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

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;

/**
 * Repositorio de operaciones de base de datos para la entidad CuestionarioEnvio.
 * 
 * @author EZENTIS
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class ICuestionarioEnvioRepositoryTest {
    /**
     * Repositorio de Cuestionarios enviados.
     */
    @Autowired
    private ICuestionarioEnvioRepository cuestionarioEnvioRepository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository#findByCorreoEnvioAndFechaFinalizacionIsNullAndFechaAnulacionIsNull(java.lang.String)}.
     */
    @Test
    public final void testFindByCorreoEnvioAndFechaFinalizacionIsNullAndFechaAnulacionIsNull() {
        CuestionarioEnvio cuestionarioEnviado = cuestionarioEnvioRepository
                .findByCorreoEnvioAndFechaFinalizacionIsNullAndFechaAnulacionIsNull("test1@ezentis.com");
        assertThat(cuestionarioEnviado).isNotNull();
        assertThat(cuestionarioEnviado.getId()).isEqualTo(1L);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository#findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndInspeccion(es.mira.progesin.persistence.entities.Inspeccion)}.
     */
    @Test
    public final void testFindByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndInspeccion() {
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        CuestionarioEnvio cuestionarioEnviado = cuestionarioEnvioRepository
                .findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndInspeccion(inspeccion);
        assertThat(cuestionarioEnviado).isNotNull();
        assertThat(cuestionarioEnviado.getId()).isEqualTo(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository#existsByCuestionarioPersonalizado(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     */
    @Test
    public final void testExistsByCuestionarioPersonalizado() {
        CuestionarioPersonalizado cuestionarioPersonalizado = new CuestionarioPersonalizado();
        cuestionarioPersonalizado.setId(1L);
        boolean existe = cuestionarioEnvioRepository.existsByCuestionarioPersonalizado(cuestionarioPersonalizado);
        assertThat(existe).isTrue();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository#findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndFechaCumplimentacionIsNull()}.
     */
    @Test
    public final void testFindByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndFechaCumplimentacionIsNull() {
        List<CuestionarioEnvio> cuestionariosEnviados = cuestionarioEnvioRepository
                .findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndFechaCumplimentacionIsNull();
        assertThat(cuestionariosEnviados).hasSize(1);
    }
    
}
