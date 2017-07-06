package es.mira.progesin.web.beans.cuestionarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador para la creación de cuestionarios personalizados a partir de un modelo.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Controller("edicionCuestionarioBean")
@Scope("session")
public class EdicionCuestionarioBean {
    
    /**
     * Modelo de cuestionario que se va a editar.
     */
    private ModeloCuestionario modeloCuestionario;
    
    /**
     * Lista de cuestionarios del modelo a editar.
     */
    private List<AreasCuestionario> listaAreasCuestionario;
    
    /**
     * Mapa que relaciona las áreas con sus respectivas preguntas.
     */
    private Map<AreasCuestionario, PreguntasCuestionario[]> preguntasSelecciondas;
    
    /**
     * Repositorio para las áreas de un modelo cuestionario.
     */
    @Autowired
    private IAreaCuestionarioRepository areaCuestionarioRepository;
    
    /**
     * Repositorio para las preguntas de un modelo de cuestionario.
     */
    @Autowired
    private IPreguntaCuestionarioRepository pregunaCuestionarioRepository;
    
    /**
     * Servicio de cuestionario personalizado.
     */
    @Autowired
    private ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;
    
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private IRegistroActividadService regActividadService;
    
    /**
     * Inicia la edición del modelo de cuestionario.
     * 
     * @param modCuestionario modelo de cuestionario a modificar
     * @return ruta de la vista de edición a la que se redirigirá la aplicación
     */
    public String editarCuestionario(ModeloCuestionario modCuestionario) {
        this.modeloCuestionario = modCuestionario;
        preguntasSelecciondas = new HashMap<>();
        listaAreasCuestionario = areaCuestionarioRepository
                .findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(modCuestionario.getId());
        for (AreasCuestionario area : listaAreasCuestionario) {
            area.setPreguntas(pregunaCuestionarioRepository.findByAreaAndFechaBajaIsNullOrderByOrdenAsc(area));
        }
        return "/cuestionarios/editarCuestionario?faces-redirect=true";
    }
    
    /**
     * Previsualiza el modelo de cuestionario.
     * 
     * @return url de la vista a la que se redirige
     */
    public String previsualizarFormulario() {
        boolean hayPreguntasSeleccionadas = false;
        String page;
        int cont = 0;
        while (hayPreguntasSeleccionadas == Boolean.FALSE && cont < listaAreasCuestionario.size()) {
            Object[] preg = preguntasSelecciondas.get(listaAreasCuestionario.get(cont));
            if (preg.length > 0) {
                hayPreguntasSeleccionadas = true;
            }
            cont++;
        }
        if (hayPreguntasSeleccionadas) {
            page = "/cuestionarios/previsualizarCuestionario?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos una pregunta",
                    "", "message");
            page = null;
        }
        return page;
    }
    
    /**
     * Guarda el cuestionario en base de datos.
     * 
     * @param nombreCuestionario a guardar
     */
    public void guardarFormulario(String nombreCuestionario) {
        try {
            RequestContext.getCurrentInstance().execute("PF('cuestionarioDialog').hide()");
            CuestionarioPersonalizado cp = new CuestionarioPersonalizado();
            cp.setModeloCuestionario(modeloCuestionario);
            cp.setNombreCuestionario(nombreCuestionario);
            List<PreguntasCuestionario> preguntasElegidas = new ArrayList<>();
            for (AreasCuestionario area : listaAreasCuestionario) {
                Object[] preg = preguntasSelecciondas.get(area);
                for (int i = 0; i < preg.length; i++) {
                    PreguntasCuestionario pc = (PreguntasCuestionario) preg[i];
                    preguntasElegidas.add(pc);
                }
            }
            cp.setPreguntasElegidas(preguntasElegidas);
            cuestionarioPersonalizadoService.save(cp);
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Cuestionario",
                    "Se ha guardado su cuestionario con éxito");
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al guardar el cuestionario");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
    }
    
}
