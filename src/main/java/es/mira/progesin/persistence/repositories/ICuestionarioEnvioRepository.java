package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;

/**
 * 
 * Interfaz del repositorio de cuestionarios enviados
 * 
 * @author Ezentis
 *
 */
public interface ICuestionarioEnvioRepository extends CrudRepository<CuestionarioEnvio, Long> {
    
    /**
     * Recupera el cuestionario enviado no finalizado perteneciente a un destinatario (no puede haber más de una)
     * 
     * @author Ezentis
     * @param correo
     * @return cuestionario enviado
     */
    CuestionarioEnvio findByCorreoEnvioAndFechaFinalizacionIsNullAndFechaAnulacionIsNull(String correo);
    
    /**
     * Recupera el cuestionario no finalizado perteneciente a un destinatario (no puede haber más de uno)
     * 
     * @author Ezentis
     * @param inspeccion
     * @return cuestionario enviado
     */
    CuestionarioEnvio findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndInspeccion(Inspeccion inspeccion);
    
    /**
     * Comprueba si existe algún cuestionario enviado asociado a un modelo de cuestionario personalizado
     * 
     * @author Ezentis
     * @param cuestionario
     * @return boolean si o no
     */
    boolean existsByCuestionarioPersonalizado(CuestionarioPersonalizado cuestionario);
    
    /**
     * Recupera los cuestionarios enviados que aún no han sido cumplimentados
     * 
     * @author Ezentis
     * @return lista de cuestionarios enviados
     */
    List<CuestionarioEnvio> findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndFechaCumplimentacionIsNull();
}
