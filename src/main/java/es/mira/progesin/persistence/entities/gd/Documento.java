package es.mira.progesin.persistence.entities.gd;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import es.mira.progesin.persistence.entities.Inspeccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad Documento. Cualquier archivo subido a la aplicación se almacena en esta tabla.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion
 * @see es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario
 * @see es.mira.progesin.web.beans.SolicitudDocPreviaBean
 * @see es.mira.progesin.web.beans.ProvisionalSolicitudBean
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@ToString
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "documentos")
@NamedEntityGraph(name = "Documento.fichero", attributeNodes = @NamedAttributeNode("fichero"))
public class Documento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la entidad, generada por medio de secuencia.
     */
    @Id
    @SequenceGenerator(name = "seq_documentos", sequenceName = "seq_documentos", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_documentos")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Fichero enlazado al documento.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_FICHERO", foreignKey = @ForeignKey(name = "FK_D_FICHERO"))
    private DocumentoBlob fichero;
    
    /**
     * ContentType del fichero adjunto.
     */
    @Column(name = "tipoContenido", nullable = false)
    private String tipoContenido;
    
    /**
     * Nombre del documento.
     */
    @Column(name = "nombre")
    private String nombre;
    
    /**
     * Fecha en la que se da de baja el documento.
     */
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    /**
     * Username del usuario que da de baja el documento.
     */
    @Column(name = "username_baja")
    private String usernameBaja;
    
    /**
     * Fecha de creación del documento.
     */
    @CreatedDate
    @Column(name = "fecha_alta")
    private Date fechaAlta;
    
    /**
     * Username del usuario que crea el documento.
     */
    @CreatedBy
    @Column(name = "username_alta")
    private String usernameAlta;
    
    /**
     * Descripción.
     */
    @Column(name = "descripcion", length = 2000)
    private String descripcion;
    
    /**
     * Palabras clave del documento.
     */
    @Column(name = "materia_indexada", length = 2000)
    private String materiaIndexada;
    
    /**
     * Tipo de documento.
     */
    @ManyToOne
    @JoinColumn(name = "tipoDocumento")
    private TipoDocumento tipoDocumento;
    
    /**
     * Inspecciones a las que está asignado el documento.
     */
    @ManyToMany
    @JoinTable(name = "documentos_inspeccion", joinColumns = {
            @JoinColumn(name = "id_documento") }, inverseJoinColumns = { @JoinColumn(name = "id_inspeccion") })
    private List<Inspeccion> inspeccion;
    
}
