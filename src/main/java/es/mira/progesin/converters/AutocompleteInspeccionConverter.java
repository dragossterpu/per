package es.mira.progesin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.services.IInspeccionesService;

/**
 * Conversor usado para el autocompletado de inspecciones.
 * 
 * @author EZENTIS
 *
 */
@Component("autocompleteInspeccion")
public class AutocompleteInspeccionConverter implements Converter {
    
    /**
     * Servicio de inspecciones.
     */
    @Autowired
    private IInspeccionesService inspecionesService;
    
    /**
     * Devuelve la inspección a partir del id (value).
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Inspeccion inspeccion = null;
        if (value != null) {
            inspeccion = inspecionesService.findInspeccionById(Long.valueOf(value));
        }
        return inspeccion;
    }
    
    /**
     * Devuelve el id de la inspección como String.
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String id = null;
        if (value != null) {
            id = ((Inspeccion) value).getId().toString();
        }
        return id;
    }
    
}
