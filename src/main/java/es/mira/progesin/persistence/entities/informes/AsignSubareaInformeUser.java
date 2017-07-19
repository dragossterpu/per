/**
 * 
 */
package es.mira.progesin.persistence.entities.informes;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.mira.progesin.persistence.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad que relaciona un subarea de un informe con el usuario que está respondiendola.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.web.beans.informes.InformeBean
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "ASIGN_SUBAREA_INFORME_USER")
@IdClass(AsignSubareaInformeUserId.class)
public class AsignSubareaInformeUser implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Subarea del informe.
     */
    @ManyToOne
    @Id
    @JoinColumn(name = "subarea_id", foreignKey = @ForeignKey(name = "FK_ASIGN_SUBAREA"), nullable = false)
    private SubareaInforme subarea;
    
    /**
     * Informe.
     */
    @ManyToOne
    @Id
    @JoinColumn(name = "informe_id", foreignKey = @ForeignKey(name = "FK_ASIGN_INFOR"), nullable = false)
    Informe informe;
    
    /**
     * Usuario asignado.
     */
    @ManyToOne
    @JoinColumn(name = "username", foreignKey = @ForeignKey(name = "FK_ASIGN_USER"), nullable = false)
    User user;
    
}
