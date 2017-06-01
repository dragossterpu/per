/**
 * 
 */
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

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.persistence.repositories.IDocumentacionPreviaRepository;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentacionRepository;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.services.gd.TipoDocumentacionService;

/**
 * Test para la clase TipoDocumentacionService.
 * 
 * @author EZENTIS
 * 
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TipoDocumentacionServiceTest {
    /**
     * Simulación del repositorio de tipo de documentación.
     */
    @Mock
    private ITipoDocumentacionRepository tipoDocumentacionRepositoryMock;
    
    /**
     * Simulación del repositorio de documentación previa.
     */
    @Mock
    private IDocumentacionPreviaRepository documentacionPreviaRepositoryMock;
    
    /**
     * Servicio de tipo de documentación.
     */
    @InjectMocks
    private ITipoDocumentacionService tipoDocumentacionServiceMock = new TipoDocumentacionService();
    
    /**
     * Comprobación de que la clase existe.
     */
    @Test
    public void type() {
        assertThat(TipoDocumentacionService.class).isNotNull();
    }
    
    /**
     * Comprobación de que la clase no es abstracta.
     */
    @Test
    public void instantiation() {
        TipoDocumentacionService target = new TipoDocumentacionService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.gd.TipoDocumentacionService#findAll()}.
     */
    @Test
    public void testFindAll() {
        tipoDocumentacionServiceMock.findAll();
        verify(tipoDocumentacionRepositoryMock, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.gd.TipoDocumentacionService#delete(java.lang.Long)}.
     */
    @Test
    public void testDelete() {
        tipoDocumentacionServiceMock.delete(1L);
        verify(tipoDocumentacionRepositoryMock, times(1)).delete(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.gd.TipoDocumentacionService#save(es.mira.progesin.persistence.entities.gd.TipoDocumentacion)}.
     */
    @Test
    public void testSaveTipoDocumentacion() {
        TipoDocumentacion tipoDoc = mock(TipoDocumentacion.class);
        tipoDocumentacionServiceMock.save(tipoDoc);
        verify(tipoDocumentacionRepositoryMock, times(1)).save(tipoDoc);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.gd.TipoDocumentacionService#findByAmbito(es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum)}.
     */
    @Test
    public void testFindByAmbito() {
        tipoDocumentacionServiceMock.findByAmbito(AmbitoInspeccionEnum.GC);
        verify(tipoDocumentacionRepositoryMock, times(1)).findByAmbito(AmbitoInspeccionEnum.GC);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.gd.TipoDocumentacionService#save(es.mira.progesin.persistence.entities.DocumentacionPrevia)}.
     */
    @Test
    public void testSaveDocumentacionPrevia() {
        DocumentacionPrevia docPrevia = mock(DocumentacionPrevia.class);
        tipoDocumentacionServiceMock.save(docPrevia);
        verify(documentacionPreviaRepositoryMock, times(1)).save(docPrevia);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.gd.TipoDocumentacionService#findByIdSolicitud(java.lang.Long)}.
     */
    @Test
    public void testFindByIdSolicitud() {
        tipoDocumentacionServiceMock.findByIdSolicitud(1L);
        verify(documentacionPreviaRepositoryMock, times(1)).findByIdSolicitud(1L);
    }
    
}
