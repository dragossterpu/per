package es.mira.progesin.services;

import java.util.Map;

import es.mira.progesin.persistence.entities.Parametro;

public interface IParametroService {

	Map<String, Map<String, String>> getMapaParametros();
	
	
	

}