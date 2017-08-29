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
 * 
 * Entidad para almacenar un Municipio.
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
@Table(name = "municipios")
public class Municipio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @SequenceGenerator(name = "seq_municipio", sequenceName = "seq_municipio", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_municipio")
    @Column(name = "id")
    private Long id;
    
    /**
     * Nombre del municipio.
     */
    @Column(name = "name", length = 100)
    private String name;
    
    /**
     * Provincia del municipio.
     */
    @ManyToOne
    @JoinColumn(name = "code_province", foreignKey = @ForeignKey(name = "FK_PROVINCIA"), nullable = false)
    private Provincia provincia;
    
    /**
     * Sobreescritura del método toString para por usar el SelectItemsConverter de manera genérica, devolviendo siempre
     * la clave primaria.
     */
    @Override
    public String toString() {
        return id.toString();
    }
}
