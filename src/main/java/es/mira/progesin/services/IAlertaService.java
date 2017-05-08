package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.RoleEnum;

/****************************************************
 * 
 * Interfaz del servicio de Alertas
 * 
 * @author Ezentis
 * 
 **************************************************/

public interface IAlertaService {
    void delete(Long id);
    
    void deleteAll();
    
    boolean exists(Long id);
    
    Alerta findOne(Long id);
    
    List<Alerta> findByFechaBajaIsNull();
    
    void crearAlertaUsuario(String seccion, String descripcion, String usuario);
    
    void crearAlertaRol(String seccion, String descripcion, RoleEnum rol);
    
    void crearAlertaRol(String seccion, String descripcion, List<RoleEnum> roles);
    
    void crearAlertaEquipo(String seccion, String descripcion, Inspeccion inspeccion);
    
    void crearAlertaJefeEquipo(String seccion, String descripcion, Inspeccion inspeccion);
    
    List<Alerta> buscarPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder, String usuario);
    
    int getCounCriteria(String usuario);
}
