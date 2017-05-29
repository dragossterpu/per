package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IMunicipioRepository;

/**
 * Implementación del servicio para la gestión de municipios.
 * @author EZENTIS
 *
 */
@Service
public class MunicipioService implements IMunicipioService {
    
    /**
     * Variable utilizada para inyectar el repositorio de municipios.
     * 
     */
    @Autowired
    private IMunicipioRepository municipioRepository;
    
    /**
     * Variable utilizada para inyectar el servicio del registro de actividad.
     * 
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Comprueba si existe un municipio conociendo su nombre.
     * 
     * @param name nombre del municipio
     * @param provincia a la que pertenece el municipio
     * @return valor booleano
     */
    @Override
    public boolean existeByNameIgnoreCaseAndProvincia(String name, Provincia provincia) {
        return municipioRepository.existsByNameIgnoreCaseAndProvincia(name, provincia);
    }
    
    /**
     * Guarda un nuevo municipio.
     * @param nombre del municipio
     * @param provincia a la que pertenece el municipio
     * @return municipio creado (true si es guardado correctamente)
     */
    @Override
    @Transactional(readOnly = false)
    public Municipio crearMunicipio(String nombre, Provincia provincia) {
        Municipio nuevoMunicipio = null;
        
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            nuevoMunicipio = new Municipio();
            nuevoMunicipio.setName(nombre);
            nuevoMunicipio.setProvincia(provincia);
            municipioRepository.save(nuevoMunicipio);
            
            String descripcion = "El usuario " + user + " ha creado el nuevo municipio " + nombre;
            
            registroActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.INSPECCION.name());
            
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
        return nuevoMunicipio;
        
    }
    
    /**
     * Busca los municipios pertenecientes a una provincia.
     * 
     * @param provincia que queremos consultar
     * @return lista de municipios por provincia
     */
    @Override
    public List<Municipio> findByProvincia(Provincia provincia) {
        return municipioRepository.findByProvincia(provincia);
    }
    
}
