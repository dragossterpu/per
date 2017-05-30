package es.mira.progesin.converters;

import java.lang.reflect.Method;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.repositories.SelectFindOne;
import es.mira.progesin.services.RegistroActividadService;

/**
 * Componente que permite generar desplegables en formularios html donde las opciones están asociadas a objetos
 * 
 * @author Ezentis
 */
@Component("selectConverter")
public class SelectItemsConverter implements Converter {
    
    @Autowired
    private SelectFindOne target;
    
    @PersistenceContext
    private EntityManager entityManagerFactory;
    
    @Autowired
    private RegistroActividadService registroActividadService;
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        String value = null;
        
        if (entity != null && "".equals(entity) == Boolean.FALSE) {
            Object id = entityManagerFactory.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
            String entityName = entity.getClass().getSimpleName();
            value = id + "#" + entityName;
        }
        return value;
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object entity = null;
        if (value != null && value.contains("#")) {
            try {
                String[] idNombreEntity = value.split("#");
                String id = idNombreEntity[0];
                String nombreMetodo = "findOne" + idNombreEntity[1];
                Method metodo = SelectFindOne.class.getMethod(nombreMetodo, String.class);
                metodo.setAccessible(true);
                entity = metodo.invoke(target, id);
            } catch (Exception e) {
                registroActividadService.altaRegActividadError("SELECT CONVERTER", e);
            }
        }
        return entity;
    }
    
}
