package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;

public interface IGuiasPasosRepository extends CrudRepository<GuiaPasos, Long> {

	List<GuiaPasos> findByIdGuiaAndFechaBajaIsNullOrderByOrdenAsc(Guia idGuia);

	@Query("select c.pasosElegidos from GuiaPersonalizada c where c.id = :idGuiaPersonalizada")
	List<GuiaPasos> findPasosElegidosGuiaPersonalizada(@Param("idGuiaPersonalizada") Long idGuiaPersonalizada);

	@Query(value = "select distinct p.* from guia_Pasos p, guia_personalizada_pasos cpp, guia_Personalizada cp "
			+ "where p.id = cpp.id_paso_elegido and cpp.id_guia_pers = cp.id and p.id = ?1", nativeQuery = true)
	GuiaPasos findPasoExistenteEnGuiasPersonalizadas(Long idPregunta);

}
