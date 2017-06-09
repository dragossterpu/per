package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.ClaseUsuario;
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Empleo;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.enums.AdministracionAccionEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.IClaseUsuarioRepository;
import es.mira.progesin.persistence.repositories.IEmpleoRepository;
import es.mira.progesin.persistence.repositories.IProvinciaRepository;
import es.mira.progesin.persistence.repositories.ITipoUnidadRepository;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.services.IDepartamentoService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IParametroService;
import es.mira.progesin.services.IPuestoTrabajoService;
import es.mira.progesin.services.ITipoEquipoService;
import es.mira.progesin.services.ITipoInspeccionService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase utilizada para cargar datos en el contexto de la aplicación al arrarancar el servidor.
 * 
 * @author EZENTIS
 *
 */
@Component("applicationBean")
@Setter
@Getter
@NoArgsConstructor
public class ApplicationBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Servicio de parámetros.
     */
    @Autowired
    private transient IParametroService parametroService;
    
    /**
     * Servicio de documentos.
     */
    @Autowired
    private transient IDocumentoService documentoService;
    
    /**
     * Repositorio de tipo de unidad.
     */
    @Autowired
    private transient ITipoUnidadRepository tipoUnidadRepository;
    
    /**
     * Repositorio de provincia.
     */
    @Autowired
    private transient IProvinciaRepository provinciaRepository;
    
    /**
     * Mapa que contendrá los parámetros de la aplicación.
     */
    private Map<String, Map<String, String>> mapaParametros;
    
    /**
     * Dominios válidos para los correos electrónicos empleados en la aplicación.
     */
    private String dominiosValidos;
    
    /**
     * Listado de provincias.
     */
    private List<Provincia> listaProvincias;
    
    /**
     * Listado de tipos de documento.
     */
    private List<TipoDocumento> listaTipos;
    
    /**
     * Listado de tipos de unidad.
     */
    private List<TipoUnidad> listaTiposUnidad;
    
    /**
     * Listado de cuerpos de estado.
     */
    private List<CuerpoEstado> listaCuerpos;
    
    /**
     * Servicio de cuerpos de estado.
     */
    @Autowired
    private transient ICuerpoEstadoService cuerposService;
    
    /**
     * Listado de puestos de trabajo.
     */
    private List<PuestoTrabajo> listaPuestosTrabajo;
    
    /**
     * Servicio de puestos de trabajo.
     */
    @Autowired
    private transient IPuestoTrabajoService puestosTrabajoService;
    
    /**
     * Listado de departamentos.
     */
    private List<Departamento> listaDepartamentos;
    
    /**
     * Servicio de departamentos.
     */
    @Autowired
    private transient IDepartamentoService departamentoService;
    
    /**
     * Listado de clases de usuario.
     */
    private List<ClaseUsuario> listaClaseUsuario;
    
    /**
     * Repositorio de clases de usuario.
     */
    @Autowired
    private transient IClaseUsuarioRepository claseUsuarioRepository;
    
    /**
     * Listado de empleos.
     */
    private List<Empleo> listaEmpleos;
    
    /**
     * Repositorio de empleos.
     */
    @Autowired
    private transient IEmpleoRepository empleoRepository;
    
    /**
     * Listado de tipos de inspección.
     */
    private List<TipoInspeccion> listaTiposInspeccion;
    
    /**
     * Servicio de modelos/tipos de inspección.
     */
    @Autowired
    private transient ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Listado de tipos de equipo.
     */
    private List<TipoEquipo> listaTiposEquipo;
    
    /**
     * Servicio de tipos de equipo.
     */
    @Autowired
    private transient ITipoEquipoService tipoEquipoService;
    
    /**
     * Inicialización de datos.
     */
    @PostConstruct
    public void init() {
        setMapaParametros(parametroService.getMapaParametros());
        setDominiosValidos(mapaParametros.get("dominiosCorreo").get("dominiosCorreo"));
        setListaTipos(documentoService.listaTiposDocumento());
        setListaProvincias((List<Provincia>) provinciaRepository.findAll());
        setListaTiposUnidad((List<TipoUnidad>) tipoUnidadRepository.findAll());
        setListaCuerpos(cuerposService.findAll());
        setListaPuestosTrabajo(puestosTrabajoService.findAll());
        setListaDepartamentos(departamentoService.findAll());
        setListaClaseUsuario((List<ClaseUsuario>) claseUsuarioRepository.findAll());
        setListaEmpleos((List<Empleo>) empleoRepository.findAll());
        setListaTiposInspeccion(tipoInspeccionService.buscaTodos());
        setListaTiposEquipo(tipoEquipoService.findAll());
    }
    
    /**
     * @param objeto Objeto a actualizar
     * @param lista Lista donde se va actualizar el objeto
     * @param accion Alta/Baja/Modificación
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void actualizarApplicationBean(Object objeto, List lista, AdministracionAccionEnum accion) {
        switch (accion) {
            case ALTA:
                lista.add(objeto);
                break;
            case BAJA:
                lista.removeIf(o -> o.equals(objeto));
                break;
            case MODIFICACION:
                lista.removeIf(o -> o.equals(objeto));
                lista.add(objeto);
                break;
        }
    }
    
}
