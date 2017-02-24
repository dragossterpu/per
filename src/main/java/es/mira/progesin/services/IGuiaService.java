package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.web.beans.GuiaBusqueda;

public interface IGuiaService {

	public List<Guia> buscarGuiaPorCriteria(GuiaBusqueda busqueda);

	public List<GuiaPasos> listaPasos(Guia guia);

	public Guia guardaGuia(Guia guia);
}