package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;

/**
 * 
 * Test del servicio Miembro.
 * 
 * @author EZENTIS
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MiembroServiceTest {
    /**
     * Simulación del repositorio de miembros.
     */
    @Mock
    private IMiembrosRepository miembrosRepository;
    
    /**
     * Servicio de miembros.
     */
    @InjectMocks
    private IMiembroService miembroService = new MiembroService();
    
    /**
     * Comprobación de que la clase existe.
     */
    @Test
    public void type() {
        assertThat(MiembroService.class).isNotNull();
    }
    
    /**
     * Comprobación de que la clase no es abstracta.
     */
    @Test
    public void instantiation() {
        MiembroService target = new MiembroService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.MiembroService#buscaMiembroByUserAndEquipo}.
     */
    @Test
    public void buscaMiembroByUser() {
        
        User usuario = mock(User.class);
        Equipo equipo = mock(Equipo.class);
        
        miembroService.buscaMiembroByUserAndEquipo(usuario, equipo);
        
        verify(miembrosRepository, times(1)).findByUsuarioAndEquipo(usuario, equipo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.MiembroService#save(Miembro)}.
     */
    @Test
    public void save() {
        Miembro miembro = mock(Miembro.class);
        miembroService.save(miembro);
        verify(miembrosRepository, times(1)).save(miembro);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.MiembroService#findByEquipo(Equipo)}.
     */
    @Test
    public void findByEquipo() {
        Equipo equipo = mock(Equipo.class);
        miembroService.findByEquipo(equipo);
        verify(miembrosRepository, times(1)).findByEquipo(equipo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.MiembroService#esJefeEquipo(String)}.
     */
    @Test
    public void esJefeEquipo() {
        String username = "usuarioLogueado";
        
        miembroService.esJefeEquipo(username);
        
        verify(miembrosRepository, times(1)).existsByUsuarioUsernameAndPosicionAndEquipoFechaBajaIsNull(username, RolEquipoEnum.JEFE_EQUIPO);
    }
    
}
