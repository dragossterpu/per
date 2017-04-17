package es.mira.progesin.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Parametro;
import es.mira.progesin.persistence.repositories.IParametrosRepository;

/**
 * @author EZENTIS
 * 
 * Sevicio para la clase Parametro
 *
 */
@Service
public class ParametroService implements IParametroService {
    
    @Autowired
    private IParametrosRepository parametrosRepo;
    
    @Override
    public Map<String, Map<String, String>> getMapaParametros() {
        List<String> secciones = parametrosRepo.findSecciones();
        Map<String, Map<String, String>> mapa = new HashMap<>();
        
        List<Parametro> listaParam;
        
        for (String seccion : secciones) {
            listaParam = parametrosRepo.findParamByParamSeccion(seccion);
            Map<String, String> mapaParametros = new HashMap<>();
            for (Parametro param : listaParam) {
                mapaParametros.put(param.getParam().getClave(), param.getParam().getValor());
            }
            mapa.put(seccion, mapaParametros);
        }
        
        return mapa;
    }
    
}
