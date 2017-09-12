package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity para almacenar las guías en BDD.
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "guias")
@NamedEntityGraph(name = "Guia.pasos", attributeNodes = @NamedAttributeNode("pasos"))
public class Guia extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la guía. Generado mediante una secuencia.
     */
    @Id
    @SequenceGenerator(name = "seq_guias", sequenceName = "seq_guias", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_guias")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Nombre de la guía.
     */
    @Column(name = "nombre_guia", nullable = false)
    private String nombre;
    
    /**
     * Orden en que se mostrará la guía.
     */
    @Column(name = "orden")
    private Integer orden;
    
    /**
     * Tipo de inspección a la que se adscribe la guía.
     */
    @ManyToOne
    @JoinColumn(name = "tipo_inspeccion", foreignKey = @ForeignKey(name = "fk_g_tipo_inspeccion"), nullable = false)
    private TipoInspeccion tipoInspeccion;
    
    /**
     * Listado de los pasos contenidos en la guía.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idGuia")
    private List<GuiaPasos> pasos;
    
    /**
     * Fecha en la que se da de baja la guía.
     */
    @Column(name = "fecha_anulacion")
    private Date fechaAnulacion;
    
    /**
     * Username del usuario que da de baja la guía.
     */
    @Column(name = "username_anulacion")
    private String usernameAnulacion;
    
    /**
     * Sobreescritura del método toString para poder utilizarlo en el conversor implementado para SelectItemsConverter.
     */
    @Override
    public String toString() {
        return id.toString();
    }
}
