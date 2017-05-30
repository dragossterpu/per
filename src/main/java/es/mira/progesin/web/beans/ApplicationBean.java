package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.IProvinciaRepository;
import es.mira.progesin.persistence.repositories.ITipoUnidadRepository;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IParametroService;
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
@Getter
@Setter
@NoArgsConstructor
public class ApplicationBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private transient IParametroService parametroService;
    
    @Autowired
    private transient IDocumentoService documentoService;
    
    @Autowired
    private transient ITipoUnidadRepository tipoUnidadRepository;
    
    @Autowired
    private transient IProvinciaRepository provinciaRepository;
    
    private Map<String, Map<String, String>> mapaParametros;
    
    private String dominiosValidos;
    
    private List<Provincia> listaProvincias;
    
    private List<TipoDocumento> listaTipos;
    
    private List<TipoUnidad> listaTiposUnidad;
    
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
        
    }
}
