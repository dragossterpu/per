package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;

/*******************************************
 * Repositorio de operaciones de base de datos para la entidad GuiasPasos
 * 
 * @author Ezentis
 * 
 *****************************************/
public interface IGuiasPasosRepository extends CrudRepository<GuiaPasos, Long> {

	/*******************************************************************************
	 * 
	 * Recupera una lista de los pasos contenidos en una guía pasada como parámetro en orden ascendente. Únicamente
	 * recupera los pasos que no tengan fecha de baja.
	 * 
	 * @return List<GuiaPasos>
	 * @param Guía
	 * 
	 *****************************************************************************/

	List<GuiaPasos> findByIdGuiaAndFechaBajaIsNullOrderByOrdenAsc(Guia idGuia);

	/*******************************************************************************
	 * 
	 * Recupera una lista de los pasos elegidos en una guía personalizada cuyo id se pasa como parámetro.
	 * 
	 * @return List<GuiaPasos>
	 * @param Long
	 * 
	 *****************************************************************************/

	@Query("select c.pasosElegidos from GuiaPersonalizada c where c.id = :idGuiaPersonalizada")
	List<GuiaPasos> findPasosElegidosGuiaPersonalizada(@Param("idGuiaPersonalizada") Long idGuiaPersonalizada);

	/*******************************************************************************
	 * 
	 * Verifica si el paso existe en alguna guía personalizada
	 * 
	 * @return GuiaPasos
	 * @param Long
	 * 
	 *****************************************************************************/

	@Query(value = "select distinct p.* from guia_Pasos p, guia_personalizada_pasos cpp, guia_Personalizada cp "
			+ "where p.id = cpp.id_paso_elegido and cpp.id_guia_pers = cp.id and p.id = ?1", nativeQuery = true)
	GuiaPasos findPasoExistenteEnGuiasPersonalizadas(Long idPaso);

}
