package es.mira.progesin.persistence.repositories;

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
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentoRepository;

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
     * Repositorio del cuerpo de estado.
     */
    @Autowired
    private ICuerpoEstadoRepository cuerpoRepository;
    
    /**
     * Repositorio de los puestos de trabajo.
     */
    @Autowired
    private IPuestoTrabajoRepository puestoTrabajoRepository;
    
    /**
     * Repositorio de departamentos.
     */
    @Autowired
    private IDepartamentoRepository departamentoRepository;
    
    /**
     * Repositorio de las clases.
     */
    
    @Autowired
    private IClaseUsuarioRepository claseUsuarioRepository;
    
    /**
     * Repositorio de empleos.
     */
    @Autowired
    private IEmpleoRepository empleoRepository;
    
    /**
     * Repositorio de tipos de inspección.
     */
    @Autowired
    private ITipoInspeccionRepository tipoInspeccionRepository;
    
    /**
     * Repositorio de equipos.
     */
    @Autowired
    private IEquipoRepository equipoRepository;
    
    /**
     * Repositorio de tipos de unidad.
     */
    @Autowired
    private ITipoUnidadRepository tipoUnidadRepository;
    
    /**
     * Repositorio de provincias.
     */
    @Autowired
    private IProvinciaRepository provinciaRepository;
    
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
     * Repositorio de tipos de equipo.
     */
    @Autowired
    private ITipoEquiposRepository tipoEquiposRepository;
    
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
        return cuerpoRepository.findOne(Integer.valueOf(id));
    }
    
    /**
     * Busca un puesto de trabajo por su id.
     * 
     * @param id id del puesto
     * @return puesto de trabajo
     */
    public PuestoTrabajo findOnePuestoTrabajo(String id) {
        return puestoTrabajoRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca un departamento por su id.
     * 
     * @param id id del departamento
     * @return departamento
     */
    public Departamento findOneDepartamento(String id) {
        return departamentoRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca una clase por su id.
     * 
     * @param id id de la clase
     * @return clase a la que pertenece el usuario
     */
    public ClaseUsuario findOneClaseUsuario(String id) {
        return claseUsuarioRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca un empleo por su id.
     * 
     * @param id id del empleo
     * @return empleo
     */
    public Empleo findOneEmpleo(String id) {
        return empleoRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * Busca un tipo de inspección por su id.
     * 
     * @param id id del tipo de inspecció
     * @return tipo de inspección
     */
    public TipoInspeccion findOneTipoInspeccion(String id) {
        return tipoInspeccionRepository.findOne(id);
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
        return provinciaRepository.findOne(id);
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
        return tipoUnidadRepository.findOne(Long.valueOf(id));
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
        return tipoEquiposRepository.findOne(Long.valueOf(id));
    }
}
