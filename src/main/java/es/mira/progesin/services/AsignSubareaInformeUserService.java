package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.informes.AsignSubareaInformeUser;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.repositories.IAsignSubareaInformeUserRepository;

/**
 * Servicio para la gestión de la asignación de subáreas de un informe para que las completen inspectores.
 * 
 * @author EZENTIS
 */
@Service
public class AsignSubareaInformeUserService implements IAsignSubareaInformeUserService {
    
    /**
     * Repositorio de áreas/usuario de cuestionarios enviados.
     */
    @Autowired
    private IAsignSubareaInformeUserRepository asignSubareaInformeUserRepository;
    
    /**
     * Guarda una asignacion de subárea de un informe a un inspector.
     * 
     * @param asignSubareaInformeUser asignación
     * @return lista de asignaciones con id
     */
    @Override
    @Transactional(readOnly = false)
    public AsignSubareaInformeUser save(AsignSubareaInformeUser asignSubareaInformeUser) {
        return asignSubareaInformeUserRepository.save(asignSubareaInformeUser);
    }
    
    /**
     * Busca las asignaciones de subáreas de un informe a inspectores.
     * 
     * @param informe informe en curso
     * @return lista de asignaciones
     */
    @Override
    public List<AsignSubareaInformeUser> findByInforme(Informe informe) {
        return asignSubareaInformeUserRepository.findByInforme(informe);
    }
    
    /**
     * Borra todas las asignaciones de subáreas de un informe.
     * 
     * @param informe informe en curso
     */
    @Override
    public void deleteByInforme(Informe informe) {
        asignSubareaInformeUserRepository.deleteByInforme(informe);
    }
    
    /**
     * Borra las asignaciones de subáreas de un informe a un inspector.
     * 
     * @param informe informe en curso
     * @param usuario inspector
     */
    @Override
    public void deleteByInformeAndUser(Informe informe, User usuario) {
        asignSubareaInformeUserRepository.deleteByInformeAndUser(informe, usuario);
        
    }
}
