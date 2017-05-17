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
import lombok.ToString;

/**
 * Entity que define la clase a la que pertenece un usuario
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "Clase_usuario")
public class ClaseUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLASE")
    @SequenceGenerator(name = "SEQ_CLASE", sequenceName = "SEQ_CLASE", allocationSize = 1, initialValue = 1)
    @Column(name = "id_clase")
    private Long id;
    
    @Column(name = "clase")
    private String clase;
    
}
