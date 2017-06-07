package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.informes.Informe;

/**
 * Servicio de informes de inspección.
 * 
 * @author EZENTIS
 */
public interface IInformeService {
    
    /**
     * Guarda la información de un informe en la bdd.
     * 
     * @param informe solicitud creada o modificada
     * @return informe sincronizado
     */
    Informe save(Informe informe);
    
}
