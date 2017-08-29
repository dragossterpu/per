package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity utilizada para almacenar la clase a la que pertenece un usuario.
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
@Table(name = "Clase_usuario")
public class ClaseUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLASE")
    @SequenceGenerator(name = "SEQ_CLASE", sequenceName = "SEQ_CLASE", allocationSize = 1, initialValue = 1)
    @Column(name = "id_clase")
    private Long id;
    
    /**
     * Clase de usuario.
     */
    @Column(name = "clase")
    private String clase;
    
    /**
     * Sobreescritura del método toString para por usar el SelectItemsConverter de manera genérica, devolviendo siempre
     * la clave primaria.
     */
    @Override
    public String toString() {
        return id.toString();
    }
    
}
