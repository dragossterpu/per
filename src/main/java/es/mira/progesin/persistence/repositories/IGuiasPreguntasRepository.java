package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;

public interface IGuiasPreguntasRepository extends CrudRepository<GuiaPasos, Long> {

	List<GuiaPasos> findByIdGuiaOrderByOrdenAsc(Guia idGuia);

}
