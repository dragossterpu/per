package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.RespuestaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IInformeRepository;
import es.mira.progesin.persistence.repositories.IRespuestaInformeRepository;
import es.mira.progesin.persistence.repositories.ISubareaInformeRepository;

/**
 * Servicio de informes de inspección.
 * 
 * @author EZENTIS
 */
@Service
public class InformeService implements IInformeService {
    
    /**
     * Repositorio de informes.
     */
    @Autowired
    private IInformeRepository informeRepository;
    
    /**
     * Repositorio respuestas.
     */
    @Autowired
    private transient IRespuestaInformeRepository respuestaInformeRepository;
    
    /**
     * Repositorio de subareas de informe.
     */
    @Autowired
    private transient ISubareaInformeRepository subareaInformeRepository;
    
    /**
     * Guarda la información de un informe en la bdd.
     * 
     * @param informe informe creado o modificado
     * @return informe sincronizado
     */
    @Override
    public Informe save(Informe informe) {
        return informeRepository.save(informe);
    }
    
    /**
     * Guarda el informe y todas las subareas que hayan sido respondidas.
     * 
     * @param informe informe
     * @param mapaRespuestas respuestas
     * @return informe actualizado
     */
    @Override
    @Transactional(readOnly = false)
    public Informe saveConRespuestas(Informe informe, Map<SubareaInforme, String> mapaRespuestas) {
        Informe informeActualizado = informeRepository.findOne(informe.getId());
        final List<RespuestaInforme> respuestas = new ArrayList<>();
        mapaRespuestas.forEach((subarea, respuesta) -> {
            if (respuesta != null) {
                subarea = subareaInformeRepository.findOne(subarea.getId());
                respuestas.add(new RespuestaInforme(informeActualizado, subarea, respuesta.getBytes()));
            }
        });
        informeActualizado.setRespuestas(respuestaInformeRepository.save(respuestas));
        return informeRepository.save(informeActualizado);
    }
    
    /**
     * Recupera un informe con sus respuestas a partir de su id.
     * 
     * @param id id del informe
     * @return informe completo
     */
    @Override
    public Informe findOne(Long id) {
        return informeRepository.findOne(id);
    }
    
    /**
     * Recupera todos los informes.
     * 
     * @return listado de informes
     */
    // TODO cambiar por criteria
    @Override
    public List<Informe> findAll() {
        return (List<Informe>) informeRepository.findAll();
    }
    
}
