package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;

/**
 * Repositorio de subareas de modelo de informe.
 * 
 * @author EZENTIS
 */
public interface ISubareaInformeRepository extends CrudRepository<SubareaInforme, Long> {
    /**
     * Elimina las subáreas de un área pasada como parámetro.
     * 
     * @param area Área de la que se desea eliminar las subáreas.
     */
    @Transactional(readOnly = false)
    void deleteByArea(AreaInforme area);
    
    /**
     * Comprueba el número de subáreas que tiene un informe sin responder.
     * 
     * @param idInforme informe que se quiere validar.
     * @return el número de subáreas que tiene un informe sin responder. Será 0 si todo es correcto.
     */
    @Query(value = "select count(ips.id_subarea) from informes i, modelos_informe_personalizados mip, informe_personal_subareas ips "
            + "where i.informe_personal_id = mip.id and mip.id = ips.id_informe_pers and i.id = ?1 and ips.id_subarea not in "
            + "(select r.subarea_id from respuestas_informe r where r.informe_id = i.id and r.texto is not null)", nativeQuery = true)
    Long buscaSubareasSinResponder(Long idInforme);
    
    /**
     * Devuelve el listado de subáreas pertenecientes a un área pasada como parámetro.
     * 
     * @param area Área de la que se desean obtener las subáreas.
     * @return Lista de subáreas.
     */
    List<SubareaInforme> findByArea(AreaInforme area);
    
    // @Override
    // List<SubareaInforme> findAll();
    
}
