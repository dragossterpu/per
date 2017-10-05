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

import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;

/**
 * @author EZENTIS
 * 
 * Test del repositorio IAlertasNotificacionesUsuarioRepository
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class IAlertasNotificacionesUsuarioRepositoryTest {
    /**
     * Repositorio de alertasnoificaciones.
     */
    @Autowired
    private IAlertasNotificacionesUsuarioRepository alertasNotificacionesUsuarioRepository;
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IAlertasNotificacionesUsuarioRepository#findByUsuario(String)}.
     */
    @Test
    public final void testFindByUsuario() {
        List<AlertasNotificacionesUsuario> listAlertasNotificaciones = alertasNotificacionesUsuarioRepository
                .findByUsuario("jcarranz");
        assertThat(listAlertasNotificaciones).hasSize(3);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IAlertasNotificacionesUsuarioRepository#findByUsuarioAndTipoAndIdMensaje(String, TipoMensajeEnum, Long)}.
     */
    @Test
    public final void testFindByUsuarioAndTipoAndIdMensaje() {
        AlertasNotificacionesUsuario aertasNotificacionesUsuario = alertasNotificacionesUsuarioRepository
                .findByUsuarioAndTipoAndIdMensaje("jcarranz", TipoMensajeEnum.NOTIFICACION, 3L);
        assertThat(aertasNotificacionesUsuario).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.persistence.repositories.IAlertasNotificacionesUsuarioRepository#findByUsuarioAndTipoOrderByFechaAltaDesc(String, TipoMensajeEnum)}.
     */
    @Test
    public final void findByUsuarioAndTipoOrderByFechaAltaDesc() {
        List<AlertasNotificacionesUsuario> listAlertasNotificaciones = alertasNotificacionesUsuarioRepository
                .findByUsuarioAndTipoOrderByFechaAltaDesc("jcarranz", TipoMensajeEnum.ALERTA);
        assertThat(listAlertasNotificaciones).hasSize(1);
    }
    
}
