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
    
    @Id
    @SequenceGenerator(name = "seq_documentos", sequenceName = "seq_documentos", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_documentos")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_FICHERO", foreignKey = @ForeignKey(name = "FK_D_FICHERO"))
    private DocumentoBlob fichero;
    
    @Column(name = "tipoContenido", nullable = false)
    private String tipoContenido;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    @Column(name = "username_baja")
    private String usernameBaja;
    
    @CreatedDate
    @Column(name = "fecha_alta")
    private Date fechaAlta;
    
    @CreatedBy
    @Column(name = "username_alta")
    private String usernameAlta;
    
    @Column(name = "descripcion", length = 2000)
    private String descripcion;
    
    @Column(name = "materia_indexada", length = 2000)
    private String materiaIndexada;
    
    @ManyToOne
    @JoinColumn(name = "tipoDocumento")
    private TipoDocumento tipoDocumento;
    
    @ManyToMany
    @JoinTable(name = "documentos_inspeccion", joinColumns = {
            @JoinColumn(name = "id_documento") }, inverseJoinColumns = { @JoinColumn(name = "id_inspeccion") })
    private List<Inspeccion> inspeccion;
    
}
