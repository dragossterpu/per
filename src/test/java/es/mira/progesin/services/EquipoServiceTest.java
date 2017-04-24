package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.repositories.IEquipoRepository;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;
import es.mira.progesin.web.beans.EquipoBusqueda;

/**
 * @author EZENTIS
 * 
 * Test del servicio Equipo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EquipoServiceTest {
    
    @Mock
    private IMiembrosRepository miembrosRepository;
    
    @Mock
    private IEquipoRepository equipoRepository;
    
    @InjectMocks
    private EquipoService equipoService;
    
    /**
     * Comprobación clase existe
     */
    @Test
    public void type() {
        assertThat(EquipoService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta
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
     * Test method for {@link es.mira.progesin.services.EquipoService#save(Miembro)}.
     */
    @Test
    public void save_Miembro() {
        Miembro miembro = mock(Miembro.class);
        equipoService.save(miembro);
        verify(miembrosRepository, times(1)).save(miembro);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#save(List)}.
     */
    @Test
    public void save_ListaMiembros() {
        List<Miembro> listaMiembros = mock(List.class);
        equipoService.save(listaMiembros);
        verify(miembrosRepository, times(1)).save(listaMiembros);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#findByEquipo(Equipo)}.
     */
    @Test
    public void findByEquipo() {
        Equipo equipo = mock(Equipo.class);
        equipoService.findByEquipo(equipo);
        verify(miembrosRepository, times(1)).findByEquipo(equipo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#buscarEquipoCriteria(EquipoBusqueda)}.
     */
    @Ignore
    @Test
    public void buscarEquipoCriteria() {
        // TODO test del buscador de equipos
        // List<Equipo> actual = equipoService.buscarEquipoCriteria(equipoBusqueda);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EquipoService#delete(Miembro)}.
     */
    @Test
    public void delete() {
        Miembro miembro = mock(Miembro.class);
        equipoService.delete(miembro);
        verify(miembrosRepository, times(1)).delete(miembro);
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
     * Test method for {@link es.mira.progesin.services.EquipoService#findByFechaBajaIsNotNull()}.
     */
    @Test
    public void findByFechaBajaIsNotNull() {
        equipoService.findByFechaBajaIsNotNull();
        verify(equipoRepository, times(1)).findByFechaBajaIsNull();
    }
    
}
