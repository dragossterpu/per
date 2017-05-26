package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.repositories.IEquipoRepository;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;
import es.mira.progesin.web.beans.EquipoBusqueda;

/**
 * Test del servicio Equipo.
 * 
 * @author EZENTIS
 */
@RunWith(MockitoJUnitRunner.class)
public class EquipoServiceTest {
    
    @Mock
    private IMiembrosRepository miembrosRepository;
    
    @Mock
    private IEquipoRepository equipoRepository;
    
    @InjectMocks
    private IEquipoService equipoService = new EquipoService();
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(EquipoService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        EquipoService target = new EquipoService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#findAll()}.
     */
    @Test
    public void findAll() {
        equipoService.findAll();
        verify(equipoRepository, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#save(Equipo)}.
     */
    @Test
    public void save_Equipo() {
        Equipo equipo = mock(Equipo.class);
        equipoService.save(equipo);
        verify(equipoRepository, times(1)).save(equipo);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.EquipoService#buscarEquipoCriteria(int, int, String, org.primefaces.model.SortOrder, EquipoBusqueda)}.
     */
    @Ignore
    @Test
    public void buscarEquipoCriteria() {
        // TODO test del buscador de equipos
        // List<Equipo> actual = equipoService.buscarEquipoCriteria(equipoBusqueda);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#existsByTipoEquipo(TipoEquipo)}.
     */
    @Test
    public void existsByTipoEquipo() {
        TipoEquipo tEquipo = mock(TipoEquipo.class);
        equipoService.existsByTipoEquipo(tEquipo);
        verify(equipoRepository, times(1)).existsByTipoEquipo(tEquipo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#findByFechaBajaIsNull()}.
     */
    @Test
    public void findByFechaBajaIsNull() {
        equipoService.findByFechaBajaIsNull();
        verify(equipoRepository, times(1)).findByFechaBajaIsNull();
    }
    
}
