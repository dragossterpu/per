package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.ClaseUsuario;
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Empleo;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.IClaseUsuarioRepository;
import es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository;
import es.mira.progesin.persistence.repositories.IDepartamentoRepository;
import es.mira.progesin.persistence.repositories.IEmpleoRepository;
import es.mira.progesin.persistence.repositories.IEquipoRepository;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.persistence.repositories.IMunicipioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IProvinciaRepository;
import es.mira.progesin.persistence.repositories.IPuestoTrabajoRepository;
import es.mira.progesin.persistence.repositories.ITipoInspeccionRepository;
import es.mira.progesin.persistence.repositories.ITipoUnidadRepository;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentoRepository;

/**
 * @author EZENTIS
 *
 */
@Component("selectFindOne")
public class SelectFindOne {
    
    @Autowired
    private IInspeccionesRepository inspeccionRepository;
    
    @Autowired
    private ICuerpoEstadoRepository cuerpoRepository;
    
    @Autowired
    private IPuestoTrabajoRepository puestoTrabajoRepository;
    
    @Autowired
    private IDepartamentoRepository departamentoRepository;
    
    @Autowired
    private IClaseUsuarioRepository claseUsuarioRepository;
    
    @Autowired
    private IEmpleoRepository empleoRepository;
    
    @Autowired
    private ITipoInspeccionRepository tipoInspeccionRepository;
    
    @Autowired
    private IEquipoRepository equipoRepository;
    
    @Autowired
    private ITipoUnidadRepository tipoUnidadRepository;
    
    @Autowired
    private IProvinciaRepository provinciaRepository;
    
    @Autowired
    private IMunicipioRepository municipioRepository;
    
    @Autowired
    private IPreguntaCuestionarioRepository preguntaCuestionarioRepository;
    
    @Autowired
    private ITipoDocumentoRepository tipoDocumentoRepository;
    
    /**
     * @param id
     * @return
     */
    public Inspeccion findOneInspeccion(String id) {
        return inspeccionRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * @param id
     * @return
     */
    public CuerpoEstado findOneCuerpoEstado(String id) {
        return cuerpoRepository.findOne(Integer.valueOf(id));
    }
    
    /**
     * @param id
     * @return
     */
    public PuestoTrabajo findOnePuestoTrabajo(String id) {
        return puestoTrabajoRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * @param id
     * @return
     */
    public Departamento findOneDepartamento(String id) {
        return departamentoRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * @param id
     * @return
     */
    public ClaseUsuario findOneClaseUsuario(String id) {
        return claseUsuarioRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * @param id
     * @return
     */
    public Empleo findOneEmpleo(String id) {
        return empleoRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * @param id
     * @return
     */
    public TipoInspeccion findOneTipoInspeccion(String id) {
        return tipoInspeccionRepository.findOne(id);
    }
    
    /**
     * @param id
     * @return
     */
    public Equipo findOneEquipo(String id) {
        return equipoRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * @param id
     * @return
     */
    public Provincia findOneProvincia(String id) {
        return provinciaRepository.findOne(id);
    }
    
    /**
     * @param id
     * @return
     */
    public Municipio findOneMunicipio(String id) {
        return municipioRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * @param id
     * @return
     */
    public TipoUnidad findOneTipoUnidad(String id) {
        return tipoUnidadRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * @param id
     * @return
     */
    public PreguntasCuestionario findOnePreguntasCuestionario(String id) {
        return preguntaCuestionarioRepository.findOne(Long.valueOf(id));
    }
    
    /**
     * @param id
     * @return
     */
    public TipoDocumento findOneTipoDocumento(String id) {
        return tipoDocumentoRepository.findOne(Long.valueOf(id));
    }
}
