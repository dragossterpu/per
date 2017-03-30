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
 * Entity para almacenar las guías en BDD
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
@Table(name = "guias")
@NamedEntityGraph(name = "Guia.pasos", attributeNodes = @NamedAttributeNode("pasos"))
public class Guia extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_guias", sequenceName = "seq_guias", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_guias")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "nombre_guia", nullable = false)
    private String nombre;
    
    @Column(name = "orden")
    private Integer orden;
    
    @ManyToOne
    @JoinColumn(name = "tipo_inspeccion", foreignKey = @ForeignKey(name = "fk_g_tipo_inspeccion"), nullable = false)
    private TipoInspeccion tipoInspeccion;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idGuia")
    private List<GuiaPasos> pasos;
    
    @Column(name = "fecha_anulacion")
    private Date fechaAnulacion;
    
    @Column(name = "username_anulacion")
    private String usernameAnulacion;
    
}
