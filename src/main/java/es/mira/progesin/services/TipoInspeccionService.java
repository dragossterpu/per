package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.AdministracionAccionEnum;
import es.mira.progesin.persistence.repositories.ITipoInspeccionRepository;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * Implementación del servicio para la gestión de tipos de inspección.
 * @author EZENTIS
 *
 */
@Service
public class TipoInspeccionService implements ITipoInspeccionService {
    
    /**
     * Variable utilizada para inyectar el repositorio de tipos de inspección.
     * 
     */
    @Autowired
    ITipoInspeccionRepository tipoInspeccionRepository;
    
    /**
     * Variable usada para actualizar la lista cargada en el contexto de la aplicación.
     */
    @Autowired
    private ApplicationBean applicationBean;
    
    /**
     * Borra físicamente un tipo de la base de datos.
     * 
     * @param tipo a borrar
     */
    @Override
    @Transactional(readOnly = false)
    public void borrarTipo(TipoInspeccion tipo) {
        tipoInspeccionRepository.delete(tipo);
        applicationBean.actualizarApplicationBean(tipo, applicationBean.getListaTiposInspeccion(),
                AdministracionAccionEnum.BAJA);
    }
    
    /**
     * Guarda el tipo de inspección en BBDD.
     * 
     * @param entity tipo a guardar
     * @param accion alta/baja/modificación
     * @return boolean (alta correcta)
     */
    @Override
    @Transactional(readOnly = false)
    public TipoInspeccion guardarTipo(TipoInspeccion entity, AdministracionAccionEnum accion) {
        TipoInspeccion tipoInspeccionActualizada = tipoInspeccionRepository.save(entity);
        applicationBean.actualizarApplicationBean(tipoInspeccionActualizada, applicationBean.getListaTiposInspeccion(),
                accion);
        return tipoInspeccionActualizada;
    }
    
    /**
     * Comprueba si existe un tipo de inspección comparando por su código.
     * 
     * @param codigo del tipo
     * @return resultado de la consulta.
     */
    @Override
    public boolean existeByCodigoIgnoreCase(String codigo) {
        return tipoInspeccionRepository.existsByCodigoIgnoreCase(codigo);
    }
    
    /**
     * Busca todos los tipos de inspección.
     * @return lista tipos isnpecciones
     */
    @Override
    public List<TipoInspeccion> buscaTodos() {
        return (List<TipoInspeccion>) tipoInspeccionRepository.findAll();
    }
    
}
