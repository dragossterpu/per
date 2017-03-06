package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.web.beans.GuiaPersonalizadaBusqueda;

public interface IGuiaPersonalizadaService {

	void delete(GuiaPersonalizada guia);

	GuiaPersonalizada save(GuiaPersonalizada guia);

	public List<GuiaPersonalizada> buscarGuiaPorCriteria(GuiaPersonalizadaBusqueda busqueda);

	public List<GuiaPasos> listaPasos(GuiaPersonalizada guia);

	public List<GuiaPersonalizada> findAll();

}
