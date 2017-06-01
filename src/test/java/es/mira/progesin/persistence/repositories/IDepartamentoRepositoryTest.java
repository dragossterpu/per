package es.mira.progesin.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.Departamento;

/**
 * Test para el repositorio de Departamento.
 * 
 * @author EZENTIS
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IDepartamentoRepositoryTest {
    
    /**
     * Repositorio sobre el que se harán las pruebas.
     */
    @Autowired
    private IDepartamentoRepository repositorio;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IDepartamentoRepository#findByFechaBajaIsNull()}.
     */
    @Test
    public final void findByFechaBajaIsNull() {
        List<Departamento> departamentos = repositorio.findByFechaBajaIsNull();
        assertThat(departamentos).hasSize(11);
        
    }
}
