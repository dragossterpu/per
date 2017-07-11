package es.mira.progesin.persistence.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.ClaseUsuario;
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Empleo;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentoRepository;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * Clase utilizada para usar el converter "SelectItemsConverter" de forma genérica en todas las vistas de primefaces.
 * 
 * @author EZENTIS
 *
 */
@Component("selectFindOne")
public class SelectFindOne {
    
    /**
     * Repositorio de la inspección.
     */
    @Autowired
    private IInspeccionesRepository inspeccionRepository;
    
    /**
     * Repositorio de equipos.
     */
    @Autowired
    private IEquipoRepository equipoRepository;
    
    /**
     * Repositorio de municipios.
     */
    @Autowired
    private IMunicipioRepository municipioRepository;
    
    /**
     * Repositorio de tipos de documento.
     */
    @Autowired
    private ITipoDocumentoRepository tipoDocumentoRepository;
    
    /**
     * Repositorio de preguntas.
     */
    @Autowired
    private IPreguntaCuestionarioRepository preguntaCuestionarioRepository;
    
    /**
     * Repositorio de pasos.
     */
    @Autowired
    private IGuiasPasosRepository guiasPasosRepository;
    
    /**
     * Repositorio de Modelos de cuestionario.
     */
    @Autowired
    private IModeloCuestionarioRepository modeloCuestionarioRepository;
    
    /**
     * Repositorio de subáreas de informe.
     */
    @Autowired
    private ISubareaInformeRepository subareaRepository;
    
    /**
     * Variable usada para actualizar la lista cargada en el contexto de la aplicación.
     */
    @Autowired
    private ApplicationBean applicationBean;
    
    /**
     * Repositorio de Modelos de Informes.
     */
    @Autowired
    private IModeloInformeRepository modeloInformeRepository;
    
    /**
     * Busca una inspección por su id.
     * 
     * @param id id de la inspección
     * @return inspección
     */
    public Inspeccion findOneInspeccion(String id) {
        return inspeccionRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca un cuerpo de estado por su id.
     * 
     * @param id id del cuerpo
     * @return cuerpo de estado
     */
    public CuerpoEstado findOneCuerpoEstado(String id) {
        CuerpoEstado cuerpo = null;
        Optional<CuerpoEstado> optional = applicationBean.getListaCuerpos().stream()
                .filter(c -> c.getId().equals(Integer.valueOf(id))).findFirst();
        if (optional.isPresent()) {
            cuerpo = optional.get();
        }
        return cuerpo;
    }
    
    /**
     * Busca un puesto de trabajo por su id.
     * 
     * @param id id del puesto
     * @return puesto de trabajo
     */
    public PuestoTrabajo findOnePuestoTrabajo(String id) {
        PuestoTrabajo puesto = null;
        Optional<PuestoTrabajo> optional = applicationBean.getListaPuestosTrabajo().stream()
                .filter(c -> c.getId().equals(Long.valueOf(id))).findFirst();
        if (optional.isPresent()) {
            puesto = optional.get();
        }
        return puesto;
    }
    
    /**
     * Busca un departamento por su id.
     * 
     * @param id id del departamento
     * @return departamento
     */
    public Departamento findOneDepartamento(String id) {
        Departamento departamento = null;
        Optional<Departamento> optional = applicationBean.getListaDepartamentos().stream()
                .filter(c -> c.getId().equals(Long.valueOf(id))).findFirst();
        if (optional.isPresent()) {
            departamento = optional.get();
        }
        return departamento;
    }
    
    /**
     * Busca una clase por su id.
     * 
     * @param id id de la clase
     * @return clase a la que pertenece el usuario
     */
    public ClaseUsuario findOneClaseUsuario(String id) {
        ClaseUsuario clase = null;
        Optional<ClaseUsuario> optional = applicationBean.getListaClaseUsuario().stream()
                .filter(c -> c.getId().equals(Long.valueOf(id))).findFirst();
        if (optional.isPresent()) {
            clase = optional.get();
        }
        return clase;
    }
    
    /**
     * Busca un empleo por su id.
     * 
     * @param id id del empleo
     * @return empleo
     */
    public Empleo findOneEmpleo(String id) {
        Empleo empleo = null;
        Optional<Empleo> optional = applicationBean.getListaEmpleos().stream()
                .filter(c -> c.getId().equals(Long.valueOf(id))).findFirst();
        if (optional.isPresent()) {
            empleo = optional.get();
        }
        return empleo;
    }
    
    /**
     * Busca un tipo de inspección por su id.
     * 
     * @param id id del tipo de inspecció
     * @return tipo de inspección
     */
    public TipoInspeccion findOneTipoInspeccion(String id) {
        TipoInspeccion tipoInspeccion = null;
        Optional<TipoInspeccion> optional = applicationBean.getListaTiposInspeccion().stream()
                .filter(c -> c.getCodigo().equals(id)).findFirst();
        if (optional.isPresent()) {
            tipoInspeccion = optional.get();
        }
        return tipoInspeccion;
    }
    
    /**
     * Busca un departamento por su id.
     * 
     * @param id id del equipo
     * @return equipo
     */
    public Equipo findOneEquipo(String id) {
        return equipoRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca un departamento por su id.
     * 
     * @param id id de la provincia
     * @return provincia
     */
    public Provincia findOneProvincia(String id) {
        Provincia provincia = null;
        Optional<Provincia> optional = applicationBean.getListaProvincias().stream()
                .filter(c -> c.getCodigo().equals(id)).findFirst();
        if (optional.isPresent()) {
            provincia = optional.get();
        }
        return provincia;
    }
    
    /**
     * Busca un municipio por su id.
     * 
     * @param id id del municipio
     * @return municipio
     */
    public Municipio findOneMunicipio(String id) {
        return municipioRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca un tipo de unidad por su id.
     * 
     * @param id id de la unidad
     * @return tipo de unidad
     */
    public TipoUnidad findOneTipoUnidad(String id) {
        TipoUnidad tipoUnidad = null;
        Optional<TipoUnidad> optional = applicationBean.getListaTiposUnidad().stream()
                .filter(c -> c.getId().equals(Long.valueOf(id))).findFirst();
        if (optional.isPresent()) {
            tipoUnidad = optional.get();
        }
        return tipoUnidad;
    }
    
    /**
     * Busca un tipo de documento por su id.
     * 
     * @param id id del tipo de documento
     * @return tipo de documento
     */
    public TipoDocumento findOneTipoDocumento(String id) {
        return tipoDocumentoRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca una pregunta por su id.
     * 
     * @param id id de la pregunta
     * @return pregunta
     */
    public PreguntasCuestionario findOnePreguntasCuestionario(String id) {
        return preguntaCuestionarioRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca el paso de una guía por su id.
     * 
     * @param id id del paso
     * @return paso de la guía
     */
    public GuiaPasos findOneGuiaPasos(String id) {
        return guiasPasosRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca un tipo de equipo por su id.
     * 
     * @param id id del equipo
     * @return tipo de equipo
     */
    public TipoEquipo findOneTipoEquipo(String id) {
        TipoEquipo tipoEquipo = null;
        Optional<TipoEquipo> optional = applicationBean.getListaTiposEquipo().stream()
                .filter(c -> c.getId().equals(Long.valueOf(id))).findFirst();
        if (optional.isPresent()) {
            tipoEquipo = optional.get();
        }
        return tipoEquipo;
    }
    
    /**
     * Busca un modelo de cuestionario por su id.
     * 
     * @param id Id a buscar
     * @return Modelo correspondiente al id
     */
    public ModeloCuestionario findOneModeloCuestionario(String id) {
        return modeloCuestionarioRepository.findOne(Integer.valueOf(id));
    }
    
    /**
     * Busca la subárea de un informe.
     * 
     * @param id id de la subárea
     * @return subárea de un informe
     */
    public SubareaInforme findOneSubareaInforme(String id) {
        return subareaRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca un modelo de informe por su id.
     * 
     * @param id Id a buscar
     * @return Modelo correspondiente al id
     */
    public ModeloInforme findOneModeloInforme(String id) {
        return modeloInformeRepository.findOne(Long.valueOf(id));
    }
}
