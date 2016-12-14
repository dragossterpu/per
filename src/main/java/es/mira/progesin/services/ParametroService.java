package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Parametro;
import es.mira.progesin.persistence.repositories.IParametrosRepository;

@Service
public class ParametroService implements IParametroService {
	
	
	
	@Autowired
	IParametrosRepository parametrosRepo;
	
	
	
	@Override
	public Map<String,Map<String, String>> getMapaParametros(){
		List<String> secciones=parametrosRepo.findSecciones();
		Map<String,Map<String, String>> mapa = new HashMap<String, Map<String, String>>();
		
		List<Parametro> listaParam=new ArrayList<Parametro>();
		
		for(String seccion:secciones){
			listaParam=parametrosRepo.findParamByParamSeccion(seccion);
			Map<String,String> mapaParametros=new HashMap<String, String>();
			for(Parametro param:listaParam){
				mapaParametros.put(param.getParam().getClave(), param.getParam().getValor());
			}
			mapa.put(seccion, mapaParametros);
		}
		
		
		return mapa;
	}



	

	
	
}
