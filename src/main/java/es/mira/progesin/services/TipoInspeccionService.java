package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.repositories.ITipoInspeccionRepository;

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
     * Borra físicamente un tipo de la base de datos.
     * 
     * @param tipo a borrar
     */
    @Override
    @Transactional(readOnly = false)
    public void borrarTipo(TipoInspeccion tipo) throws ProgesinException {
        tipoInspeccionRepository.delete(tipo);
    }
    
    /**
     * @param entity tipo a guaradar
     * @return boolean (alta correcta)
     */
    @Override
    @Transactional(readOnly = false)
    public TipoInspeccion guardarTipo(TipoInspeccion entity) throws ProgesinException {
        return tipoInspeccionRepository.save(entity);
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
