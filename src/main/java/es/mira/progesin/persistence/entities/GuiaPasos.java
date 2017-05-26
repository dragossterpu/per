package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * 
 * Entidad para el almacenamiento de pasos de guía.
 * 
 * @author Ezentis
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "guiaPasos")
public class GuiaPasos implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del paso. Se genera a través de una secuencia.
     */
    @Id
    @SequenceGenerator(name = "seq_pasosguia", sequenceName = "seq_pasosguia", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pasosguia")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Identificador de la guía a la que están asignados los pasos.
     */
    @ManyToOne
    @JoinColumn(name = "idGuia", foreignKey = @ForeignKey(name = "fk_gp_Guia"))
    private Guia idGuia;
    
    /**
     * Texto descriptivo del paso.
     */
    @Column(name = "paso", nullable = false, length = 2000)
    private String paso;
    
    /**
     * Orden del paso en la guía.
     */
    @Column(name = "orden", nullable = false)
    private Integer orden;
    
    /**
     * Fecha de baja del paso.
     */
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    /**
     * Usuario que da de baja el paso.
     */
    @Column(name = "username_baja")
    private String usernameBaja;
    
    /**
     * Implementación del método hashCode.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    /**
     * Implentación del método equals.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GuiaPasos other = (GuiaPasos) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
