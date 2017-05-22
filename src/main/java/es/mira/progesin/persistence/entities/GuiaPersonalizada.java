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
 * Entidad que se empleará para el almacenamiento y recuperación de objetos de tipo Guía personalizada en base de datos
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "guiaPersonalizada")
public class GuiaPersonalizada implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_guiapersonalizada", sequenceName = "seq_guiapersonalizada", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_guiapersonalizada")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "nombre_guia_personalizada", nullable = false, length = 100)
    private String nombreGuiaPersonalizada;
    
    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;
    
    @CreatedBy
    @Column(name = "username_creacion", nullable = false)
    private String usernameCreacion;
    
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    @Column(name = "username_baja")
    private String usernameBaja;
    
    @Column(name = "fecha_anulacion")
    private Date fechaAnulacion;
    
    @Column(name = "username_anulacion")
    private String usernameAnulacion;
    
    @ManyToOne
    @JoinColumn(name = "id_modelo_guia", foreignKey = @ForeignKey(name = "fk_gpr_modelo_guia"), nullable = false)
    private Guia guia;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "guia_personalizada_pasos", joinColumns = @JoinColumn(name = "id_guia_pers"), inverseJoinColumns = @JoinColumn(name = "id_paso_elegido"))
    private List<GuiaPasos> pasosElegidos;
    
    // @ManyToOne
    // @JoinColumn(name = "inspeccion", foreignKey = @ForeignKey(name = "fk_gpr_inspeccion"))
    // private Inspeccion inspeccion;
    
    @ManyToMany
    @JoinTable(name = "guia_inspeccion", joinColumns = { @JoinColumn(name = "id_guia") }, inverseJoinColumns = {
            @JoinColumn(name = "id_inspeccion") })
    private List<Inspeccion> inspeccion;
}
