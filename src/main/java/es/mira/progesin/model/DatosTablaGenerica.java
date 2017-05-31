package es.mira.progesin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity donde se guardan las respuestas de tipo tabla o matriz de un cuestionario.
 * 
 * @author EZENTIS
 *
 */
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@Table(name = "respuesta_datos_tabla")
public class DatosTablaGenerica implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * clave de la tabla.
     */
    @Id
    @SequenceGenerator(name = "seq_respuestatabla", sequenceName = "seq_respuestatabla", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_respuestatabla")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * nombre de la fila en caso de que sea una matriz.
     */
    private String nombreFila;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo01;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo02;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo03;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo04;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo05;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo06;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo07;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo08;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo09;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo10;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo11;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo12;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo13;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo14;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo15;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo16;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo17;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo18;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo19;
    
    /**
     * columna de tabla o matriz.
     */
    private String campo20;
    
}
