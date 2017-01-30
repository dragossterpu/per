package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionarioId;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TiposRespuestasPersonalizables;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.DataTableView;
import es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("nuevoModeloCuestionarioBean")
@Scope(FacesViewScope.NAME)

public class nuevoModeloCuestionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	ModeloCuestionario modelo;
	
	String nombreCuestionario;
	private List<String> listadoAreas;
	
	
	private String error;
	
	private boolean skip;
	
	private List<String> listaTipoPreguntasInicial;
	private List<String> listaTipoPreguntasFinal;
	private DualListModel<String> listaTipoPreguntas;
	
	//Personalizar
	private String tipoPersonalizado="";
	private List<TiposRespuestasPersonalizables> listaTiposPersonalizables;
	private List<String> listadoValoresNuevaRespuesta;
	private List<String> listadoValoresFila;
	
	private Map<String, DataTableView> mapaRespuestasTabla;
	
	private DataTableView datosTabla;
	
	//Nueva pregunta
	private Map<String,List<PreguntasCuestionario>> mapaPreguntas;
	private Map<String,List<PreguntasCuestionario>> mapaPreguntasAux;
	
	//Inputs
	private UIInput nuevaArea;
	private UIInput nuevoValor; 
	private UIInput nuevoValorRadio;
	private UIInput nuevoValorTabla;
	private UIInput nuevoValorFila;
	private UIInput preguntaNueva;
	private UIInput nuevaRespuesta;
	
	
	private String tipoSeleccionado;
	
	
	@Autowired
	IConfiguracionRespuestasCuestionarioRepository configuracionRespuestasCuestionarioRepository;
	
	@Autowired
	IModeloCuestionarioService modeloCuestionarioService;
	
	@Autowired
	INotificacionService notificacionesService;
	
	@Autowired
	IRegistroActividadService registroActividadService;
	
	@Autowired
	private VisualizarCuestionario visualizarCuestionario;
	
	
	@PostConstruct
	public void init() {
		modelo=new ModeloCuestionario();
		listadoAreas=new ArrayList<String>();
		
		listaTipoPreguntasInicial= configuracionRespuestasCuestionarioRepository.findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc();
		listaTipoPreguntasFinal=new ArrayList<String>();
		
		listaTipoPreguntas = new DualListModel<String>(listaTipoPreguntasInicial, listaTipoPreguntasFinal);
		listaTiposPersonalizables= Arrays.asList(TiposRespuestasPersonalizables.values());
		listadoValoresNuevaRespuesta=new ArrayList<String>();
		listadoValoresFila=new ArrayList<String>();
		
		mapaPreguntas=new HashMap<String, List<PreguntasCuestionario>>();
		mapaPreguntasAux=new HashMap<String, List<PreguntasCuestionario>>();
		
		
		}	
		
	public String onFlowProcess(FlowEvent event) {
			
			if (skip) {
				// reset in case user goes back
				skip = false;
				return "confirm";
			}
			else {
				if ("areas".equals(event.getNewStep())) {
					modelo.setDescripcion(nombreCuestionario);
					}
				if ("tipoRespuestas".equals(event.getNewStep())) {
					guardaAreas();
					
				}
				if ("preguntas".equals(event.getNewStep())) {
						
				}
				if("finalizar".equals(event.getNewStep())) {
					guardaPreguntas();
				}
				
				}
				return event.getNewStep();
			}
	
	public void aniadeArea(){
		listadoAreas.add(nuevaArea.getLocalValue().toString());
		nuevaArea.setValue(""); 
	}
	
	public void borraArea(String nueva){
		listadoAreas.remove(nuevaArea.getLocalValue().toString());
	}
	
	public void onSelectArea(SelectEvent event) {
		String nuevo= event.getObject().toString();    
		nuevaArea.setValue(nuevo);	
    }
    	
	 public void guardaAreas(){
		 	List<AreasCuestionario> areas=new ArrayList<AreasCuestionario>();
		 			 	
	    	for (int i=0; i<listadoAreas.size(); i++){
	    		AreasCuestionario nueva=new AreasCuestionario();
	    		nueva.setArea(listadoAreas.get(i));
	    		
	    		nueva.setOrden(i);
	    		nueva.setPreguntas(new ArrayList<PreguntasCuestionario>());
	    		areas.add(nueva);
	    	}
	    	modelo.setAreas(areas);
	    	
	    	for(String area : listadoAreas ){
				mapaPreguntas.put(area, new ArrayList<PreguntasCuestionario>());
				mapaPreguntasAux.put(area, new ArrayList<PreguntasCuestionario>());
			}
	    }
    	
	 
	 public void onSelectTipo(SelectEvent event) {

	        tipoSeleccionado= event.getObject().toString();
	        if (tipoSeleccionado.startsWith("TABLA") || tipoSeleccionado.startsWith("MATRIZ")){
	        	datosTabla=new DataTableView();
	        	construirTipoRespuestaTablaMatrizVacia(tipoSeleccionado);
	        }
	    }
	 
	 private void construirTipoRespuestaTablaMatrizVacia(String tipo) {
			List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestasCuestionarioRepository.findByConfigSeccionOrderByConfigClaveAsc(tipo);
			if (tipo != null && tipo.startsWith("TABLA")) {
				datosTabla.crearTabla(valoresColumnas);
			}
			else {
				datosTabla.crearMatriz(valoresColumnas);
			}
			//mapaRespuestasTabla.put(tipo, dataTableView); 
		}
	 
	 public List<String> getValoresTipoRespuesta(String tipo) {
			return configuracionRespuestasCuestionarioRepository.findValoresPorSeccion(tipo);
		}
	 
	 public void aniadeValor(){
	    	listadoValoresNuevaRespuesta.add(nuevoValor.getLocalValue().toString());
	    }
	
	public void borraValor(){
 	listadoValoresNuevaRespuesta.remove(nuevoValor.getLocalValue().toString());
 }
	
	public void onSelectValor(SelectEvent event) {
		String nuevo= event.getObject().toString();    
		nuevoValor.setValue(nuevo);	
 }
	    
	public void aniadeValorRadio(){
 	listadoValoresNuevaRespuesta.add(nuevoValorRadio.getLocalValue().toString());
 }

	public void borraValorRadio(){
		listadoValoresNuevaRespuesta.remove(nuevoValorRadio.getLocalValue().toString());
	}
	
	public void onSelectValorRadio(SelectEvent event) {
		String nuevo= event.getObject().toString();    
		nuevoValorRadio.setValue(nuevo);	
	}
	public void aniadeValorTabla(){
		listadoValoresNuevaRespuesta.add(nuevoValorTabla.getLocalValue().toString());
	}
	
	public void borraValorTabla(){
	listadoValoresNuevaRespuesta.remove(nuevoValorTabla.getLocalValue().toString());
	}
	
	public void onSelectValorTabla(SelectEvent event) {
	String nuevo= event.getObject().toString();    
	nuevoValorTabla.setValue(nuevo);	
	}
	
	 public void aniadeValorFila(){
	 	listadoValoresFila.add(nuevoValorFila.getLocalValue().toString());
	 }
	 
	 public void borraValorFila(){
	 	listadoValoresFila.remove(nuevoValorFila.getLocalValue().toString());
	 }
	 
	 public void onSelectValorFila(SelectEvent event) {
			String nuevo= event.getObject().toString();    
			nuevoValorFila.setValue(nuevo);	
	 }
 
 public void guardaTipoRespuesta(){
 	
		String seccion=tipoPersonalizado.concat(listadoValoresNuevaRespuesta.toString());
		
		
		for(int i=0; i< listadoValoresNuevaRespuesta.size(); i++){
			String valor=listadoValoresNuevaRespuesta.get(i);
			ConfiguracionRespuestasCuestionario nuevoValor=new ConfiguracionRespuestasCuestionario();
			ConfiguracionRespuestasCuestionarioId datos= new ConfiguracionRespuestasCuestionarioId();
			
			switch (tipoPersonalizado){
			case "RADIO": 
				datos.setSeccion(seccion);
				datos.setValor(valor);
				datos.setClave(valor);
				break;
			default : 
				datos.setSeccion(seccion);
				datos.setValor(valor);
				datos.setClave("campo"+(i+1));
				break;
			}
			nuevoValor.setConfig(datos);
			configuracionRespuestasCuestionarioRepository.save(nuevoValor);
				
		}
		
		//Valores de fila para Matriz
		
		if(tipoPersonalizado.equals("MATRIZ")){
			for(int i=0; i< listadoValoresFila.size(); i++){
				String valor=listadoValoresFila.get(i);
				ConfiguracionRespuestasCuestionario nuevoValor=new ConfiguracionRespuestasCuestionario();
				ConfiguracionRespuestasCuestionarioId datos= new ConfiguracionRespuestasCuestionarioId();
				datos.setSeccion(seccion);
				datos.setValor(valor);
				datos.setClave("nombreFila");
				nuevoValor.setConfig(datos);
				configuracionRespuestasCuestionarioRepository.save(nuevoValor);
			}
		}
		
		//Actualizar lista
		List<String> nuevoListado= configuracionRespuestasCuestionarioRepository.findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc();
		
		listaTipoPreguntas.setSource(nuevoListado);	
		
		listadoValoresNuevaRespuesta=new ArrayList<String>();
		listadoValoresFila=new ArrayList<String>();
 }
 
 public void aniadePregunta(String areaSelec){
 	
	PreguntasCuestionario nuevaPregunta =new PreguntasCuestionario();
 	String pregunta=preguntaNueva.getLocalValue().toString();
 	String tipo=nuevaRespuesta.getLocalValue().toString();	
 	
 	nuevaPregunta.setPregunta(pregunta);
 	nuevaPregunta.setTipoRespuesta(tipo);
 	
 	if (!pregunta.isEmpty() && !tipo.isEmpty()){
	 	List<PreguntasCuestionario> listado=new ArrayList<PreguntasCuestionario>();
	 	listado= mapaPreguntasAux.get(areaSelec);
	 	listado.add(nuevaPregunta);
	 	mapaPreguntas.replace(areaSelec, listado);
		mapaPreguntasAux.replace(areaSelec, listado);
 	}
 	guardaPreguntas();
  }
 
 public void onSelectPregunta(SelectEvent event) {
 	PreguntasCuestionario nuevo= (PreguntasCuestionario) event.getObject();    
		preguntaNueva.setValue(nuevo.getPregunta());
		nuevaRespuesta.setValue(nuevo.getTipoRespuesta());
 }
 
	

public void guardaPreguntas(){
	for (String areaString: listadoAreas){
		List<PreguntasCuestionario> listado= mapaPreguntasAux.get(areaString);
		List<PreguntasCuestionario> listadoAux=new ArrayList<PreguntasCuestionario>();
		
		for (int i=0; i< listado.size() ; i++){
			PreguntasCuestionario preg=listado.get(i);
			preg.setOrden(i);
			listadoAux.add(preg);
		}
		for (AreasCuestionario area: modelo.getAreas()){
			if (area.getArea().equals(areaString)){
				area.setPreguntas(listadoAux);
			}
		}
	}
}
 
 public String guardaCuestionario(){
 	
 	
	modelo.setCodigo("sin codigo");
 	modelo.setNombreFichero(modelo.getDescripcion());
 	modelo.setIdDocumento((long) 0);
 	
 	modelo= modeloCuestionarioService.save(modelo);
	
	notificacionesService.crearNotificacionRol("Se crea nuevo modelo:".concat(modelo.getDescripcion()), SeccionesEnum.CUESTIONARIO.getDescripcion(), RoleEnum.ADMIN);
	registroActividadService.altaRegActividad("Se crea nuevo modelo:".concat(modelo.getDescripcion()), EstadoRegActividadEnum.ALTA.name(), SeccionesEnum.CUESTIONARIO.getDescripcion());
	
 	return "cuestionarios/modelosCuestionarios";
 	
 	
 }
 
 
}
