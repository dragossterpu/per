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
 * Entidad Documento. Cualquier archivo subido a la aplicación se almacena en esta tabla.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.ModeloSolicitud
 * @see es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion
 * @see es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario
 * @see es.mira.progesin.web.beans.SolicitudDocPreviaBean
 * @see es.mira.progesin.web.beans.ProvisionalSolicitudBean
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "documentos", schema = "public")
public class Documento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "documentos_id_seq", sequenceName = "documentos_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentos_id_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	// no vale con postgres
	// @Column(name = "fichero")
	// @Lob
	// private Blob fichero;

	@Column(name = "fichero", nullable = false)
	private byte[] fichero;

	@Column(name = "tipoContenido", nullable = false)
	private String tipoContenido;

	@Column(name = "nombre")
	private String nombre;
}
