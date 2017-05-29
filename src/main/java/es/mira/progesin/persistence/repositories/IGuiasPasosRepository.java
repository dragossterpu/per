package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;

/**
 * Repositorio de operaciones de base de datos para la entidad GuiasPasos.
 * 
 * @author EZENTIS
 * 
 */
public interface IGuiasPasosRepository extends CrudRepository<GuiaPasos, Long> {
    
    /**
     * 
     * Recupera una lista de los pasos contenidos en una guía pasada como parámetro en orden ascendente. Únicamente
     * recupera los pasos que no tengan fecha de baja.
     * 
     * @param idGuia Guía de la que se desea obtener el listado de pasos
     * @return List<GuiaPasos> Listado de pasos resultante
     * 
     */
    
    List<GuiaPasos> findByIdGuiaAndFechaBajaIsNullOrderByOrdenAsc(Guia idGuia);
    
    /**
     * Recupera una lista de los pasos contenidos en una guía pasada como parámetro en orden ascendente.
     * 
     * @param idGuia Guía de la que se desea obtener el listado de pasos
     * @return List<GuiaPasos> Listado de pasos resultante
     * 
     */
    List<GuiaPasos> findByIdGuiaOrderByOrdenAsc(Guia idGuia);
    
    /**
     * Recupera una lista de los pasos elegidos en una guía personalizada cuyo id se pasa como parámetro.
     * 
     * @param idGuiaPersonalizada Identificador de la guí de la que se quieren recuperar sus pasos
     * @return Listado de los pasos contenidos en la guía
     */
    
    @Query(value = "select gp.* from Guia_Personalizada_pasos gpp, guia_pasos gp where gpp.id_paso_elegido=gp.id and gpp.id_guia_pers=:idGuiaPersonalizada order by gp.orden", nativeQuery = true)
    List<GuiaPasos> findPasosElegidosGuiaPersonalizada(@Param("idGuiaPersonalizada") Long idGuiaPersonalizada);
    
    /**
     * Verifica si el paso existe en alguna guía personalizada.
     * 
     * @param idPaso Identificador del paso que queremos verificar si está asociado
     * @return Paso asociado
     * 
     */
    
    @Query(value = "select distinct p.* from guia_Pasos p, guia_personalizada_pasos cpp, guia_Personalizada cp "
            + "where p.id = cpp.id_paso_elegido and cpp.id_guia_pers = cp.id and p.id = ?1", nativeQuery = true)
    GuiaPasos findPasoExistenteEnGuiasPersonalizadas(Long idPaso);
    
}
