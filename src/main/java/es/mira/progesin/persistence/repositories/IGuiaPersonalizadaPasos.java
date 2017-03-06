package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;

public interface IGuiaPersonalizadaPasos extends CrudRepository<GuiaPasos, Long> {
	List<GuiaPasos> findByIdGuiaOrderByOrdenAsc(GuiaPersonalizada idGuia);

}
