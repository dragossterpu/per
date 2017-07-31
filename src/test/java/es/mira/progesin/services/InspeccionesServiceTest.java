package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.CuatrimestreEnum;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.web.beans.InspeccionBusqueda;

/**
 * Test del servicio de Inspecciones.
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest(SecurityContextHolder.class)
// Evita conflictos con clases del sistema al enlazar los mocks por tipo
@PowerMockIgnore({ "javax.management.*", "javax.security.*" })
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class InspeccionesServiceTest {
    /**
     * Inyección del servicio de inspecciones.
     */
    @InjectMocks
    private IInspeccionesService servicio = new InspeccionesService();
    
    /**
     * Simulación de la SessionFactory.
     * 
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Simulación del repositorio de inspecciones.
     * 
     */
    @Mock
    private IInspeccionesRepository inspeccionesRepository;
    
    /**
     * Simulación del securityContext.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Simulación de la autenticación.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.InspeccionesService#save(Inspeccion)}.
     */
    @Test
    public final void testSave() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        servicio.save(inspeccion);
        verify(inspeccionesRepository, times(1)).save(inspeccion);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.InspeccionesService#delete(Inspeccion)}.
     */
    @Test
    public final void testDelete() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        servicio.delete(inspeccion);
        verify(inspeccionesRepository, times(1)).delete(inspeccion);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarNoFinalizadaPorNombreUnidadONumero(String)}.
     */
    @Test
    public final void testBuscarNoFinalizadaPorNombreUnidadONumero() {
        servicio.buscarNoFinalizadaPorNombreUnidadONumero("1");
        verify(inspeccionesRepository, times(1)).buscarNoFinalizadaPorNombreUnidadONumero("%" + "1" + "%");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(String, String)}.
     */
    @Test
    public final void testBuscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo() {
        servicio.buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo("1", "sistema");
        verify(inspeccionesRepository, times(1)).buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo("%" + "1" + "%",
                "sistema");
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.InspeccionesService#findInspeccionById(Long)}.
     */
    @Test
    public final void testFindInspeccionById() {
        servicio.findInspeccionById(1L);
        verify(inspeccionesRepository, times(1)).findOne(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_estado_asociar() {
        
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setEstado(EstadoInspeccionEnum.C_PEND_SOLICITAR_DOC_PREVIA);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("administrador").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_usuarioAlta_asociar() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setUsuarioCreacion("system");
        busqueda.setAsociar(true);
        
        User user = User.builder().username("ezentis").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_tipoInspeccion_asociar() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        TipoInspeccion tipo = TipoInspeccion.builder().codigo("I.G.S.").build();
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setTipoInspeccion(tipo);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("ezentis").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_ambito_asociar() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setAmbito(AmbitoInspeccionEnum.GC);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("administrador").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(3);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_tipoUnidad_asociar() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        TipoUnidad tipo = TipoUnidad.builder().id(3L).build();
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setTipoUnidad(tipo);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("administrador").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(7);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_nombreUnidad_asociar() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setNombreUnidad("Lérida");
        busqueda.setAsociar(true);
        
        User user = User.builder().username("adm").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_municipio_asociar() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        Municipio municipio = Municipio.builder().id(1L).build();
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setMunicipio(municipio);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("adm").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(0);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_fechaAlta_asociar() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        LocalDate localDate = LocalDate.of(2017, 4, 28);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setFechaDesde(fechaDesde);
        busqueda.setFechaHasta(fechaHasta);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("admi").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_equipo_asociar() {
        int first = 0;
        int pageSize = 20;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        Equipo equipo = Equipo.builder().id(1L).build();
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setEquipo(equipo);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("admi").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_equipo_asociar_ascendente() {
        int first = 0;
        int pageSize = 0;
        String sortField = null;
        SortOrder sortOrder = SortOrder.ASCENDING;
        
        Equipo equipo = Equipo.builder().id(1L).build();
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setEquipo(equipo);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("adminis").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_equipo_asociar_descendente() {
        int first = 0;
        int pageSize = 0;
        String sortField = "equipo";
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        Equipo equipo = Equipo.builder().id(1L).build();
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setEquipo(equipo);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("adminis").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_id_asociar() {
        int first = 0;
        int pageSize = 0;
        String sortField = "equipo";
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setId("3");
        busqueda.setAsociar(true);
        
        User user = User.builder().username("administr").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_anio_asociar() {
        int first = 0;
        int pageSize = 0;
        String sortField = "equipo";
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setAnio("2017");
        busqueda.setAsociar(true);
        
        User user = User.builder().username("administr").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_cuatrimestre_asociar() {
        int first = 0;
        int pageSize = 0;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setCuatrimestre(CuatrimestreEnum.PRIMERO);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("administr").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_provincia_asociar() {
        int first = 0;
        int pageSize = 0;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        Provincia provincia = new Provincia();
        provincia.setCodigo("28");
        busqueda.setProvincia(provincia);
        busqueda.setAsociar(true);
        
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarInspeccionPorCriteria(int, int, String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testBuscarInspeccionPorCriteria_jefeEquipo_asociar() {
        int first = 0;
        int pageSize = 0;
        String sortField = null;
        SortOrder sortOrder = SortOrder.DESCENDING;
        
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setJefeEquipo("ajangulo");
        busqueda.setAsociar(true);
        
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        
        List<Inspeccion> lista = inspeccionService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder,
                busqueda);
        assertThat(lista).hasSize(8);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#getCountInspeccionCriteria(InspeccionBusqueda)}.
     */
    @Test
    public final void testGetCountInspeccionCriteria() {
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(authentication.getPrincipal()).thenReturn(user);
        
        InspeccionesService inspeccionService = new InspeccionesService(sessionFactory);
        InspeccionBusqueda busqueda = new InspeccionBusqueda();
        busqueda.setEstado(EstadoInspeccionEnum.C_PEND_SOLICITAR_DOC_PREVIA);
        busqueda.setAsociar(true);
        
        int numRegistros = inspeccionService.getCountInspeccionCriteria(busqueda);
        assertThat(numRegistros).isEqualTo(8);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.InspeccionesService#listaInspeccionesAsociadas(Inspeccion)}.
     */
    @Test
    public final void testListaInspeccionesAsociadas() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        servicio.listaInspeccionesAsociadas(inspeccion);
        verify(inspeccionesRepository, times(1)).cargaInspeccionesAsociadas(inspeccion.getId());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.InspeccionesService#existeByTipoInspeccion(TipoInspeccion)}.
     */
    @Test
    public final void testExisteByTipoInspeccion() {
        TipoInspeccion tipo = mock(TipoInspeccion.class);
        servicio.existeByTipoInspeccion(tipo);
        verify(inspeccionesRepository, times(1)).existsByTipoInspeccion(tipo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.InspeccionesService#buscarPorNombreUnidadONumero(String)}.
     */
    @Test
    public final void testBuscarPorNombreUnidadONumero() {
        servicio.buscarPorNombreUnidadONumero("prueba");
        verify(inspeccionesRepository, times(1)).buscarPorNombreUnidadONumero("%prueba%");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#cambiarEstado(Inspeccion, EstadoInspeccionEnum)}.
     */
    @Test
    public final void testCambiarEstado() {
        Inspeccion inspeccion = Inspeccion.builder().id(10L).estadoInspeccion(EstadoInspeccionEnum.A_SIN_INICIAR)
                .build();
        servicio.cambiarEstado(inspeccion, EstadoInspeccionEnum.B_COMUNICACION_REALIZACION);
        assertThat(inspeccion.getEstadoInspeccion()).isEqualTo(EstadoInspeccionEnum.B_COMUNICACION_REALIZACION);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#findByEstadoInspeccion(EstadoInspeccionEnum)}.
     */
    @Test
    public final void testFindByEstadoInspeccion() {
        servicio.findByEstadoInspeccion(EstadoInspeccionEnum.A_SIN_INICIAR);
        verify(inspeccionesRepository, times(1)).findByEstadoInspeccion(EstadoInspeccionEnum.A_SIN_INICIAR);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.InspeccionesService#findInspeccionesByUsuario(String)}.
     */
    @Test
    public final void testFindInspeccionesByUsuario() {
        servicio.findInspeccionesByUsuario("system");
        verify(inspeccionesRepository, times(1)).findInspeccionesByUsuario("system");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InspeccionesService#buscarPorNombreUnidadONumeroUsuario(String, User)}.
     */
    @Test
    public final void testBuscarPorNombreUnidadONumeroUsuario() {
        User usuario = User.builder().username("ajangulo").build();
        servicio.buscarPorNombreUnidadONumeroUsuario("prueba", usuario);
        verify(inspeccionesRepository, times(1)).buscarPorNombreUnidadONumeroYUsuario("%prueba%", usuario);
    }
    
}
