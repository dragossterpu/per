package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * Entidad que se empleará para el almacenamiento y recuperación de objetos de tipo Guía personalizada en base de datos.
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "guiaPersonalizada")
public class GuiaPersonalizada implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la guía que se genera mediante una secuencia.
     */
    @Id
    @SequenceGenerator(name = "seq_guiapersonalizada", sequenceName = "seq_guiapersonalizada", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_guiapersonalizada")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Nombre de la guía personalizada.
     */
    @Column(name = "nombre_guia_personalizada", nullable = false, length = 100)
    private String nombreGuiaPersonalizada;
    
    /**
     * Fecha en la que se crea la guía personalizada.
     */
    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;
    
    /**
     * Username que crea la guía personalizada.
     */
    @CreatedBy
    @Column(name = "username_creacion", nullable = false)
    private String usernameCreacion;
    
    /**
     * Fecha en la que se da de baja la guía personalizada.
     */
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    /**
     * Username del usuario en que se da de baja la guía personalizada.
     */
    @Column(name = "username_baja")
    private String usernameBaja;
    
    /**
     * Fecha en la que se anula la guía personalizada.
     */
    @Column(name = "fecha_anulacion")
    private Date fechaAnulacion;
    
    /**
     * Username del usuario que anula la guía personalizada.
     */
    @Column(name = "username_anulacion")
    private String usernameAnulacion;
    
    /**
     * Modelo de guía desde la que se ha creado la guía personalizada.
     */
    @ManyToOne
    @JoinColumn(name = "id_modelo_guia", foreignKey = @ForeignKey(name = "fk_gpr_modelo_guia"), nullable = false)
    private Guia guia;
    
    /**
     * Pasos que se han seleccionado.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "guia_personalizada_pasos", joinColumns = @JoinColumn(name = "id_guia_pers"), inverseJoinColumns = @JoinColumn(name = "id_paso_elegido"))
    private List<GuiaPasos> pasosElegidos;
    
    /**
     * Inspecciones asignadas a la guía personalizada.
     */
    @ManyToMany
    @JoinTable(name = "guia_inspeccion", joinColumns = { @JoinColumn(name = "id_guia") }, inverseJoinColumns = {
            @JoinColumn(name = "id_inspeccion") })
    private List<Inspeccion> inspeccion;
}
