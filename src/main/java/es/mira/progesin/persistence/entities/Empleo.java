package es.mira.progesin.persistence.entities;

import java.io.Serializable;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity creada para almacenar un empleo.
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@Getter
@Setter
@Entity
@Table(name = "EMPLEO")
public class Empleo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMPLEO")
    @SequenceGenerator(name = "SEQ_EMPLEO", sequenceName = "SEQ_EMPLEO", allocationSize = 1, initialValue = 1)
    @Column(name = "ID", length = 2)
    private Long id;
    
    /**
     * Cuerpo de estado.
     */
    @ManyToOne
    @JoinColumn(name = "ID_CUERPO", foreignKey = @ForeignKey(name = "FK_EM_CUERPO"))
    private CuerpoEstado cuerpo;
    
    /**
     * Descripción.
     */
    @Column(name = "DESCRIPCION", length = 100)
    private String descripcion;
    
    /**
     * Nombre corto.
     */
    @Column(name = "NOMBRE_CORTO", length = 20)
    private String nombreCorto;
    
    /**
     * Sobreescritura del método toString para por usar el SelectItemsConverter de manera genérica, devolviendo siempre
     * la clave primaria.
     */
    @Override
    public String toString() {
        return id.toString();
    }
    
}
