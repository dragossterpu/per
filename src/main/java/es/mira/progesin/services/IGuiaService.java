package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.web.beans.GuiaBusqueda;

/**
 * 
 * Servicio de guías.
 * 
 * @author EZENTIS
 * 
 */

public interface IGuiaService {
    
    /**
     * 
     * Devuelve una lista de guías en función de los criterios de búsqueda recibidos como parámetro. El listado se
     * devuelve paginado.
     * 
     * @param first Primer elemento del listado
     * @param pageSize Número máximo de registros recuperados
     * @param sortField Campo por el que sed realiza la ordenación del listado
     * @param sortOrder Sentido de la ordenación
     * @param busqueda Objeto que contiene los parámetros de búsqueda
     * 
     * @return List<Guia> Listado resultante de la búsqueda
     * 
     */
    
    public List<Guia> buscarGuiaPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            GuiaBusqueda busqueda);
    
    /**
     * Devuelve el número total de registros resultado de la búsqueda.
     * 
     * @param busqueda Objeto que contiene los parámetros de búsqueda
     * @return número de registros resultantes de la búsqueda
     */
    
    public int getCounCriteria(GuiaBusqueda busqueda);
    
    /**
     * 
     * Devuelve la lista de pasos contenidos en una guía recibida como parámetro.
     * 
     * @return List<GuiaPasos>
     * @param guia Guía de la que se desean recuperar los pasos
     * 
     */
    
    public List<GuiaPasos> listaPasos(Guia guia);
    
    /**
     * Devuelve la lista de pasos que no han sido dados de baja y están contenidos en una guía recibida como parámetro.
     *
     * @param guia Guía de la que se desean recuperar los pasos
     * @return Lista de pasos
     */
    public List<GuiaPasos> listaPasosNoNull(Guia guia);
    
    /**
     *
     * Almacena en BDD una guía pasada como parámetro.
     *
     * @param guia a guardar
     * @return Guia guardada
     *
     */
    
    public Guia guardaGuia(Guia guia);
    
    /**
     *
     * Comprueba la existencia de un paso recibido como parámetro en las guías personalizadas.
     *
     * @param paso Paso del que queremos verificar la existencia
     * @return boolean Existencia o no del paso
     * 
     */
    
    public boolean existePaso(GuiaPasos paso);
    
    /**
     * Permite la eliminación de una guía de la base de datos.
     *
     * @param guia guía a eliminar
     */
    public void eliminar(Guia guia);
    
    /**
     * Comprueba si existen modelos de gías asociadas a un determinado tipo de inspección.
     *
     * @param tipo tipo de Inspección
     * @return boolean existencia o no de la asociación
     */
    boolean existeByTipoInspeccion(TipoInspeccion tipo);
    
}