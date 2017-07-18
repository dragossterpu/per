package es.mira.progesin.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;

/**
 * Servicio para nuevos modelos de informe.
 * 
 * @author EZENTIS
 *
 */

public interface INuevoModeloInformeService {
    
    /**
     * Guarda en base de datos un nuevo modelo de informe.
     * 
     * @param nuevoModelo Modelo de informe a guardar
     * @param listaAreas Lsita de áreas del informe
     * @return Modelo guardado
     */
    @Transactional(readOnly = false)
    ModeloInforme guardaModelo(ModeloInforme nuevoModelo, List<AreaInforme> listaAreas);
    
    /**
     * Añade un área al modelo.
     * 
     * @param area Área a añadir
     * @param listaAreas Lista de áreas del modelo
     */
    void aniadeArea(String area, List<AreaInforme> listaAreas);
    
    /**
     * Añade un subárea a un área.
     * @param area Área a la que se añadirá el subárea.
     * @param subArea Subárea a añadir
     */
    void aniadeSubarea(AreaInforme area, String subArea);
    
    /**
     * Copia la lista de áreas de un modelo recibido como parámetro.
     * 
     * @param modelo Modelo del que se desea copiar la lista de áreas.
     * @return Lista de áreas copiada.
     */
    List<AreaInforme> clonarListaAreas(ModeloInforme modelo);
}
