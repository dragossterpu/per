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
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;

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
public class IInspeccionesRepositoryTest {
    
    /**
     * Repositorio de inspecciones.
     */
    @Autowired
    private IInspeccionesRepository repositorio;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInspeccionesRepository#buscarPorNombreUnidadONumero(String)}.
     */
    @Test
    public final void buscarPorNombreUnidadONumero() {
        List<Inspeccion> lista = repositorio.buscarPorNombreUnidadONumero("Lérida");
        assertThat(lista).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInspeccionesRepository#buscarNoFinalizadaPorNombreUnidadONumero(String)}.
     */
    @Test
    public final void buscarNoFinalizadaPorNombreUnidadONumero() {
        List<Inspeccion> lista = repositorio.buscarNoFinalizadaPorNombreUnidadONumero("Lérida");
        assertThat(lista).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInspeccionesRepository#buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(String, String)}.
     */
    @Test
    public final void buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo() {
        List<Inspeccion> lista = repositorio.buscarNoFinalizadaPorNombreUnidadONumero("1");
        assertThat(lista).hasSize(0);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInspeccionesRepository#cargaInspeccionesDocumento(Long)}.
     */
    @Test
    public final void cargaInspeccionesDocumento() {
        List<Inspeccion> lista = repositorio.cargaInspeccionesDocumento(2L);
        assertThat(lista).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInspeccionesRepository#cargaInspeccionesGuia(Long)}.
     */
    @Test
    public final void cargaInspeccionesGuia() {
        List<Inspeccion> lista = repositorio.cargaInspeccionesGuia(1L);
        assertThat(lista).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInspeccionesRepository#cargaInspeccionesAsociadas(Long)}.
     */
    @Test
    public final void cargaInspeccionesAsociadas() {
        List<Inspeccion> lista = repositorio.cargaInspeccionesAsociadas(1L);
        assertThat(lista).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInspeccionesRepository#existsByTipoInspeccion(TipoInspeccion)}.
     */
    
    @Test
    public final void existsByTipoInspeccion() {
        TipoInspeccion tipo = TipoInspeccion.builder().codigo("I.G.P.").build();
        boolean existe = repositorio.existsByTipoInspeccion(tipo);
        assertThat(existe).isTrue();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInspeccionesRepository#findByEstadoInspeccion(EstadoInspeccionEnum)}.
     */
    @Test
    public final void findByEstadoInspeccion() {
        List<Inspeccion> lista = repositorio.findByEstadoInspeccion(EstadoInspeccionEnum.C_PEND_SOLICITAR_DOC_PREVIA);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInspeccionesRepository#findInspeccionesByUsuario(String)}.
     */
    @Test
    public final void findInspeccionesByUsuario() {
        List<Inspeccion> lista = repositorio.findInspeccionesByUsuario("ajangulo");
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IInspeccionesRepository#buscarPorNombreUnidadONumeroYUsuario(String, User)}.
     */
    @Test
    public final void buscarPorNombreUnidadONumeroYUsuario() {
        User usuario = User.builder().username("ajangulo").build();
        List<Inspeccion> lista = repositorio.buscarPorNombreUnidadONumeroYUsuario("Lérida", usuario);
        assertThat(lista).hasSize(2);
    }
}
