package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

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
import es.mira.progesin.services.IAlertasNotificacionesUsuarioService;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
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
	private List<AreasCuestionario> listadoAreas;
	private List<String> listadoAreasString;
	
	private boolean skip;
	
	private List<String> listaTipoPreguntasInicial;
	private List<String> listaTipoPreguntasFinal;
	private DualListModel<String> listaTipoPreguntas;
	
	//Personalizar
	private String tipoPersonalizado="";
	private List<TiposRespuestasPersonalizables> listaTiposPersonalizables;
	private List<String> listadoValoresNuevaRespuesta;
	private List<String> listadoValoresFila;
	
	
	
	//Nueva pregunta
	private PreguntasCuestionario nuevaPregunta;
	private Map<String,List<PreguntasCuestionario>> mapaPreguntas;
	private List<PreguntasCuestionario> listaPreguntas;
	
	//Inputs
	private UIInput nuevaArea;
	private UIInput nuevoValor; 
	private UIInput nuevoValorRadio;
	private UIInput nuevoValorTabla;
	private UIInput nuevoValorFila;
	private UIInput preguntaNueva;
	private UIInput nuevaRespuesta;
	
	
	@Autowired
	IConfiguracionRespuestasCuestionarioRepository configuracionRespuestasCuestionarioRepository;
	
	@Autowired
	IModeloCuestionarioService modeloCuestionarioService;
	
	@Autowired
	INotificacionService notificacionesService;
	
	@Autowired
	IRegistroActividadService registroActividadService;
	
	
	@PostConstruct
	public void init() {
		//nuevaArea= new UIInput();
		modelo=new ModeloCuestionario();
		nuevaPregunta =new PreguntasCuestionario();
		listaPreguntas= new ArrayList<PreguntasCuestionario>();
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
					nombreCuestionario= "";
					listadoAreas= Lists.newArrayList();
					listadoAreasString= Lists.newArrayList();
					}
				if ("tipoRespuestas".equals(event.getNewStep())) {
					guardaAreas();
					listaTipoPreguntasInicial= configuracionRespuestasCuestionarioRepository.findAllDistinctTipoRespuesta();
					listaTipoPreguntasFinal=new ArrayList<String>();
					
					listaTipoPreguntas = new DualListModel<String>(listaTipoPreguntasInicial, listaTipoPreguntasFinal);
					listaTiposPersonalizables= Arrays.asList(TiposRespuestasPersonalizables.values());
					listadoValoresNuevaRespuesta=new ArrayList<String>();
					listadoValoresFila=new ArrayList<String>();
				}
				if ("preguntas".equals(event.getNewStep())) {
					mapaPreguntas=new HashMap<String, List<PreguntasCuestionario>>();
					
					for(AreasCuestionario area : listadoAreas ){
						mapaPreguntas.put(area.getArea(), new ArrayList<PreguntasCuestionario>());
					}
					
					
					
				}
				
				}
				return event.getNewStep();
			}
	
	public void aniadeArea(){
		listadoAreasString.add(nuevaArea.getLocalValue().toString());
		nuevaArea.setValue(""); 
	}
	
	public void borraArea(String nueva){
		listadoAreasString.remove(nuevaArea.getLocalValue().toString());
	}
	
	public void onSelectArea(SelectEvent event) {
		String nuevo= event.getObject().toString();    
		nuevaArea.setValue(nuevo);	
    }
	
	 public void guardaAreas(){
		 	
	    	for (int i=0; i<listadoAreasString.size(); i++){
	    		AreasCuestionario nueva=new AreasCuestionario();
	    		nueva.setArea(listadoAreasString.get(i));
	    		
	    		nueva.setOrden(i);
	    		nueva.setPreguntas(new ArrayList<PreguntasCuestionario>());
	    		listadoAreas.add(nueva);
	    	}
	    	modelo.setAreas(listadoAreas);
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
    	List<String> nuevoListado=listaTipoPreguntas.getSource();
		String seccion=tipoPersonalizado.concat(listadoValoresNuevaRespuesta.toString());
		
		//Valores RADIO y TABLA, nombres de Campo para MAtrices
		
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
		nuevoListado.add(seccion);
		listaTipoPreguntas.setSource(nuevoListado);	
		
		listadoValoresNuevaRespuesta=new ArrayList<String>();
		listadoValoresFila=new ArrayList<String>();
    }
    
    public void grabaArea(AreasCuestionario area){
    	nuevaPregunta =new PreguntasCuestionario();
    	nuevaPregunta.setArea(area);
    }
    
    public void aniadePregunta(AreasCuestionario areaSelec){
    	mapaPreguntas=new HashMap<String, List<PreguntasCuestionario>>();
    	grabaArea(areaSelec);
    	
    	String pregunta=preguntaNueva.getLocalValue().toString();
    	String tipo=nuevaRespuesta.getLocalValue().toString();		
    	
    	cargaEnLista(pregunta, tipo);
		List<PreguntasCuestionario> listaPreguntasArea= new ArrayList<PreguntasCuestionario>();
		for (PreguntasCuestionario preg : listaPreguntas) {
			AreasCuestionario area = preg.getArea();
			
			if (mapaPreguntas.get(area.getArea()) == null) {
				listaPreguntasArea = new ArrayList<>();
			}
			else {
				listaPreguntasArea = mapaPreguntas.get(area.getArea());
			}
			listaPreguntasArea.add(preg);
			mapaPreguntas.put(area.getArea(), listaPreguntasArea);
		}
		
       	}
    
    public void onSelectPregunta(SelectEvent event) {
    	PreguntasCuestionario nuevo= (PreguntasCuestionario) event.getObject();    
		preguntaNueva.setValue(nuevo.getPregunta());
		nuevaRespuesta.setValue(nuevo.getTipoRespuesta());
    }
    
    public void onReorderPregunta(){
    	for(int i=0; i < listaPreguntas.size(); i++){
    		PreguntasCuestionario preg= listaPreguntas.get(i);
    		if(preg.getPregunta().equals(preguntaNueva)){
    			preg.setOrden(i);
    		}
    	}
    }
    
    public void cargaEnLista(String pregunta, String tipo){
    	
    	AreasCuestionario areaSelec= nuevaPregunta.getArea();
    	
    	nuevaPregunta.setArea(areaSelec);
    	nuevaPregunta.setPregunta(pregunta);
    	nuevaPregunta.setTipoRespuesta(tipo);
    	nuevaPregunta.setOrden(listaPreguntas.size());
    	
    	
    	listaPreguntas.add(nuevaPregunta);
    	

    }
    	
    
  
    public void onReorderPreguntas(){
    	
    }
    
    public String guardaCuestionario(){
    	
    	
    	modelo.setCodigo("sin codigo");
    	modelo.setNombreFichero(modelo.getDescripcion());
    	modelo.setIdDocumento((long) 0);
    		

    	Map<String,List<PreguntasCuestionario>> mapa= new HashMap();
    	List<PreguntasCuestionario> listaPreguntasArea= new ArrayList<PreguntasCuestionario>();
    	for (PreguntasCuestionario preg : listaPreguntas) {
			AreasCuestionario area = preg.getArea();
			
			if (mapa.get(area.getArea()) == null) {
				listaPreguntasArea = new ArrayList<>();
			}
			else {
				listaPreguntasArea = mapa.get(area.getArea());
			}
			listaPreguntasArea.add(preg);
			mapa.put(area.getArea(), listaPreguntasArea);
		}
    	
    	for (int i=0;i < listadoAreasString.size();i++){
		List<PreguntasCuestionario> listaPregunt= mapa.get(listadoAreasString.get(i));
		modelo.getAreas().get(i).setPreguntas(listaPregunt);
	}
    	
    	modelo= modeloCuestionarioService.save(modelo);
    	
    	notificacionesService.crearNotificacionRol("Se crea nuevo modelo", SeccionesEnum.CUESTIONARIO.getDescripcion(), RoleEnum.ADMIN);
    	registroActividadService.altaRegActividad("Se crea nuevo modelo", EstadoRegActividadEnum.ALTA.name(), SeccionesEnum.CUESTIONARIO.getDescripcion());
   	
    	return "cuestionarios/modelosCuestionarios";
    	
    	
    }
    	
    	
}
