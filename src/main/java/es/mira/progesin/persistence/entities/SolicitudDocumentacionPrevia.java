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
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import es.mira.progesin.persistence.entities.gd.Documento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad para la Solicitud de documentación previa basada en un modelo de solicitud y asociada a una inspección. Por
 * cada solicitud podrá haber documentación previa que el interlocutor de una unidad deberá subir, por medio de un
 * usuario provisional.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.Inspeccion
 * @see es.mira.progesin.persistence.entities.DocumentacionPrevia
 * @see es.mira.progesin.web.beans.SolicitudDocPreviaBean
 * @see es.mira.progesin.web.beans.ProvisionalSolicitudBean
 */

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "SOLICITUD_DOC_PREVIA")
@NamedEntityGraph(name = "SolicitudDocumentacionPrevia.documentos", attributeNodes = @NamedAttributeNode("documentos"))
public class SolicitudDocumentacionPrevia implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @SequenceGenerator(name = "seq_soldocprevia", sequenceName = "seq_soldocprevia", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_soldocprevia")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Asunto.
     */
    @Column(name = "asunto")
    private String asunto;
    
    /**
     * Inspección asociada.
     */
    @ManyToOne
    @JoinColumn(name = "id_inspeccion", foreignKey = @ForeignKey(name = "fk_sdp_inspeccion"), nullable = false)
    private Inspeccion inspeccion;
    
    /**
     * Nombre del destinatario.
     */
    @Column(name = "destinatario")
    private String destinatario;
    
    /**
     * Correo destinatario.
     */
    @Column(name = "correoDestinatario")
    private String correoDestinatario;
    
    /**
     * Fecha límite de envío.
     */
    @Column(name = "fechaLimiteEnvio")
    private Date fechaLimiteEnvio;
    
    /**
     * Fecha límite de cumplimentación.
     */
    @Column(name = "fechaLimiteCumplimentar")
    private Date fechaLimiteCumplimentar;
    
    /**
     * Atributo que sirve para la elección de descargar o no las plantillas.
     */
    @Column(name = "descargaPlantillas")
    private String descargaPlantillas;
    
    /**
     * Puesto del usuario de apoyo.
     */
    @Column(name = "apoyoPuesto")
    private String apoyoPuesto;
    
    /**
     * Nombre del usuario de apoyo.
     */
    @Column(name = "apoyoNombre")
    private String apoyoNombre;
    
    /**
     * Teléfono del usuario de apoyo.
     */
    @Column(name = "apoyoTelefono")
    private String apoyoTelefono;
    
    /**
     * Corero del usuario de apoyo.
     */
    @Column(name = "apoyoCorreo")
    private String apoyoCorreo;
    
    /**
     * Fecha de alta de la solicitud.
     */
    @CreatedDate
    @Column(name = "fechaAlta")
    private Date fechaAlta;
    
    /**
     * Usuario de alta de la solicitud.
     */
    @CreatedBy
    @Column(name = "username_alta", nullable = false)
    private String usernameAlta;
    
    /**
     * Fecha de validación por parte de apoyo.
     */
    @Column(name = "fechaValidApoyo")
    private Date fechaValidApoyo;
    
    /**
     * Login que valida la solicitud perteneciente al grupo de apoyo.
     */
    @Column(name = "usernameValidApoyo")
    private String usernameValidApoyo;
    
    /**
     * Fecha de validación por parte del jefe de equipo.
     */
    @Column(name = "fechaValidJefeEquipo")
    private Date fechaValidJefeEquipo;
    
    /**
     * Login del jefe de equipo que valida la solicitud.
     */
    @Column(name = "usernameValidJefeEquipo")
    private String usernameValidJefeEquipo;
    
    /**
     * Fecha de envío de la solicitud.
     */
    @Column(name = "fecha_envio")
    private Date fechaEnvio;
    
    /**
     * Login del usuario que envía la solicitud.
     */
    @Column(name = "usernameEnvio")
    private String usernameEnvio;
    
    /**
     * Nombre completo de la persona que recibe la solicitud.
     */
    @Column(name = "nombreCompletoInterlocutor")
    private String nombreCompletoInterlocutor;
    
    /**
     * Categoría de la persona que recibe la solicitud.
     */
    @Column(name = "categoriaInterlocutor")
    private String categoriaInterlocutor;
    
    /**
     * Cargo de la persona que recibe la solicitud.
     */
    @Column(name = "cargoInterlocutor")
    private String cargoInterlocutor;
    
    /**
     * Correo corporativo de la persona que recibe la solicitud.
     */
    @Column(name = "correoCorporativoInter")
    private String correoCorporativoInterlocutor;
    
    /**
     * Correo corporativo complementario de la persona que recibe la solicitud.
     */
    @Column(name = "correoCorporativoInterCompl")
    private String correoCorporativoInterlocutorCompl;
    
    /**
     * Teléfono de la persona que recibe la solicitud.
     */
    @Column(name = "telefonoInterlocutor")
    private String telefonoInterlocutor;
    
    /**
     * Fecha de finalización de la solicitud.
     */
    @Column(name = "fechaFinalizacion")
    private Date fechaFinalizacion;
    
    /**
     * Usuario de finalización de la solicitud cuando la documentación está aprobada.
     */
    @Column(name = "usuarioFinalizacion")
    private String usuarioFinalizacion;
    
    /**
     * Fecha de cumplimentación de la solicitud cuando la documentación está aprobada.
     */
    @Column(name = "fechaCumplimentacion")
    private Date fechaCumplimentacion;
    
    /**
     * Fecha de no conforme con la solicitud.
     */
    @Column(name = "fechaNoConforme")
    private Date fechaNoConforme;
    
    /**
     * Usuario de no conforme con la solicitud.
     */
    @Column(name = "usuarioNoConforme")
    private String usuarioNoConforme;
    
    /**
     * Fecha de baja de la solicitud.
     */
    @Column(name = "fechaBaja")
    private Date fechaBaja;
    
    /**
     * Usuario de baja de la solicitud.
     */
    @Column(name = "usernameBaja")
    private String usernameBaja;
    
    /**
     * Lista con los documentos asociados a la solicitud.
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "solicitud_previa_docs", joinColumns = @JoinColumn(name = "id_solicitud_previa"), inverseJoinColumns = @JoinColumn(name = "id_documento"))
    private List<Documento> documentos;
}
